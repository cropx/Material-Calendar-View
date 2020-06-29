package com.applandeo.materialcalendarview.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.applandeo.materialcalendarview.utils.DayColorsUtils;
import com.applandeo.materialcalendarview.utils.EventDayUtils;
import com.applandeo.materialcalendarview.utils.ImageUtils;
import com.applandeo.materialcalendarview.utils.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * This class is responsible for loading a one day cell.
 * <p>
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */

class CalendarDayAdapter extends ArrayAdapter<Date> {
    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private int mPageMonth;
   // private Calendar mToday;

    private CalendarProperties mCalendarProperties;

    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context, CalendarProperties calendarProperties,
                       ArrayList<Date> dates, int pageMonth) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent, false);
        }

        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);
        ImageView dayIcon = (ImageView) view.findViewById(R.id.dayIcon);
        ImageView dayIcon2 = (ImageView) view.findViewById(R.id.dayIcon2);
        TextView value = (TextView) view.findViewById(R.id.valueLabel);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.dayContainer);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));
        boolean isToday = DateUtils.isToday(day);

        //here we set the internal icons of the day
        if (mCalendarProperties.getEventDays() != null && mCalendarProperties.getEventsEnabled()) {

            Stream.of(mCalendarProperties.getEventDays()).filter(eventDate ->
                    eventDate.getCalendar().equals(day)).findFirst().executeIfPresent(eventDay -> {

                if (eventDay.getImageDrawable() != null) {
                    loadIcon(dayIcon, day, eventDay.getImageDrawable());
                }

                if (eventDay.getImageBottomDrawable() != null) {
                    loadIcon(dayIcon2, day, eventDay.getImageBottomDrawable());
                }

                if (eventDay.getValue() != null) {
                    value.setText(eventDay.getValue());
                }
            });

        }

        //here we set the background of
        if(isToday){
            container.setBackgroundColor(mCalendarProperties.getTodayBackgroundColor());
        }
        setLabelColors(dayLabel, day,isToday);

        dayLabel.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        return view;
    }

    private void setLabelColors(TextView dayLabel, Calendar day, boolean isToday) {
        // Setting not current month day color
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Setting view for all SelectedDays
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            return;
        }

        // Setting disabled days color
        if (!isActiveDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Setting custom label color for event day
        if (isEventDayWithLabelColor(day)) {
            DayColorsUtils.setCurrentMonthDayColors(day, isToday, dayLabel, mCalendarProperties);
            return;
        }

        // Setting current month day color
        DayColorsUtils.setCurrentMonthDayColors(day, isToday, dayLabel, mCalendarProperties);
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isEventDayWithLabelColor(Calendar day) {
        return EventDayUtils.isEventDayWithLabelColor(day, mCalendarProperties);
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth &&
                !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                        || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private void loadIcon(ImageView dayIcon, Calendar day,Object myDrawable) {
        ImageUtils.loadImage(dayIcon, myDrawable);

        // If a day doesn't belong to current month then image is transparent
        if (!isCurrentMonthDay(day) || !isActiveDay(day)) {
            dayIcon.setAlpha(0.12f);
        }
    }
}

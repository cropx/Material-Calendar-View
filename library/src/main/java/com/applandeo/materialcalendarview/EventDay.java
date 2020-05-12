package com.applandeo.materialcalendarview;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;

import androidx.annotation.DrawableRes;
import androidx.annotation.RestrictTo;

import com.applandeo.materialcalendarview.utils.DateUtils;

import java.util.Calendar;

/**
 * This class represents an event of a day. An instance of this class is returned when user click
 * a day cell. This class can be overridden to make calendar more functional. A list of instances of
 * this class can be passed to CalendarView object using setEvents() method.
 * <p>
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */

public class EventDay {
    private Calendar mDay;
    private Object mDrawable;
    private Object mBottomDrawable;
    private int mLabelColor;
    private long id;
    private SpannableString value;
    private boolean mIsDisabled;

    /**
     * @param day Calendar object which represents a date of the event
     */
    public EventDay(Calendar day,Long id) {
        this.id = id;
        mDay = day;
    }

    /**
     * @param day      Calendar object which represents a date of the event
     * @param drawable Drawable resource which will be displayed in a day cell
     */
    public EventDay(Calendar day, @DrawableRes int drawable,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDrawable = drawable;
        mDay = day;

    }

    /**
     * @param day      Calendar object which represents a date of the event
     * @param drawable Drawable which will be displayed in a day cell
     */
    public EventDay(Calendar day, Drawable drawable,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
    }

    public EventDay(Calendar day, Drawable drawable,Drawable bottomdrawable,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
        mBottomDrawable=bottomdrawable;
    }

    public EventDay(Calendar day, Drawable drawable,SpannableString valueText,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
        value=valueText;
    }


    /**
     * @param day        Calendar object which represents a date of the event
     * @param drawable   Drawable resource which will be displayed in a day cell
     * @param labelColor Color which will be displayed as label text color a day cell
     */
    public EventDay(Calendar day, @DrawableRes int drawable , int labelColor,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
        mLabelColor = labelColor;
    }


    /**
     * @param day        Calendar object which represents a date of the event
     * @param drawable   Drawable which will be displayed in a day cell
     * @param labelColor Color which will be displayed as label text color a day cell
     */
    public EventDay(Calendar day, Drawable drawable , int labelColor,Long id) {
        this.id = id;
        DateUtils.setMidnight(day);
        mDay = day;
        mDrawable = drawable;
        mLabelColor = labelColor;
    }

    public long getId() {
        return id;
    }

    public SpannableString getValue() {
        return value;
    }

    /**
     * @return An image resource which will be displayed in the day row
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Object getImageDrawable() {
        return mDrawable;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Object getImageBottomDrawable() {
        return mBottomDrawable;
    }


    /**
     * @return Color which will be displayed as row label text color
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public int getLabelColor() {
        return mLabelColor;
    }


    /**
     * @return Calendar object which represents a date of current event
     */
    public Calendar getCalendar() {
        return mDay;
    }


    /**
     * @return Boolean value if day is not disabled
     */
    public boolean isEnabled() {
        return !mIsDisabled;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setEnabled(boolean enabled) {
        mIsDisabled = enabled;
    }
}

package com.project.base.ui.widget.timedialog.data.source;

import com.project.base.ui.widget.timedialog.config.PickerConfig;
import com.project.base.ui.widget.timedialog.data.WheelCalendar;
import com.project.base.ui.widget.timedialog.utils.PickerConstants;
import com.project.base.ui.widget.timedialog.utils.Utils;

import java.util.Calendar;


/**
 * Created by sbl on 16/6/13.
 */
public class TimeRepository implements TimeDataSource {
    private PickerConfig mPickerConfig;
    private WheelCalendar mCalendarMin;
    private WheelCalendar mCalendarMax;

    private boolean mIsMinNoRange;
    private boolean mIsMaxNoRange;

    public TimeRepository(PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
        mCalendarMin = pickerConfig.mMinCalendar;
        mCalendarMax = pickerConfig.mMaxCalendar;

        mIsMinNoRange = mCalendarMin.isNoRange();
        mIsMaxNoRange = mCalendarMax.isNoRange();
    }

    @Override
    public int getMinYear() {
        if (mIsMinNoRange)
            return PickerConstants.DEFAULT_MIN_YEAR;
        else
            return mCalendarMin.year;
    }

    @Override
    public int getMaxYear() {
        if (mIsMaxNoRange)
            return getMinYear() + PickerConstants.YEAR_COUNT;

        return mCalendarMax.year;
    }

    @Override
    public int getMinMonth(int year) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year))
            return mCalendarMin.month;

        return PickerConstants.MIN_MONTH;
    }

    @Override
    public int getMaxMonth(int year) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year))
            return mCalendarMax.month;

        return PickerConstants.MAX_MONTH;
    }

    @Override
    public int getMinDay(int year, int month) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month))
            return mCalendarMin.day;

        return PickerConstants.MIN_DAY;
    }

    @Override
    public int getMaxDay(int year, int month) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month))
            return mCalendarMax.day;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month - 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMinHour(int year, int month, int day) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month, day))
            return mCalendarMin.hour;
        else
            return PickerConstants.MIN_HOUR;
    }

    @Override
    public int getMaxHour(int year, int month, int day) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month, day))
            return mCalendarMax.hour;

        return PickerConstants.MAX_HOUR;
    }

    @Override
    public int getMinMinute(int year, int month, int day, int hour) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month, day, hour))
            return mCalendarMin.minute;
        else
            return PickerConstants.MIN_MINUTE;
    }

    @Override
    public int getMaxMinute(int year, int month, int day, int hour) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month, day, hour))
            return mCalendarMax.minute;

        return PickerConstants.MAX_MINUTE;
    }

    @Override
    public boolean isMinYear(int year) {
        return Utils.isTimeEquals(mCalendarMin, year);
    }

    @Override
    public boolean isMinMonth(int year, int month) {
        return Utils.isTimeEquals(mCalendarMin, year, month);
    }

    @Override
    public boolean isMinDay(int year, int month, int day) {
        return Utils.isTimeEquals(mCalendarMin, year, month, day);
    }

    @Override
    public boolean isMinHour(int year, int month, int day, int hour) {
        return Utils.isTimeEquals(mCalendarMin, year, month, day, hour);
    }


    @Override
    public WheelCalendar getDefaultCalendar() {
        return mPickerConfig.mCurrentCalendar;
    }
}

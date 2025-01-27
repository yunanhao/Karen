package com.project.base.ui.widget.timedialog.config;

import com.project.base.ui.widget.timedialog.data.WheelCalendar;
import com.project.base.ui.widget.timedialog.listener.OnCalendarSetListener;
import com.project.base.ui.widget.timedialog.listener.OnDataSetListener;

/**
 * Created by sbl on 16/6/13.
 */
public class PickerConfig {
    public int mThemeColor = DefaultConfig.COLOR;

    public String mCancelString = DefaultConfig.CANCEL;
    public String mSureString = DefaultConfig.SURE;
    public String mTitleString = DefaultConfig.TITLE;

    public int mWheelTVNormalColor = DefaultConfig.TV_NORMAL_COLOR;
    public int mWheelTVSelectorColor = DefaultConfig.TV_SELECTOR_COLOR;
    public int mWheelTVSize = DefaultConfig.TV_SIZE;
    public boolean cyclic = DefaultConfig.CYCLIC;

    public String mYear = DefaultConfig.YEAR;
    public String mMonth = DefaultConfig.MONTH;
    public String mDay = DefaultConfig.DAY;
    public String mHour = DefaultConfig.HOUR;
    public String mMinute = DefaultConfig.MINUTE;

    /**
     * The min timeMillseconds
     */
    public WheelCalendar mMinCalendar = new WheelCalendar(0);

    /**
     * The max timeMillseconds
     */
    public WheelCalendar mMaxCalendar = new WheelCalendar(0);

    /**
     * The default selector timeMillseconds
     */
    public WheelCalendar mCurrentCalendar = new WheelCalendar(System.currentTimeMillis());


    /**
     * 时间返回弹框
     */
    public OnCalendarSetListener onCalendarSetListener;

    /**
     * 数据返回弹框
     */
    public OnDataSetListener onDataSetListener;
}

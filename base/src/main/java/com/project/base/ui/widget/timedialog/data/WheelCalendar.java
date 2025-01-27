package com.project.base.ui.widget.timedialog.data;

import java.util.Calendar;

/**
 * Created by sbl on 16/6/13.
 */
public class WheelCalendar {

    public int year, month, day, hour, minute;

    private boolean noRange;

    private long millseconds;

    public WheelCalendar(long millseconds) {
        this.millseconds = millseconds;
        initData(millseconds);
    }

    public long getMillseconds(){
        return millseconds;
    }

    private void initData(long millseconds) {
        if (millseconds == 0) {
            noRange = true;
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millseconds);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public boolean isNoRange() {
        return noRange;
    }


}

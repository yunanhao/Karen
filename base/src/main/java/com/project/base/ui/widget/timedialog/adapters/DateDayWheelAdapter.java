package com.project.base.ui.widget.timedialog.adapters;

import android.content.Context;

import com.project.base.ui.widget.timedialog.wheel.DateBean;

import java.util.ArrayList;


public class DateDayWheelAdapter extends AbstractWheelTextAdapter {

    private ArrayList<DateBean> items;

    public DateDayWheelAdapter(Context context,
                               ArrayList<DateBean> items) {
        super(context);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            DateBean db = items.get(index);
            return db.getMonth() + "月" + db.getDay() + "日 " + db.getOther();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}

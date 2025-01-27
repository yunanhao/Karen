package com.project.base.ui.widget.timedialog.adapters;

import android.content.Context;

import java.util.ArrayList;


public class DateHourMinuteWheelAdapter extends AbstractWheelTextAdapter {

    private ArrayList<Integer> items;

    private String type;

    public DateHourMinuteWheelAdapter(Context context, ArrayList<Integer> items, String type) {
        super(context);
        this.type = type;
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            return String.format("%02d", items.get(index)) + type;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}

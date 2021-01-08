package com.xinger.mita.rvcalendar.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.utils.CalendarDateType;
import com.xinger.mita.rvcalendar.utils.CalendarProxy;

import org.w3c.dom.Text;

public class CalendarView extends FrameLayout {

    public View rootView;
    public TextView calendarTitle;
    public CalendarProxy calendarProxy;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, -1);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        calendarProxy = new CalendarProxy();
        rootView = LayoutInflater.from(context).inflate(R.layout.calendar_root, this, true);
        calendarTitle = rootView.findViewById(R.id.calendarTitle);
        calendarTitle.setText(calendarProxy.getDateFormat(CalendarDateType.DATE_Y_M));
    }
}

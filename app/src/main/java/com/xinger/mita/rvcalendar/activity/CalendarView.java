package com.xinger.mita.rvcalendar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.adapter.DateAdapter;
import com.xinger.mita.rvcalendar.adapter.DayAdapter;
import com.xinger.mita.rvcalendar.adapter.WeekAdapter;
import com.xinger.mita.rvcalendar.entity.DayInfo;
import com.xinger.mita.rvcalendar.utils.CalendarDateType;
import com.xinger.mita.rvcalendar.utils.CalendarProxy;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.support.constraint.motion.utils.Oscillator.TAG;

public class CalendarView extends LinearLayout implements View.OnClickListener {


    public static final String TAG = "CalendarView";

    public View rootView;
    public TextView calendarTitle;
    public CalendarProxy calendarProxy;
    public RecyclerView calendarWeekRv, calendarDaysRv;
    public ImageView reduceMonth, addMonth, controlIv;
    private int currentYear, currentMonth, currentDay;
    private DayAdapter dayAdapter;
    private String startTimeData="111";
    private String endTimeData="111";
    private boolean openStatus = true;
    private List<DayInfo> dayInfos1;
    //定义一个接口对象listerner
    public OnIndicatorChangeListener onIndicatorChangeListener;
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
        calendarWeekRv = rootView.findViewById(R.id.calendarWeek_rv);
        calendarDaysRv = rootView.findViewById(R.id.calendarDays_rv);
        reduceMonth = rootView.findViewById(R.id.iv_reduce_month);
        addMonth = rootView.findViewById(R.id.iv_add_month);
        controlIv = rootView.findViewById(R.id.iv_open);
        addMonth.setOnClickListener(this);
        controlIv.setOnClickListener(this);
        reduceMonth.setOnClickListener(this);
        initBaseData();
        initBaseView();
    }

    private void initBaseData() {
        currentYear = calendarProxy.getCalendarYear();
        currentMonth = calendarProxy.getCalendarMonth();
        currentDay = calendarProxy.getCalendarDay();
        Log.e(TAG, "currentYear : " + currentYear + "currentMonth : " + currentMonth + "currentDay : " + currentDay);
    }

    @SuppressLint("DefaultLocale")
    private void initBaseView() {
        calendarTitle.setText(String.format("%d年%d月", currentYear, currentMonth));
        calendarWeekRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
        calendarDaysRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
        WeekAdapter weekAdapter = new WeekAdapter(getContext());
        dayAdapter = new DayAdapter(getContext(), queryDays4YearMonth());
        calendarWeekRv.setAdapter(weekAdapter);
        calendarDaysRv.setAdapter(dayAdapter);
    }

    public void addMonth() {
        if (currentMonth < 12) {
            currentMonth += 1;
        } else {
            currentYear += 1;
            currentMonth = 1;
        }
        controlDays();
    }

    public void editMonth() {
        if (currentMonth > 1) {
            currentMonth -= 1;
        } else {
            currentYear -= 1;
            currentMonth = 12;
        }
        controlDays();
    }

    public void controlDays() {
        List<DayInfo> dayInfos = queryDays4YearMonth();
        if (openStatus) {
            open();
        } else {
            close(dayInfos);
        }
    }

    public void open() {
        dayAdapter.upDateList(queryDays4YearMonth());
    }

    public void close(List<DayInfo> dayInfos) {
        if (currentMonth == calendarProxy.getCalendarMonth()) {
            int i = currentDay / 7;
            dayInfos1 = dayInfos.subList(i * 7, i * 7 + 7);
            dayAdapter.upDateList(dayInfos1);
        } else {
            dayInfos1 = dayInfos.subList(0, 7);
            dayAdapter.upDateList(dayInfos1);
        }
        for (int i=0;i<dayInfos1.size();i++){
            Log.e("缩放",dayInfos1.get(i).getDay()+"");
            if(dayInfos1.get(i).getDay()>0){
                startTimeData=timeToStamp(currentYear+"-"+currentMonth+"-"+dayInfos1.get(i).getDay());
                return;
            }
        }
        endTimeData=timeToStamp(currentYear+"-"+currentMonth+"-"+dayInfos1.get(dayInfos1.size()-1).getDay());
    }

    public List<DayInfo> queryDays4YearMonth() {
        Log.e(TAG, "currentYear : " + currentYear + "currentMonth : " + currentMonth);
        calendarTitle.setText(String.format("%d年%d月", currentYear, currentMonth));
        List<DayInfo> day4YearMonth = calendarProxy.getDay4YearMonth(currentYear, currentMonth, false);
        Log.e(TAG, "day4YearMonth" + day4YearMonth.size());
        for (int i=0;i<day4YearMonth.size();i++){
            if(day4YearMonth.get(i).getDay()>0){
                day4YearMonth.get(i).setTimeData(timeToStamp(currentYear+"-"+currentMonth+"-"+day4YearMonth.get(i).getDay()));
                Log.e(TAG, "设置时间戳" +currentYear+"-"+currentMonth+"-"+day4YearMonth.get(i).getDay()+"================"+ day4YearMonth.get(i).getTimeData());
            }
        }
        startTimeData=timeToStamp(currentYear+"-"+currentMonth+"-"+1);
        endTimeData=timeToStamp(currentYear+"-"+currentMonth+"-"+day4YearMonth.get(day4YearMonth.size()-1).getDay());
        return day4YearMonth;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_month:
                //    addMonth();
                if (getOnIndicatorChangeListener() != null) {
                    getOnIndicatorChangeListener().onIndicatorChange(0);
                }
                break;
            case R.id.iv_reduce_month:
                editMonth();
                break;
            case R.id.iv_open:
                openStatus = !openStatus;
                controlDays();
                break;
        }
    }

    /* //日期转换为时间戳 */
    public String timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp+"";
    }


    public interface OnIndicatorChangeListener {
        void onIndicatorChange(int position);
    }

    public OnIndicatorChangeListener getOnIndicatorChangeListener() {
        return onIndicatorChangeListener;
    }

    public void setOnIndicatorChangeListener(OnIndicatorChangeListener onIndicatorChangeListener) {
        this.onIndicatorChangeListener = onIndicatorChangeListener;
    }
}

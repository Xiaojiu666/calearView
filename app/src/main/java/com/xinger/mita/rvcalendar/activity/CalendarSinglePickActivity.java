package com.xinger.mita.rvcalendar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.adapter.DateAdapter;
import com.xinger.mita.rvcalendar.adapter.MonthAdapter;
import com.xinger.mita.rvcalendar.entity.DateEntity;
import com.xinger.mita.rvcalendar.entity.MonthEntity;
import com.xinger.mita.rvcalendar.utils.Lunar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author MiTa
 * @date 2017/12/21.
 */
public class CalendarSinglePickActivity extends AppCompatActivity {

    private final int CALENDAR_TODAY = 77;

    private RecyclerView mRvCalendar;

    private List<MonthEntity> monthList = new ArrayList<>();

    private int year, month, day;
    private int nowDay;
    private int lastDateSelect = -1, lastMonthSelect = -1;
    private TextView tv_cal_title;
    private ImageView iv_left;
    private ImageView iv_right;
    private List<DateEntity> list = new ArrayList<>();
    private int position = 0;
    private DateAdapter adapter;
    private ImageView iv_more;
    private int nowmonth;
    private List<DateEntity> sublist;
    private boolean isShowMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_calendar);
        super.onCreate(savedInstanceState);
        getViews();
        initData();
        initCalendarRv();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        nowDay = day;
        nowmonth = month;
        calendar.set(year, month, 1);

        for (int i = 0; i < 12; i++) {
            List<DateEntity> deList = new ArrayList<>();
            MonthEntity monthEntity = new MonthEntity();
            int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int empty = calendar.get(Calendar.DAY_OF_WEEK);
            empty = empty == 1 ? 0 : empty - 1;
            for (int j = 0; j < empty; j++) {
                DateEntity de = new DateEntity();
                de.setType(1);
                deList.add(de);
            }
            for (int j = 1; j <= maxDayOfMonth; j++) {
                DateEntity de = new DateEntity();
                if (i == 0) {
                    de.setType(j < nowDay ? 4 : 0);
                } else {
                    de.setType(0);
                }
                de.setDate(j);
                de.setParentPos(i);
                de.setDesc(Lunar.getLunarDate(year, month + 1, j));
                deList.add(de);
            }
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            monthEntity.setTitle(year + "年" + month + "月");
            monthEntity.setYear(year);
            monthEntity.setList(deList);

            monthList.add(monthEntity);
            calendar.add(Calendar.MONTH, 1);

        }
        tv_cal_title.setText(monthList.get(position).getTitle());
    }

    private void initCalendarRv() {
        GridLayoutManager glm = new GridLayoutManager(this, 7) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        glm.setAutoMeasureEnabled(true);
        list.addAll(monthList.get(position).getList());
        adapter = new DateAdapter(this, list);
        mRvCalendar.setAdapter(adapter);
        mRvCalendar.setLayoutManager(glm);
    }

    private void getViews() {
        mRvCalendar = findViewById(R.id.rv_calendar);
        tv_cal_title = findViewById(R.id.tv_cal_title);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_more = findViewById(R.id.iv_more);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    tv_cal_title.setText(monthList.get(position).getTitle());
                    showAllOrSeven();
                    // adapter.notifyDataSetChanged();
                }
            }
        });
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < monthList.size() - 1) {
                    position++;
                    tv_cal_title.setText(monthList.get(position).getTitle());
                    showAllOrSeven();
                }

            }
        });
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllOrSeven();
                isShowMore = !isShowMore;
            }
        });
    }

    private void showAllOrSeven() {
        if (isShowMore) {
            list.clear();
            list.addAll(monthList.get(position).getList());
        } else {
            list.clear();
            list.addAll(monthList.get(position).getList().subList(0, 7));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        return simpleDateFormat.format(cal.getTime()) + "000000000";
    }

    /**
     * end
     * 本周结束时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        return simpleDateFormat.format(cal.getTime()) + "235959999";
    }

}

package com.xinger.mita.rvcalendar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.entity.DayInfo;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.WeekViewHolder> {

    private Context context;
    private List<DayInfo> list;

    public DayAdapter(Context context, List<DayInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_date, viewGroup, false);
        return new WeekViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        DayInfo de = list.get(position);
        if (de.getDay() <= 0) {
            holder.mTvDate.setText("");
        } else {
            holder.mTvDate.setText(de.getDay() + "");
        }
    }

    public void upDateList(List<DayInfo> newList) {
        if (list == null) {
            return;
        }
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class WeekViewHolder extends RecyclerView.ViewHolder {

        WeekViewHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mLlDate = (LinearLayout) itemView.findViewById(R.id.ll_date);
        }

        TextView mTvDate;
        LinearLayout mLlDate;
    }
}

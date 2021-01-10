package com.xinger.mita.rvcalendar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.entity.DateEntity;

import java.util.ArrayList;
import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekViewHolder> {

    private Context context;
    private List<String> list;

    public WeekAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        list.add("日");
        list.add("一");
        list.add("二");
        list.add("三");
        list.add("四");
        list.add("五");
        list.add("六");
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_date, viewGroup, false);
        return new WeekViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {
        String de = list.get(position);
       holder.mTvDate.setText(de);
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

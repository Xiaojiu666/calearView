package com.xinger.mita.rvcalendar.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinger.mita.rvcalendar.R;
import com.xinger.mita.rvcalendar.entity.DateEntity;

import java.util.List;

/**
 * @author MiTa
 * @date 2017/11/20.
 */
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private Context context;
    private List<DateEntity> list;

    public DateAdapter(Context context, List<DateEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        DateEntity de = list.get(position);

        int date = de.getDate();
        int type = de.getType();

        if (type == 1) {//留白
            holder.mTvDate.setText("");
            holder.itemView.setClickable(false);
        } else if (type == 0) {
            //日常
            holder.mTvDate.setText(String.valueOf(de.getDate()));
            holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.black_2c));
//            if(de.getDate()>25){
//                holder.mLlDate.setBackgroundResource(R.mipmap.icon_clock);
//                holder.mTvDate.setTextColor(ContextCompat.getColor(context, R.color.color_FFB525));
//            }
        } else if (type == 4) {//今天之前的日期
            holder.itemView.setClickable(false);
            holder.mTvDate.setText(String.valueOf(de.getDate()));
//            holder.mTvDate.setText("");
            holder.mLlDate.setBackgroundResource(R.mipmap.icon_gift);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {

        DateViewHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mLlDate = (LinearLayout) itemView.findViewById(R.id.ll_date);
        }

        TextView mTvDate;
        LinearLayout mLlDate;
    }

}

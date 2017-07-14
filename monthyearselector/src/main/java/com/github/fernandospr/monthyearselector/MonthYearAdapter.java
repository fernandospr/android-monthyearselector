package com.github.fernandospr.monthyearselector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MonthYearAdapter extends RecyclerView.Adapter<MonthYearAdapter.ViewHolder> {

    private final List<Integer> mItems;
    private Listener mListener;

    public MonthYearAdapter(List<Integer> items) {
        this.mItems = items;
    }

    @Override
    public MonthYearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.monthyear_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonthYearAdapter.ViewHolder holder, int position) {
        Integer item = mItems.get(position);
        holder.mTitle.setText(String.valueOf(item));
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;

        ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface Listener {
        void onItemClick(int position);
    }
}
package com.github.fernandospr.monthyearselector;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MonthYearAdapter extends RecyclerView.Adapter<MonthYearAdapter.ViewHolder> {

    private final List<Integer> mItems;
    private Listener mListener;
    private int mSelectedPosition = -1;

    public MonthYearAdapter(List<Integer> items, int selectedPosition) {
        this.mItems = items;
        this.mSelectedPosition = selectedPosition;
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
        holder.mTitle.setText(String.format(Locale.getDefault(), "%02d", item));
        holder.mTitle.setBackgroundColor(mSelectedPosition == position ? ContextCompat.getColor(holder.mTitle.getContext(), R.color.monthyear_item_selected_color) : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void setSelected(int position) {
        int previouslySelectedPosition = mSelectedPosition;
        mSelectedPosition = position;
        if (previouslySelectedPosition != -1) {
            notifyItemChanged(previouslySelectedPosition);
        }
        notifyItemChanged(mSelectedPosition);
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
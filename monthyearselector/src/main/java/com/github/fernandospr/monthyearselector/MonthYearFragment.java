package com.github.fernandospr.monthyearselector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MonthYearFragment extends Fragment {

    public static final String MONTHS = "months";
    public static final String YEARS = "years";

    private Listener mListener;
    private List<Integer> mMonths;
    private List<Integer> mYears;

    public MonthYearFragment() {
    }

    public static MonthYearFragment newInstance(List<Integer> months, List<Integer> years) {
        MonthYearFragment fragment = new MonthYearFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(MONTHS, new ArrayList<>(months));
        args.putIntegerArrayList(YEARS, new ArrayList<>(years));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMonths = getArguments().getIntegerArrayList(MONTHS);
        mYears = getArguments().getIntegerArrayList(YEARS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener){
            this.mListener = (Listener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.monthyear_view, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView monthList = (RecyclerView) view.findViewById(R.id.rvMonth);
        RecyclerView yearList = (RecyclerView) view.findViewById(R.id.rvYear);

        MonthYearAdapter monthsAdapter = new MonthYearAdapter(mMonths);
        MonthYearAdapter yearsAdapter = new MonthYearAdapter(mYears);
        monthList.setAdapter(monthsAdapter);
        yearList.setAdapter(yearsAdapter);
        monthList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        yearList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        monthsAdapter.setListener(new MonthYearAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null) {
                    mListener.onMonthClick(mMonths.get(position));
                }
            }
        });
        yearsAdapter.setListener(new MonthYearAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null) {
                    mListener.onYearClick(mYears.get(position));
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public interface Listener {
        void onMonthClick(int month);
        void onYearClick(int year);
    }
}

package com.github.fernandospr.monthyearselector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MonthYearFragment extends Fragment {

    private static final String MONTHS = "months";
    private static final String YEARS = "years";
    private static final String SELECTED_MONTH = "selectedMonth";
    private static final String SELECTED_YEAR = "selectedYear";

    private Listener mListener;
    private List<Integer> mMonths;
    private List<Integer> mYears;

    private int mSelectedMonth;
    private int mSelectedYear;

    public MonthYearFragment() {
    }

    private static MonthYearFragment newInstance(List<Integer> months, List<Integer> years, int selectedMonth, int selectedYear) {
        MonthYearFragment fragment = new MonthYearFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(MONTHS, new ArrayList<>(months));
        args.putIntegerArrayList(YEARS, new ArrayList<>(years));
        args.putInt(SELECTED_MONTH, selectedMonth);
        args.putInt(SELECTED_YEAR, selectedYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMonths = getArguments().getIntegerArrayList(MONTHS);
        mYears = getArguments().getIntegerArrayList(YEARS);
        mSelectedMonth = getArguments().getInt(SELECTED_MONTH, 0);
        mSelectedYear = getArguments().getInt(SELECTED_YEAR, 0);
        if (savedInstanceState != null) {
            mSelectedMonth = savedInstanceState.getInt(SELECTED_MONTH);
            mSelectedYear = savedInstanceState.getInt(SELECTED_YEAR);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_MONTH, mSelectedMonth);
        outState.putInt(SELECTED_YEAR, mSelectedYear);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.monthyear_view, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView monthList = (RecyclerView) view.findViewById(R.id.rvMonth);
        RecyclerView yearList = (RecyclerView) view.findViewById(R.id.rvYear);

        final MonthYearAdapter monthsAdapter = new MonthYearAdapter(mMonths, getSelectedMonthPosition());
        final MonthYearAdapter yearsAdapter = new MonthYearAdapter(mYears, getSelectedYearPosition());
        monthList.setAdapter(monthsAdapter);
        yearList.setAdapter(yearsAdapter);
        monthList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        yearList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        monthsAdapter.setListener(new MonthYearAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                mSelectedMonth = mMonths.get(position);
                if (mListener != null) {
                    mListener.onMonthClick(mSelectedMonth);
                }
                monthsAdapter.setSelected(position);
            }
        });
        yearsAdapter.setListener(new MonthYearAdapter.Listener() {
            @Override
            public void onItemClick(int position) {
                mSelectedYear = mYears.get(position);
                if (mListener != null) {
                    mListener.onYearClick(mSelectedYear);
                }
                yearsAdapter.setSelected(position);
            }
        });
    }

    private int getSelectedYearPosition() {
        return getPositionForValue(mYears, mSelectedYear);
    }

    private int getSelectedMonthPosition() {
        return getPositionForValue(mMonths, mSelectedMonth);
    }

    private int getPositionForValue(List<Integer> values, int value) {
        if (value == 0) {
            return -1;
        }
        for (int i = 0; i < values.size(); i++) {
            int item = values.get(i);
            if (item == value) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public int getSelectedYear() {
        return mSelectedYear;
    }

    public interface Listener {
        void onMonthClick(int month);

        void onYearClick(int year);
    }

    public static class Builder {

        private final List<Integer> mMonths;
        private final List<Integer> mYears;
        private int mSelectedMonth = 0;
        private int mSelectedYear = 0;

        public Builder(List<Integer> months, List<Integer> years) {
            this.mMonths = months;
            this.mYears = years;
        }

        public Builder withSelectedMonth(int selectedMonth) {
            this.mSelectedMonth = selectedMonth;
            return this;
        }

        public Builder withSelectedYear(int selectedYear) {
            this.mSelectedYear = selectedYear;
            return this;
        }

        public MonthYearFragment build() {
            return MonthYearFragment.newInstance(mMonths, mYears, mSelectedMonth, mSelectedYear);
        }
    }

    public static void hideFragment(FragmentActivity activity,
                                    Fragment fragment) {
        if (fragment != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public static Fragment showFragment(FragmentActivity activity,
                                        Fragment fragment,
                                        int containerViewId,
                                        boolean addToBackStack,
                                        int enter,
                                        int exit) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.replace(containerViewId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
        return fragment;
    }
}

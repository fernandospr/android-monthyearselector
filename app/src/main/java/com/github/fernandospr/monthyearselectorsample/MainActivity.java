package com.github.fernandospr.monthyearselectorsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.github.fernandospr.monthyearselector.MonthYearFragment;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MonthYearFragment.Listener {

    private int mSelectedMonth;
    private int mSelectedYear;
    private TextView mSelectedMonthYearTextView;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectedMonthYearTextView = (TextView) findViewById(R.id.tvMonthYearSelected);

        findViewById(R.id.tvMonthYearSelected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMonthYearSelector();
            }
        });
    }

    private void showMonthYearSelector() {
        MonthYearFragment.removeFragment(this, mFragment);

        int currentYear = getCurrentYear();

        MonthYearFragment.Builder builder = new MonthYearFragment.Builder()
                .withAllMonths()
                .withYears(currentYear, currentYear + 50)
                .withSelectedMonth(mSelectedMonth)
                .withSelectedYear(mSelectedYear);
        mFragment = builder.build();

        MonthYearFragment.showFragment(this,
                mFragment,
                R.id.flMonthYearSelector,
                true,
                R.anim.monthyear_anim_enter,
                R.anim.monthyear_anim_exit);
    }

    private static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public void onMonthClick(int month) {
        mSelectedMonth = month;
        updateSelectedMonthYearTextView();
    }

    @Override
    public void onYearClick(int year) {
        mSelectedYear = year;
        updateSelectedMonthYearTextView();
    }

    private void updateSelectedMonthYearTextView() {
        String value = "";
        if (mSelectedMonth != 0) {
            value += String.format(Locale.getDefault(), "%02d", mSelectedMonth);
        } else {
            value += "MM";
        }
        value += "/";
        if (mSelectedYear != 0) {
            value += String.format(Locale.getDefault(), "%d", mSelectedYear);
        } else {
            value += "YYYY";
        }

        SpannableString spannableValue = new SpannableString(value);
        if (mSelectedMonth != 0) {
            spannableValue.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        if (mSelectedYear != 0) {
            spannableValue.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 3, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        mSelectedMonthYearTextView.setText(spannableValue);
    }
}

package hu.intellicode.savethreelivestoday.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    // Limitations by the law. They control if a person can donate blood or not.
    private static final int DAY_LIMIT_BETWEEN_DONATIONS = 56;
    private static final int DAY_LIMIT_AFTER_PLASMAPHERESIS = 2;
    private static final int DONATION_LIMIT_PER_YEAR_FOR_FEMALES = 4;
    private static final int DONATION_LIMIT_PER_YEAR_FOR_MALES = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_main, contentContainer, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contentContainer.addView(view);


        TextView dayCounterNumber = findViewById(R.id.tv_day_counter_number);
        TextView dayCounterMessage = findViewById(R.id.tv_day_counter_message);
        ImageView dayCounterImage = findViewById(R.id.iv_day_counter_image);
        //TODO get the count of days since the last donation and plasmapheresis from the user's database
        // default value should be 0, if the user has never donated.
        int daysSinceLastDonation = 8;
        int daysSinceLastPlasmapheresis = 100;
        // TODO if the user cannot donate blood, set daysToWait -1. Else do the following line.
        int daysToWait = getDaysToWait(daysSinceLastDonation, daysSinceLastPlasmapheresis);
        // TODO change the iv_day_counter_image based on daysToWait
        if (daysToWait == 0) {
            // The user can donate
            dayCounterMessage.setText(getString(R.string.day_counter_message_positive));
        } else if (daysToWait > 0) {
            // The user has to wait for the donation
            dayCounterMessage.setText(getString(R.string.day_counter_message_waiting, daysToWait));
            dayCounterNumber.setText(String.valueOf(daysToWait));
            dayCounterNumber.setVisibility(View.VISIBLE);
        } else {
            // The user cannot donate
            dayCounterMessage.setText(getString(R.string.day_counter_message_negative));
        }
    }

    /**
     * Calculate the maximum number of days the user has to wait before she/he can donate blood.
     *
     * @param daysSinceLastDonation       The number of days since the user donated blood.
     *                                    If she/he has never donated it before, it's value is 0.
     * @param daysSinceLastPlasmapheresis The number of days since the user donated plasma.
     *                                    If she/he has never donated it before, it's value is 0.
     * @return The maximum number of days the user has to wait based on her/his previous donations.
     */
    private int getDaysToWait(int daysSinceLastDonation, int daysSinceLastPlasmapheresis) {
        List<Integer> daysToWait = new ArrayList<>();
        if (!isBelowLimitPerYear()) {
            daysToWait.add(getDaysTillNextYear());
        }
        if (daysSinceLastDonation != 0 && daysSinceLastDonation <= DAY_LIMIT_BETWEEN_DONATIONS) {
            daysToWait.add(DAY_LIMIT_BETWEEN_DONATIONS - daysSinceLastDonation);
        }
        if (daysSinceLastPlasmapheresis != 0
                && daysSinceLastPlasmapheresis <= DAY_LIMIT_AFTER_PLASMAPHERESIS) {
            daysToWait.add(DAY_LIMIT_AFTER_PLASMAPHERESIS - daysSinceLastPlasmapheresis);
        }
        if (daysToWait.size() == 0) {
            return 0;
        }
        Collections.sort(daysToWait);
        return daysToWait.get(daysToWait.size() - 1);
    }

    // TODO get the donation counts / year from the user's database
    private boolean isBelowLimitPerYear() {
        return true;
    }

    /**
     * I used this method: https://stackoverflow.com/a/24409106
     *
     * @return the days till next year (Jan. 1.) from today.
     */
    private int getDaysTillNextYear() {
        Calendar currentDate = Calendar.getInstance();
        Date currentTime = currentDate.getTime();
        int currentYear = currentDate.get(Calendar.YEAR);
        Date nextYearJanFirst = new GregorianCalendar(currentYear + 1, 0, 1).getTime();
        return Math.round((nextYearJanFirst.getTime() - currentTime.getTime()) / 86400000);
    }
}

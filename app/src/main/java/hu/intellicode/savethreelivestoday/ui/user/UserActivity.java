package hu.intellicode.savethreelivestoday.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.BaseActivity;

public class UserActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView tv_BloodType;
    TextView tv_Age;
    TextView tv_Sex;
    TextView tv_Weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_user, contentContainer, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contentContainer.addView(view);

        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sex = sharedPreferences.getString(getString(R.string.sex), "");
        String bloodType = sharedPreferences.getString(getString(R.string.blood_type), "");

        String age = sharedPreferences.getString(getString(R.string.age), "");
        tv_Age = findViewById(R.id.tv_Age);
        tv_Age.setText("Age: " + age);

        String weight = sharedPreferences.getString(getString(R.string.weight), "");
        tv_Weight = findViewById(R.id.tv_Weight);
        tv_Weight.setText("Weight: " + weight + "KG");

        setSex(sex);
        setBloodType(bloodType);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_set_user_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_set_user) {
            Intent setUserDetailsActivity = new Intent(this, UserDetailsActivity.class);
            startActivity(setUserDetailsActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.weight))) {
            String weight = sharedPreferences.getString(getString(R.string.weight), "");
            tv_Weight = findViewById(R.id.tv_Weight);
            tv_Weight.setText("Weight: " + weight + "KG");
        } else if (key.equals(getString(R.string.age))) {
            String age = sharedPreferences.getString(getString(R.string.age), "");
            tv_Age = findViewById(R.id.tv_Age);
            tv_Age.setText("Age: " + age);
        } else if (key.equals(getString(R.string.sex))) {
            String sex = sharedPreferences.getString(getString(R.string.sex), "");
            tv_Sex = findViewById(R.id.tv_Sex);
            setSex(sex);
        } else if (key.equals(getString(R.string.blood_type))) {
            String bloodType = sharedPreferences.getString(getString(R.string.blood_type), "");
            tv_Weight = findViewById(R.id.tv_Weight);
            setBloodType(bloodType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setSex(String sex) {
        tv_Sex = findViewById(R.id.tv_Sex);
        if (sex.equals(getString(R.string.sex_female_value))) {
            tv_Sex.setText("Sex: Female");
        } else
            tv_Sex.setText("Sex: Male");
    }

    private void setBloodType(String bloodType) {
        tv_BloodType = findViewById(R.id.tv_BloodType);
        String value = "";

        if (bloodType.equals(getString(R.string.blood_type_0_minus_value))) {
            value = getString(R.string.blood_type_0_minus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_a_minus_value))) {
            value = getString(R.string.blood_type_a_minus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_b_minus_value))) {
            value = getString(R.string.blood_type_b_minus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_ab_minus_value))) {
            value = getString(R.string.blood_type_ab_minus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_0_plus_value))) {
            value = getString(R.string.blood_type_0_plus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_a_plus_value))) {
            value = getString(R.string.blood_type_a_plus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_b_plus_value))) {
            value = getString(R.string.blood_type_b_plus_value);
        } else if (bloodType.equals(getString(R.string.blood_type_ab_plus_value))) {
            value = getString(R.string.blood_type_ab_plus_value);
        }

        tv_BloodType.setText("Blood Type: " + value);
    }
}

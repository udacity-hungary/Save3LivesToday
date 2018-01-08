package hu.intellicode.savethreelivestoday.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.info.InfoActivity;
import hu.intellicode.savethreelivestoday.ui.main.MainActivity;
import hu.intellicode.savethreelivestoday.ui.map.MapActivity;
import hu.intellicode.savethreelivestoday.ui.settings.SettingsActivity;
import hu.intellicode.savethreelivestoday.ui.statistics.StatisticsActivity;
import hu.intellicode.savethreelivestoday.ui.user.UserActivity;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout contentContainer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        drawerLayout = findViewById(R.id.drawer_layout);
        contentContainer = findViewById(R.id.content_frame);

        NavigationView navigationDrawer = findViewById(R.id.navigation_drawer);
        navigationDrawer.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_drawer_main: {
                startActivity(new Intent(this, MainActivity.class));
                break;
            }
            case R.id.menu_drawer_map: {
                startActivity(new Intent(this, MapActivity.class));
                break;
            }
            case R.id.menu_drawer_user: {
                startActivity(new Intent(this, UserActivity.class));
                break;
            }
            case R.id.menu_drawer_statistics: {
                startActivity(new Intent(this, StatisticsActivity.class));
                break;
            }
            case R.id.menu_drawer_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            }
            case R.id.menu_drawer_info: {
                startActivity(new Intent(this, InfoActivity.class));
                break;
            }
            default: {
                throw new IllegalStateException("Unknown menu item " + item.getTitle());
            }
        }
        drawerLayout.closeDrawers();
        return true;
    }
}

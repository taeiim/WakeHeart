package com.dsm.wakeheart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.dsm.wakeheart.Fragment.Graph1Fragment;
import com.dsm.wakeheart.Fragment.Graph2Fragment;
import com.dsm.wakeheart.Fragment.HealthFragment;
import com.dsm.wakeheart.Fragment.HelperFragment;
import com.dsm.wakeheart.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
    }

    private void createNavItems() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_graph1, R.drawable.chart1_icon, R.color.colorIcon);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_graph2, R.drawable.chart2_icon, R.color.colorIcon);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_main, R.drawable.button_icon, R.color.colorIcon);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_health, R.drawable.health_icon, R.color.colorIcon);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_helper, R.drawable.helper_icon, R.color.colorIcon);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setCurrentItem(2);  //처음 시작 화면 main


    }


    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            Graph1Fragment graph1Fragment = new Graph1Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, graph1Fragment).commit();
        } else if (position == 1) {
            Graph2Fragment graph2Fragment = new Graph2Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, graph2Fragment).commit();
        } else if (position == 2) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mainFragment).commit();
        } else if (position == 3) {
            HealthFragment healthFragment = new HealthFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, healthFragment).commit();
        } else if (position == 4) {
            HelperFragment helperFragment = new HelperFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, helperFragment).commit();
        }
        return true;
    }
}

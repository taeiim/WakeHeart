package com.dsm.wakeheart;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationView();
    }

    private void setNavigationView() {
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


        bottomNavigation.setCurrentItem(2);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
            }
        });
    }
}

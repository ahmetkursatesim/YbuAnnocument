package com.example.ahmetkrst.homework_2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager vpager;
    PAdapter ppager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab =findViewById(R.id.tab_lay);
        vpager=findViewById(R.id.pager);


        tab.addTab(tab.newTab().setText("Foods").setIcon(R.mipmap.food));
        tab.addTab(tab.newTab().setText("Announcement").setIcon(R.mipmap.announ));
        tab.addTab(tab.newTab().setText("News").setIcon(R.mipmap.newss));

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{android.R.attr.state_enabled},

        };

       int[] colorRes = new int[]{
                Color.BLUE,
                Color.RED,


        };

        ColorStateList myList = new ColorStateList(states, colorRes);
        tab.setTabTextColors(myList);
        tab.setTabGravity(tab.GRAVITY_FILL);
        ppager=new PAdapter(getSupportFragmentManager(),tab.getTabCount());
        vpager.setAdapter(ppager);
        vpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }
}

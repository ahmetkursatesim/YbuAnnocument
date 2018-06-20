package com.example.ahmetkrst.homework_2;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

    public class PAdapter extends FragmentStatePagerAdapter {
        int Tabslength;

        public PAdapter(FragmentManager FragM, int Tabslength) {
            super(FragM);
            this.Tabslength = Tabslength;
        }

        @Override
        public Fragment getItem(int position) {



            switch (position) {
                case 0:
                    FoodFragment foodsFrag= new FoodFragment();
                    return foodsFrag;
                case 1:
                    AnnouncementsFragment aFrag = new AnnouncementsFragment();
                    return aFrag;

                case 2:
                    NewsFragment newsFrag= new NewsFragment();
                    return newsFrag;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return Tabslength;
        }
    }

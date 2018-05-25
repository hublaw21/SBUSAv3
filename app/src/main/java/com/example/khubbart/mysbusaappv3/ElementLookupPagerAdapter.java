package com.example.khubbart.mysbusaappv3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ElementLookupPagerAdapter extends FragmentPagerAdapter{
    int numberOfTabs;
    public ElementLookupPagerAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.numberOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SelectJumpFragment tab1 = new SelectJumpFragment();
                return tab1;
            case 1:
                SelectSpinFragment tab2 = new SelectSpinFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return numberOfTabs;
    }
}

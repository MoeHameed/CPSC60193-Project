package com.example.osdronedetect.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.osdronedetect.R;

import java.util.Map;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private ScanSettingsFragment scanSettingsFragment = null;
    private MapViewFragment mapViewFragment = null;
    private UploadLogFragment uploadLogFragment = null;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                if (scanSettingsFragment == null) scanSettingsFragment = ScanSettingsFragment.newInstance();
                return scanSettingsFragment;
            case 1:
                if (mapViewFragment == null) mapViewFragment = MapViewFragment.newInstance();
                return mapViewFragment;
            case 2:
                if (uploadLogFragment == null) uploadLogFragment = UploadLogFragment.newInstance();
                return uploadLogFragment;
        }

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 1;
    }

    public ScanSettingsFragment getScanSettingsFragment()
    {
        return scanSettingsFragment;
    }

    public MapViewFragment getMapViewFragment()
    {
        return mapViewFragment;
    }
}
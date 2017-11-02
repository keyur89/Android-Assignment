package com.test.assignment.Scenario1.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.assignment.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Scenario1Fragment extends Fragment {

    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.tabIndicator)
    TabLayout tabIndicator;
    @Bind(R.id.txtTabText)
    TextView txtTabText;
    @Bind(R.id.rlButtonContainer)
    RelativeLayout rlButtonContainer;
    @Bind(R.id.btnRed)
    Button btnRed;
    @Bind(R.id.btnBlue)
    Button btnBlue;
    @Bind(R.id.btnGreen)
    Button btnGreen;

    SectionsPagerAdapter mSectionsPagerAdapter;

    public Scenario1Fragment() {
    }

    public static Scenario1Fragment newInstance() {
        Scenario1Fragment fragment = new Scenario1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_scenario1, container, false);
        ButterKnife.bind(this, rootView);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabIndicator.setupWithViewPager(mViewPager, true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                txtTabText.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlButtonContainer.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_light));
            }
        });
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlButtonContainer.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            }
        });
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlButtonContainer.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_light));
            }
        });
        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Fragment1.newInstance(position + 1);
                case 1:
                    return Fragment2.newInstance(position + 1);
                case 2:
                    return Fragment3.newInstance(position + 1);
                case 3:
                    return Fragment4.newInstance(position + 1);
                case 4:
                    return Fragment5.newInstance(position + 1);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }
    }
}

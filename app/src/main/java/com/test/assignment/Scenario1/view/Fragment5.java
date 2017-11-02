package com.test.assignment.Scenario1.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.assignment.R;

/**
 * Created by Keyur Tailor on 02-Nov-17.
 */

public class Fragment5 extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment5() {
    }

    public static Fragment5 newInstance(int sectionNumber) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scenario1, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Fragment number : " + getArguments().getInt(ARG_SECTION_NUMBER), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}

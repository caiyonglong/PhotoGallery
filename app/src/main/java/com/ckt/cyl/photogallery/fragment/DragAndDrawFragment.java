package com.ckt.cyl.photogallery.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckt.cyl.photogallery.R;
import com.ckt.cyl.photogallery.databinding.FragmentDrawBinding;

/**
 * Created by D22434 on 2017/7/31.
 */

public class DragAndDrawFragment extends Fragment {
    public static DragAndDrawFragment newInstance() {
        return new DragAndDrawFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDrawBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_draw, container, false);
        return binding.getRoot();
    }
}

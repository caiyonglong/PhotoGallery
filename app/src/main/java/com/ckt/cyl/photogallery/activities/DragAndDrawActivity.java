package com.ckt.cyl.photogallery.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.ckt.cyl.photogallery.fragment.DragAndDrawFragment;

/**
 * Created by D22434 on 2017/7/31.
 */

public class DragAndDrawActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, DragAndDrawActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return DragAndDrawFragment.newInstance();
    }
}

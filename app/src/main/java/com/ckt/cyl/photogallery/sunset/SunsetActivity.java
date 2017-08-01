package com.ckt.cyl.photogallery.sunset;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ckt.cyl.photogallery.activities.SingleFragmentActivity;
import com.ckt.cyl.photogallery.draw.DragAndDrawFragment;

/**
 * Created by D22434 on 2017/7/31.
 */

public class SunsetActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, SunsetActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return SunsetFragment.newInstance();
    }
}

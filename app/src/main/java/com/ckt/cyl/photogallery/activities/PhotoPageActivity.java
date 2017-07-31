package com.ckt.cyl.photogallery.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.ckt.cyl.photogallery.fragment.PhotoPageFragment;

public class PhotoPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Uri PhotoPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(PhotoPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }

}

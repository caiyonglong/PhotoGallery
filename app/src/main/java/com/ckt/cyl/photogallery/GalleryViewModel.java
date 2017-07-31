package com.ckt.cyl.photogallery;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by D22434 on 2017/7/28.
 * View_Model
 */

public class GalleryViewModel extends BaseObservable {
    private GalleryItem item;

    public GalleryViewModel(GalleryItem item) {
        this.item = item;
    }

    //
    @BindingAdapter("img")
    public static void loadInternetImage(ImageView iv, String img) {
        Picasso.with(iv.getContext()).load(img).into(iv);

    }

    public String getImage() {
        return item.getImage_url();
    }
}

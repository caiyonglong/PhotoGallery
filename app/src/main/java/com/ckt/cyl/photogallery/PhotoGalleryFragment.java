package com.ckt.cyl.photogallery;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ckt.cyl.photogallery.databinding.FragmentPhotoGalleryBinding;
import com.ckt.cyl.photogallery.databinding.ListItemViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D22434 on 2017/7/28.
 */

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    PhotoGalleryAdapter adapter;

    public static PhotoGalleryFragment newInstance() {

        Bundle args = new Bundle();

        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        new FetchItemsTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentPhotoGalleryBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_photo_gallery, container, false);

        binding.photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new PhotoGalleryAdapter(mItems);
        binding.photoRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }


    private class PhotoGalleryHolder extends RecyclerView.ViewHolder {
        private ListItemViewBinding mBinding;
        private GalleryViewModel galleryViewModel;

        public PhotoGalleryHolder(ListItemViewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(GalleryItem item) {
            galleryViewModel = new GalleryViewModel(item);
            mBinding.setGallery(galleryViewModel);
            //更新绑定数据
            mBinding.executePendingBindings();

        }
    }

    private class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryHolder> {
        private List<GalleryItem> mItems;

        public PhotoGalleryAdapter(List<GalleryItem> items) {
            this.mItems = items;
        }

        @Override
        public PhotoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemViewBinding binding = DataBindingUtil
                    .inflate(inflater, R.layout.list_item_view, parent, false);

            return new PhotoGalleryHolder(binding);
        }

        @Override
        public void onBindViewHolder(PhotoGalleryHolder holder, int position) {
            GalleryItem item = mItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    List<GalleryItem> mItems = new ArrayList<>();

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {

            return new FlickerFetchr().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            adapter.notifyDataSetChanged();
//            binding.photoRecyclerView.setAdapter(new PhotoGalleryAdapter(mItems));
        }
    }
}

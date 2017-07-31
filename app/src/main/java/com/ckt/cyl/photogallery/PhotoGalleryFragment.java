package com.ckt.cyl.photogallery;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.ckt.cyl.photogallery.databinding.FragmentPhotoGalleryBinding;
import com.ckt.cyl.photogallery.databinding.ListItemViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D22434 on 2017/7/28.
 */

public class PhotoGalleryFragment extends VisibleFragment {
    private static final String TAG = "PhotoGalleryFragment";
    PhotoGalleryAdapter adapter;
    private FragmentPhotoGalleryBinding binding;
    private ThumbnailDownloader<PhotoGalleryHolder> mThumbnailDownloader;

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
        setHasOptionsMenu(true);
        updateItems();


        mThumbnailDownloader = new ThumbnailDownloader<>();
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_photo_gallery, container, false);

        binding.photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloader.quit();
        Log.i(TAG, "Background Thread destroyed");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_grallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                QueryPreferences.setStoreQuery(getActivity(), query);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoreQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
        if (PollService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.stop_polling);
        } else {
            toggleItem.setTitle(R.string.start_polling);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoreQuery(getActivity(), null);
                updateItems();
                return true;
            case R.id.menu_item_toggle_polling:
                boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
                PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                getActivity().invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateItems() {
        String query = QueryPreferences.getStoreQuery(getActivity());
        new FetchItemsTask(query).execute();
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

        private String mQuery;

        public FetchItemsTask(String query) {
            mQuery = query;
        }

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {

            if (mQuery != null) {
                return new FlickerFetchr().searchPhotos(mQuery);
            } else {
                return new FlickerFetchr().fetchRecentPhotos();
            }
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
            Log.d(TAG, mItems.size() + "---");
        }
    }

    private void setupAdapter() {
        adapter = new PhotoGalleryAdapter(mItems);
        adapter.notifyDataSetChanged();
        binding.emptyView.setVisibility(mItems.size() > 0 ? View.GONE : View.VISIBLE);
        binding.photoRecyclerView.setAdapter(adapter);
    }

}

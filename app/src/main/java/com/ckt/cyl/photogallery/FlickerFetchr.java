package com.ckt.cyl.photogallery;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D22434 on 2017/7/28.
 * 网络请求类
 * 封装获取图片请求操作
 */

public class FlickerFetchr {
    private static final String TAG = "PhotoGalleryFragment";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems() {
        List<GalleryItem> items = new ArrayList<>();

        /**http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=美女&tag2=全部&ftags=小清新&ie=utf8
         */
        try {
            String url = Uri.parse("http://image.baidu.com/channel/listjson")
                    .buildUpon()
                    .appendQueryParameter("pn", "0")
                    .appendQueryParameter("rn", "10")
                    .appendQueryParameter("tag1", "美女")
                    .appendQueryParameter("tag2", "全部")
                    .appendQueryParameter("ftags", "小清新")
                    .appendQueryParameter("ie", "utf8")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.d(TAG, "Fetched contents of URL: " + jsonString);
            /**
             * 第一种方式解析
             *
             */
            JSONObject jsonObject = new JSONObject(jsonString);
            parseItems(items, jsonObject);

            /**
             * GSON解析数据
             */
//            Gson gson = new Gson();
//            List<GalleryItem> items = gson.fromJson(jsonString, GsonData.class).getData();

//            for (GalleryItem galleryItem : items) {
//
//                Log.d(TAG, "Fetched to fetch URL: " + galleryItem.getId());
//                Log.d(TAG, "Fetched to fetch URL: " + galleryItem.getAbs());
//                Log.d(TAG, "Fetched to fetch URL: " + galleryItem.getDes());
//                Log.d(TAG, "Fetched to fetch URL: " + galleryItem.getImage_url());
//            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "Fetched to fetch URL: " + e);
        }
        return items;
    }

    /**
     * 相对于一般方式解析方法
     *
     * @param items
     * @param jsonObject
     * @throws IOException
     * @throws JSONException
     */
    private void parseItems(List<GalleryItem> items, JSONObject jsonObject) throws IOException, JSONException {

        JSONArray photosJsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < photosJsonArray.length(); i++) {
            JSONObject photoObject = photosJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoObject.getString("id"));
            item.setAbs(photoObject.getString("abs"));
            item.setDes(photoObject.getString("des"));
            //url有可能为空
            if (photoObject.getString("image_url") == null) {
                continue;
            }
            item.setImage_url(photoObject.getString("image_url"));
            items.add(item);
        }

    }


}

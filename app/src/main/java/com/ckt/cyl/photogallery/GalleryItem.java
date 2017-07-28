package com.ckt.cyl.photogallery;

/**
 * Created by D22434 on 2017/7/28.
 */

public class GalleryItem {

    /**
     * id : 21558403377
     * abs:
     * des:(可能为空)
     * image_url : http://b.hiphotos.baidu.com/image/pic/item/908fa0ec08fa513d17b6a2ea386d55fbb2fbd9e2.jpg
     */

    private String id;
    private String abs;
    private String des;
    private String image_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


}

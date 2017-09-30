package pe.cibertec.cleanarquitecturedemo.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsEntity {

    private String objectId;


    private String title;


    private String detail;

    @SerializedName("image_url")
    private String imageUrl;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

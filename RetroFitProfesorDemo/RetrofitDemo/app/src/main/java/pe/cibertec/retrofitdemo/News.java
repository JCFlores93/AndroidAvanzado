package pe.cibertec.retrofitdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 20/05/2017.
 */

public class News {
    //Nuestra clase que recibirá del servicio


    private String objectId;


    private String title;


    private String detail;

    @SerializedName("image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "Post{"+
                "title = '" + getTitle() +"\'" +
                " , imageUrl = '" + getImageUrl() +"\' " +
                "}";

        };

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


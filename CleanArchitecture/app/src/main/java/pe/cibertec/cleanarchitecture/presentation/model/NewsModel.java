package pe.cibertec.cleanarchitecture.presentation.model;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsModel {

    private final String objectId;

    private String title;

    private String detail;

    private String imageUrl;


    public NewsModel(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
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

    @Override
    public String toString() {
        return title;
    }
}

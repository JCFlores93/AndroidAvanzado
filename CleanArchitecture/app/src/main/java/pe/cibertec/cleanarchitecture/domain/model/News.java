package pe.cibertec.cleanarchitecture.domain.model;

/**
 * Created by Android on 27/05/2017.
 */

public class News {

    private final String objectId;

    private String title;

    private String detail;

    private String imageUrl;


    public News(String objectId) {
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
}

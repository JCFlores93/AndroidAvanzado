package pe.cibertec.okhttpdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 20/05/2017.
 */

public class News {

    public String title ;

    public  String detail;

    @SerializedName("image_url")
    public String imageUrl;
}

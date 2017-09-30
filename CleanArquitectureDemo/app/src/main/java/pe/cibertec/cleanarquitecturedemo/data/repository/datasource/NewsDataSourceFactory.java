package pe.cibertec.cleanarquitecturedemo.data.repository.datasource;

import android.content.Context;

import pe.cibertec.cleanarquitecturedemo.data.net.RestApi;

/**
 * Created by Android on 27/05/2017.
 */

public class NewsDataSourceFactory {
    //Devuelve cual es el repositorio mas adecuado
    private  final Context context;

    public  NewsDataSourceFactory(Context context){
        this.context = context;

    }

    public NewsDataSource create(){
        RestApi restApi = new
    }
}

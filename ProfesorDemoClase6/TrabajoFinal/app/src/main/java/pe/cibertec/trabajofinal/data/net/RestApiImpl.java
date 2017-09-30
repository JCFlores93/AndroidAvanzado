package pe.cibertec.trabajofinal.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;

import pe.cibertec.trabajofinal.data.entity.UsersEntity;
import pe.cibertec.trabajofinal.data.exception.NetworkException;
import pe.cibertec.trabajofinal.domain.model.Users;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class RestApiImpl implements RestApi{

    private final Context context;
    private final UsersService usersService;

    public RestApiImpl(Context context, UsersService usersService) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.usersService = retrofit.create(UsersService.class);
    }

    @Override
    public void postUsers(Callback<UsersEntity> callback) {
        if (hasConnection()) {
            Call<List<UsersEntity>> call = usersService.insertUsers(callback).enqueue(callback);
            try {
                Response<List<UsersEntity>> response = call.execute();
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new NetworkException());
                }
            } catch (IOException e) {
                callback.onError(e);
            }
        } else {
            callback.onError(new NetworkException());
        }
    }

    private boolean hasConnection() {
        boolean isConnected;
        ConnectivityManager manager = (ConnectivityManager) this.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isConnected = (info != null && info.isConnectedOrConnecting());
        return isConnected;
    }
}

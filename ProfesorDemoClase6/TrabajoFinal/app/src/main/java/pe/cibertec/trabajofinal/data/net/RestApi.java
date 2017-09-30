package pe.cibertec.trabajofinal.data.net;

import pe.cibertec.trabajofinal.data.entity.UsersEntity;
import retrofit2.Callback;

/**
 * Created by USUARIO on 1/06/2017.
 */

public interface RestApi {

    void postUsers(Callback<UsersEntity> callback);
    interface Callback<T>{
        void onSuccess(T response);
        void onError(Throwable e);
    }
}

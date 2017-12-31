package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import pe.cibertec.trabajofinalcibertec.data.net.RestApi;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class NetworkUsersDataSource implements UsersDataSource{

    private final RestApi restApi;
    private final UsersCache usersCache;

    public NetworkUsersDataSource(RestApi restApi, UsersCache usersCache) {
        this.restApi = restApi;
        this.usersCache = usersCache;
    }

    @Override
    public void usersEntityList(final DataSourceCallback<List<UsersEntity>> callback) {
        this.restApi.getUsersList(new RestApi.Callback<List<UsersEntity>>() {
            @Override
            public void onSuccess(List<UsersEntity> response) {
                usersCache.put(response);
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }
        });

    }


        @Override
        public void usersEntityUsers(UsersEntity usersEntity, final DataSourceCallback<UsersEntity> callback) {
            /*this.restApi.insertUsers(usersEntity, new RestApi.Callback<UsersEntity>() {
                @Override
                public void onSuccess(UsersEntity response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onError(Throwable e) {
                    callback.onError(e);
                }
            });*/
        }

    @Override
    public void usersEntityLogin(UsersEntity usersEntity, final DataSourceCallback<UsersEntity> callback) {
        this.restApi.loginUsers(usersEntity, new RestApi.Callback<UsersEntity>() {
            @Override
            public void onSuccess(UsersEntity response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }
        });
    }
}

package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class DiskUsersDataSource implements UsersDataSource{

    private final UsersCache usersCache;

    public DiskUsersDataSource(UsersCache usersCache) {
        this.usersCache = usersCache;
    }

    @Override
    public void usersEntityList(final DataSourceCallback<List<UsersEntity>> callback) {
        this.usersCache.list(new UsersCache.Callback<List<UsersEntity>>() {
            @Override
            public void onSuccess(List<UsersEntity> usersEntityList) {
                callback.onSuccess(usersEntityList);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);

            }
        });
    }

    @Override
    public void usersEntityUsers(UsersEntity usersEntity, DataSourceCallback<UsersEntity> callback) {

    }

    @Override
    public void usersEntityLogin(UsersEntity usersEntity, DataSourceCallback<UsersEntity> callback) {

    }
}

package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import android.content.Context;

import pe.cibertec.trabajofinalcibertec.data.cache.UsersCache;
import pe.cibertec.trabajofinalcibertec.data.cache.UsersCacheImpl;
import pe.cibertec.trabajofinalcibertec.data.net.RestApi;
import pe.cibertec.trabajofinalcibertec.data.net.RestApiImpl;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class UsersDataSourceFactory {
    private final Context context;

    public UsersDataSourceFactory(Context context) {
        this.context = context;
    }

    public UsersDataSource createNetworkDataSource(){
        RestApi restApi = new RestApiImpl(context);
        UsersCache usersCache = new UsersCacheImpl();
        return new NetworkUsersDataSource(restApi,usersCache);
    }

    public UsersDataSource createDiskDataSource(){
        UsersCache usersCache = new UsersCacheImpl();
        return new DiskUsersDataSource(usersCache);
    }
}

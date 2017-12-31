package pe.cibertec.trabajofinalcibertec.data.repository.datasource;

import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;

/**
 * Created by Android on 27/05/2017.
 */

public interface UsersDataSource {

    void usersEntityList(DataSourceCallback<List<UsersEntity>> callback);

    void  usersEntityUsers(UsersEntity usersEntity, DataSourceCallback<UsersEntity> callback);

    //Llamamos este metodo para el login
    void  usersEntityLogin(UsersEntity usersEntity, DataSourceCallback<UsersEntity> callback);
}

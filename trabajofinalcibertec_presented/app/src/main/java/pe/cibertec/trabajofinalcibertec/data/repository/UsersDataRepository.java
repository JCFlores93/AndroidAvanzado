package pe.cibertec.trabajofinalcibertec.data.repository;

import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import pe.cibertec.trabajofinalcibertec.data.entity.mapper.UsersEntityDataMapper;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.DataSourceCallback;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.UsersDataSource;
import pe.cibertec.trabajofinalcibertec.data.repository.datasource.UsersDataSourceFactory;
import pe.cibertec.trabajofinalcibertec.domain.model.Users;
import pe.cibertec.trabajofinalcibertec.domain.repository.RepositoryCallback;
import pe.cibertec.trabajofinalcibertec.domain.repository.UserRepository;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class UsersDataRepository implements UserRepository{

    private final UsersDataSourceFactory usersDataSourceFactory;
    private final UsersEntityDataMapper usersEntityDataMapper ;

    public UsersDataRepository(UsersDataSourceFactory usersDataSourceFactory,
                               UsersEntityDataMapper usersEntityDataMapper) {
        this.usersDataSourceFactory = usersDataSourceFactory;
        this.usersEntityDataMapper = usersEntityDataMapper;
    }
    //Llamamos para el login
    @Override
    public void userLogin(Users users, final RepositoryCallback<Users> callback) {
        final UsersDataSource usersDataSource = usersDataSourceFactory.createNetworkDataSource();
        usersDataSource.usersEntityLogin(usersEntityDataMapper.sendItTransform(users), new DataSourceCallback<UsersEntity>() {
            @Override
            public void onSuccess(UsersEntity usersEntity) {
                callback.onSuccess(usersEntityDataMapper.transform(usersEntity));
            }

            @Override
            public void onError(Throwable exception) {
                callback.onError(exception);
            }
        });


    }
}

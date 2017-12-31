package pe.cibertec.trabajofinalcibertec.domain.repository;

import pe.cibertec.trabajofinalcibertec.domain.model.Users;

/**
 * Created by USUARIO on 3/06/2017.
 */

public interface UserRepository {

    //Mandamos un usuario que sólo contendrá login y password
    void userLogin(Users users,RepositoryCallback<Users> callback);
}

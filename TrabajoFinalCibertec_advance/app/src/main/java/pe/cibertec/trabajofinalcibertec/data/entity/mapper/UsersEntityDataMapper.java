package pe.cibertec.trabajofinalcibertec.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinalcibertec.data.entity.UsersEntity;
import pe.cibertec.trabajofinalcibertec.domain.model.Users;

/**
 * Created by USUARIO on 2/06/2017.
 */

public class UsersEntityDataMapper {

    public UsersEntityDataMapper() {
    }

    public Users transform(UsersEntity usersEntity) {
        Users users = new Users(null);
        //users.setObjectId(usersEntity.getObjectId());
        users.setName(usersEntity.getName());
        users.setUserToken(usersEntity.getUserToken());
        users.setEmail(usersEntity.getEmail());
        users.setPassword(usersEntity.getPassword());
        return users;
    }


    public List<Users> transform(List<UsersEntity> usersEntityList) {
        List<Users> usersList = new ArrayList<>();
        for (UsersEntity newsEntity : usersEntityList) {
            usersList.add(transform(newsEntity));
        }
        return usersList;
    }
    //Así cambiamos la data obtenida puesto que estamos enviando toda la clase y no sólo parámetos
    public UsersEntity sendItTransform(Users users) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setObjectId(users.getObjectId());
        usersEntity.setName(users.getName());
        usersEntity.setUserToken(users.getUserToken());
        usersEntity.setEmail(users.getEmail());
        usersEntity.setPassword(users.getPassword());
        return usersEntity;
    }
    //De igual manera pasamos la lista a Entity
    public List<UsersEntity> sendItTransform(List<Users> usersList) {
        List<UsersEntity> usersEntityList = new ArrayList<>();
        for (Users users : usersList) {
            usersEntityList.add(sendItTransform(users));
        }
        return usersEntityList;
    }
}

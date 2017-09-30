package pe.cibertec.trabajofinal.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinal.data.entity.UsersEntity;
import pe.cibertec.trabajofinal.domain.model.Users;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class UsersEntityDataMapper {

    public UsersEntityDataMapper() {
    }

    public Users transform(UsersEntity usersEntity) {
        Users users = new Users(usersEntity.getObjectId());
        users.setName(usersEntity.getName());
        users.setPw(usersEntity.getPw());
        users.setEmail(usersEntity.getEmail());
        return users;
    }

    public List<Users> transform(List<UsersEntity> newsEntityList) {
        List<Users> newsList = new ArrayList<>();
        for (UsersEntity usersEntity : newsEntityList) {
            newsList.add(transform(usersEntity));
        }
        return newsList;
    }
}

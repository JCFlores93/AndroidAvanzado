package pe.cibertec.trabajofinalcibertec.presentation.model.mapper;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.trabajofinalcibertec.domain.model.Users;
import pe.cibertec.trabajofinalcibertec.presentation.model.UsersModel;

/**
 * Created by USUARIO on 4/06/2017.
 */

public class UsersModelDataMapper {

    public UsersModelDataMapper() {
    }

    public UsersModel transform(Users users) {
        UsersModel newsModel = new UsersModel(users.getObjectId());
        newsModel.setEmail(users.getEmail());
        newsModel.setPassword(users.getPassword());
        newsModel.setUserToken(users.getUserToken());
        return newsModel;
    }

    public List<UsersModel> transform(List<Users> usersList) {
        List<UsersModel> usersModelList = new ArrayList<>();
        for(Users users : usersList) {
            usersModelList.add(transform(users));
        }
        return usersModelList;
    }

    public Users sendItTransform(UsersModel newsModel) {
        Users users = new Users(newsModel.getObjectId());
        users.setEmail(newsModel.getEmail());
        users.setPassword(newsModel.getPassword());
        users.setUserToken(newsModel.getUserToken());
        return users;
    }

    public List<Users> sendItTransform(List<UsersModel> usersModelList) {

        List<Users> usersList = new ArrayList<>();
        for(UsersModel  usersModel : usersModelList) {
            usersList.add(sendItTransform(usersModel));
        }
        return usersList;
    }
}

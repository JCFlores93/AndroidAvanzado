package pe.cibertec.trabajofinalcibertec.domain.model;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class Users {
    private String objectId;

    private String email;

    private String password;

    private String name;

    private String userToken;

    public Users(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public String toString() {
        return "";
    }
}

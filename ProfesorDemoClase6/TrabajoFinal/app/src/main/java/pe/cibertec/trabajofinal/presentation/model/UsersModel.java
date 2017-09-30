package pe.cibertec.trabajofinal.presentation.model;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class UsersModel {

    private final String objectId;

    private String email;

    private String pw;

    private String name;


    public UsersModel(String objectId) {
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

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return email;
    }

}

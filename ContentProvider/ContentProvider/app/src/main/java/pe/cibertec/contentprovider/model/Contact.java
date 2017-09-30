package pe.cibertec.contentprovider.model;

/**
 * Created by Android on 29/04/2017.
 */

public class Contact {
    public long id;
    public String name;
    public String phone;
    public String dob;

    @Override
    public String toString() {
        return id + " - " + name + " - " + phone + " - " + dob;
    }
}

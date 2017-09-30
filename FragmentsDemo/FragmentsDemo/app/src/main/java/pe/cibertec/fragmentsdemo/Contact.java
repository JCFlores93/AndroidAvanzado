package pe.cibertec.fragmentsdemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Android on 22/04/2017.
 */

public class Contact implements Parcelable{
    private String name;
    private String phone;
    private String email;
    private String address;
    private Date dob;

    public Contact(){
    }
    protected Contact(Parcel in) {
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        address = in.readString();
        dob = (Date) in.readSerializable();
    }
    //traduce nuestros datos y al momento de crear leer un objeto parce
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeSerializable(dob);
        // TODO : set dob in view
    }
}

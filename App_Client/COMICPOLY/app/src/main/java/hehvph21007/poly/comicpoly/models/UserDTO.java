package hehvph21007.poly.comicpoly.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class UserDTO implements Parcelable {
    String _id;
    String userName;
    String passWord;
    String email;
    String fullName;

    String token;

    public UserDTO(String _id, String userName, String passWord, String email, String fullName) {
        this._id = _id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.fullName = fullName;
    }

    protected UserDTO(Parcel in) {
        _id = in.readString();
        userName = in.readString();
        passWord = in.readString();
        email = in.readString();
        fullName = in.readString();
        token = in.readString();
    }

    public static final Creator<UserDTO> CREATOR = new Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(token);
    }
}


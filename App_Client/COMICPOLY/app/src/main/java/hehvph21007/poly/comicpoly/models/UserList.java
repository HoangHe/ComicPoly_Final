package hehvph21007.poly.comicpoly.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserList implements Parcelable {
    private List<UserDTO> userList;

    public UserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    protected UserList(Parcel in) {
        userList = in.createTypedArrayList(UserDTO.CREATOR);
    }

    public static final Creator<UserList> CREATOR = new Creator<UserList>() {
        @Override
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        @Override
        public UserList[] newArray(int size) {
            return new UserList[size];
        }
    };

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(userList);
    }
}

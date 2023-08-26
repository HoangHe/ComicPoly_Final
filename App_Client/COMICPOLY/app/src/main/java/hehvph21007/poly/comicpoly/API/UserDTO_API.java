package hehvph21007.poly.comicpoly.API;

import com.google.gson.annotations.SerializedName;

import hehvph21007.poly.comicpoly.models.UserDTO;

public class UserDTO_API {

    @SerializedName("user")
    private UserDTO user;
    @SerializedName("token")
    private String token;
    @SerializedName("msg")
    private String msg;


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

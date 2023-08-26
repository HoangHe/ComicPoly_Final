package hehvph21007.poly.comicpoly.Interface;


import java.util.List;

import hehvph21007.poly.comicpoly.API.UserDTO_API;
import hehvph21007.poly.comicpoly.models.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserInterface {
    @POST("users/login")
    Call<UserDTO_API> Login(@Body UserDTO objC);

    @GET("users/list")
    Call<List<UserDTO>> list_user();

    @POST("users/register")
    Call<UserDTO> Register(@Body UserDTO objC);

    @POST("users/logout")
    Call<UserDTO> Logout(@Query("id") String userId);
}

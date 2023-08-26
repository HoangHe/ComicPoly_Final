package hehvph21007.poly.comicpoly.Interface;

import java.util.List;

import hehvph21007.poly.comicpoly.models.ComicDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ComicInterface {
    @GET("comic/list")
    Call<List<ComicDTO>> danh_sach_comic();
}

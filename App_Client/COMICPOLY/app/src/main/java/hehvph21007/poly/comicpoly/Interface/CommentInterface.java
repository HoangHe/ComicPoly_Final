package hehvph21007.poly.comicpoly.Interface;


import java.util.List;

import hehvph21007.poly.comicpoly.models.CommentDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentInterface {
    @GET("comment/list") // Thay "comment" bằng đường dẫn API lấy danh sách comment theo id truyện
    Call<List<CommentDTO>> getCommentsByComicId(@Query("id_comic") String id_comic);
    // Định nghĩa phương thức POST để thêm bình luận mới
    @FormUrlEncoded
    @POST("comment/send") // Điền đúng đường dẫn API để thêm bình luận
    Call<CommentDTO> addComment(
            @Field("id_comic") String comicId,
            @Field("id_user") String userId,
            @Field("content") String commentContent
    );
}

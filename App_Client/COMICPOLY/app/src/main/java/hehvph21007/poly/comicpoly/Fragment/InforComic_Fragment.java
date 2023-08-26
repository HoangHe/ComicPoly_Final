package hehvph21007.poly.comicpoly.Fragment;

import static hehvph21007.poly.comicpoly.MainActivity.BASE_URL;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import hehvph21007.poly.comicpoly.Adapter.viewpager2.CommentAdapter;
import hehvph21007.poly.comicpoly.Interface.CommentInterface;
import hehvph21007.poly.comicpoly.MainActivity;
import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.models.ComicDTO;
import hehvph21007.poly.comicpoly.models.CommentDTO;
import hehvph21007.poly.comicpoly.models.SharedPreferencesUtil;
import hehvph21007.poly.comicpoly.models.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InforComic_Fragment extends Fragment {
    private ComicDTO comicDTO; // Thêm biến để lưu thông tin Commic
    private View view; // Khai báo biến view ở đây
    private UserDTO userDTO;
    private List<UserDTO> userDataList;
    private RecyclerView recyclerViewComments;
    private List<CommentDTO> commentList;
    private CommentAdapter commentAdapter;


    public static InforComic_Fragment newInstanceComent(ComicDTO comicDTO, UserDTO userDTO, List<UserDTO> userDataList) {
        InforComic_Fragment comment_fragment = new InforComic_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("COMIC_EXTRA", comicDTO);
        args.putParcelable("USER_DATA_EXTRA", userDTO);
        args.putParcelableArrayList("LIST_USER", (ArrayList<? extends Parcelable>) userDataList);
        comment_fragment.setArguments(args);
        return comment_fragment;
    }

    public InforComic_Fragment() {
        // Required empty public constructor
    }

    public static InforComic_Fragment newInstance(ComicDTO commic) {
        InforComic_Fragment inforComic_fragment = new InforComic_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("COMIC_EXTRA", commic); // Đặt Commic vào arguments
        inforComic_fragment.setArguments(args);
        return inforComic_fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comicDTO = getArguments().getParcelable("COMIC_EXTRA");
            userDTO = getArguments().getParcelable("USER_DATA_EXTRA");
            // Tiếp tục xử lý thông tin `comic` và `userData` theo ý muốn

            Log.d("TAG", "onCreate COMENT: "+"COMIC"+ comicDTO + "User_LIST" +  userDataList + "UserDTO: " + userDTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_infor_comic, container, false);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        TextView txtAuther = view.findViewById(R.id.txtAuther);
        TextView txtYear = view.findViewById(R.id.txtYear);




        if (comicDTO != null) {
            // Hiển thị thông tin Commic lên TextView
            txtAuther.setText(" Tác giả:  " + comicDTO.getAuthor());
            txtYear.setText(" Năm xuất bản:  " + comicDTO.getPublish() );
            txtDescription.setText(" Thông tin: "+ comicDTO.getDesc());
        }
        recyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList,userDataList);
        recyclerViewComments.setAdapter(commentAdapter);
        if (comicDTO != null) {
            getCommentsByComicId(comicDTO.get_id());  // Gọi API để lấy danh sách comment
            Log.d("COMENT", "comicDTO: "+ comicDTO.get_id());
        }
        // TODO: Xử lý các sự kiện khi click vào các nút button (nếu cần)

        ImageView btnAddComment = view.findViewById(R.id.btnSend);
        EditText editTextComment = view.findViewById(R.id.edtComment);
        userDTO = SharedPreferencesUtil.getObject(getActivity(), "userDTO", UserDTO.class);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDTO == null) {
                    // Hiển thị thông báo "Cần đăng nhập mới bình luận được"
                    Toast.makeText(getActivity(), "Cần đăng nhập mới bình luận được", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm bình luận theo id truyện
                    String commentContent = editTextComment.getText().toString().trim();
                    if (!commentContent.isEmpty()) {
                        // Gọi API để thêm bình luận mới
                        addComment(comicDTO.get_id(), userDTO.get_id(), commentContent);
                    }else{
                        Toast.makeText(getActivity(), "Nội dung bình luận không được để trống.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void getCommentsByComicId(String comicId) {
        // Tạo Retrofit và interface để gọi API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentInterface commentInterface = retrofit.create(CommentInterface.class);

        // Gọi API lấy danh sách comment theo ID truyện
        Call<List<CommentDTO>> call = commentInterface.getCommentsByComicId(comicId);
        call.enqueue(new Callback<List<CommentDTO>>() {
            @Override
            public void onResponse(Call<List<CommentDTO>> call, Response<List<CommentDTO>> response) {
                if (response.isSuccessful()) {
                    List<CommentDTO> comments = response.body();
                    if (comments != null) {
                        commentList.addAll(comments);
                        Log.d("LISTCOMENT", "onResponse: "+ comments);
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        // Hiển thị thông báo không có bình luận nào
                        Log.d("NOTCONMENT", "KHông Thấy cmt");
                    }
                } else {
                    // Hiển thị thông báo lỗi
                    Log.d("NOTCONMENT", "LỖI cmt");
                }
            }

            @Override
            public void onFailure(Call<List<CommentDTO>> call, Throwable t) {
                Log.d("NOTCONMENT", "KHông tìm thấy cmt"+ t);
            }
        });
    }
    private void addComment(String comicId, String userId, String commentContent) {

        Gson gson = new GsonBuilder().setLenient().create();
        // Tạo Retrofit và interface để gọi API

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CommentInterface commentInterface = retrofit.create(CommentInterface.class);

        // Gọi API để thêm bình luận mới
        Call<CommentDTO> call = commentInterface.addComment(comicId, userId, commentContent);
        call.enqueue(new Callback<CommentDTO>() {
            @Override
            public void onResponse(Call<CommentDTO> call, Response<CommentDTO> response) {
                if (response.isSuccessful()) {
                    // Cập nhật danh sách bình luận sau khi thêm thành công
                    CommentDTO newComment = response.body();
                    List<CommentDTO> newComments = new ArrayList<>(commentList);
                    newComments.add(newComment);
                    commentAdapter.updateCommentList(newComments);
                } else {
                    // Hiển thị thông báo lỗi khi thêm bình luận không thành công
                    Toast.makeText(getActivity(), "Lỗi khi thêm bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentDTO> call, Throwable t) {
                // Hiển thị thông báo lỗi khi gọi API thêm bình luận thất bại
                Toast.makeText(getActivity(), "Lỗi khi gọi API thêm bình luận", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
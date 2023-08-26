package hehvph21007.poly.comicpoly.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hehvph21007.poly.comicpoly.API.UserDTO_API;
import hehvph21007.poly.comicpoly.HomeActivity;
import hehvph21007.poly.comicpoly.Interface.UserInterface;
import hehvph21007.poly.comicpoly.LoginActivity;
import hehvph21007.poly.comicpoly.MainActivity;
import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.RegisterActivity;
import hehvph21007.poly.comicpoly.models.SharedPreferencesUtil;
import hehvph21007.poly.comicpoly.models.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Profile_Fragment extends Fragment {
    private UserDTO userDTO;
    private Button btn_login;
    private Button btn_sigup;
    private TextView txtname,txtid;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        anhXa();
        // Lấy dữ liệu truyền vào từ ViewPager2Adapter
        if (getArguments() != null) {
            userDTO = getArguments().getParcelable("userData");
            if (userDTO != null) {
                String username = userDTO.getUserName();
                String userId = userDTO.get_id();
                // Tiếp tục xử lý dữ liệu username và userId theo ý muốn

            }
        }




        // Tiếp tục cài đặt Fragment theo ý muốn

        return view;
    }

    public static Profile_Fragment newInstance(UserDTO userDTO) {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userDTO);
        fragment.setArguments(args);
        return fragment;
    }

    private void anhXa() {
        //btn
        btn_login = view.findViewById(R.id.btn_login);
        txtname = view.findViewById(R.id.nameText);
        txtid = view.findViewById(R.id.idUser);
        btn_sigup = view.findViewById(R.id.btn_signup);
        LinearLayout hide = view.findViewById(R.id.hide_user);
        LinearLayout btn_logout = view.findViewById(R.id.btn_logout);

        CardView cv_user = view.findViewById(R.id.cv_user);
        // Kiểm tra nếu userData không null thì hiển thị chào mừng và ẩn nút đăng nhập và đăng ký

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        String fullName = preferences.getString("fullName",null);
        String email = preferences.getString("email",null);
        String id_user = preferences.getString("id_user",null);

        if(token != null){
            txtname.setText("Xin chào " + fullName);
            txtid.setText(email);
            hide.setVisibility(View.INVISIBLE);
            cv_user.setVisibility(View.VISIBLE);
        }else{
            txtname.setText("Bạn chưa đăng nhập");
            txtid.setText("");
            cv_user.setVisibility(View.INVISIBLE);
            hide.setVisibility(View.VISIBLE);
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiển thị thông báo xác nhận xóa User
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout(preferences,id_user);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btn_sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
    }


    void logout(SharedPreferences sharedPreferences,String id_user) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);

        Call<UserDTO> call = userInterface.Logout(id_user);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    // Xử lý phản hồi thành công
                    Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    // Tiến hành xóa thông tin người dùng đã lưu (token, id_user, fullName, email)
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    SharedPreferencesUtil.removeObject(getActivity(), "userDTO");

                    // Chuyển đến màn hình HomeActivity hoặc màn hình đăng nhập tùy theo logic của bạn
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    editor.commit();
                } else {
                    // Xử lý phản hồi không thành công
                    Toast.makeText(getActivity(), "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Xử lý lỗi
                Log.e("LoginActivity", "Logout failed", t);
                Toast.makeText(getActivity(), "Đăng xuất thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
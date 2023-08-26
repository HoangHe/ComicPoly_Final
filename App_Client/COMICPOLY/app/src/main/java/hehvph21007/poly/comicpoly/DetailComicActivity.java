package hehvph21007.poly.comicpoly;

import static hehvph21007.poly.comicpoly.MainActivity.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hehvph21007.poly.comicpoly.Adapter.viewpager2.TabsPagerAdapter;
import hehvph21007.poly.comicpoly.Fragment.InforComic_Fragment;
import hehvph21007.poly.comicpoly.Fragment.TabContent_Fragment;
import hehvph21007.poly.comicpoly.Interface.UserInterface;
import hehvph21007.poly.comicpoly.models.ComicDTO;
import hehvph21007.poly.comicpoly.models.SharedPreferencesUtil;
import hehvph21007.poly.comicpoly.models.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailComicActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private ComicDTO comicDTO;
    private UserDTO userDTO;

    List<UserDTO> list_user = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        TextView txtname = findViewById(R.id.name);
        ImageView avt = findViewById(R.id.avt);
        Button btnread = findViewById(R.id.btnread);
        getList_user();
        comicDTO = getIntent().getParcelableExtra("COMIC_EXTRA");
        userDTO = SharedPreferencesUtil.getObject(this, "userDTO", UserDTO.class);

        Log.d("TAG", "onCreate: USERDTO" + userDTO);
        Log.d("TAG", "onCreate: listUser" + list_user);
        Log.d("TAG", "onCreate: comic" + comicDTO);


        if (comicDTO != null) {

            // Hiển thị chi tiết thông tin Commic
            // Ví dụ: gán giá trị cho các TextView
            txtname.setText(comicDTO.getName());

            Picasso.get().load(comicDTO.getImage()).into(avt);
            Log.d("zzzzzzzz", " aa" + comicDTO.getImage());

        }
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailComicActivity.this, ReadComics.class);
                // Đính kèm thông tin comic vào Intent
                intent.putExtra("COMIC_EXTRA", comicDTO);
                startActivity(intent);
            }
        });
        // Khởi tạo và cấu hình Adapter cho ViewPager
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        InforComic_Fragment inforComic_fragment = InforComic_Fragment.newInstance(comicDTO); // Truyền Commic vào FragmentGT
        InforComic_Fragment comment_fragment = InforComic_Fragment.newInstanceComent(comicDTO, userDTO, list_user);



        Log.d("Detail", "comicDTO: " + comicDTO);
        Log.d("Detail", "list_user: " + list_user);
        tabsPagerAdapter.addFragment(inforComic_fragment, "Giới Thiệu");
        tabsPagerAdapter.addFragment(TabContent_Fragment.newInstance(), "Mục Lục");
        viewPager.setAdapter(tabsPagerAdapter);

        // Kết nối ViewPager với TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }


    private void getList_user(){
        // tạo gson
        Gson gson= new GsonBuilder().setLenient().create();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // sử dụng interface

        UserInterface userInterface=retrofit.create(UserInterface.class);

        //tạo đối tượng

        Call<List<UserDTO>> objCall= userInterface.list_user();
        objCall.enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                if(response.isSuccessful()){
                    list_user.clear();
                    list_user.addAll(response.body());
                    Log.d("zzzzzzzz", "onResponse user: "+list_user);
                    Log.d("zzzzzzzzzzzz", "onResponse: "+ response.body());
                    //tạo adapter đổ lên listview


                }
                else {
                    Toast.makeText(DetailComicActivity.this, "Không lấy được dữ liệu" +response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {

                Log.e("RetrofitError", "onFailure: ", t);
                Toast.makeText(DetailComicActivity.this, "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}
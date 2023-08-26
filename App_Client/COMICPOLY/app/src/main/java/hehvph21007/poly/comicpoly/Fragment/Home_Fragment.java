package hehvph21007.poly.comicpoly.Fragment;

import static hehvph21007.poly.comicpoly.MainActivity.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hehvph21007.poly.comicpoly.Adapter.viewpager2.ComicsAdapter;
import hehvph21007.poly.comicpoly.Adapter.viewpager2.SlideAdapterHome;
import hehvph21007.poly.comicpoly.DetailComicActivity;
import hehvph21007.poly.comicpoly.Interface.ComicInterface;
import hehvph21007.poly.comicpoly.Interface.UserInterface;
import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.models.ComicDTO;
import hehvph21007.poly.comicpoly.models.Photo;
import hehvph21007.poly.comicpoly.models.UserDTO;
import hehvph21007.poly.comicpoly.models.UserList;
import me.relex.circleindicator.CircleIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Home_Fragment extends Fragment {

    UserDTO userDTO;

    private ViewPager vpr;
    private RecyclerView rcvComic;
    private ComicsAdapter comicsAdapter;
    private CircleIndicator circleIndicator;
    private SlideAdapterHome slideAdapter;
    private Timer timer;
    private List<Photo> photoList;
    private Handler handler = new Handler(Looper.getMainLooper());
    List<ComicDTO> list_comic = new ArrayList<>();
    List<UserDTO> list_user ;

    View view;

    public Home_Fragment() {
        // Required empty public constructor
    }
    public static Home_Fragment newInstance(UserDTO userDTO) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userDTO);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home_, container, false);
         if (getArguments() != null) {
             userDTO = getArguments().getParcelable("userData");
             if (userDTO != null) {
                 String username = userDTO.getUserName();
                 String userId = userDTO.get_id();
                 // Tiếp tục xử lý dữ liệu username và userId theo ý muốn
             }
         }

         anhXa();
        getComic();

        // Inflate the layout for this fragment
        return view;
    }

    private void anhXa(){
        //slide
        vpr = (ViewPager) view.findViewById(R.id.vpr);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circle_indicator);
        photoList = getListPhoto();
        slideAdapter = new SlideAdapterHome(getContext(), photoList);
        vpr.setAdapter(slideAdapter);
        circleIndicator.setViewPager(vpr);
        slideAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlide();
        // list comic
        rcvComic=view.findViewById(R.id.rcv_ComicList);
        //

        list_user = new ArrayList<>();
        Log.d("LIST", "LISTCOMIC: " + list_comic);
        comicsAdapter = new ComicsAdapter(getActivity(), list_comic, new ComicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ComicDTO comicDTO) {
                Intent intent = new Intent(getActivity(), DetailComicActivity.class);
                intent.putExtra("COMIC_EXTRA", comicDTO);
                intent.putExtra("USER_DATA_EXTRA", userDTO); // Truyền userData qua Intent
                Log.d("zzz"," COMICDTO" + comicDTO);
                Log.d("zzz"," COMICDTO USER" + userDTO);
                startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        rcvComic.setLayoutManager(gridLayoutManager);
        rcvComic.setAdapter(comicsAdapter);




    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(R.drawable.banner));
        return list;
    }
    private void autoSlide() {
        if (photoList == null || photoList.isEmpty() || vpr == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = vpr.getCurrentItem();
                        int toltalItem = photoList.size() - 1;
                        if (curentItem < toltalItem) {
                            curentItem++;
                            vpr.setCurrentItem(curentItem);
                        } else {
                            vpr.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 3000, 4000);
    }



    private void getComic(){
        // tạo gson
        Gson gson= new GsonBuilder().setLenient().create();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // sử dụng interface

        ComicInterface comicInterface=retrofit.create(ComicInterface.class);

        //tạo đối tượng

        Call<List<ComicDTO>> objCall= comicInterface.danh_sach_comic();
        objCall.enqueue(new Callback<List<ComicDTO>>() {
            @Override
            public void onResponse(Call<List<ComicDTO>> call, Response<List<ComicDTO>> response) {
                if(response.isSuccessful()){
                    list_comic.clear();
                    list_comic.addAll(response.body());
                    Log.d("zzzzzzzz", "onResponse: LIST COMIC"+list_comic);
                    Log.d("zzzzzzzzzzzz", "onResponse: LISTCOMIC"+ response.body());
                    comicsAdapter.notifyDataSetChanged();

                    //tạo adapter đổ lên listview


                }
                else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu" +response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ComicDTO>> call, Throwable t) {

                Log.e("RetrofitError", "onFailure: ", t);
                Toast.makeText(getActivity(), "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }






    @Override
    public void onResume() {
        super.onResume();
    }



}
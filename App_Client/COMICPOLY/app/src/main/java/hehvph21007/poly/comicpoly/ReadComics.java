package hehvph21007.poly.comicpoly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hehvph21007.poly.comicpoly.Adapter.viewpager2.ReadComicsAdapter;
import hehvph21007.poly.comicpoly.models.ComicDTO;

public class ReadComics extends AppCompatActivity {
    private ComicDTO comicDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comics);
        // Nhận thông tin comic từ Intent
        Intent intent = getIntent();
        comicDTO = intent.getParcelableExtra("COMIC_EXTRA");
        if (comicDTO != null) {
            // Hiển thị hình ảnh coverImage
            ImageView coverImageView = findViewById(R.id.readimg);
            Picasso.get().load(comicDTO.getImage()).into(coverImageView);

            // Lấy danh sách các hình ảnh từ comic
            List<String> images = comicDTO.getContent();

            // Hiển thị danh sách các hình ảnh bằng RecyclerView
            RecyclerView recyclerView = findViewById(R.id.readrec);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ReadComicsAdapter imageAdapter = new ReadComicsAdapter(images);
            recyclerView.setAdapter(imageAdapter);
            Log.d("TAG", "Danh sách images: " + images.toString());
            // ... (thực hiện các chức năng khác với thông tin comic)
        } else {
            // Xử lý trường hợp comic là null
        }
    }
}
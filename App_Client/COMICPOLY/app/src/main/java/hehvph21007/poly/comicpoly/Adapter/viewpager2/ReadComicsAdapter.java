package hehvph21007.poly.comicpoly.Adapter.viewpager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hehvph21007.poly.comicpoly.R;

public class ReadComicsAdapter extends RecyclerView.Adapter<ReadComicsAdapter.ReadComicsHolder>{
    private List<String> imageList;

    public ReadComicsAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ReadComicsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_comics_item, parent, false);
        return new ReadComicsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadComicsHolder holder, int position) {
        String imageUrl = imageList.get(position);
        Log.d("TAG", "URL_image thứ " + position + ": " + imageUrl);
        Picasso.get()
                .load(imageUrl)

                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ReadComicsHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ReadComicsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgread);


            // Customize the item view as needed
//            // For example, set the dimensions of the ImageView
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    100, // desired width
//                    100  // desired height
//            );
//            imageView.setLayoutParams(layoutParams);
        }
    }
}


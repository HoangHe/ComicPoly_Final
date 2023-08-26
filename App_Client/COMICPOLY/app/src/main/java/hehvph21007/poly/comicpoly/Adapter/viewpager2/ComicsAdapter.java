package hehvph21007.poly.comicpoly.Adapter.viewpager2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hehvph21007.poly.comicpoly.DetailComicActivity;
import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.models.ComicDTO;


public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {
    private Context context;
    private List<ComicDTO> list;
    private OnItemClickListener listener;
    private ComicDTO comicDTO;

    public ComicsAdapter(Context context, List<ComicDTO> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        comicDTO = list.get(position);


        if (comicDTO == null)
            return;

        Log.d("ddddddddd", "linkkkkkkkkkk: " + comicDTO.getImage());
        Picasso.get().load(comicDTO.getImage()).into(holder.imgComic);
        holder.tvName.setText(comicDTO.getName());

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgComic;
        private TextView tvName;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);

            imgComic = itemView.findViewById(R.id.imgComic);
            tvName = itemView.findViewById(R.id.item_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ComicDTO comicDTO = list.get(position);
                listener.onItemClick(comicDTO);
                Log.d("ITEM", "onClick: " + comicDTO);
            }
        }
    }

    // Interface để xử lý sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(ComicDTO comicDTO);
    }

}

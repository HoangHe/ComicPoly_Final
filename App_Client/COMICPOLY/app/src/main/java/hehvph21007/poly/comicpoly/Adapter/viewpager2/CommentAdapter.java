package hehvph21007.poly.comicpoly.Adapter.viewpager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.models.CommentDTO;
import hehvph21007.poly.comicpoly.models.UserDTO;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private List<CommentDTO> commentList;
    private List<UserDTO> userDataList;

    public CommentAdapter(List<CommentDTO> commentList, List<UserDTO> userDataList) {
        this.commentList = commentList;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDTO comment = commentList.get(position);
        holder.txtCommentContent.setText(comment.getContent());
        holder.txtTime.setText(comment.getTime());

        // Tìm username tương ứng với userId trong danh sách userDataList
        CommentDTO.UserDTO userDTO = comment.getId_user();
        holder.txtFullName.setText(userDTO.getFullName());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtCommentContent, txtFullName, txtTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtCommentContent = itemView.findViewById(R.id.txtComment);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }

    public void updateCommentList(List<CommentDTO> newComments) {
        commentList.clear();
        commentList.addAll(newComments);
        notifyDataSetChanged();
    }

    // Phương thức để tìm username theo userId
}

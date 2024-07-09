package com.example.btl.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.databinding.ItemPostBinding;
import com.example.btl.entites.PostEntity;

import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<PostEntity> posts;
    private Map<String, String> userNamesMap;
    private Map<String, String> userAvatarsMap;


    public PostAdapter(List<PostEntity> posts,Map<String, String> userNamesMap, Map<String, String> userAvatarsMap) {

        this.posts = posts;
        this.userNamesMap = userNamesMap;
        this.userAvatarsMap = userAvatarsMap;

    }

    public void updatePostList(List<PostEntity> newPostList) {
        posts.clear();
        posts.addAll(newPostList);
        notifyDataSetChanged();
    }
    public void updateUserNamesMap(Map<String, String> newUserNamesMap) {
        userNamesMap.clear();
        userNamesMap.putAll(newUserNamesMap);
        notifyDataSetChanged();
    }
    public void updateUserAvatarsMap(Map<String, String> newUserAvatarsMap) {
        userAvatarsMap.clear();
        userAvatarsMap.putAll(newUserAvatarsMap);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostEntity post = posts.get(position);
        if (post == null) {
            return;
        }
        holder.binding.tweetText.setText(post.getTitle());
        holder.binding.tweetDetails.setText(post.getContent());
        holder.binding.userName.setText(userNamesMap.get(post.getUserId()));
        Glide.with(holder.binding.tweetImage.getContext())
                .load(post.getImage())
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_noimage)
                .into(holder.binding.tweetImage);
        Glide.with(holder.binding.profileImage.getContext())
                .load(userAvatarsMap.get(post.getUserId()))
                .placeholder(R.drawable.ic_noimage)
                .error(R.drawable.ic_noimage)
                .into(holder.binding.profileImage);
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

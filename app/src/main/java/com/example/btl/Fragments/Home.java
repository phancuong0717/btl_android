package com.example.btl.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.btl.Adapters.PostAdapter;
import com.example.btl.R;
import com.example.btl.databinding.FragmentHomeBinding;
import com.example.btl.entites.PostEntity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    private FragmentHomeBinding binding;
    private View view;
    private PostAdapter adapter;
    private FirebaseFirestore db;
    private List<PostEntity> postList;
    private Map<String, String> userNamesMap;
    private Map<String, String> userAvatarsMap;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = binding.rcvListPost;
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new PostAdapter(new ArrayList<>(),new HashMap<>(), new HashMap<>());
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        postList = new ArrayList<>();
        userNamesMap = new HashMap<>();
        userAvatarsMap = new HashMap<>();
        loadPosts();
        return view;
    }


    private void getUserName(String userId) {
        db.collection("Users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String userName = task.getResult().getString("name");
                String userAvatar = task.getResult().getString("avatar");
                userNamesMap.put(userId, userName);
                userAvatarsMap.put(userId, userAvatar);
                adapter.updateUserNamesMap(userNamesMap);
                adapter.updateUserAvatarsMap(userAvatarsMap);


    private void loadPosts() {
        db.collection("posts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PostEntity post = document.toObject(PostEntity.class);
                            postList.add(post);
                            getUserName(post.getUserId());
                        }

                    } else {

                    }
                });
    }

    private void getUserName(String userId) {
        db.collection("Users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String userName = task.getResult().getString("name");
                String userAvatar = task.getResult().getString("avatar");
                userNamesMap.put(userId, userName);
                userAvatarsMap.put(userId, userAvatar);
                adapter.updateUserNamesMap(userNamesMap);
                adapter.updateUserAvatarsMap(userAvatarsMap);
                adapter.updatePostList(postList);
            } else {
            }
        });
    }
}

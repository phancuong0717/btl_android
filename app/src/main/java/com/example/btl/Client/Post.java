package com.example.btl.Client;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl.R;
import com.example.btl.databinding.ActivityPostBinding;
import com.example.btl.entites.PostEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Post extends AppCompatActivity {
    private ActivityPostBinding binding;
    FirebaseFirestore db;
    private Uri uri;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent data = o.getData();
                        if (data == null) {
                            return;
                        }
                        uri = data.getData();
                        binding.imageDetail.setImageURI(uri);
                    }


                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        binding.addPhotosButton.setOnClickListener(v -> {
            checkPermissionReadExternalStorage();
        });
        binding.btnSave.setOnClickListener(v -> {
            addPost();
            finish();
        });
        binding.btnCancel.setOnClickListener(v-> finish());


    }

    private void addPost() {
        String title = binding.title.getText().toString();
        String restaurantName = binding.restaurantName.getText().toString();
        String content = binding.contents.getText().toString();
        String image = uri.toString();
        String id = db.collection("posts").document().getId();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        PostEntity post = new PostEntity(id, title, content, userId, restaurantName, image,   "","",true);

        db.collection("posts").document(id).set(post)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Post added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding post", Toast.LENGTH_SHORT).show());
    }

    private void checkPermissionReadExternalStorage() {
        String readExternalImage = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            readExternalImage = android.Manifest.permission.READ_MEDIA_IMAGES;
        else
            readExternalImage = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, readExternalImage) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
            binding.addPhotosButton.setVisibility(View.GONE);
        } else
            ActivityCompat.requestPermissions(this, new String[]{readExternalImage}, 100);

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        launcher.launch(Intent.createChooser(intent, "Select image"));

    }
    private void queryPostById() {
        String title = binding.restaurantName.getText().toString().trim();
        if (!TextUtils.isEmpty(title)) {
            db.collection("posts")
                    .whereEqualTo("title", title)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                Toast.makeText(this, "Post ID: " + id, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No post found with the given title", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error getting posts", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
        }
    }
}
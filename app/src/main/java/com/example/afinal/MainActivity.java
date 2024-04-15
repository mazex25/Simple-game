package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.Database.DAO;
import com.example.afinal.User.User;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private ImageButton btnDel;
    private Button btnAdd, btnFinish;
    private TextView  userName;
    private Uri selectedImageUri;
    private static final int IMG_REQUEST = 1;
    public static DAO dao;
    public static User user;
    private static int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        ControlButton3();
        userName.setText("User: " + user.getFullname().toString());
    }

    private void Mapping() {
        img = findViewById(R.id.imageAdd);
        btnAdd = findViewById(R.id.btn_add);
        btnDel = findViewById(R.id.btn_del);
        btnFinish = findViewById(R.id.btn_finish);
        userName = findViewById(R.id.UserName);
    }

    private void ControlButton3() {
        ImageView[] imageViews = new ImageView[9]; // Tạo mảng ImageView với 9 phần tử
        // Khởi tạo từng ImageView trong mảng
        imageViews[0] = findViewById(R.id.view1);
        imageViews[1] = findViewById(R.id.view2);
        imageViews[2] = findViewById(R.id.view3);
        imageViews[3] = findViewById(R.id.view4);
        imageViews[4] = findViewById(R.id.view5);
        imageViews[5] = findViewById(R.id.view6);
        imageViews[6] = findViewById(R.id.view7);
        imageViews[7] = findViewById(R.id.view8);
        imageViews[8] = findViewById(R.id.view9);

        //
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMG_REQUEST);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUri != null) {
                    img.setImageURI(null);
                    selectedImageUri = null;
                }
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Đặt OnDragListener cho mỗi ImageView trong mảng 3x3
        for (ImageView imageView : imageViews) {
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN && imageView.getDrawable() != null) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
                        v.startDrag(data, shadowBuilder, imageView, 0);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            imageView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_ENTERED:
                            // Thay đổi màu của ô khi kéo hình ảnh vào
                            v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.green));
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            // Trở lại màu ban đầu khi kéo hình ảnh ra ngoài
                            v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.blue));
                            break;
                        case DragEvent.ACTION_DROP:
                            // Cập nhật của ô thành hình ảnh được kéo vào
                            ImageView droppedImageView = (ImageView) event.getLocalState();
                            if (v != droppedImageView) {
                                ((ImageView) v).setImageDrawable(droppedImageView.getDrawable());

                                // Xóa hình ảnh từ ImageView ban đầu
                                droppedImageView.setImageDrawable(null);

                                // Thay đổi màu nền của ô
                                v.setBackgroundColor(Color.GREEN);
                                score++;
                            }
                        case DragEvent.ACTION_DRAG_ENDED:
                            // Kiểm tra hình ảnh có thả vào imageview mới sau khi kéo vào
                            if (!event.getResult()) {
                                // Nếu không, đặt lại hình ảnh cho ImageView ban đầu
                                ImageView originalImageView = (ImageView) event.getLocalState();
                                originalImageView.setVisibility(View.VISIBLE);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

        }
        for (final ImageView imageView : imageViews) {
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN && imageView.getDrawable() != null) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
                        v.startDrag(data, shadowBuilder, v, 0);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        btnDel.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        // Khi hình ảnh được thả vào ImageButton, xóa hình ảnh từ ImageView ban đầu
                        ImageView droppedImageView = (ImageView) event.getLocalState();
                        droppedImageView.setImageDrawable(null);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu điểm vào new score
                user.setNewScore(score);
                // Chuyển sang score board và reset score về 0
                score = 0;
                Intent intent = new Intent(getApplicationContext(), ResultAndLogOut.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            // Lưu đường dẫn của hình ảnh đã chọn
            selectedImageUri = data.getData();
            // Hiển thị hình ảnh đã chọn
            img.setImageURI(selectedImageUri);
        }
    }
}
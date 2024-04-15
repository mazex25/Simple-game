package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.ControlButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.afinal.Database.DAO;

public class ResultAndLogOut extends AppCompatActivity {

    private TextView pScore, nScore;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_and_log_out);
        MappingRL();
        ControlButton4();
        pScore.setText("Previous score: " + MainActivity.user.getPreviousScore().toString());
        nScore.setText("New score: " + MainActivity.user.getNewSore().toString());
    }

    private void MappingRL() {
        pScore = findViewById(R.id.previousScore);
        nScore = findViewById(R.id.newScore);
        btnLogout = findViewById(R.id.btn_logout);
    }

    private void ControlButton4() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cap nhat diem new vao previous
                MainActivity.dao = new DAO(ResultAndLogOut.this);
                MainActivity.user.setPreviousScore(Integer.valueOf(MainActivity.user.getNewSore().toString()));
                MainActivity.dao.updatePreviousScore(MainActivity.user);
                //Quay lai login
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
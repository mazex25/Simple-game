package com.example.afinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afinal.Database.DAO;
import com.example.afinal.User.User;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUser, loginPassword;
    private Button btnLogIn, btnRegister, btnExit;
    private DAO dao;
    boolean passVisible;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MappingLogIn();
        ControlButton1();
        HideShowPass();
        dao = new DAO(this);
    }

    private void MappingLogIn() {
        loginUser = findViewById(R.id.loginUser);
        loginPassword = findViewById(R.id.loginPass);
        btnLogIn = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnExit = findViewById(R.id.btn_exit);
    }

    private void HideShowPass() {
        loginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;

                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=loginPassword.getRight()-loginPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = loginPassword.getSelectionEnd();
                        if (passVisible) {
                            // Doi icon
                            loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24, 0);
                            // An mat khau
                            loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        } else {
                            // Doi icon
                            loginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24, 0);
                            // Hien mat khau
                            loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible = true;
                        }
                        loginPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void ControlButton1() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khoi tao lai LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                // Tao su kien ket thuc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyen toi SignUpActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = loginUser.getText().toString().trim();
                password = loginPassword.getText().toString().trim();
                User userExist = dao.checkUsernameAndPassword(username, password);

                //Thong bao khi khong dien thong tin hoac thieu thong tin
                if (username.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                boolean checkCredentials = false;
                if (userExist != null) {
                    checkCredentials = dao.signIn(userExist);
                }
                //Thong bao dang nhap thanh cong va chuyen toi MainActivity
                if (checkCredentials) {
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    MainActivity.user = userExist;
                    startActivity(intent);
                } else {
                    //Thong bao nhap sai thong tin
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            loginUser.setText(data.getStringExtra("username"));
            loginPassword.setText(data.getStringExtra("password"));
        }
    }
}
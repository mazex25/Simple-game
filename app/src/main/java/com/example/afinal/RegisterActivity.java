package com.example.afinal;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtTK, edtMK, edtFN, edtCP;
    private Button btnSignUp, btnCancel;
    public DAO dao;
    boolean passVisible, confPassVisible;
    String username, password, fullname, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MappingSignUp();
        ControlButton2();
        HideShowPass();
        dao = new DAO(this);
    }

    private void MappingSignUp() {
        edtTK = findViewById(R.id.signUpUser);
        edtMK = findViewById(R.id.signUpPassword);
        edtFN = findViewById(R.id.signUpFullName);
        edtCP = findViewById(R.id.signUpConfirm);
        btnSignUp = findViewById(R.id.btn_signUp);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void HideShowPass() {
        edtMK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int R1 = 2;

                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=edtMK.getRight()-edtMK.getCompoundDrawables()[R1].getBounds().width()) {
                        int selection = edtMK.getSelectionEnd();
                        if (passVisible) {
                            // Doi icon
                            edtMK.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24, 0);
                            // An mat khau
                            edtMK.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible = false;
                        } else {
                            // Doi icon
                            edtMK.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24, 0);
                            // Hien mat khau
                            edtMK.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible = true;
                        }
                        edtMK.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        edtCP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int R2 = 2;
                if (event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=edtCP.getRight()-edtCP.getCompoundDrawables()[R2].getBounds().width()) {
                        int selection = edtCP.getSelectionEnd();
                        if (confPassVisible) {
                            // Doi icon
                            edtCP.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24, 0);
                            // An mat khau
                            edtCP.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confPassVisible = false;
                        } else {
                            // Doi icon
                            edtCP.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24, 0);
                            // Hien mat khau
                            edtCP.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confPassVisible = true;
                        }
                        edtCP.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void ControlButton2() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtTK.getText().toString().trim();
                password = edtMK.getText().toString().trim();
                fullname = edtFN.getText().toString().trim();
                confirmPassword = edtCP.getText().toString().trim();

                //Thong bao khi khong dien thong tin hoac thieu thong tin
                if (fullname.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    //Xac nhan mat khau
                    Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Kiem tra thong tin tai khoan
                if (dao.UserExited(username)) {
                    //Thong bao tai khoan da ton tai
                    Toast.makeText(RegisterActivity.this, "User already exists; Please login", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insertData(new User(null, fullname, username, password, null, null));
                    Toast.makeText(RegisterActivity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();

                    //Quay lai login
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            //chuyen ve LoginActivity
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
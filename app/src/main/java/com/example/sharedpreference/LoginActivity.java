package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // menginisialisasi variable dengan form user dan form password
        mViewUser=findViewById(R.id.et_emailSignin);
        mViewPassword=findViewById(R.id.et_passwordSignin);

        // menjalankan method checker(), jika tombol signin disentuh di keyboard
        mViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    razia();
                    return true;
                }
                return false;
            }
        });

        // menjalankan method razia() jika tombol signin disentuh
        findViewById(R.id.button_signinSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                razia();
            }
        });
        // RegisterActivity jika merasakan tombol signup disentuh
        findViewById(R.id.button_signupSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }
    }

    // mencheck inputan user dan password memberikan akses ke MainActivity
    private void razia(){
        // mereset semua error fokus menjadi default
        mViewUser.setError(null);
        mViewPassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        // mengambil text dari form user dan password dengan variable String
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        // jika form user kosong atau tidak memenuhi kriteria, maka di set error
        if (TextUtils.isEmpty(user)){
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        } else if (!cekUser(user)){
            mViewUser.setError("This username not found");
            fokus = mViewUser;
            cancel = true;
        }

        // jika form password kosong atau tidak memnuhi kriteria, maka di set error
        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if (!cekPassword(password)){
            mViewPassword.setError("This password incorrect");
            fokus = mViewPassword;
            cancel = true;
        }

        // jika cancel true, variable akan mendapatkan fokus
        if (cancel) fokus.requestFocus();
        else masuk();
    }

    // Menuju ke MainActivity dan Set User dan Status sedang login, di preferences
    private void masuk(){
        Preferences.setLoggedInUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(),true);
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    // True jika parameter password sama dengan data password yang terdaftar di sharedpreferences
    private boolean cekPassword(String password){
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    // True jika parameter user sama dengan data user yang terdaftar di sharedpreference
    private boolean cekUser(String user) {
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}

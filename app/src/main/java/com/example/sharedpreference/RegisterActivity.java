package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword, mViewRepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Menginisialisasi variable dengan form user, form password, dan form repassword
        mViewUser = findViewById(R.id.et_emailSignup);
        mViewPassword = findViewById(R.id.et_passwordSignup);
        mViewRepassword = findViewById(R.id.et_passwordSignup2);

        // Menjalankan method razia() jika tombol sign up sudah dipilih
        mViewRepassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    razia();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.button_signupSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                razia();
            }
        });
    }

    // Mencheck inputan user dan password, memberikan akses ke MainActivity
    private void razia(){
        // mereset semua error menjadi default
        mViewUser.setError(null);
        mViewPassword.setError(null);
        mViewRepassword.setError(null);
        View fokus = null;
        boolean cancel = false;

        // Mengambil text dari form user,password dan repassword dengan variable baru bertype String
        String repassword = mViewRepassword.getText().toString();
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();

        // Jika form user kosong atau memenuhi kriteria di cekUser() maka..
        if (TextUtils.isEmpty(user)){
            mViewUser.setError("This field is required");
            fokus = mViewUser;
            cancel = true;
        } else if(cekUser(user)){
            mViewUser.setError("This username already exist");
            fokus = mViewUser;
            cancel = true;
        }

        // Jika form password kosong atau memnuhi kriteria di method cekpassword, maka...
        if (TextUtils.isEmpty(password)){
            mViewPassword.setError("This field is required");
            fokus = mViewPassword;
            cancel = true;
        } else if(!cekPassword(password,repassword)){
            mViewRepassword.setError("This password is incorrect");
            fokus = mViewRepassword;
            cancel = true;
        }

        // Jika cancel true, variable fkus mendapatkan fokus, jika false kembali ke LoginActivity
        if (cancel){
            fokus.requestFocus();
        } else {
            Preferences.setRegisteredUser(getBaseContext(),user);
            Preferences.setRegisteredPass(getBaseContext(),password);
            finish();
        }
    }

    // Jika parameter password sama dengan repassword
    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }

    // Jika parameter user sama dengan data yang sama dengan user di data Preference
    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}

package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deklarasi dan Menginisialisasi variable nama dengan Label nama dari Layout MainActivity
        TextView nama = findViewById(R.id.tv_namaMain);

        // Menset Label nama dengan data user sedang login dari Preferences
        nama.setText(Preferences.getLoggedInUser(getBaseContext()));

        // Menset Status dan User yang sedang login menjadi default atau kosong
        findViewById(R.id.button_logoutMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menghapus Status login dan kembali ke LoginActivity
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        });
    }
}

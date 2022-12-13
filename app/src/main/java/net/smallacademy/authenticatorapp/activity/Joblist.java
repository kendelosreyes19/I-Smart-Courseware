package net.smallacademy.authenticatorapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.smallacademy.authenticatorapp.MainActivity;
import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.databinding.ActivityJoblistBinding;

public class Joblist extends AppCompatActivity {
    ImageView BackImage;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joblist);

        textView1 = findViewById(R.id.textViewLink);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        textView2 = findViewById(R.id.textViewLink2);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());

        BackImage = findViewById(R.id.go_imageJoblist);
        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
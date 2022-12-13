package net.smallacademy.authenticatorapp.authenticatorapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.smallacademy.authenticatorapp.MainActivity;
import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.authenticatorapp.QuizSplashActivity;
import net.smallacademy.authenticatorapp.authenticatorapp.adapter.CategoryAdapter;


public class CategoryActivity extends AppCompatActivity {
    GridView categorGridView;
    ImageView BackImage;
    Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        categorGridView = findViewById(R.id.categoryview);
        CategoryAdapter adapter = new CategoryAdapter(QuizSplashActivity.catList,CategoryActivity.this);
        categorGridView.setAdapter(adapter);

        BackImage = findViewById(R.id.go_imageCategory);
        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intent);
                CategoryActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
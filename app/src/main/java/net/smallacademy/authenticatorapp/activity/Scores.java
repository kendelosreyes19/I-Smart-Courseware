package net.smallacademy.authenticatorapp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import net.smallacademy.authenticatorapp.model.DownModel;
import net.smallacademy.authenticatorapp.holadapter.MyAdapter;
import net.smallacademy.authenticatorapp.R;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    ArrayList<DownModel> downModelArrayList = new ArrayList<>();
    MyAdapter myAdapter;
    ImageView BackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

    }
}

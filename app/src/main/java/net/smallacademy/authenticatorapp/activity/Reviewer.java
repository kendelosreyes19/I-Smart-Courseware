package net.smallacademy.authenticatorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import net.smallacademy.authenticatorapp.model.DownModeReviewer;
import net.smallacademy.authenticatorapp.MainActivity;
import net.smallacademy.authenticatorapp.holadapter.MyAdapterReviewer;
import net.smallacademy.authenticatorapp.R;

import java.util.ArrayList;

public class Reviewer extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    ArrayList<DownModeReviewer> downModeReviewerArrayList = new ArrayList<>();
    MyAdapterReviewer myAdapterReviewer;
    ImageView BackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewer);

        setUpRV();
        setUpFB();
        dataFromFirebase();

        BackImage = findViewById(R.id.go_imageReviewer);
        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }


    private void dataFromFirebase() {
        if(downModeReviewerArrayList.size()>0)
            downModeReviewerArrayList.clear();

        //db=FirebaseFirestore.getInstance();

        db.collection("reviewers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult()) {

                            DownModeReviewer downModeReviewer = new DownModeReviewer(documentSnapshot.getString("name"),
                                    documentSnapshot.getString("link"));
                            downModeReviewerArrayList.add(downModeReviewer);

                        }

                        myAdapterReviewer = new MyAdapterReviewer(Reviewer.this, downModeReviewerArrayList);
                        mRecyclerView.setAdapter(myAdapterReviewer);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Reviewer.this, "Error ;-.-;", Toast.LENGTH_SHORT).show();
                    }
                })
        ;


    }

    private void setUpFB(){
        db=FirebaseFirestore.getInstance();
    }

    private void setUpRV(){
        mRecyclerView= findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}

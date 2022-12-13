package net.smallacademy.authenticatorapp.authenticatorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.smallacademy.authenticatorapp.model.DownModel;
import net.smallacademy.authenticatorapp.MainActivity;
import net.smallacademy.authenticatorapp.holadapter.MyAdapter;
import net.smallacademy.authenticatorapp.R;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ScoresActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView total, total2, math, english, fili, science, tmath, teng, tfili, tsci, useName, useEmail, evaluation, baseOn;
    String text;
    FirebaseFirestore db,fStore;
    RecyclerView mRecyclerView;
    ArrayList<DownModel> downModelArrayList = new ArrayList<>();
    MyAdapter myAdapter;
    ImageView BackImage;
    String userId;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        setUpRV();
        setUpFB();
        dataFromFirebase();

        fAuth            = FirebaseAuth.getInstance();
        fStore           = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        useName = findViewById(R.id.nameScore);
        useEmail= findViewById(R.id.emailScore);

        math = findViewById(R.id.scoreMath);
        tmath = findViewById(R.id.tm);

        english = findViewById(R.id.scoreEnglish);
        teng = findViewById(R.id.te);

        science = findViewById(R.id.scoreScience);
        tsci = findViewById(R.id.ts);

        fili = findViewById(R.id.scoreFilipino);
        tfili = findViewById(R.id.tf);

        total = findViewById(R.id.scr_totalScore);
        total2 = findViewById(R.id.scr_totalItems);

        evaluation = findViewById(R.id.scr_evaluation);

        String hotdog = getResources().getString(R.string.helloKa);

        baseOn =  findViewById(R.id.baseSa);



        DocumentReference documentReference = fStore.collection("user score").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    useName.setText(documentSnapshot.getString("Full Name"));
                    useEmail.setText(documentSnapshot.getString("Email"));

                    math.setText(documentSnapshot.getString("Math"));
                    tmath.setText(documentSnapshot.getString("Total Math"));

                    english.setText(documentSnapshot.getString("English"));
                    teng.setText(documentSnapshot.getString("Total English"));

                    science.setText(documentSnapshot.getString("Science"));
                    tsci.setText(documentSnapshot.getString("Total Science"));

                    fili.setText(documentSnapshot.getString("Filipino"));
                    tfili.setText(documentSnapshot.getString("Total Filipino"));

                    total.setText(documentSnapshot.getString("Total Score"));
                    total2.setText(documentSnapshot.getString("Total Items"));

                    baseOn.setText("Your score in Filipino is " + documentSnapshot.getString("Filipino") +
                                    "/" + documentSnapshot.getString("Total Filipino") + ", in English " + documentSnapshot.getString("English")
                    + "/" + documentSnapshot.getString("Total English") + ", in Math " + documentSnapshot.getString("Math")
                            + "/" + documentSnapshot.getString("Total Math") + ", in Scienc e " + documentSnapshot.getString("Science")
                            + "/" + documentSnapshot.getString("Total Science") + ", and your Total Score is " + documentSnapshot.getString("Total Score")
                            + "/" + documentSnapshot.getString("Total Items") + ".");


                    evaluation.setText(hotdog + documentSnapshot.getString("Suggested Strand"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });



        BackImage = findViewById(R.id.go_imageScores);
        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void dataFromFirebase() {
        if(downModelArrayList.size()>0)
            downModelArrayList.clear();

        //db=FirebaseFirestore.getInstance();

        db.collection("certificate")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult()) {

                            DownModel downModel= new DownModel(documentSnapshot.getString("name"), documentSnapshot.getString("link"));
                            downModelArrayList.add(downModel);
                        }

                        myAdapter= new MyAdapter(ScoresActivity.this,downModelArrayList);
                        mRecyclerView.setAdapter(myAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ScoresActivity.this, "Error ;-.-;", Toast.LENGTH_SHORT).show();
                    }
                });
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
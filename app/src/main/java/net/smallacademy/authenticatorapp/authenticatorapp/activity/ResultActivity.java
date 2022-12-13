package net.smallacademy.authenticatorapp.authenticatorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.authenticatorapp.db.QuizDb;
import net.smallacademy.authenticatorapp.authenticatorapp.pref.Quiz;

import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {
    TextView start, score, total, resName, resEmail;
    int scrrr;
    TextView categoryName, ansCatName;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth fAuth;

    //Recycler Data
    String[] list;
    ResultAdapter resultAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        list = bundle.getString("list").split("~~~");

        recyclerView = findViewById(R.id.recycler_ans);
        resultAdapter = new ResultAdapter(list);
        recyclerView.setAdapter(resultAdapter);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        resName = findViewById(R.id.resultName);
        resEmail = findViewById(R.id.resultEmail);

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    resName.setText(documentSnapshot.getString("Full Name"));
                    resEmail.setText(documentSnapshot.getString("Email"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        start = findViewById(R.id.start);
        score = findViewById(R.id.score);
        total = findViewById(R.id.total);
        categoryName = findViewById(R.id.catsName);
        ansCatName = findViewById(R.id.resultAnsType);

        String score_str = getIntent().getStringExtra("score");
        String total_str = getIntent().getStringExtra("total");

        Quiz quiz = new Quiz(ResultActivity.this);


        if (quiz.getQuiz(QuizDb.subType).contains(QuizDb.math)) {

            categoryName.setText("Math Score");
            ansCatName.setText("Math Answers");

            quiz.setQuiz(score_str, QuizDb.mcor);
            quiz.setQuiz(total_str, QuizDb.mtot);
            quiz.setQuiz("1", QuizDb.msta);

        } else if (quiz.getQuiz(QuizDb.subType).contains(QuizDb.english)) {

            categoryName.setText("English Score");
            ansCatName.setText("English Answers");

            quiz.setQuiz(score_str, QuizDb.ecor);
            quiz.setQuiz(total_str, QuizDb.etot);
            quiz.setQuiz("1", QuizDb.esta);

        } else if (quiz.getQuiz(QuizDb.subType).contains(QuizDb.science)) {

            categoryName.setText("Science Score");
            ansCatName.setText("Science Answers");

            quiz.setQuiz(score_str, QuizDb.scor);
            quiz.setQuiz(total_str, QuizDb.stot);
            quiz.setQuiz("1", QuizDb.ssta);

        } else if (quiz.getQuiz(QuizDb.subType).contains(QuizDb.fil) || quiz.getQuiz(QuizDb.subType).contains("phi")) {

            categoryName.setText("Filipino Score");
            ansCatName.setText("Filipino Answers");

            quiz.setQuiz(score_str, QuizDb.fcor);
            quiz.setQuiz(total_str, QuizDb.ftot);
            quiz.setQuiz("1", QuizDb.fsta);

        }

        scrrr = Integer.parseInt(score_str);
        int ttlll = Integer.parseInt(total_str);

        String grd = "";

        try {

            if (ttlll == scrrr) {
                grd = "<font color='#008577' size='16px'> Rating :A</font>";
            } else if (ttlll / 2 < scrrr) {
                grd = "<font color='#008577' size='16px'> Rating :B</font>";
            } else {
                grd = "<font color='#008577' size='16px'> Rating :C</font>";
            }

        } catch (Exception e) {

            Log.d("errrrrrrrrrrr", "onCreate: " + e.toString());
        }


        String grad = "/" + total_str + " " + grd;
        total.setText(Html.fromHtml(grad));
        score.setText(Html.fromHtml(score_str));


        start.setOnClickListener(v -> {

            String getName = resName.getText().toString();
            String getEmail = resEmail.getText().toString();

            userId = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference2 = fStore.collection("user score").document(userId);

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("Full Name", getName);
            hashMap.put("Email", getEmail);

            documentReference2.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    ResultActivity.this.finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ResultActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        });


    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(ResultActivity.this, CategoryActivity.class));
        finish();

    }
}


class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {


    String[] dt;

    public ResultAdapter(String[] dt) {
        this.dt = dt;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ans, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {


        String qq = dt[position].split("~~")[0];
        String aa = dt[position].split("~~")[1];

        holder.questionsText.setText(qq);
        holder.answerText.setText(aa);

    }

    @Override
    public int getItemCount() {
        return dt.length;
    }

    static class ResultHolder extends RecyclerView.ViewHolder {
        TextView questionsText, answerText;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            questionsText = itemView.findViewById(R.id.ans_que);
            answerText = itemView.findViewById(R.id.ans_ans);

        }
    }

}


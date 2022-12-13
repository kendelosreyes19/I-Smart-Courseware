package net.smallacademy.authenticatorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import net.smallacademy.authenticatorapp.activity.AboutUs;
import net.smallacademy.authenticatorapp.activity.ContactUs;
import net.smallacademy.authenticatorapp.activity.Joblist;
import net.smallacademy.authenticatorapp.activity.Login;
import net.smallacademy.authenticatorapp.activity.Profile;
import net.smallacademy.authenticatorapp.activity.Reviewer;
import net.smallacademy.authenticatorapp.activity.Strands;
import net.smallacademy.authenticatorapp.authenticatorapp.QuizSplashActivity;
import net.smallacademy.authenticatorapp.authenticatorapp.activity.ScoresActivity;
import net.smallacademy.authenticatorapp.authenticatorapp.db.QuizDb;
import net.smallacademy.authenticatorapp.authenticatorapp.pref.Quiz;
import net.smallacademy.authenticatorapp.user.LogData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView mExam, mStrands, mReviewer, mJoblist, mResult, mainActName, mainActEmail, mainEvaluation, mainTime;
    TextView total, total2, math, english, fili, science, tmath, teng, tfili, tsci;
    TextView fetchtotal, fetchtotal2, fetmath, fetmathtot, feteng, fetengtot, fetsci, fetscietot, fetfil, fetfiltot;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Quiz quiz = new Quiz(MainActivity.this);

        math = findViewById(R.id.scoreMath);
        tmath = findViewById(R.id.tm);

        english = findViewById(R.id.scoreEng);
        teng = findViewById(R.id.te);

        fili = findViewById(R.id.scoreFil);
        tfili = findViewById(R.id.tf);

        science = findViewById(R.id.scoreScie);
        tsci = findViewById(R.id.ts);

        total = findViewById(R.id.totalSco);
        total2 = findViewById(R.id.totalItems);

        mainEvaluation = findViewById(R.id.txtEvaluation);

        //Correct Questions

        String m = quiz.getQuiz(QuizDb.mcor);
        String e = quiz.getQuiz(QuizDb.ecor);
        String f = quiz.getQuiz(QuizDb.fcor);
        String s = quiz.getQuiz(QuizDb.scor);

        math.setText(m);
        english.setText(e);
        fili.setText(f);
        science.setText(s);

        //Total Questions

        String tm = quiz.getQuiz(QuizDb.mtot);
        String te = quiz.getQuiz(QuizDb.etot);
        String tf = quiz.getQuiz(QuizDb.ftot);
        String ts = quiz.getQuiz(QuizDb.stot);

        tmath.setText(tm);
        teng.setText(te);
        tfili.setText(tf);
        tsci.setText(ts);

        //Fetching Scores

        fetmath = findViewById(R.id.fetchMath);
        fetmathtot = findViewById(R.id.fetchMathTot);

        feteng = findViewById(R.id.fetchEng);
        fetengtot = findViewById(R.id.fetchEngTot);

        fetfil = findViewById(R.id.fetchFil);
        fetfiltot = findViewById(R.id.fetchFilTot);

        fetsci = findViewById(R.id.fetchScie);
        fetscietot = findViewById(R.id.fetchScieTot);

        fetchtotal = findViewById(R.id.fetchTotalScore);
        fetchtotal2 = findViewById(R.id.fetchTotalItems);


        try {
            int ttl = Integer.parseInt(m) + Integer.parseInt(e) + Integer.parseInt(f) + Integer.parseInt(s);
            total.setText(String.valueOf(ttl));

            int ttl2 = Integer.parseInt(tm) + Integer.parseInt(te) + Integer.parseInt(tf) + Integer.parseInt(ts);
            total2.setText(String.valueOf(ttl2));

        } catch (Exception ignored) {

        }

        try {
            if ((Integer.parseInt(m) > Integer.parseInt(e) && Integer.parseInt(m) > Integer.parseInt(f))
                    &&
                    (Integer.parseInt(s) > Integer.parseInt(e) && Integer.parseInt(s) > Integer.parseInt(f))){
                mainEvaluation.setText("STEM STRAND");
            }

            else if ((Integer.parseInt(e) > Integer.parseInt(s) && Integer.parseInt(e) > Integer.parseInt(f))
                    &&
                    (Integer.parseInt(m) > Integer.parseInt(s) && Integer.parseInt(m) > Integer.parseInt(f))){
                mainEvaluation.setText("ABM STRAND");
            }

            else if ((Integer.parseInt(f) > Integer.parseInt(s) && Integer.parseInt(f) > Integer.parseInt(m))
                    &&
                    (Integer.parseInt(e) > Integer.parseInt(s) && Integer.parseInt(e) > Integer.parseInt(m))){
                mainEvaluation.setText("HUMSS STRAND");
            }

            else {
                mainEvaluation.setText("GAS STRAND");
            }
        } catch (Exception ignored) {

        }

        fAuth            = FirebaseAuth.getInstance();
        fStore           = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        mainActName = findViewById(R.id.txtUseName);
        mainActEmail = findViewById(R.id.txtUseEmailAdd);

        mainTime = findViewById(R.id.textTime);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        mainTime.setText(dateTime);

        DocumentReference documentReference = fStore.collection("user score").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    mainActName.setText(documentSnapshot.getString("Full Name"));
                    mainActEmail.setText(documentSnapshot.getString("Email"));


                    fetmath.setText(documentSnapshot.getString("Math"));
                    fetmathtot.setText(documentSnapshot.getString("Total Math"));

                    feteng.setText(documentSnapshot.getString("English"));
                    fetengtot.setText(documentSnapshot.getString("Total English"));

                    fetfil.setText(documentSnapshot.getString("Filipino"));
                    fetfiltot.setText(documentSnapshot.getString("Total Filipino"));

                    fetsci.setText(documentSnapshot.getString("Science"));
                    fetscietot.setText(documentSnapshot.getString("Total Science"));

                    fetchtotal.setText(documentSnapshot.getString("Total Score"));
                    fetchtotal2.setText(documentSnapshot.getString("Total Items"));

                    Log.d("tag", "onEvent: Document do not exists");

                }
            }
        });

        LogData data = new LogData(MainActivity.this);
        mExam = findViewById(R.id.textExam);
        mExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(total.getText().toString().isEmpty())) {

                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You can't take the exam anymore.").setPositiveButton("Okay", dialogClickListener)
                            .show();

                }
                else if (!(fetchtotal.getText().toString().isEmpty())){
                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            dialog.dismiss();
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You can't take the exam anymore.").setPositiveButton("Okay", dialogClickListener)
                            .show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), QuizSplashActivity.class));
                }
            }
        });

        mStrands = findViewById(R.id.textStrands);
        mStrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Strands.class));
            }
        });

        mReviewer = findViewById(R.id.textReview);
        mReviewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Reviewer.class));
            }
        });

        mJoblist = findViewById(R.id.textJoblist);
        mJoblist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Joblist.class));
            }
        });

        mResult = findViewById(R.id.textScore);
        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(total.getText().toString().isEmpty())) {

                    String getName = mainActName.getText().toString();
                    String getEmail = mainActEmail.getText().toString();

                    String getMath = math.getText().toString();
                    String getTotalMath = tmath.getText().toString();

                    String getEng = english.getText().toString();
                    String getTotalEng = teng.getText().toString();

                    String getScie = science.getText().toString();
                    String getTotalScie = tsci.getText().toString();

                    String getFil = fili.getText().toString();
                    String getTotalFil = tfili.getText().toString();

                    String getTotal = total.getText().toString();
                    String getTotalItems = total2.getText().toString();

                    String getStrand = mainEvaluation.getText().toString();
                    String getTime = mainTime.getText().toString();

                    userId = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("user score").document(userId);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Full Name", getName);
                    hashMap.put("Email", getEmail);

                    hashMap.put("Math", getMath);
                    hashMap.put("Total Math", getTotalMath);

                    hashMap.put("English", getEng);
                    hashMap.put("Total English", getTotalEng);

                    hashMap.put("Science", getScie);
                    hashMap.put("Total Science", getTotalScie);

                    hashMap.put("Filipino", getFil);
                    hashMap.put("Total Filipino", getTotalFil);

                    hashMap.put("Total Score", getTotal);
                    hashMap.put("Total Items", getTotalItems);

                    hashMap.put("Suggested Strand", getStrand);
                    hashMap.put("Examination Date", getTime);

                    documentReference.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(getApplicationContext(),ScoresActivity.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Total Questions

                }
                else if (!(fetchtotal.getText().toString().isEmpty())){
                    startActivity(new Intent(getApplicationContext(),ScoresActivity.class));
                }
                else {
                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            startActivity(new Intent(getApplicationContext(), QuizSplashActivity.class));
                        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.dismiss();
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please take all the exam first.").setPositiveButton("Exam", dialogClickListener)
                            .setNegativeButton("Cancel", dialogClickListener)
                            .show();
                }
            }
        });

        // codes of toolbar

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;

                    case R.id.nav_profile:

                        Intent profileIntent = new Intent(MainActivity.this, Profile.class);
                        startActivity(profileIntent);
                        break;

                    case R.id.nav_aboutUs:

                        Intent AboutUsIntent = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(AboutUsIntent);
                        break;

                    case R.id.nav_contactUs:

                        Intent ContactUsIntent = new Intent(MainActivity.this, ContactUs.class);
                        startActivity(ContactUsIntent);
                        break;

                    case R.id.nav_logout:

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage("Are you sure that you want to log out?")
                                .setPositiveButton("YES", (dialogInterface, i) -> {
                                    FirebaseAuth.getInstance().signOut();


//                                        File sprefs_directory = new File(Environment.getDataDirectory() + "/data/" + getPackageName() + "/shared_prefs");
//                                        File[] files = sprefs_directory.listFiles();
//                                        for(File file : files) {
//                                            file.delete();
//                                        }

                                    getApplicationContext().getSharedPreferences("email", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("pass", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("login", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("uid", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("fname", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("phone", 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences("add", 0).edit().clear().apply();


                                    getApplicationContext().getSharedPreferences(QuizDb.subType, 0).edit().clear().apply();

                                    getApplicationContext().getSharedPreferences(QuizDb.math, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.mcor, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.mtot, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.msta, 0).edit().clear().apply();

                                    getApplicationContext().getSharedPreferences(QuizDb.english, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.ecor, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.etot, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.esta, 0).edit().clear().apply();

                                    getApplicationContext().getSharedPreferences(QuizDb.fil, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.fcor, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.ftot, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.fsta, 0).edit().clear().apply();

                                    getApplicationContext().getSharedPreferences(QuizDb.science, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.scor, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.stot, 0).edit().clear().apply();
                                    getApplicationContext().getSharedPreferences(QuizDb.ssta, 0).edit().clear().apply();

                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                }).setNegativeButton("NO", null);

                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
                return false;
            }
        });

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}

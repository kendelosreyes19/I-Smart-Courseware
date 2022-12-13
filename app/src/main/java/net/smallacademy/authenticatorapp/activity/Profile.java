package net.smallacademy.authenticatorapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import net.smallacademy.authenticatorapp.MainActivity;
import net.smallacademy.authenticatorapp.R;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;

    EditText fullName,email,phone,addre;
    TextView verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button changeProfileImage;
    FirebaseUser user;
    ImageView profileImage,BackImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phone          = findViewById(R.id.profilePhone);
        phone.setFocusable(false);
        phone.setEnabled(false);
        phone.setCursorVisible(false);
        phone.setKeyListener(null);
        fullName       = findViewById(R.id.profileName);
        fullName.setFocusable(false);
        fullName.setEnabled(false);
        fullName.setCursorVisible(false);
        fullName.setKeyListener(null);
        email          = findViewById(R.id.profileEmail);
        email.setFocusable(false);
        email.setEnabled(false);
        email.setCursorVisible(false);
        email.setKeyListener(null);
        addre          = findViewById(R.id.profileMainAddress);
        addre.setFocusable(false);
        addre.setEnabled(false);
        addre.setCursorVisible(false);
        addre.setKeyListener(null);
        BackImage      = findViewById(R.id.go_imageProfile);
        profileImage       = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);
        fAuth            = FirebaseAuth.getInstance();
        fStore           = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();



        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        resendCode = findViewById(R.id.resendCode);
        verifyMsg  = findViewById(R.id.verifyMsg);

        userId = fAuth.getCurrentUser().getUid();
        user   = fAuth.getCurrentUser();

        // verifying email

        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if((!user.isEmailVerified())){
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                            }
                        });
                    }
                    else if((user.isEmailVerified())){
                        Toast.makeText(v.getContext(), "Your email is already been verified.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    phone.setText(documentSnapshot.getString("Phone Number"));
                    fullName.setText(documentSnapshot.getString("Full Name"));
                    email.setText(documentSnapshot.getString("Email"));
                    addre.setText(documentSnapshot.getString("Address"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        BackImage = findViewById(R.id.go_imageProfile);
        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // changing profile picture

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("Full Name",fullName.getText().toString());
                i.putExtra("Email",email.getText().toString());
                i.putExtra("Phone Number",phone.getText().toString());
                i.putExtra("Address",addre.getText().toString());
                startActivity(i);
            }
        });
    }
}

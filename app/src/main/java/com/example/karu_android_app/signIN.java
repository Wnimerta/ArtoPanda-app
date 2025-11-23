package com.example.karu_android_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signIN extends AppCompatActivity implements OnClickListener{
    private EditText signInEmail,signInPass;
    private TextView signUpTextView;
    private Button signInBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = mAuth.getCurrentUser();
        if(firebaseuser!= null){
            startActivity(new Intent(getApplicationContext(), dashboard.class));
        }


        signInEmail=findViewById(R.id.emailText);
        signInPass=findViewById(R.id.passwordText);

        signUpTextView=findViewById(R.id.newHere);
        signInBtn=findViewById(R.id.logInBTN);

        signUpTextView.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.logInBTN) {
            userLogin();
        } else if (viewId == R.id.newHere) {
            Intent intent = new Intent(getApplicationContext(), signup.class);
            startActivity(intent);
        }


    }

    private void userLogin() {
        String email = signInEmail.getText().toString().trim();
        String pass = signInPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            signInEmail.setError("Email Required");
            signInEmail.requestFocus();
            return;
        }
        if(! Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInEmail.setError("Enter a valid email");
            signInEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            signInPass.setError("Password Required");
            signInEmail.requestFocus();
            return;
        }
        if(pass.length()<8){
            signInPass.setError("Password must be at least eight digit");
             signInEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                   Intent intent = new Intent(getApplicationContext(),dashboard.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }
}
package com.example.unified;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText login_email,login_password;
    Button Sign_in;
    TextView SIGNUP;
    ProgressBar login_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email= findViewById(R.id.loginemail);
        login_password=findViewById(R.id.loginpassword);
        Sign_in=findViewById(R.id.Signinbtn);
        login_progress=findViewById(R.id.loginprogressbar);
        SIGNUP=findViewById(R.id.sign_uploginwindow);

        Sign_in.setOnClickListener(v->Signinfunc());
        SIGNUP.setOnClickListener(v->startActivity(new Intent(Login.this,Signup.class)));
    }
    void Signinfunc(){
        String Email= login_email.getText().toString();
        String pass= login_password.getText().toString();
        boolean isAuthentication= Authentication(Email,pass);
        if(!isAuthentication){
            return;
        }
        Loginaccountinfirebase(Email,pass);

    }
    void Loginaccountinfirebase(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        changeinProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeinProgress(false);
                if (task.isSuccessful() ){
                    //login is successfull
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent( Login.this,MainActivity.class));
                        // go to main activity
                        finish();
                    }
                    else{//toast of verify mail
                        Utility.ShowToast(Login.this,"E-mail not verified , Please verify your E-mail");
                    }
                }
                else{
                    //login failed
                    Utility.ShowToast(Login.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeinProgress(boolean InProgress){
        if (InProgress) {
            login_progress.setVisibility(View.VISIBLE);
            Sign_in.setVisibility(View.GONE);
        }
        else{
            login_progress.setVisibility(View.GONE );
            Sign_in.setVisibility(View.VISIBLE);
        }
    }
    boolean Authentication(String Email,String pass) {
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            login_email.setError("Email is invalid");
            return false;
        }
        if (pass.length() <6){
            login_password.setError("Length of password must be greater than 6");
            return false;
        }
        return true;
    }
}
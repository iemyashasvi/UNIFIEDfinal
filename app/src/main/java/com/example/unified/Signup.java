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

public class Signup extends AppCompatActivity {
    EditText editTextTextPersonNamev,passwordv,confirmpasswordv;
    Button sign_up;
    ProgressBar progress_bar2;
    TextView loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextTextPersonNamev= findViewById(R.id.editTextTextPersonName);
        passwordv=findViewById(R.id.password);
        confirmpasswordv=findViewById(R.id.confirmpassword);
        sign_up=findViewById(R.id.signup);
        progress_bar2=findViewById(R.id.loginbar);
        loginbtn=findViewById(R.id.login_btn);
        sign_up.setOnClickListener(v->Sign_Up());
        loginbtn.setOnClickListener(v->startActivity(new Intent(Signup.this,Login.class)));
    }

    void Sign_Up(){
        String Email= editTextTextPersonNamev.getText().toString();
        String pass= passwordv.getText().toString();
        String cpass=confirmpasswordv.getText().toString();
        boolean isAuthentication= Authentication(Email,pass,cpass);
        if(!isAuthentication){
             return;
        }
        createAccountinFirebase(Email,pass);

    }
    void createAccountinFirebase(String Email,String pass){
        changeinProgress(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance() ;
        firebaseAuth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(Signup.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Utility.ShowToast(Signup.this, "Account Created Successfully");
                    //CREATE ACCOOUNT IS DONE
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else {
                    changeinProgress(false);
                    //FAILED CREATING ACCOUNT
                    Utility.ShowToast(Signup.this, task.getException().getLocalizedMessage());
                }

            }
        });

    }
    void changeinProgress(boolean InProgress){
        if (InProgress) {
            progress_bar2.setVisibility(View.VISIBLE);
            sign_up.setVisibility(View.GONE);
        }
        else{
            progress_bar2.setVisibility(View.GONE );
            sign_up.setVisibility(View.VISIBLE);
        }
    }

    boolean Authentication(String Email,String pass, String cpass) {
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            editTextTextPersonNamev.setError("Email is invalid");
            return false;
        }
        if (pass.length() <6){
            passwordv.setError("Length of password must be greater than 6");
            return false;
        }
        if(!pass.equals(cpass)){
            confirmpasswordv.setError("Both passwords do not match");
            return false;
        }
        return true;
    }
}
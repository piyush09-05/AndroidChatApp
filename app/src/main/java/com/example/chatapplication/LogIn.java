package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatapplication.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityLogInBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());
         mAuth = FirebaseAuth.getInstance();

         progressDialog = new ProgressDialog(LogIn.this);
         progressDialog.setTitle("Logging In");
         progressDialog.setMessage("Just a moment");

        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(LogIn.this, ChatActivity.class);
            startActivity(intent);
        }

         binding.button.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {
                 if(!binding.username.getText().toString().isEmpty() && !binding.password.getText().toString().isEmpty()){
                     progressDialog.show();
                     mAuth.signInWithEmailAndPassword(binding.username.getText().toString(), binding.password.getText().toString())
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     progressDialog.dismiss();
                                     if(task.isSuccessful()){
                                         Intent i = new Intent(LogIn.this, ChatActivity.class);
                                         startActivity(i);
                                     }
                                     else{
                                         task.addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         });
                                     }
                                 }
                             });
                 }
                 else{
                     Toast.makeText(LogIn.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                 }
             }
         });
         binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(LogIn.this, SignUpActivity.class);
                 startActivity(intent);
             }
         });

    }
}
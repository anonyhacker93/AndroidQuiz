package com.sheoran.dinesh.androidquiz.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.model.User;
public class UserAccountActivity extends BaseActivity {

    private EditText userName;
    private EditText userPwd;

    private DatabaseReference usersReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userName = findViewById(R.id.userName);
        userPwd = findViewById(R.id.userPwd);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignin = findViewById(R.id.btnSignin);
        initFirebase();

        btnSignin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                signIn();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                login();
            }
        });
    }

    private void initFirebase(){
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");
    }

    private void signIn(){
        final ProgressDialog progressDialog = getProgressDialog(R.string.sign_up_dialog_title,R.string.login_progress_dialog_msg);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserAccountActivity.this);
        alertDialog.setTitle("Sign In");
        alertDialog.setMessage(getResourceString(R.string.sign_fragment_title));
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.signin,null);



        alertDialog.setPositiveButton("SignIn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
               progressDialog.show();
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        EditText edName = view.findViewById(R.id.newUserName);
                        EditText edEmail = view.findViewById(R.id.newUserEmail);
                        EditText edPwd = view.findViewById(R.id.newUserPwd);
                        final String nUserName = edName.getText().toString();
                        final String nUserEmail = edEmail.getText().toString();
                        final String nUserPwd = edPwd.getText().toString();

                       User userInfo = new User(nUserName,nUserEmail,nUserPwd);
                       if(dataSnapshot.child(nUserName).exists()){
                           Toast.makeText(UserAccountActivity.this, "User already exist !", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           usersReference.child(nUserName).setValue(userInfo);
                           Toast.makeText(UserAccountActivity.this, "Account created successfully !", Toast.LENGTH_SHORT).show();
                           dialogInterface.dismiss();
                       }
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    private void login(){
    final String uName = userName.getText().toString();
    final String uPwd = userPwd.getText().toString();
    final ProgressDialog progressDialog = getProgressDialog(R.string.login_progress_dialog_title,R.string.login_progress_dialog_msg);
        progressDialog.show();
    usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.child(uName).exists()){

             if(!uPwd.isEmpty() && !uName.isEmpty()) {
                 User userInfo = dataSnapshot.child(uName).getValue(User.class);
                 if (uPwd.equals(userInfo.getPassword())) {
                     Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
                     startActivity(intent);
                     Toast.makeText(UserAccountActivity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                     finish();
                 } else {
                     Toast.makeText(UserAccountActivity.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                 }
             }else{
                 if(uName.isEmpty()){
                     Toast.makeText(UserAccountActivity.this, "Please enter user name ", Toast.LENGTH_SHORT).show();
                 }else {
                     Toast.makeText(UserAccountActivity.this, "Please enter password ", Toast.LENGTH_SHORT).show();
                 }
             }
            }
            else {
                Toast.makeText(UserAccountActivity.this, "User not existed !", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    });
    }


}

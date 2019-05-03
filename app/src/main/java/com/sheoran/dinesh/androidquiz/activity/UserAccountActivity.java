package com.sheoran.dinesh.androidquiz.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.databinding.LoginBinding;
import com.sheoran.dinesh.androidquiz.databinding.SigninBinding;
import com.sheoran.dinesh.androidquiz.firebase.FirebaseConfigManager;
import com.sheoran.dinesh.androidquiz.firebase.UserAccounFirebaseConfig;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeAdapter;
import com.sheoran.dinesh.androidquiz.model.User;
import com.sheoran.dinesh.androidquiz.util.ProgressDialogUtil;

public class UserAccountActivity extends BaseActivity {

    private LoginBinding _binding;

    private UserAccounFirebaseConfig _userAccounFirebaseConfig;
    private FirebaseConfigManager _firebaseConfigManager;

    private ProgressDialog _progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = DataBindingUtil.setContentView(this, R.layout.login);
        _binding.setUserInstance(new User(_binding.userName.getText().toString(),"",_binding.userPwd.getText().toString()));
        init();


        _binding.btnSignin.setOnClickListener((view) -> {

            signIn();
        });

        _binding.btnLogin.setOnClickListener((view) -> {
           /* User user = _binding.getUserInstance();
            user.setName(_binding.userName.getText().toString());
            user.setPassword(_binding.userPwd.getText().toString());
            user.setEmail("");*/
            login(new User("a","a","a"));
        });
    }

    private void init() {
        _progressDialog = ProgressDialogUtil.getDialog(this,"Please wait","Loading");
        _firebaseConfigManager = FirebaseConfigManager.getInstance(this);
        _userAccounFirebaseConfig = _firebaseConfigManager.getUserAccountConfig();

        _userAccounFirebaseConfig.setFirebaseDataChangeNotifier(new FirebaseDataChangeAdapter() {
            @Override
            public void onLoad(boolean isSuccess, String msg) {
                _progressDialog.dismiss();
                Toast.makeText(UserAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                if (isSuccess) {
                    Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAdd(boolean isSucess, String msg) {
                _progressDialog.dismiss();
                Toast.makeText(UserAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signIn() {
        _progressDialog = ProgressDialogUtil.getDialog(this, "Signin", "Please wait");

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserAccountActivity.this);
        alertDialog.setTitle("Sign In");
        alertDialog.setMessage(getResourceString(R.string.sign_fragment_title));
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        SigninBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.signin, null, false);
        alertDialog.setView(binding.getRoot());
        alertDialog.show();

        alertDialog.setPositiveButton("SignIn", (final DialogInterface dialogInterface, int i) -> {
            User user = binding.getUser();
            _progressDialog.show();
            _userAccounFirebaseConfig.requestToCreateAccount(user);
        });

        alertDialog.setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {
            dialogInterface.dismiss();
        });


    }

    private void login(User user) {

        final ProgressDialog progressDialog = ProgressDialogUtil.getDialog(this, "Authenticating", "Login...");
        progressDialog.show();
        _userAccounFirebaseConfig.requestToLogin(user);

    }


}

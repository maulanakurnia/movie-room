package com.maulanakurnia.movieroom.ui.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.ui.viewmodel.UserViewModel;
import com.maulanakurnia.movieroom.utils.SharedPrefConfig;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class LoginActivity extends AppCompatActivity {

    protected TextView register;
    protected Button btn_login;
    protected EditText tv_username, tv_password;
    protected UserViewModel userViewModel;
    private SharedPrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        userViewModel = new ViewModelProvider.AndroidViewModelFactory(LoginActivity.this.getApplication()).create(UserViewModel.class);

        tv_username     = findViewById(R.id.login_username);
        tv_password     = findViewById(R.id.login_password);
        btn_login       = findViewById(R.id.login_submit);
        register        = findViewById(R.id.login_register);

        prefConfig = new SharedPrefConfig(getApplicationContext());
        if(prefConfig.readLoginStatus()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        tv_password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                loginCheck();
                return true;
            }
            return false;
        });

        register.setOnClickListener(v -> startActivity(new Intent(getBaseContext(),RegisterActivity.class)));
        btn_login.setOnClickListener(v -> {
            loginCheck();
        });

    }

    private void loginCheck() {
        tv_username.setError(null);
        tv_password.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String username = tv_username.getText().toString();
        String password = tv_password.getText().toString();

        if (TextUtils.isEmpty(username)){
            tv_username.setError("This field is required");
            focused = tv_username;
            cancel.set(true);
        }

        if (TextUtils.isEmpty(password)){
            tv_password.setError("This field is required");
            focused = tv_username;
            cancel.set(true);
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();
        }
        else {
            userViewModel.repository.checkUsername(username).observe(this, musername -> {
                if(musername == null) {
                    tv_username.setError("This Username is not found");
                    tv_username.requestFocus();
                    cancel.set(true);
                }
                if(!cancel.get()) {
                    userViewModel.login(username, password).observe(this, user -> {
                        if(user == null) {
                            tv_password.setError("Invalid Password");
                            tv_password.requestFocus();
                            cancel.set(true);
                        }
                        if(!cancel.get()) {
                            Intent intent = new Intent(this, MainActivity.class);
                            SharedPreferences sharedPreferences=getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("sp_name", username);
                            editor.apply();
                            prefConfig.writeLoginStatus(true);
                            assert musername != null;
                            prefConfig.writeLoginUserID(musername.getId());
                            assert user != null;
                            Toast.makeText(this, "Welcome back "+user.getNickname(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}

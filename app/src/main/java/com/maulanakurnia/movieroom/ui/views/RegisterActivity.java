package com.maulanakurnia.movieroom.ui.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.User;
import com.maulanakurnia.movieroom.ui.viewmodel.UserViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class RegisterActivity extends AppCompatActivity {

    protected EditText et_fullname, et_nickname, et_username, et_password, et_confirm_password;
    protected ImageView iv_back;
    protected UserViewModel userViewModel;
    protected Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initUI();

        iv_back.setOnClickListener(v -> {
            finish();
        });

        btn_register.setOnClickListener(v -> {
            checkRegister();
        });

    }

    private void initUI() {
        et_fullname            = findViewById(R.id.register_fullname);
        et_nickname            = findViewById(R.id.register_nickname);
        et_username            = findViewById(R.id.register_username);
        et_password            = findViewById(R.id.register_password);
        et_confirm_password    = findViewById(R.id.register_confirm_password);
        iv_back                = findViewById(R.id.register_arrow_back);
        btn_register           = findViewById(R.id.register_submit);
        userViewModel          = new ViewModelProvider.AndroidViewModelFactory(RegisterActivity.this.getApplication()).create(UserViewModel.class);
    }

    private void checkRegister() {
        et_username.setError(null);
        et_password.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String fullname  = et_fullname.getText().toString().trim();
        String nickname  = et_nickname.getText().toString().trim();
        String username  = et_username.getText().toString().trim();
        String password  = et_password.getText().toString().trim();
        String cPassword = et_confirm_password.getText().toString().trim();

        if(fullname.isEmpty()) {
            et_fullname.setError("This field is required");
            focused = et_fullname;
            cancel.set(true);
        }

        if(nickname.isEmpty()) {
            et_nickname.setError("This field is required");
            focused = et_nickname;
            cancel.set(true);
        }

        if(username.isEmpty()) {
            et_username.setError("This field is required");
            focused = et_username;
            cancel.set(true);
        }else {
            userViewModel.checkUsername(username).observe(this, (Observer<User>) user -> {
                if(user != null) {
                    et_username.setError("username already exist");
                    et_username.requestFocus();
                    cancel.set(true);
                }
            });
        }

        if(password.isEmpty()) {
            et_password.setError("This field is required");
            focused = et_password;
            cancel.set(true);
        }

        if(cPassword.isEmpty()) {
            et_confirm_password.setError("This field is required");
            focused = et_confirm_password;
            cancel.set(true);
        } else if(!password.equals(cPassword)) {
            et_password.setError("password not same");
            et_confirm_password.setError("password not same");
            focused = et_confirm_password;
            cancel.set(true);
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();
        } else {

            User user = new User();
            user.setFullname(fullname);
            user.setNickname(nickname);
            user.setUsername(username);
            user.setPassword(password);

            userViewModel.insert(user);
            finish();
            Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_SHORT).show();
        }
    }
}

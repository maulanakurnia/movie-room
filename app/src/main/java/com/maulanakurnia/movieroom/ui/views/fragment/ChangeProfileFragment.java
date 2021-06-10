package com.maulanakurnia.movieroom.ui.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.data.model.User;
import com.maulanakurnia.movieroom.ui.viewmodel.UserViewModel;
import com.maulanakurnia.movieroom.utils.SharedPrefConfig;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Maulana Kurnia on 6/8/2021
 * Keep Coding & Stay Awesome!
 **/
public class ChangeProfileFragment extends Fragment {

    protected BottomNavigationView nav_view;
    protected ImageView back;
    protected SharedPrefConfig prefConfig;
    protected UserViewModel userViewModel;
    protected EditText et_fullname, et_nickname, et_username;
    protected TextView tv_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);

        back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        nav_view.setVisibility(View.GONE);

        tv_save.setOnClickListener(v -> {
            checkChange();
        });
    }

    private void initUi(View view) {
        nav_view        = requireActivity().findViewById(R.id.bottom_navigation);
        back            = view.findViewById(R.id.change_profile_arrow_back);
        userViewModel   = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(UserViewModel.class);
        prefConfig      = new SharedPrefConfig(requireContext().getApplicationContext());
        et_fullname     = view.findViewById(R.id.change_profile_fullname);
        et_nickname     = view.findViewById(R.id.change_profile_nickname);
        et_username     = view.findViewById(R.id.change_profile_username);
        tv_save         = view.findViewById(R.id.change_profile_submit);

        userViewModel.getUser(prefConfig.readLoginUserID()).observe(requireActivity(), user -> {

            if(user != null) {
                et_fullname.setText(user.getFullname());
                et_nickname.setText(user.getNickname());
                et_username.setText(user.getUsername());
            }

        });
    }

    private void checkChange() {
        et_fullname.setError(null);
        et_nickname.setError(null);
        et_username.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String fullname  = et_fullname.getText().toString().trim();
        String nickname  = et_nickname.getText().toString().trim();
        String username  = et_username.getText().toString().trim();

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
            userViewModel.checkUsername(username).observe(requireActivity(), user -> {
                if(user != null && user.getId() != prefConfig.readLoginUserID()) {
                    et_username.setError("username already exist");
                    et_username.requestFocus();
                    cancel.set(true);
                }
            });
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();

        } else {
            User user = new User();
            user.setId(prefConfig.readLoginUserID());
            user.setFullname(fullname);
            user.setNickname(nickname);
            user.setUsername(username);

            userViewModel.update(user);

            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }
}

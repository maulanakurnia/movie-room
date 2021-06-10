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
public class ChangePasswordFragment extends Fragment {

    protected BottomNavigationView nav_view;
    protected ImageView iv_back;
    protected EditText et_new_password, et_confirm_new_password, et_old_password;
    protected TextView tv_save;
    protected SharedPrefConfig prefConfig;
    protected UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);

        iv_back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        nav_view.setVisibility(View.GONE);

        tv_save.setOnClickListener(v-> {
            checkChange();
        });
    }

    private void initUi(View view) {
        nav_view                = requireActivity().findViewById(R.id.bottom_navigation);
        iv_back                 = view.findViewById(R.id.change_password_arrow_back);
        et_old_password         = view.findViewById(R.id.change_old_password);
        et_new_password         = view.findViewById(R.id.change_new_password);
        et_confirm_new_password = view.findViewById(R.id.change_confirm_new_password);
        tv_save                 = view.findViewById(R.id.change_password_submit);
        userViewModel           = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(UserViewModel.class);
        prefConfig              = new SharedPrefConfig(requireContext().getApplicationContext());
    }

    private void checkChange() {
        et_old_password.setError(null);
        et_new_password.setError(null);
        et_confirm_new_password.setError(null);
        View focused = null;
        AtomicBoolean cancel = new AtomicBoolean(false);

        String old_password             = et_old_password.getText().toString().trim();
        String new_password             = et_new_password.getText().toString().trim();
        String confirm_new_password     = et_confirm_new_password.getText().toString().trim();

        if(old_password.isEmpty()) {
            et_old_password.setError("This field is required");
            focused = et_old_password;
            cancel.set(true);
        }else {
            userViewModel.getUser(prefConfig.readLoginUserID()).observe(requireActivity(), user -> {
                if(user != null && !user.getPassword().equals(old_password)) {
                    et_old_password.setError("incorrect password");
                    et_old_password.requestFocus();
                    cancel.set(true);
                }
            });
        }

        if(new_password.isEmpty()) {
            et_new_password.setError("This field is required");
            focused = et_new_password;
            cancel.set(true);
        }

        if(confirm_new_password.isEmpty()) {
            et_confirm_new_password.setError("This field is required");
            focused = et_confirm_new_password;
            cancel.set(true);
        }

        if(cancel.get()) {
            assert focused != null;
            focused.requestFocus();

        } else {
            User user = new User();
            user.setId(prefConfig.readLoginUserID());
            user.setPassword(new_password);

            userViewModel.updatePassword(user);

            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }
}

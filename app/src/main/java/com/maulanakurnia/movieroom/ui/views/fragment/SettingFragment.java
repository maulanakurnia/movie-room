package com.maulanakurnia.movieroom.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.maulanakurnia.movieroom.R;
import com.maulanakurnia.movieroom.ui.views.LoginActivity;
import com.maulanakurnia.movieroom.utils.SharedPrefConfig;

/**
 * Created by Maulana Kurnia on 6/4/2021
 * Keep Coding & Stay Awesome!
 **/
public class SettingFragment extends Fragment {
    private SharedPrefConfig prefConfig;
    protected LinearLayout sign_out;
    protected ConstraintLayout change_profile, change_password, about_app;
    protected BottomNavigationView navView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initUi(view);

        change_profile.setOnClickListener(v -> {
            loadFragment(new ChangeProfileFragment());
        });

        change_password.setOnClickListener(v -> {
            loadFragment(new ChangePasswordFragment());
        });

        about_app.setOnClickListener(v -> {
            Toast.makeText(getContext(), "clicked change about app", Toast.LENGTH_SHORT).show();
        });

        sign_out.setOnClickListener(v -> {
            prefConfig.clearLoggedInUser(requireContext());
            startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
        });
    }

    private void initUi(View view) {
        sign_out        = view.findViewById(R.id.llSignOut);
        change_profile  = view.findViewById(R.id.clChangeProfile);
        change_password = view.findViewById(R.id.clChangePassword);
        about_app       = view.findViewById(R.id.clAboutApp);
        navView         = requireActivity().findViewById(R.id.bottom_navigation);
        prefConfig      = new SharedPrefConfig(requireContext());

        navView.setVisibility(View.VISIBLE);
    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null){
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fr_main_container,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}

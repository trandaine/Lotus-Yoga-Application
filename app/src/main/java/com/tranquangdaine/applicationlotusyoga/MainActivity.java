package com.tranquangdaine.applicationlotusyoga;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.tranquangdaine.applicationlotusyoga.databinding.ActivityMainBinding;
import com.tranquangdaine.applicationlotusyoga.ui.categories.CategoryFragment;
import com.tranquangdaine.applicationlotusyoga.ui.courses.CourseFragment;
import com.tranquangdaine.applicationlotusyoga.ui.home.HomeFragment;
import com.tranquangdaine.applicationlotusyoga.ui.teachers.TeacherFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_course, R.id.navigation_teacher, R.id.navigation_teacher)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                loadFragment(new HomeFragment()); // Placeholder Fragment - Create this next
                return true;
            } else if (id == R.id.navigation_course) {
                loadFragment(new CourseFragment()); // Placeholder Fragment - Create this next
                return true;
            } else if (id == R.id.navigation_teacher) {
                loadFragment(new TeacherFragment()); // Placeholder Fragment - Create this next
                return true;
            } else if (id == R.id.navigation_category) {
                loadFragment(new CategoryFragment()); // Placeholder Fragment - Create this next
                return true;
            }
            return false;
        });


    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }

}
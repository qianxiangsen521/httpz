package com.cnr.cnrmodule;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mvvm,R.id.navigation_mvp,
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_mvvm:
                    if ( Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.navigation_mvvm) {
                        Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_mvvm);
                    }
                    return true;
                    case R.id.navigation_mvp:
                        if ( Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.navigation_mvp) {
                            Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_mvp);
                        }

                        return true;
                    case R.id.navigation_home:
                        if ( Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.navigation_home) {
                            Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_home);
                        }

                        return true;
                    case R.id.navigation_dashboard:
                        if ( Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.navigation_dashboard) {
                            Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_dashboard);
                        }

                        return true;
                    case R.id.navigation_notifications:
                        if ( Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).getCurrentDestination().getId() != R.id.navigation_notifications) {
                            Navigation.findNavController(HomeActivity.this, R.id.nav_host_fragment).navigate(R.id.navigation_notifications);
                        }
                        return true;
                }
                return false;
            }
        });
    }

}

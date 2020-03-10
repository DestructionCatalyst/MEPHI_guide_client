package com.example.mephiguide;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public int selectedTheme = 0;
    final String FILE_NAME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //selectedTheme = getIntent().getIntExtra("position", 0);
        selectedTheme = loadTheme();

        selectTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_reminder, R.id.navigation_navigation, R.id.navigation_qr, R.id.navigation_telephony, R.id.navigation_options)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    private void selectTheme(){
        switch (selectedTheme){
            case 0:
                setTheme(R.style.AppTheme);
                break;
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.InpheTheme);
                break;
            case 3:
                setTheme(R.style.LaplasTheme);
                break;
            default:
                setTheme(R.style.AppTheme);
        }
    }

    private int loadTheme(){
        String read = "";
        int res = 0;
        try {
            FileInputStream fin = this.openFileInput(FILE_NAME);
            byte [] b = new byte[fin.available()];
            fin.read(b);
            read = new String (b);
            res = Integer.parseInt(read);
            fin.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }
}

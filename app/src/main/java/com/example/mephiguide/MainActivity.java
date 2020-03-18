package com.example.mephiguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public int selectedTheme = 0;
    private final String FILE_NAME_THEMES = "theme";
    private final String FILE_NAME_AUTO = "autotheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
        int th = selectedTheme;
        if (th == 0){
            th = readGroup() + 1;
        }

        switch (th){
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
            case 4:
                setTheme(R.style.IphibTheme);
                break;
            case 5:
                setTheme(R.style.NesphiTheme);
                break;
            case 6:
                setTheme(R.style.IcisTheme);
                break;
            case 7:
                setTheme(R.style.IphtiesTheme);
                break;
            case 8:
                setTheme(R.style.IphtiesTheme);
                break;
            case 9:
                setTheme(R.style.NesphiTheme);
                break;
            case 10:
                setTheme(R.style.IcisTheme);
                break;

            default:
                setTheme(R.style.AppTheme);
        }
    }

    private int loadTheme(){

        FileHelper fhelp = new FileHelper(this);
        String read = fhelp.readFile(FILE_NAME_THEMES);
        int res = 0;

        try {
            res = Integer.parseInt(read);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return res;

    }

    private int readGroup(){

        FileHelper fhelp = new FileHelper(this);
        String toRead = fhelp.readFile(FILE_NAME_AUTO);

        int res = 0;

        try {
            res = Integer.parseInt(toRead);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return res;
    }

    public void reset(){
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}

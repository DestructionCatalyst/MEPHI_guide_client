package com.example.mephiguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public int selectedTheme = 0;
    private final String FILE_NAME_THEMES = "theme";
    private final String FILE_NAME_AUTO = "autotheme";

    private int preloadedIdInst = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MyLog.i("Starting MEPHI guide application, version "+BuildConfig.VERSION_NAME);

        selectedTheme = readTheme();

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void selectTheme(){
        MyLog.v("Selecting theme");

        int th = selectedTheme;
        if (th == 0){
            th = readGroup() + 1;
        }

        switch (th){
            case 0:
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
            case 9:
                setTheme(R.style.NesphiTheme);
                break;
            case 6:
            case 10:
                setTheme(R.style.IcisTheme);
                break;
            case 7:
            case 8:
                setTheme(R.style.IphtiesTheme);
                break;
            default:
                MyLog.w("Incorrect theme identifier!");
                setTheme(R.style.AppTheme);
        }
    }

    private int readTheme(){
        MyLog.v("Loading theme");

        if(preloadedIdInst == -1) {
            FileHelper fhelp = new FileHelper(this);
            String read = "";
            read = fhelp.readFile(FILE_NAME_THEMES);
            int res = 0;

            try {
                res = Integer.parseInt(read);
            } catch (NumberFormatException e) {
                MyLog.w("THEMES file is missing or corrupted!");
                e.printStackTrace();
            }

            preloadedIdInst = res;
            return res;
        }

        return preloadedIdInst;

    }

    public int readGroup(){
        MyLog.v("Loading theme to auto-select");

        FileHelper fhelp = new FileHelper(this);
        String toRead = fhelp.readFile(FILE_NAME_AUTO);

        int res = 0;

        try {
            res = Integer.parseInt(toRead);
        }
        catch (NumberFormatException e){
            MyLog.w("AUTO_THEMES file is missing or corrupted!");
            e.printStackTrace();
        }
        return res;
    }

    public DisplayMetrics getDisplayMetrics(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        return metricsB;
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    public void reset(){
        MyLog.i("Resetting the application");
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
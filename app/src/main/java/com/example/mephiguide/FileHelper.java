package com.example.mephiguide;

import androidx.fragment.app.FragmentActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class FileHelper {

    FragmentActivity context;

    public FileHelper(FragmentActivity context){
        this.context = context;
    }

    public String readFile(final String FILE_NAME){

        String toRead = "";
        try {
            FileInputStream fin = context.openFileInput(FILE_NAME);
            byte [] b = new byte[fin.available()];
            fin.read(b);
            toRead = new String (b);
            fin.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return toRead;
    }

    public boolean writeFile(final String FILE_NAME, String toWrite){
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            if (toWrite != null)
                fos.write(toWrite.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

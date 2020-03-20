package com.example.mephiguide;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, String> {

    private MutableLiveData target;
    private JSONStrategy strat;

    public NetworkTask(MutableLiveData target, JSONStrategy strat){

        this.target = target;
        this.strat = strat;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... path) {
        String content;
        Log.d("Connection"," Conntecting to server, URL is "+path[0]);
        try{
            content = getContent(path[0]);
        }
        catch (IOException ex){
            content = ex.getMessage();
        }

        return content;
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        try {
            URL url=new URL(path);
            HttpURLConnection c=(HttpURLConnection)url.openConnection();
            c.setRequestMethod("GET");
            c.setConnectTimeout(10000);
            c.setReadTimeout(20000);
            c.connect();
            reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return(buf.toString());
        }
        catch(Exception e){
            e.printStackTrace();
            /*StackTraceElement [] err = e.getStackTrace();
            String tmp=e.getMessage()+"\n";
            for (int i = 0;i<err.length;i++){
                tmp+=err[i]+"\n   ";
            }*/
            return "Не удалось подключиться к серверу! Проверьте подключение к Интернету.";
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        if (content.startsWith("[")){
            //Открываем
            Log.d("Connection","Got JSON from the server");
            JSONHelper helper1 = new JSONHelper(strat, target);
            helper1.execute(content);
        }
        else{
            //Ругаемся
            Log.d("Connection","Error getting JSON from the server");
            target.postValue(null);
        }

    }

}

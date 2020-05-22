package com.example.mephiguide.mail_sending;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mephiguide.MainActivity;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    private MainActivity activity;

    public SendEmailAsyncTask(MainActivity a, String user, String pass, String from, String body,
                              String[] recipients, String subject, String filename) {
        activity = a;
        m = new Mail(user, pass);
        m.set_from(user);
        m.setBody(from+" отправил письмо в техподдержку: "+body);
        m.set_to(recipients);
        m.set_subject(subject);
        if (!filename.equals("")) {
            try {
                m.addAttachment(filename);
            } catch (Exception e) {
                Log.e("MyLogs", "Error adding attachment!");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("MyLogs", "Sending email...");
        try {
            if (m.send()) {
                Log.d("MyLogs", "Email sent.");
            } else {
                Log.w("MyLogs", "Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e("MyLogs", "Bad account details");
            e.printStackTrace();
            //activity.displayMessage("Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e("MyLogs", "Email failed");
            e.printStackTrace();
            //activity.displayMessage("Email failed to send.");
            return false;
        } catch (Exception e) {
            Log.e("MyLogs", "Unexpected error occurred while sending an email.");
            e.printStackTrace();
            //activity.displayMessage("Unexpected error occured.");
            return false;
        }
    }
}

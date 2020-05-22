package com.example.mephiguide.ui.options;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mephiguide.FileHelper;
import com.example.mephiguide.MainActivity;
import com.example.mephiguide.R;

public class OptionsFragment extends Fragment {

    private final String FILE_NAME = "theme";

    private Spinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);

        spinner = root.findViewById(R.id.options_spinner);
        initializeSpinner(((MainActivity)this.getActivity()).selectedTheme);

        final TextView support = root.findViewById(R.id.options_support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail();
            }
        });

        final TextView about = root.findViewById(R.id.options_about);
        about.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showMessage();
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    private void showMessage(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Приложение «Путеводитель по НИЯУ МИФИ»\n" +
                "Версия " +getString(R.string.app_version)+"\n\n"+
                "©\n" +
                "Национальный исследовательский ядерный университет «МИФИ»,\n" +
                "Институт Интеллектуальных Кибернетических Систем (ИИКС)\n" +
                "Кафедра №22 «Кибернетика»\n" +
                "Разработано в рамках курса «Проектная практика»\n" +
                "Руководитель проекта: Немешаев Сергей Александрович\n" +
                "Куратор проекта: Дадтеев Казбек Маирбекович\n" +
                "Разработчик: Комза Владислав Петрович\n"+
                "\n"+
                "Библиотека для сканирования QR-кодов:\n" +
                "Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]")
                .setTitle("О программе")
                .setPositiveButton("OK", null);
        AlertDialog dialog1 = builder1.create();
        dialog1.show();
    }

    private void openMail(){

        Log.d("MyLogs", "Preparing to send e-mail");

        //FileHelper helper = new FileHelper(this.getActivity());
        //AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        //builder1.setMessage(helper.readFile("mylog.log"))
                //.setTitle("Logs");
        //AlertDialog dialog1 = builder1.create();
        //dialog1.show();

        LayoutInflater li = LayoutInflater.from(this.getActivity());
        View promptsView = li.inflate(R.layout.support_window, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(promptsView);

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userEmail = (EditText) promptsView.findViewById(R.id.support_input_email);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.support_input_text);

        builder/*.setMessage("Для повышения качества обработки обращений в техподдержку, а также для " +
                "последующей отладки и усовершенствования приложения Вы можете отправить log-файл, " +
                "в котором содержится отладочная информация. Это настоятельно реомендуется" +
                "сделать, если Вы столкнулись с нестабильной работой приложения. Со стороны разработчика" +
                "подтверждаю, что в файле не содержится никаких Ваших личных данных, лишь информация, " +
                "связанная с работой данного приложения.\n")*/
                .setCancelable(true)
                .setPositiveButton("Отправить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                sendWithLogs(userEmail.getText().toString(), userInput.getText().toString());
                            }
                        })
                /*.setNegativeButton("Отправить без логов",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                sendEmail(userInput.getText().toString());
                            }
                        })*/
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                //.setTitle("Техподдержка");
        AlertDialog dialog1 = builder.create();
        dialog1.show();

    }

    private void sendEmail(String message){
        try
        {
            Intent contactintent = new Intent(Intent.ACTION_SENDTO);
            contactintent.setData(android.net.Uri.parse("mailto:" + "mephiapp@gmail.com"));

            startActivity(Intent.createChooser(contactintent, "Send mail..."));

        }
        catch (ActivityNotFoundException anfe)
        {
            Toast.makeText(this.getActivity(), "На устройстве не найден почтовый клиент", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendWithLogs(String from, String message){
        try
        {
            //File outputFile = new File(this.getActivity().getFilesDir()+"/mylog.log");
            String[] recipients = { "mephiapp@gmail.com" };

            FileHelper helper = new FileHelper(this.getActivity());
            message = message + helper.readFile("mylog.log");
            /*
            SendEmailAsyncTask email = new SendEmailAsyncTask(
                    (MainActivity) this.getActivity(),
                    "proxymailmephiapp@gmail.com",
                    "f8dXVc4Y5c",
                    from,
                    message,
                    recipients,
                    "MephiGuide app support",
                    this.getActivity().getFilesDir()+"/mylog.log"
            );
            Log.d("MyLogs", "Creating email sender");
            email.execute();*/
            //f8dXVc4Y5c
            Intent contactintent = new Intent(Intent.ACTION_SENDTO);
            //contactintent.setType("message/rfc822");
            contactintent.setData(android.net.Uri.parse("mailto:" + "mephiapp@gmail.com"));
            contactintent.putExtra(Intent.EXTRA_TEXT, message);
            //contactintent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));

            startActivity(Intent.createChooser(contactintent, "Choose an Email App:"));
            //Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            //emailIntent.setType("message/rfc822");
            //emailIntent.setDataAndType(android.net.Uri.parse("mailto:" + "mephiapp@gmail.com"), "message/rfc822");
            //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mephiapp@gmail.com"});
            //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
            //startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
        catch (ActivityNotFoundException anfe)
        {
            Toast.makeText(this.getActivity(), "На устройстве не найден почтовый клиент", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeSpinner(int theme){

        String [] s = new String[11];
        s[0] = "Автоматически";
        s[1] = "Стандартная";
        s[2] = "ИЯФиТ";
        s[3] = "ЛаПлаз";
        s[4] = "ИФИБ";
        s[5] = "ИНТЕЛ";
        s[6] = "ИИКС";
        s[7] = "ИФТИС";
        s[8] = "ИФТЭБ";
        s[9] = "ИМО";
        s[10] = "ФБИУКС";

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, s);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setSelection(theme);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != ((MainActivity)getActivity()).selectedTheme) {

                    saveTheme(position);
                    ((MainActivity) getActivity()).reset();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void saveTheme(int theme){

        FileHelper fhelp = new FileHelper(this.getActivity());
        fhelp.writeFile(FILE_NAME, ""+theme);

    }

}

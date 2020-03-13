package com.example.mephiguide.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mephiguide.R;
import com.example.mephiguide.ValueKeeper;
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private final String FILE_NAME = "group";
    private HomeViewModel homeViewModel;

    private ListView listView;
    private Spinner spinner;
    private Switch sw;
    private TextView textView;
    private ArrayList<Group> groups;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getNews().observe(getViewLifecycleOwner(), new Observer<ArrayList<News>>() {
            @Override
            public void onChanged(ArrayList<News> news) {
                setNewsAdapter(news);

            }
        });

        homeViewModel.getGroups().observe(getViewLifecycleOwner(), new Observer<ArrayList<Group>>() {
            @Override
            public void onChanged(ArrayList<Group> groups) {
                setGroupsAdapter(groups);

            }
        });

        listView = root.findViewById(R.id.home_listView);
        spinner = root.findViewById(R.id.home_spinner_groups);
        sw = root.findViewById(R.id.home_switch_target_mode);
        textView = root.findViewById(R.id.home_textView_forme);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ValueKeeper.getInstance().curGroup = (Group)parent.getItemAtPosition(position);
                Log.d("Mics", ValueKeeper.getInstance().curGroup.name);
                changeGroup();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sw.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw.getVisibility() == View.VISIBLE) {//If it is visible, then not a 'guest' is selected
                    refresh(isChecked);
                }
                else refresh(false);
            }
        });

        return root;
    }

    void setNewsAdapter(ArrayList<News> news){
        MyAdapter newsAdapter = new MyAdapter(this.getActivity(), this, news);
        listView.setAdapter(newsAdapter);
    }

    void setGroupsAdapter(ArrayList<Group> groups){
        this.groups = groups;
        readFile();
        ArrayAdapter groupAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(groupAdapter);
        spinner.setSelection(ValueKeeper.getInstance().curGroup.id);
    }

    void changeGroup(){
        FileOutputStream fos;
        //TODO Вынести в отдельный класс, лучше  - тред
        try {
            fos = this.getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
            String write = "";
            write = ValueKeeper.getInstance().curGroup.name;
            fos.write(write.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this.getActivity(), "Ошибка сохранения данных!", Toast.LENGTH_SHORT).show();
        }
        if (ValueKeeper.getInstance().curGroup.idInst == 0){
            sw.setChecked(false);
            sw.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else {
            //listView.setVisibility(View.INVISIBLE);
            refresh(sw.isChecked());
            listView.setVisibility(View.VISIBLE);
            sw.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    void refresh(boolean targeting){
        if(targeting){
            homeViewModel.updateNews(ValueKeeper.getInstance().curGroup.idInst);
        }
        else{
            homeViewModel.updateNews(0);
        }
    }

    private  void readFile(){
        //TODO move to different file/thread
        String read = "";
        boolean flag = false, found = false;
        try {
            FileInputStream fin = this.getActivity().openFileInput(FILE_NAME);
            byte [] b = new byte[fin.available()];
            fin.read(b);
            read = new String (b);
            fin.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            flag = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if ((flag)||(read.equals(""))||(read.equals("(Гость)"))){//Если файла нет, он пуст или там гость, то у нас гость
            ValueKeeper.getInstance().curGroup = new Group(0,"(Гость)",0);
        }
        else {
            int tmp = Collections.binarySearch(groups, new Group(0, read, 0), new Comparator<Group>() {

                /**
                 * Compares its two arguments for order.  Returns a negative integer,
                 * zero, or a positive integer as the first argument is less than, equal
                 * to, or greater than the second.<p>
                 * <p>
                 * In the foregoing description, the notation
                 * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
                 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
                 * <tt>0</tt>, or <tt>1</tt> according to whether the value of
                 * <i>expression</i> is negative, zero or positive.<p>
                 * <p>
                 * The implementor must ensure that <tt>sgn(compare(x, y)) ==
                 * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
                 * implies that <tt>compare(x, y)</tt> must throw an exception if and only
                 * if <tt>compare(y, x)</tt> throws an exception.)<p>
                 * <p>
                 * The implementor must also ensure that the relation is transitive:
                 * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
                 * <tt>compare(x, z)&gt;0</tt>.<p>
                 * <p>
                 * Finally, the implementor must ensure that <tt>compare(x, y)==0</tt>
                 * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
                 * <tt>z</tt>.<p>
                 * <p>
                 * It is generally the case, but <i>not</i> strictly required that
                 * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
                 * any comparator that violates this condition should clearly indicate
                 * this fact.  The recommended language is "Note: this comparator
                 * imposes orderings that are inconsistent with equals."
                 *
                 * @param o1 the first object to be compared.
                 * @param o2 the second object to be compared.
                 * @return a negative integer, zero, or a positive integer as the
                 * first argument is less than, equal to, or greater than the
                 * second.
                 * @throws NullPointerException if an argument is null and this
                 *                              comparator does not permit null arguments
                 * @throws ClassCastException   if the arguments' types prevent them from
                 *                              being compared by this comparator.
                 */
                @Override
                public int compare(Group o1, Group o2) {
                    if (o1.name.equals(o2.name)) {
                        return 0;
                    }
                    else return 1;
                }
            });
            if (tmp < 0){
                ValueKeeper.getInstance().curGroup = new Group(0, "(Гость)", 0);
            }
            else {
                ValueKeeper.getInstance().curGroup = groups.get(tmp);
            }
            /*
            for (Group tmp: groups) {
                if (tmp.name.equalsIgnoreCase(read)) {
                    curGroup = tmp;
                    found = true;
                }
            }
            if (!found){curGroup = new group(0,"(Гость)",0);}
            */

        }
    }
}
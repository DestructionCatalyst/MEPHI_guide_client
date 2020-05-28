package com.example.mephiguide.ui.navigation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mephiguide.MainActivity;
import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;
import com.example.mephiguide.data_types.Dot;
import com.example.mephiguide.data_types.Way;
import com.example.mephiguide.ui.LoadErrorMessage.LoadErrorMessage;
import com.example.mephiguide.ui.LoadErrorMessage.LoadStateController;

import java.util.ArrayList;

public class NavigationFragment extends Fragment {

    private NavigationViewModel navigationViewModel;

    private ArrayList<Dot> dots;
    private ArrayList<Way> ways;
    private ArrayList<Dot> show;

    private AutoCompleteTextView actv_from;
    private AutoCompleteTextView actv_to;
    private Button button;
    private MyMapView gfx;

    private Pathfinder pathfinder;

    private LoadErrorMessage lem;
    private LoadStateController lsc;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        navigationViewModel =
                new ViewModelProvider(this).get(NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        navigationViewModel.getDots().observe(getViewLifecycleOwner(), new Observer<ArrayList<Dot>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Dot> d) {
                if(d != null) {
                    dots = d;
                    show = new ArrayList<>();
                    for (Dot dot:dots) {
                        if (dot.getName() != null){show.add(dot);}
                    }
                    lsc.increaseLevel();
                    setAdapters();
                }
                else{
                    lsc.showError();
                    MyLog.w("Unable to load groups!");
                }
            }
        });
        navigationViewModel.getWays().observe(getViewLifecycleOwner(), new Observer<ArrayList<Way>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Way> w) {
                if(w != null) {
                    ways = w;
                    lsc.increaseLevel();
                }
                else{
                    lsc.showError();
                    MyLog.w("Unable to load groups!");
                }
            }
        });

        lem = root.findViewById(R.id.navi_lem);
        lsc = new LoadStateController(lem, 2);
        lsc.setOnLoadedListener(new LoadStateController.OnLoadedListener() {
            @Override
            public void onLoaded() {
                pathfinder = new Pathfinder(dots, ways);
            }
        });

        actv_from = root.findViewById(R.id.navi_auto_text_from);
        actv_to = root.findViewById(R.id.navi_auto_text_to);

        button = root.findViewById(R.id.navi_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathfinder != null)
                    runPathfinder();
            }
        });

        gfx = root.findViewById(R.id.navi_draw_field);

        return root;
    }

    private void setAdapters(){
        ArrayAdapter adapter1 = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, show);
        actv_from.setAdapter(adapter1);
        actv_to.setAdapter(adapter1);
    }

    private void runPathfinder(){

        float[] floatArray = null;
        if (pathfinder != null) {
            floatArray = pathfinder.getPath(
                    pathfinder.findId(actv_from.getText().toString()),
                    pathfinder.findId(actv_to.getText().toString()));
        }
        if(floatArray != null) {
            gfx.drawWay(floatArray);
            MyLog.i("Path was found!");
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Неверно указана начальная и/или конечная точка!")
                    .setTitle("Ошибка!")
                    .setPositiveButton("ОК", null);

            AlertDialog dialog1 = builder1.create();
            dialog1.show();
            MyLog.i("Path was not found!");
        }
        ((MainActivity)this.getActivity()).hideKeyboard();

    }


}
package com.example.mephiguide.ui.home;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mephiguide.FileHelper;
import com.example.mephiguide.IWebUtilities;
import com.example.mephiguide.JSONHelpers.GroupsJSONHelper;
import com.example.mephiguide.JSONHelpers.NewsJSONHelper;
import com.example.mephiguide.MyLog;
import com.example.mephiguide.NetworkTask;
import com.example.mephiguide.data_types.Group;
import com.example.mephiguide.data_types.News;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel implements IWebUtilities {

    private final String FILE_NAME_GROUP = "group";
    private final String FILE_NAME_AUTO = "autotheme";
    private final String FILE_NAME_CHECK = "checked";

    private final String lnkpostNews = "getnews?inst=";
    private final String lnkpostGroups = "getgroups/";

    private MutableLiveData<ArrayList<News>> newsData;
    private MutableLiveData<ArrayList<Group>> groupsData;

    private Group currentGroup;

    public HomeViewModel() {

        newsData = new MutableLiveData<>();
        groupsData = new MutableLiveData<>();
        updateNews(0);
        updateGroups();
    }

    LiveData<ArrayList<News>> getNews() {
        return newsData;
    }

    LiveData<ArrayList<Group>> getGroups(){
        return groupsData;
    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    void updateNews(int group){

        NetworkTask task = new NetworkTask(newsData, new NewsJSONHelper());
        task.execute(IWebUtilities.linkBase + lnkpostNews + group);

    }

    private void updateGroups(){

        NetworkTask task1 = new NetworkTask(groupsData, new GroupsJSONHelper());
        task1.execute(IWebUtilities.linkBase + lnkpostGroups);
    }

    private Group getGroup(int index){
        return groupsData.getValue().get(index);
    }

    public boolean changeGroup(Group selectedGroup, int selectedTheme, FragmentActivity activity){

        boolean reset = false;
        if ((selectedGroup.getId() != currentGroup.getId())
                && (selectedTheme == 0))
            reset = true;

        currentGroup = selectedGroup;

        MyLog.i("Group changed to " + getCurrentGroup().getName());

        FileHelper fhelp = new FileHelper(activity);
        fhelp.writeFile(FILE_NAME_GROUP, "" + getCurrentGroup().getId());
        fhelp.writeFile(FILE_NAME_AUTO, ""+ getCurrentGroup().getIdInst());
        /*
        handleGroupChange();
        if (reset) ((MainActivity)getActivity()).reset();
         */
        return reset;
    }

    void readGroupsFile(FragmentActivity activity){

        FileHelper fhelp = new FileHelper(activity);
        String fileContent = fhelp.readFile(FILE_NAME_GROUP);
        if ((fileContent.equals(""))||(fileContent.equals("0"))){//Если файла нет, он пуст или там гость, то у нас гость
            currentGroup = Group.createGuest();
        }
        else {
            try {
                int tmp = Integer.parseInt(fileContent);
                currentGroup = getGroup(tmp);
            }
            catch (NumberFormatException e){
                MyLog.w("Group file corrupted!");
                currentGroup = Group.createGuest();
            }

        }
    }
}
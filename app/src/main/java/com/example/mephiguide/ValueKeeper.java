package com.example.mephiguide;

import com.example.mephiguide.data_types.Group;

public class ValueKeeper {

    public final String lnkbase="http://194.87.232.95:45555/home/";
    public Group curGroup = new Group(0, "(Гость)", 0);

    private static final ValueKeeper ourInstance = new ValueKeeper();

    public static ValueKeeper getInstance() {
        return ourInstance;
    }

    private ValueKeeper() {
    }
}

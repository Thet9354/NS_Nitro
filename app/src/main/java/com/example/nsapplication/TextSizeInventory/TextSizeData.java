package com.example.nsapplication.TextSizeInventory;

import com.example.nsapplication.IPPTInventory.Vocation;

import java.util.ArrayList;
import java.util.List;

public class TextSizeData {

    public static List<TextSize> getTextSizeList()
    {
        List<TextSize> textSizeList = new ArrayList<>();

        TextSize twelve = new TextSize();
        twelve.setTextSize("12");
        textSizeList.add(twelve);

        TextSize fourteen = new TextSize();
        fourteen.setTextSize("14");
        textSizeList.add(fourteen);

        TextSize sixteen = new TextSize();
        sixteen.setTextSize("16");
        textSizeList.add(sixteen);

        return textSizeList;
    }

}

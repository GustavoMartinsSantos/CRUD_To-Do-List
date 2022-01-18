package com.example.todolist.classes;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.example.todolist.classes.Task.getTasksByString;

public abstract class Server {
    private static String getURLStringByMultArray (String[][] array) {
        String getString = "?";

        for(String[] getArray : array) {
            getString += getArray[0] + "=" + getArray[1];
        }

        return getString;
    }

    public static String Execute (String link, String[][] getArray, String[][] postArray) {
        String result = new String();
        PutData putData;
        int postArrayLength = postArray.length;

        String[] fields = new String[postArrayLength];
        String[] values = new String[postArrayLength];

        for(int c = 0; c < postArrayLength; c++) {
            fields[c] = postArray[c][0];
            values[c] = postArray[c][1];
        }

        fields[postArrayLength - 1] = "Mobile";
        values[postArrayLength - 1] = "true";

        putData = new PutData(link + getURLStringByMultArray(getArray), "POST", fields, values);
        if(putData.startPut()) {
            if(putData.onComplete()) {
                result = putData.getResult();
            }
        }

        return result;
    }
}

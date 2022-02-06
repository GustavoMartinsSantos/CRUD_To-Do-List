package com.example.todolist.classes;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public abstract class Server {
    private static String serverIP = "192.168.13.232";

    private static String getURLStringByMultArray (String[][] array) {
        String getString = "?";

        for(String[] getArray : array) {
            getString += getArray[0] + "=" + getArray[1] + "&";
        }

        return getString;
    }

    public static String Execute (String link, String[][] getArray, String[][] postArray) {
        int postArrayLength = 0;
        String result = new String();
        PutData putData;
        link = "http://" + serverIP + "/" + link;

        if(getArray != null)
            link += getURLStringByMultArray(getArray);
        if(postArray != null)
            postArrayLength = postArray.length;

        String[] fields = new String[postArrayLength + 1];
        String[] values = new String[postArrayLength + 1];

        for(int c = 0; c < postArrayLength; c++) {
            fields[c] = postArray[c][0];
            values[c] = postArray[c][1];
        }

        fields[postArrayLength] = "Mobile";
        values[postArrayLength] = "true";

        putData = new PutData(link, "POST", fields, values);
        if(putData.startPut()) {
            if(putData.onComplete()) {
                result = putData.getResult();
            }
        }

        return result;
    }
}
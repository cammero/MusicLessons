package com.cameo;

import javax.xml.crypto.Data;

public class Main {

    public static void main(String[] args) {
        DatabaseManager.setup();
        SearchForLessons searchForLessons = new SearchForLessons();
        //DatabaseManager.shutdown();
    }

}

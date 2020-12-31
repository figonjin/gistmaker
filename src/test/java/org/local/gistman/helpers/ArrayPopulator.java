package org.local.gistman.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayPopulator {

    public static ArrayList<String> populateArray(String filepath) throws IOException {
        Scanner s = new Scanner(new File(filepath));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        return list;
    }

}

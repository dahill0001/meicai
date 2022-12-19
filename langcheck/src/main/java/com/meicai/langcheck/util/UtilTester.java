package com.meicai.langcheck.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTester {

     public long countSyllables(String s) {
        int counter = 0;
        s = s.toLowerCase(); // converting all string to lowercase
        if(s.contains("the ")){
            counter++;
        }
        String[] split = s.split("e!$|e[?]$|e,|e |e[),]|e$");

        ArrayList<String> al = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile("[aeiouy]+");

        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            Matcher m = tokSplitter.matcher(s1);

            while (m.find()) {
                al.add(m.group());
            }
        }

        counter += al.size();
        return counter;
    }
    public void test() {
        String ans = "Regal";
        String[] tokens = {"Regal", "standard", "OK", "Good", ".", "hello word"} ;
        System.out.println("Regal" + ":" + countSyllables("Regal"));
        System.out.println("standard" + ":" + countSyllables("standard"));
        System.out.println("OK" + ":" + countSyllables("OK"));
        System.out.println("Good" + ":" + countSyllables("Good"));
        System.out.println("." + ":" + countSyllables("."));
        System.out.println("hello word" + ":" + countSyllables("hello word"));
        
        long totalSyllables = Arrays.stream(tokens).mapToLong(this::countSyllables).sum();
        System.out.println("total" + ":" + totalSyllables);
    }

    public static void main(String []args){ 
    	new UtilTester().test();

    }

}

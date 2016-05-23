/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.badnewscouch.vsm.messages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ujmp.core.collections.ArrayIndexList;

/**
 *
 * @author Beka
 */
public class Message 
{
    public static int sCounter = 0;
    private static final int NUMMESSAGES = 9;
    
    public static final String TEXT0 = "Hier kommt Text0";
    public static final String TEXT1=  "Hier kommt Text1";
    public static final String TEXT2 = "Hier kommt Text2";
    public static final String TEXT3 = "Hier kommt Text3";
    public static final String TEXT4 = "Hier kommt Text4";
    public static final String TEXT5 = "Hier kommt Text5";
    public static final String TEXT6 = "Hier kommt Text6";
    public static final String TEXT7 = "Hier kommt Text7";
    public static final String TEXT8 = "Hier kommt Text8";
    
    public static HashMap<Integer, ArrayList<String>> messages = new HashMap();
    private static ArrayList<String> list0 = new ArrayIndexList<>();
    private static ArrayList<String> list1 = new ArrayIndexList<>();
    private static ArrayList<String> list2 = new ArrayIndexList<>();
    
    public static void putMessages()
    {
        list0.add(TEXT0);
        list0.add(TEXT1);
        list0.add(TEXT2);
        
        list1.add(TEXT3);
        list1.add(TEXT4);
        list1.add(TEXT5);
        
        list2.add(TEXT6);
        list2.add(TEXT7);
        list2.add(TEXT8); 
        
        messages.put(0, list0);
        messages.put(1, list1);
        messages.put(2, list2);
    }
    
    public static void incrementCounter()
    {
            sCounter++;
    }
    
    public static boolean isTextLeft()
    {
        if(sCounter < (NUMMESSAGES / 3))
            return true;
        return false;
    }
    
}



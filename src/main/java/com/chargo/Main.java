package com.chargo;

import com.chargo.helper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        for (int i = 0;i<15;i++){
            encodeDecode();
        }

    }

    public static void encodeDecode(){

        Helper helper = new Helper(); //ok
        String msgToEncode = "zcharlesbonjour";



        System.out.println("message to encode : "+ msgToEncode);

        ArrayList<Carte> cards = helper.createCardInOrder();

        Collections.shuffle(cards);

        ArrayList<Integer> keyflow = helper.generateKeyFlow(msgToEncode.length(), cards);
//        System.out.println("keyflow : " + keyflow);

        ArrayList<Integer> msgInt = helper.strToInt(msgToEncode.toCharArray());
        if (!helper.verify(msgInt,keyflow)){
            encodeDecode();
            return;
        }



        char[] key = helper.intToCharTab(keyflow);
        char[] encodedMessage  = helper.encode(msgToEncode.toCharArray(),key);

        IOHelper iOhelper = new IOHelper();
        String filename = iOhelper.exportCardOrder(cards);
//        System.out.println("message encodé : " + Arrays.toString(encodedMessage));
        System.out.println("message int: " + helper.strToInt(encodedMessage));
        // import jeu de carte pour la key
        ArrayList<Carte> cardsImport = iOhelper.importCardOrder(filename);
        ArrayList<Integer> keyFlowImport = helper.generateKeyFlow(msgToEncode.length(), cardsImport);
        char[] keyImport = helper.intToCharTab(keyflow);


        char[] decodedMessage = helper.decode(encodedMessage,keyImport);
        System.out.println("--------------------");
        System.out.println("message a encoder : " + Arrays.toString(msgToEncode.toCharArray()));
        System.out.println("key flow char: " + Arrays.toString(key));
        System.out.println("message en int : " + helper.strToInt(msgToEncode.toCharArray()));
        System.out.println("key flow int: " + keyflow);
        System.out.println("message encodé en int : " + helper.strToInt(encodedMessage));
        System.out.println("message encodé : " + Arrays.toString(encodedMessage));
        System.out.println("--------------------");
        System.out.println("message encodé en int : " + helper.strToInt(encodedMessage));
        System.out.println("key flow int: " + keyflow);
        System.out.println("message decode en int : " + helper.strToInt(decodedMessage));
        System.out.println("message decode en char : " + Arrays.toString(decodedMessage));

    }
}

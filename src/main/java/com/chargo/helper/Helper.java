package com.chargo.helper;

import com.chargo.Carte;
import com.chargo.utils.Valeur;
import com.chargo.enums.CouleurEnum;
import com.chargo.enums.FormeEnum;
import com.chargo.enums.TeteEnum;
import com.chargo.utils.Caractere;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Helper {
    private final ArrayList<Caractere> CONVERT_MAP;
    private int NB_ALPHA;

    public Helper() {
        CONVERT_MAP = new ArrayList<Caractere>();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int iter = 1;
        for (char ch : alphabet) {
            CONVERT_MAP.add(new Caractere(ch,iter));
            iter++;
        }
        NB_ALPHA = CONVERT_MAP.size();

    }

    public ArrayList<Integer> strToInt(char[] chars) {
        ArrayList<Integer> sortie = new ArrayList<>();
        for (char ch : chars) {
            for (Caractere caractere : CONVERT_MAP)
                if (caractere.getLettre() == ch) {
                    sortie.add(caractere.getValue());
                }
        }
        return sortie;
    }

    public char[] encode(char[] msg, char[] key) {
        ArrayList<Integer> msgInt = strToInt(msg);
        ArrayList<Integer> keyInt = strToInt(key);
//        System.out.println("msg : " + Arrays.toString(msg));
        System.out.println("msg int : " + msgInt.toString());

        char[] encryptedMsg;
        ArrayList<Integer> encryptedMessageInt = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {

            System.out.println("==========");
            System.out.println(msgInt.get(i));
            System.out.println(keyInt.get(i));
            if (msgInt.get(i) + keyInt.get(i) > NB_ALPHA) {
                encryptedMessageInt.add(((msgInt.get(i) + keyInt.get(i)) % NB_ALPHA));
            } else {
                encryptedMessageInt.add(msgInt.get(i) + keyInt.get(i));
            }
        }

        System.out.println(intToCharTab(encryptedMessageInt));

        return intToCharTab(encryptedMessageInt);
    }

    public char[] decode(char[] encodedMessage, char[] key) {
        ArrayList<Integer> encodedMessageInt = strToInt(encodedMessage);
        ArrayList<Integer> keyInt = strToInt(key);
        ArrayList<Integer> decodedMessageInt = new ArrayList<>();

        System.out.println("-----dans decode------");
        System.out.println("encoded message :" + Arrays.toString(encodedMessage));


        System.out.println("decoded key :" + keyInt.toString());
        System.out.println("encoded message :" + encodedMessageInt.toString());

        for (int i = 0; i < encodedMessage.length; i++){
            if (encodedMessageInt.get(i) - keyInt.get(i) <= 0){
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i) + NB_ALPHA);
            } else {
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i));
            }
        }
        System.out.println("decoded message : " + decodedMessageInt);
        return intToCharTab(decodedMessageInt);
    }

    public char[] intToCharTab(ArrayList<Integer> flowInterger) {
        char[] charTab = new char[flowInterger.size()];

        for (int i=0; i<flowInterger.size(); i++){
            for (Caractere caractere : CONVERT_MAP) {
                if (caractere.getValue() == flowInterger.get(i)) {
                    charTab[i] = caractere.getLettre();
                }

            }
        }

        System.out.println("!!!!!!!!!!!");
        System.out.println(flowInterger.toString());
        System.out.println("charTab : " + Arrays.toString(charTab));
        return charTab;
    }

    public ArrayList<Carte> createCardInOrder() {
        ArrayList<Carte> cartes = new ArrayList<>();
        int position = 1;
        for (FormeEnum forme : FormeEnum.values()) {
            CouleurEnum couleur;
            if (forme.equals(FormeEnum.TREFLE) || forme.equals(FormeEnum.PIC)) {
                couleur = CouleurEnum.NOIR;
            } else {
                couleur = CouleurEnum.ROUGE;
            }
            for (int value = 1; value <= 13; value++) {
                Carte carte;
                if (value < 10) {
                    carte = new Carte(new Valeur(false, value), forme, couleur, false, position);

                } else {
                    TeteEnum tete;
                    switch (value) {
                        case 11:
                            tete = TeteEnum.VALET;
                            break;
                        case 12:
                            tete = TeteEnum.DAME;
                            break;
                        case 13:
                            tete = TeteEnum.ROI;
                            break;

                        default:
                            tete = TeteEnum.RIEN;
                    }
                    carte = new Carte(new Valeur(true, value, tete), forme, couleur, false, position);
                }
                cartes.add(carte);
                position++;
            }
        }
        cartes.add(new Carte(CouleurEnum.NOIR, true, 53));
        cartes.add(new Carte(CouleurEnum.ROUGE, true, 53));


        return cartes;
    }

    public ArrayList<Integer> generateKeyFlow(int length, ArrayList<Carte> cards) {
        ArrayList<Carte> cardJoker = reculJoker(cards);
        ArrayList<Carte> cardJokerSlice = sliceFromJoker(cardJoker);
        ArrayList<Carte> coupeSimple = coupeSimpleDeterminee(cardJokerSlice);

        return readNumbers(coupeSimple, length);
    }

    /*
     Recul du joker noir d’une position :
      Vous faites reculer le joker noir d’une place
      (vous le permutez avec la carte qui est juste derri`ere lui).
       Si le joker noir est en derni`ere position il passe derri`ere
       la carte du dessus (donc, en deuxi`eme position).

       Vous faites reculer le joker rouge de deux cartes.
        S’il  ́etait en derni`ere position,
        il passe en troisi`eme position; s’il  ́etait en avant
         derni`ere position il passe en deuxi`eme.
     */
    public ArrayList<Carte> reculJoker(ArrayList<Carte> cards) {
        boolean noirTrigger = false;
        boolean rougeTrigger = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                if (cards.get(i).getCouleurEnum().equals(CouleurEnum.NOIR) && !noirTrigger) {
                    // swap : Params: list – The list in which to swap elements. i – the index of one element to be swapped. j – the index of the other element to be swapped.
                    Collections.swap(cards, i, ((i + 1) % cards.size()));
                    noirTrigger = true;
                } else if (cards.get(i).getCouleurEnum().equals(CouleurEnum.ROUGE) && !rougeTrigger) {
                    Collections.swap(cards, i, ((i + 2) % cards.size()));
                    rougeTrigger = true;
                }
            }
        }
        return cards;
    }

    public ArrayList<Carte> sliceFromJoker(ArrayList<Carte> cards) {

        //get pos of joker
        ArrayList<Integer> jokerIndexes = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                jokerIndexes.add(i);
            }
        }
        ArrayList<Carte> croppedCardsList = new ArrayList<>(cards);
        ArrayList<Carte> beforeFirstJoker = new ArrayList<>();
        for (int i = 0; i < jokerIndexes.get(0); i++) {
            beforeFirstJoker.add(cards.get(i));
        }
        ArrayList<Carte> afterSecondJoker = new ArrayList<>();
        for (int i = jokerIndexes.get(1) + 1; i < cards.size(); i++) {
            afterSecondJoker.add(cards.get(i));
        }
        croppedCardsList.removeAll(beforeFirstJoker);
        croppedCardsList.removeAll(afterSecondJoker);
        croppedCardsList.addAll(beforeFirstJoker);
        croppedCardsList.addAll(0, afterSecondJoker);
        return croppedCardsList;
    }


    public ArrayList<Carte> coupeSimpleDeterminee(ArrayList<Carte> cards) {
        Carte derniereCarte = cards.get(cards.size() - 1);
        ArrayList<Carte> carteDessus = new ArrayList<>();
        ArrayList<Carte> croppedList = new ArrayList<>(cards);

        for (int i = 0; i < derniereCarte.id; i++) {
            carteDessus.add(cards.get(i));
        }
        croppedList.removeAll(carteDessus);
        croppedList.addAll(carteDessus);
        croppedList.remove(derniereCarte);
        croppedList.add(derniereCarte);
        return croppedList;
    }






    public ArrayList<Integer> readNumbers(ArrayList<Carte> cards, int length) {
        ArrayList<Integer> flowInterger = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Carte carte_n = cards.get(i);
            int n = carte_n.id;

            Carte carte_m = cards.get((n + 1) % cards.size());
            int m = carte_m.id;
            if (carte_m.isJoker()) {
                return generateKeyFlow(length, cards);
            }

            if (m > NB_ALPHA) {
                m -= NB_ALPHA;
            }

            flowInterger.add(m);
        }
        return flowInterger;
    }

    public boolean verify(ArrayList<Integer> msgInt, ArrayList<Integer> keyflow){
        for (int i = 0; i < msgInt.size(); i++){
            if (msgInt.get(i).equals(keyflow.get(i)) && msgInt.get(i) == 26){
                return false;
            }
        }
        return true;
    }
}

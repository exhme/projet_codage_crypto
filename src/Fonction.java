/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.mycompany.jdc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author ndeoliveirarafael
 */
public class Fonction {

    // Init alphabet
    public char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    // Init card format
    public String[] atouts = { "pique", "carre", "coeur", "trefle" };
    public String[] tetes = { "as", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix", "valet",
            "dame", "roi" };
    // Init card format

    private HashMap<Character, Integer> alphabetHMap;
    public HashMap<Integer, String> paquetHmap;

    public HashMap<Character, Integer> getAlphabetHmap() {
        return this.alphabetHMap;
    }

    public HashMap<Integer, String> getPaquetHmap() {
        return this.paquetHmap;
    }

    public Fonction() {
        this.alphabetHMap = this.CreateIntAlphabet();
        this.paquetHmap = this.initCardDeck();
    }

    public HashMap<Integer, String> initCardDeck() {
        HashMap<Integer, String> cards = new HashMap<Integer, String>();
        int compteur = 0;
        for (int i = 0; i < tetes.length; i++) {
            for (int y = 0; y < atouts.length; y++) {
                cards.put(compteur, tetes[i] + "_" + atouts[y]);
                compteur++;
            }
        }
        cards.put(compteur, "joker_noir");
        cards.put(compteur + 1, "joker_rouge");
        return cards;
    }

    public HashMap<Character, Integer> CreateIntAlphabet() {
        HashMap<Character, Integer> alphaInt = new HashMap<Character, Integer>();
        for (int i = 0; i < alphabet.length; i++) {
            alphaInt.put(alphabet[i], i + 1);
        }
        return alphaInt;
    }

    // LETTRE -> Chiffre
    public ArrayList<Integer> Chiffrer(String message) {
        ArrayList<Integer> resultInt = new ArrayList<Integer>();
        for (int i = 0; i < message.length(); i++) {
            char charRecupe = Character.toUpperCase(message.charAt(i));
            resultInt.add(this.alphabetHMap.get(charRecupe));
        }
        return resultInt;
    }

    public ArrayList<Integer> CreerCleCryptageCards() {
        // Clée de cryptage shuffled
        // Utile pour la génération de paquet de carte
        ArrayList<Integer> cleeIntCards = new ArrayList(this.getPaquetHmap().keySet());
        Collections.shuffle(cleeIntCards);
        return cleeIntCards;
    }

    public String GenererMessageCryptedCards(String message, ArrayList<Integer> cryptedCards) {
        String messageCrypter = "";
        ArrayList<Integer> messageToInt = this.Chiffrer(message);

        for (int i = 0; i < messageToInt.size(); i++) {
            int y = i % cryptedCards.size();
            int somme = messageToInt.get(i) + cryptedCards.get(y) - 1;
            int chiffreResultat = somme % 26;
            messageCrypter = messageCrypter + alphabet[chiffreResultat];
        }

        return messageCrypter;
    }

    public String DecrypterMessageCryptedCards(String messageCrypter, ArrayList<Integer> cryptedCards) {
        String messageInitial = "";
        ArrayList<Integer> messageCrypteToInt = this.Chiffrer(messageCrypter);

        for (int i = 0; i < messageCrypteToInt.size(); i++) {
            int y = i % cryptedCards.size();
            int somme = messageCrypteToInt.get(i) - cryptedCards.get(y) - 1;
            if (somme < 0) {
                somme = somme + 26;
            }
            // int chiffreResultat = somme % 26;
            messageInitial = messageInitial + alphabet[somme];
        }

        return messageInitial;
    }

    public String CreerCleCryptageAlpha() {
        // Clée de cryptage randomiser
        int nbChar = 26;
        String CleChiffre = "";
        Random nombre = new Random();
        for (int i = 0; i < nbChar; i++) {
            int randInt = nombre.nextInt(0 + 26);
            // A+B+T+X....
            CleChiffre = CleChiffre + alphabet[randInt];
        }
        return CleChiffre;
    }

    public ArrayList<Integer> melange(ArrayList<Integer> arrayShuffledCards) {

        int jokerNoirPos = -1;
        int jokerRougePos = -1;
        for (int i = 0; i < arrayShuffledCards.size(); i++) {
            // System.out.println(arrayShuffledCards.get(i));

            // Point n°1
            if (arrayShuffledCards.get(i) == 52) {
                int nextVal=0;
                if (i < arrayShuffledCards.size() - 1) {
                    nextVal = arrayShuffledCards.get(i + 1);
                    arrayShuffledCards.set(i, nextVal);
                    arrayShuffledCards.set(i + 1, 52);
                    if(nextVal  == 53){
                        jokerRougePos = i;
                    }
                    jokerNoirPos = i + 1;
                    i++;
                } else {
                    nextVal = arrayShuffledCards.get(1);
                    arrayShuffledCards.set(i, nextVal);
                    arrayShuffledCards.set(1, 52);
                    jokerNoirPos = 1;
                }

                if(nextVal  == 53){
                    jokerRougePos = i;
                }
            }

            // Point n°2
            if (arrayShuffledCards.get(i) == 53 && jokerRougePos == -1) {// numero joker rouge
                int nextVal = 0;
                if (i < arrayShuffledCards.size() - 2) { // 51ème carte ou moins
                    nextVal = arrayShuffledCards.get(i + 2);
                    arrayShuffledCards.set(i, nextVal);
                    arrayShuffledCards.set(i + 2, 53);
                    jokerRougePos = i + 2;
                } else { // 52ème => 2ème (1) ou 53ème carte => 3ème (2)
                    nextVal = arrayShuffledCards.get(i - 51);
                    arrayShuffledCards.set(i, nextVal);
                    arrayShuffledCards.set(i - 51, 53);
                    jokerRougePos = i - 51;
                }
                if(nextVal  == 52){
                    jokerNoirPos = i;
                }
            }

        }

        //System.out.println(jokerNoirPos);
        //System.out.println(jokerRougePos);

        ArrayList<Integer> newShuffledArray = new ArrayList<Integer>();
        int firstJoker;
        int secondJoker;
        if (jokerNoirPos < jokerRougePos) {
            firstJoker = jokerNoirPos;
            secondJoker = jokerRougePos;
        } else {
            firstJoker = jokerRougePos;
            secondJoker = jokerNoirPos;
        }

        // Place la 3ème partie du deck (après le 2ème joker) au début
        for (int x = secondJoker + 1; x < arrayShuffledCards.size(); x++) {
            newShuffledArray.add(arrayShuffledCards.get(x));
        }

        // Place la 2ème partie du deck (la partie non-permuté par les joker)
        for (int y = firstJoker; y < secondJoker + 1; y++) {
            newShuffledArray.add(arrayShuffledCards.get(y));
        }

        // Place la 1ème partie du deck (avant le 1er joker) à la fin
        for (int w = 0; w < firstJoker; w++) {
            newShuffledArray.add(arrayShuffledCards.get(w));
        }

        // Étape N°4
        int lastValue = newShuffledArray.get(53);
        arrayShuffledCards = new ArrayList<>();
        for (int y = lastValue + 1; y < newShuffledArray.size(); y++) {
            arrayShuffledCards.add(newShuffledArray.get(y));
        }
        for (int x = 0; x < lastValue + 1; x++) {
            arrayShuffledCards.add(newShuffledArray.get(x));
        }
        arrayShuffledCards.add(newShuffledArray.get(53));

        return arrayShuffledCards;
    }

    public boolean checkIfIndexIsJoker(ArrayList<Integer> listOfCards, int indexToCheck) {
        boolean check = false;
        if (listOfCards.get(indexToCheck) == 52 || listOfCards.get(indexToCheck) == 53) {
            check = true;
        }
        return check;
    }

    public String getAlphaKey(int messageLength,ArrayList<Integer> listOfCards) {
        String key = "";
        for(int o =0; o <messageLength;o++){
            do {
                listOfCards = this.melange(listOfCards);
            } while (this.checkIfIndexIsJoker(listOfCards, listOfCards.get(0)));

            int m =listOfCards.get( listOfCards.get(0) ) %26;
            key = key+alphabet[m];
        }
        return key;
    }

    public HashMap<Integer, Character> searchSpecialChar(String message){

        HashMap<Integer, Character> specialChar = new HashMap<Integer, Character>();
        for (int i = 0; i < message.length() ; i++) {
            if(!(this.alphabetHMap.containsKey(Character.toUpperCase(message.charAt(i))))){
                specialChar.put(i, message.charAt(i));
            }
        }
        return specialChar;
    }

    public String cleanMessage(String msg, HashMap<Integer, Character> specialChar ){

        ArrayList<Integer> posList = new ArrayList(specialChar.keySet());
        for (Integer integer : posList) {
            if(integer>0 && integer<msg.length()){
                msg= msg.substring(0,integer)+ "|"+msg.substring(integer+1,msg.length());
            }else{
                if(integer == 0){
                    msg = "|"+msg.substring(1, msg.length());
                }else{
                    msg = msg.substring(0, msg.length()-1);
                }
            }
        }
        return msg.replace("|","");
    }

    public String recreateInitMessage(String msg, HashMap<Integer, Character> specialChar ){
        ArrayList<Integer> keyList = new ArrayList<>(specialChar.keySet());
        Collections.sort(keyList);
        for (int i : keyList) {
            msg = msg.substring(0,i)+specialChar.get(i)+msg.substring(i);
        }
        return msg;
    }


    public String filterMessage(String msg){
        String message = "";
        for (int i=0; i< msg.length(); i++){
            message = message + Filtrer(msg.charAt(i));
        }
        return message;
    }

    public Character Filtrer(Character c){
        Character result = c;
        switch(c){
            case 'À':
                result='A';
                break;
            case 'Á':
                result='A';
                break;
            case 'Â':
                result='A';
                break;
            case 'Ã':
                result='A';
                break;
            case 'Ä':
                result='A';
                break;
            case 'Å':
                result='A';
                break;
            //case 'Æ':
            // c='AE';
            //break;
            case 'Ç':
                result='C';
                break;
            case 'ç':
                result='C';
                break;
            case 'È':
                result='E';
                break;
            case 'É':
                result='E';
                break;
            case 'Ê':
                result='E';
                break;
            case 'Ë':
                result='E';
                break;
            case 'Ì':
                result='I';
                break;
            case 'Í':
                result='I';
                break;
            case 'Î':
                result='I';
                break;
            case 'Ï':
                result='I';
                break;
            case 'Ñ':
                result='N';
                break;
            case 'Ò':
                result='O';
                break;
            case 'Ó':
                result='O';
                break;
            case 'Ô':
                result='O';
                break;
            case 'Õ':
                result='O';
                break;
            case 'Ö':
                result='O';
                break;
            case 'Ù':
                result='U';
                break;
            case 'Ú':
                result='U';
                break;
            case 'Û':
                result='U';
                break;
            case 'Ü':
                result='U';
                break;
            case 'Ý':
                result='Y';
                break;
        }
        return result;
    }

    // public String GenererMessageCrypte(String message, String cleeCryptage ) {
    // String messageCrypter ="";
    // ArrayList<Integer> cleeToInt = this.Chiffrer(cleeCryptage);
    // ArrayList<Integer> messageToInt = this.Chiffrer(message);

    // for(int i = 0 ; i<messageToInt.size(); i++){
    // int y = i % cleeToInt.size();
    // int somme =messageToInt.get(i) + cleeToInt.get(y)-1;
    // int chiffreResultat = somme % 26;
    // messageCrypter = messageCrypter+alphabet[chiffreResultat];
    // }

    // return messageCrypter;
    // }

    // public String DecrypterMessage(String messageCrypter, String cleeCryptage ) {
    // String messageInitial ="";
    // ArrayList<Integer> cleeToInt = this.Chiffrer(cleeCryptage);
    // ArrayList<Integer> messageCrypteToInt = this.Chiffrer(messageCrypter);

    // for(int i = 0 ; i<messageCrypteToInt.size(); i++){
    // int y = i % cleeToInt.size();
    // int somme =messageCrypteToInt.get(i) - cleeToInt.get(y)-1;
    // if(somme<0){
    // somme=somme+26;
    // }
    // //int chiffreResultat = somme % 26;
    // messageInitial = messageInitial+alphabet[somme];
    // }

    // return messageInitial;
    // }
}

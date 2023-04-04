import Enums.CouleurEnum;
import Enums.FormeEnum;
import Enums.TeteEnum;

import java.util.*;

public class Helper {
    private final Map<Character, Integer> CONVERT_MAP;

    public Helper() {
        CONVERT_MAP = new HashMap<>();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 1; i < alphabet.length + 1; i++) {
            CONVERT_MAP.put(alphabet[i - 1], i);
        }
    }

    public ArrayList<Integer> strToInt(char[] chars) {
        ArrayList<Integer> sortie = new ArrayList<>();
        for (char ch : chars) {
            sortie.add(CONVERT_MAP.get(ch));
        }
        return sortie;
    }

    public char[] encode(char[] msg, char[] key) {
        ArrayList<Integer> msgInt = strToInt(msg);
        ArrayList<Integer> keyInt = strToInt(key);
//        System.out.println("msg : " + Arrays.toString(msg));
        System.out.println("msg int : " + msgInt.toString());

//        System.out.println("key : " + Arrays.toString(key));
//        System.out.println("key int : " + keyInt.toString());

        ArrayList<Integer> encryptedMessageInt = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
//            System.out.println("msgInt.get("+ i +") : " + msgInt.get(i));
//            System.out.println("keyInt.get("+ i +") : " + keyInt.get(i));
//            if ((msgInt.get(i) + keyInt.get(i)) % 26 == 0) {
//                encryptedMessageInt.add(1);
//            } else {
//            }
            if (msgInt.get(i) + keyInt.get(i) > 26) {
                encryptedMessageInt.add(((msgInt.get(i) + keyInt.get(i)) % 26) + 1);
            } else {
                encryptedMessageInt.add(msgInt.get(i) + keyInt.get(i));
            }
        }

        return intToCharTab(encryptedMessageInt);
    }

    public char[] decode(char[] encodedMessage, char[] key){
        ArrayList<Integer> encodedMessageInt = strToInt(encodedMessage);
        ArrayList<Integer> keyInt = strToInt(key);
        ArrayList<Integer> decodedMessageInt = new ArrayList<>();

        System.out.println("decoded key :" + keyInt.toString());
        System.out.println("encoded message :" + encodedMessageInt.toString());

        for (int i = 0; i < encodedMessage.length; i++){
            System.out.println("Resultat de " + encodedMessageInt.get(i) + " - " + keyInt.get(i) + ": " + (encodedMessageInt.get(i) - keyInt.get(i)));
            if (encodedMessageInt.get(i) - keyInt.get(i) <= 0){
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i) + 25);
            } else {
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i));
            }
        }
        System.out.println("decoded message : " + decodedMessageInt);
        return intToCharTab(decodedMessageInt);
    }

    public char[] intToCharTab(ArrayList<Integer> flowInterger) {
        char[] charTab = new char[flowInterger.size()];

        for (int i = 0; i < flowInterger.size(); i++) {
            for (Map.Entry<Character, Integer> entry : CONVERT_MAP.entrySet()) {

                if (entry.getValue().equals(flowInterger.get(i))) {
//                    System.out.println("letter found: " + entry.getKey() + " for value: " + entry.getValue());
                    //System.out.println(entry.getKey());
                    charTab[i] = entry.getKey();
                }
            }
        }

//        System.out.println("charTab : " + Arrays.toString(charTab));
        return charTab;
    }

    public ArrayList<Carte> buildAllCard() {
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


    public ArrayList<Carte> reculJoker(ArrayList<Carte> cards) {
        boolean noirTrigger = false;
        boolean rougeTrigger = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                if (cards.get(i).getCouleurEnum().equals(CouleurEnum.NOIR) && !noirTrigger) {
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
        ArrayList<Integer> jokerIndexes = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                jokerIndexes.add(i);
            }
        }
        ArrayList<Carte> croppedCardsList = new ArrayList<Carte>(cards);
        ArrayList<Carte> beforeFirstJoker = new ArrayList<Carte>();
        for (int i = 0; i < jokerIndexes.get(0); i++) {
            beforeFirstJoker.add(cards.get(i));
        }
        ArrayList<Carte> afterSecondJoker = new ArrayList<Carte>();
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
        ArrayList<Carte> carteDessus = new ArrayList<Carte>();
        ArrayList<Carte> croppedList = new ArrayList<Carte>(cards);

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

            if (m > 26) {
                m -= 26;
            }

            flowInterger.add(m);
        }
        return flowInterger;
    }
}

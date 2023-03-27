import Enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    private final Map<Character, Integer> CONVERT_MAP;

    public Helper() {
        CONVERT_MAP = new HashMap<>();
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 1; i < alphabet.length + 1; i++) {
            CONVERT_MAP.put(alphabet[i - 1], i);
        }
    }

    public ArrayList<Integer> strToInt(String str) {
        ArrayList<Integer> sortie = new ArrayList<>();
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            sortie.add(CONVERT_MAP.get(ch));
        }
        return sortie;
    }


    public Map<Character, Integer> getCONVERT_MAP() {
        return CONVERT_MAP;
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

    public ArrayList<Integer> generatekeyFlow(int length, ArrayList<Carte> cards) {
        ArrayList<Carte> cardJoker = reculJoker(cards);
        ArrayList<Carte> cardJokerSlice = sliceFromJoker(cards);
        return null;
    }


    public ArrayList<Carte> reculJoker(ArrayList<Carte> cards){
        boolean noirTrigger = false;
        boolean rougeTrigger = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                if (cards.get(i).getCouleurEnum().equals(CouleurEnum.NOIR) && !noirTrigger) {
                    Collections.swap(cards, i, (i+1)%cards.size());
                    noirTrigger = true;
                }

                else if (cards.get(i).getCouleurEnum().equals(CouleurEnum.ROUGE) && !rougeTrigger) {
                    Collections.swap(cards, i, (i+2)%cards.size());
                    rougeTrigger = true;
                }


            }
        }
        return cards;
    }

    public ArrayList<Carte> sliceFromJoker(ArrayList<Carte> cards){

        //get pos of joker
        ArrayList<Integer> jokerIndexes = new ArrayList<Integer>();
        for (int i = 0; i<cards.size();i++){
            if (cards.get(i).isJoker()){
                   jokerIndexes.add(i);
            }

        }

        ArrayList<Carte> croppedCardsList = new ArrayList<Carte>(cards);


        ArrayList<Carte> beforeFirstJoker = new ArrayList<Carte>();
        for (int i = 0; i<jokerIndexes.get(0);i++){
            beforeFirstJoker.add(cards.get(i));
        }
        ArrayList<Carte> afterSecondJoker = new ArrayList<Carte>();
        for (int i = jokerIndexes.get(1)+1; i<cards.size();i++){
            afterSecondJoker.add(cards.get(i));
        }
        croppedCardsList.removeAll(beforeFirstJoker);
        croppedCardsList.removeAll(afterSecondJoker);

        croppedCardsList.addAll(beforeFirstJoker);
        croppedCardsList.addAll(0,afterSecondJoker);


        return cards;
    }
}

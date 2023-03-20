import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {
    private Map<Character, Integer> CONVERT_MAP;

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


    public HashMap<Integer, Carte> buildAllCard() {
        HashMap<Integer, Carte> cartes = new HashMap<>();
        int position = 0;
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
                    carte = new Carte(new Valeur(false, value), forme, couleur, false);

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
                    carte = new Carte(new Valeur(true, value, tete), forme, couleur, false);
                }
                cartes.put(position++, carte);
            }
        }
        cartes.put(position++, new Carte(CouleurEnum.NOIR, true));
        cartes.put(position++, new Carte(CouleurEnum.ROUGE, true));


        return cartes;
    }
}

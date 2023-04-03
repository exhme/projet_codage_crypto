import Enums.CouleurEnum;
import Enums.FormeEnum;
import Enums.TeteEnum;

import java.io.*;
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


        System.out.println("msg int : " + msgInt.toString());
        System.out.println("key int : " + keyInt.toString());

        char[] encryptedMsg;
        ArrayList<Integer> encryptedMessageInt = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            encryptedMessageInt.add((msgInt.get(i) + keyInt.get(i)) % 26);
        }
        encryptedMsg = intToCharTab(encryptedMessageInt);

        return encryptedMsg;
    }

    public char[] decode(char[] encodedMessage, char[] key) {
        ArrayList<Integer> encodedMessageInt = strToInt(encodedMessage);
        ArrayList<Integer> keyInt = strToInt(key);
        ArrayList<Integer> decodedMessageInt = new ArrayList<>();

        for (int i = 0; i < encodedMessage.length; i++) {
            if (encodedMessageInt.get(i) - keyInt.get(i) < 0) {
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i) + 26);
            } else {
                decodedMessageInt.add(encodedMessageInt.get(i) - keyInt.get(i));
            }
        }

        return intToCharTab(decodedMessageInt);
    }

    public char[] intToCharTab(ArrayList<Integer> flowInterger) {
        char[] charTab = new char[flowInterger.size()];

        for (int i = 0; i < flowInterger.size(); i++) {
            for (Map.Entry<Character, Integer> entry : CONVERT_MAP.entrySet()) {
                if (entry.getValue().equals(flowInterger.get(i))) {
                    charTab[i] = entry.getKey();
                }
            }
        }
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

        ArrayList<Integer> numbers = readNumbers(coupeSimple, length);


        return numbers;
    }


    public ArrayList<Carte> reculJoker(ArrayList<Carte> cards) {
        boolean noirTrigger = false;
        boolean rougeTrigger = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isJoker()) {
                if (cards.get(i).getCouleurEnum().equals(CouleurEnum.NOIR) && !noirTrigger) {
                    Collections.swap(cards, i, (i + 1) % cards.size());
                    noirTrigger = true;
                } else if (cards.get(i).getCouleurEnum().equals(CouleurEnum.ROUGE) && !rougeTrigger) {
                    Collections.swap(cards, i, (i + 2) % cards.size());
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

    public String exportCardOrder(ArrayList<Carte> cards) {
        System.out.println("export du jeu de carte");
        try {
            File file = new File("export.txt");
            //creation du fichier si il existe pas
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < cards.size(); i++) {
               //traitement specifique pour les joker
                if (cards.get(i).isJoker()){
                    CouleurEnum couleur = cards.get(i).getCouleurEnum();
                    if (couleur == CouleurEnum.NOIR){
                        bw.write(Integer.toString(cards.get(i).id)+"0");
                    } else if (couleur == CouleurEnum.ROUGE) {
                        bw.write(Integer.toString(cards.get(i).id)+"1");
                    }
                }
                else {
                    bw.write(Integer.toString(cards.get(i).id));
                }
                if (i - 1 != cards.size()) {
                    bw.newLine();
                }
            }
            bw.close();
            System.out.println("jeu exporté : " + file.getName());
            return file.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Carte> importCardOrder(String filename){
        ArrayList<Integer> outputFile = new ArrayList<>();
        ArrayList<Carte> carteInOrder = createCardInOrder();
        try{
            File file = new File(filename);
            if (!file.exists()){
                System.out.println("[Importer] : file not found");
                return null;
            }
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                outputFile.add(Integer.parseInt(line));
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArrayList<Carte> outputCarte = new ArrayList<>();


        System.out.println("output file size : "+outputFile.size());
        System.out.println("carte dans lordre size : "+carteInOrder.size());

        System.out.println(outputFile);
        for (int i = 0 ; i<outputFile.size();i++){
            if (outputFile.get(i)==530){
                // joker noir
                for (int j =0; j < carteInOrder.size();j++){
                    if (carteInOrder.get(j).isJoker() && carteInOrder.get(j).id == 53 && carteInOrder.get(j).getCouleurEnum().equals(CouleurEnum.NOIR)){
                        outputCarte.add(carteInOrder.get(j));
                    }
                }
            }
            else if (outputFile.get(i)==531){
                //joker rouge
                for (int j =0; j < carteInOrder.size();j++){
                    if (carteInOrder.get(j).isJoker() && carteInOrder.get(j).id == 53 && carteInOrder.get(j).getCouleurEnum().equals(CouleurEnum.ROUGE)){
                        outputCarte.add(carteInOrder.get(j));
                    }
                }
            }
            for (int y = 0; y<carteInOrder.size();y++){
                if (outputFile.get(i) == carteInOrder.get(y).id){
                    outputCarte.add(carteInOrder.get(y));
                }
            }
        }

        return outputCarte;

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


    public String encodeButtonTriggered(String msg) {
       ArrayList<Carte> cards = createCardInOrder();
       Collections.shuffle(cards);
       ArrayList<Integer> keyflow = generateKeyFlow(msg.length(),cards);
        System.out.println("keyflow : "+keyflow);
        char[] key = intToCharTab(keyflow);
        char[] encodedMessage = encode(msg.toCharArray(),key);
        System.out.println("encoded message : "+new String(encodedMessage));

        String filename = exportCardOrder(cards);

        return filename;

    }

}

import Enums.CouleurEnum;

import java.io.*;
import java.util.ArrayList;

public class IOhelper extends Helper{

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
        System.out.println("[Importer] : importation terminée");

        return outputCarte;

    }
}

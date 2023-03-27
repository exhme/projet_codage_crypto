import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        // View vue = new View();
        System.out.println("bonjour");

        String msgToEncode = "alerte";

        Helper helper = new Helper();
        ArrayList<Integer> msgInt = helper.strToInt(msgToEncode);
        ArrayList<Carte> cards = helper.buildAllCard();

        ArrayList<Carte> cardsShuffle = cards;
        Collections.shuffle(cardsShuffle);
        ArrayList<Integer> keyflow = helper.generatekeyFlow(msgInt.size(), cardsShuffle);


        ArrayList<Integer> list = helper.strToInt("bonjour");


    }


}

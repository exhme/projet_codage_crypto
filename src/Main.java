import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        // View vue = new View();

        String msgToEncode = "alerte";
        System.out.println("message to encode : "+msgToEncode);

        Helper helper = new Helper();
        ArrayList<Carte> cards = helper.buildAllCard();

        ArrayList<Carte> cardsShuffle = cards;

        //for (int i = 0 ; i<100;i++){
        Collections.shuffle(cardsShuffle);
        ArrayList<Integer> keyflow = helper.generatekeyFlow(msgToEncode.length(), cardsShuffle);
        System.out.println("keyflow : "+keyflow);
        char[] key = helper.intToCharTab(keyflow);
        char[] encodedMessage  = helper.encode(msgToEncode.toCharArray(),key);
        System.out.println("message encodé : "+ Arrays.toString(encodedMessage));


        char[] decodedMessage = helper.decode(encodedMessage,key);
        System.out.println("message decodé : "+ Arrays.toString(decodedMessage));


    }


}

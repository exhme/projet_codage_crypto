import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        Helper helper = new Helper();
        String msgToEncode = "alerte";
        View view = new View();



        System.out.println("message to encode : "+ msgToEncode);

        ArrayList<Carte> cards = helper.createCardInOrder();

        Collections.shuffle(cards);
        helper.exportCardOrder(cards);
        ArrayList<Carte> cardImport = helper.importCardOrder("export.txt");
        ArrayList<Integer> keyflow = helper.generateKeyFlow(msgToEncode.length(), cardImport);
        System.out.println("keyflow : "+keyflow);


        char[] key = helper.intToCharTab(keyflow);
        char[] encodedMessage  = helper.encode(msgToEncode.toCharArray(),key);
        System.out.println("message encodé : "+ Arrays.toString(encodedMessage));

        char[] decodedMessage = helper.decode(encodedMessage,key);
        System.out.println("message decodé : "+ Arrays.toString(decodedMessage));
    }
}

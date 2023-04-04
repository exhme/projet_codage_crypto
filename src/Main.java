import java.lang.reflect.GenericDeclaration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        Helper helper = new Helper();
        String msgToEncode = "azerty";



        System.out.println("message to encode : "+ msgToEncode);

        ArrayList<Carte> cards = helper.createCardInOrder();

        Collections.shuffle(cards);
<<<<<<< HEAD
        helper.exportCardOrder(cards);
        ArrayList<Carte> cardImport = helper.importCardOrder("export.txt");
        ArrayList<Integer> keyflow = helper.generateKeyFlow(msgToEncode.length(), cardImport);
        System.out.println("keyflow : "+keyflow);
=======
        ArrayList<Integer> keyflow = helper.generateKeyFlow(msgToEncode.length(), cards);
//        System.out.println("keyflow : " + keyflow);
>>>>>>> fix_algo


        char[] key = helper.intToCharTab(keyflow);
        char[] encodedMessage  = helper.encode(msgToEncode.toCharArray(),key);
//        System.out.println("message encodé : " + Arrays.toString(encodedMessage));
        System.out.println("message int: " + helper.strToInt(encodedMessage));

        char[] decodedMessage = helper.decode(encodedMessage,key);
        System.out.println("message decodé : "+ Arrays.toString(decodedMessage));
        System.out.println("decoded int : " + helper.strToInt(decodedMessage));
    }
}

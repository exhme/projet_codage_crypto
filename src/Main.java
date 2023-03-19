import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        System.out.println("bonjour");
        Helper helper = new Helper();
        System.out.println(helper.getCONVERT_MAP());
        ArrayList<Integer> messageEnInt = helper.strToInt("bonjour");
        HashMap<Integer,Carte> cards = helper.buildAllCard();

        
    }


}

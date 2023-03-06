import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        System.out.println("bonjour");
        Helper helper = new Helper();
        System.out.println(helper.getCONVERT_MAP());
        ArrayList<Integer> messageEnInt = helper.strToInt("bonjour");
        ArrayList<Carte> cards = helper.buildAllCard();
    }


}

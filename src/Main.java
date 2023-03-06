import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("bonjour");

        Helper helper = new Helper();
        System.out.println(helper.getCONVERT_MAP());
        int [] messageEnInt = helper.strToInt("bonjour");
    }


}

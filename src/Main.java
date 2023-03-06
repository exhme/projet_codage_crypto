import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("bonjour");

        strToInt("bonjour");
    }




    public static void strToInt(String str){
        Map<Integer,Character> CONVERT_MAP = new HashMap<>();
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for (int i = 1; i<alphabet.length+1;i++){
            CONVERT_MAP.put(i,alphabet[i-1]);
        }

        System.out.println(CONVERT_MAP);

    }
}

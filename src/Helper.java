import java.util.HashMap;
import java.util.Map;

public class Helper {
    private Map<Character,Integer> CONVERT_MAP;

    public Helper(){
        CONVERT_MAP = new HashMap<>();
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for (int i = 1; i<alphabet.length+1;i++){
            CONVERT_MAP.put(alphabet[i-1],i);
        }





    }


    public int[] strToInt(String str){
        int[] sortie = new int[str.length()];
        System.out.println("taille : "+sortie.length);
        char[] chars = str.toCharArray();
        for (int i=0;i<chars.length;i++){
            sortie[i] = CONVERT_MAP.get(chars[i]);
        }
        return sortie;
    }

    public Map<Character, Integer> getCONVERT_MAP() {
        return CONVERT_MAP;
    }
}

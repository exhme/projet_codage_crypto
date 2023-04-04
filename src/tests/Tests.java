
import Enums.CouleurEnum;
import Enums.FormeEnum;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class Tests {

    @Test
    public void testReculJoker1() {
        // créer une liste de cartes contenant 2 jokers (noir et rouge)
        ArrayList<Carte> cards = new ArrayList<>();
        cards.add(new Carte(new Valeur(false, 10), FormeEnum.PIC,CouleurEnum.NOIR,false, 10));
        cards.add(new Carte(new Valeur(false, 5), FormeEnum.COEUR,CouleurEnum.ROUGE,false, 5));
        cards.add(new Carte(CouleurEnum.ROUGE, true, 53));
        cards.add(new Carte(new Valeur(false, 2), FormeEnum.TREFLE,CouleurEnum.NOIR,false,2));
        cards.add(new Carte(CouleurEnum.NOIR, true, 53));
        cards.add(new Carte(new Valeur(false,13 ),FormeEnum.CARREAU, CouleurEnum.ROUGE,false, 13));

        // appel à la méthode reculJoker
        Helper helper = new Helper();
        ArrayList<Carte> result = helper.reculJoker(cards);

        // vérification que les jokers ont été déplacés correctement
        assertEquals(result.get(4).getId(), 53); // joker rouge a été déplacé au 5ème emplacement
    }

    @Test
    public void testReculJoker2() {
        // créer une liste de cartes contenant 2 jokers (noir et rouge)
        ArrayList<Carte> cards = new ArrayList<>();
        cards.add(new Carte(new Valeur(false, 10), FormeEnum.PIC,CouleurEnum.NOIR,false, 10));
        cards.add(new Carte(new Valeur(false, 5), FormeEnum.COEUR,CouleurEnum.ROUGE,false, 5));
        cards.add(new Carte(CouleurEnum.ROUGE, true, 53));
        cards.add(new Carte(new Valeur(false, 2), FormeEnum.TREFLE,CouleurEnum.NOIR,false,2));
        cards.add(new Carte(CouleurEnum.NOIR, true, 53));
        cards.add(new Carte(new Valeur(false,13 ),FormeEnum.CARREAU, CouleurEnum.ROUGE,false, 13));

        // appel à la méthode reculJoker
        Helper helper = new Helper();
        ArrayList<Carte> result = helper.reculJoker(cards);

        // vérification que les jokers ont été déplacés correctement
        assertEquals(result.get(2).getId(), 53); // joker noir a été déplacé au 3ème emplacement
    }
}

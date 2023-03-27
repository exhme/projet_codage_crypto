import Enums.CouleurEnum;
import Enums.FormeEnum;

public class Carte {
    public int id;
    private Valeur valeur;
    private FormeEnum formeEnum;
    private CouleurEnum couleurEnum;
    private boolean joker;

    public Carte(Valeur valeur, FormeEnum formeEnum, CouleurEnum couleurEnum, boolean joker, int id) {
        this.valeur = valeur;
        this.formeEnum = formeEnum;
        this.couleurEnum = couleurEnum;
        this.joker = joker;
        this.id = id;
    }

    public Carte(CouleurEnum couleur, boolean joker, int id) {
        this.couleurEnum = couleur;
        this.joker = joker;
        this.id = id;
    }

    public Valeur getValeur() {
        return valeur;
    }

    public void setValeur(Valeur valeur) {
        this.valeur = valeur;
    }

    public FormeEnum getFormeEnum() {
        return formeEnum;
    }

    public void setFormeEnum(FormeEnum formeEnum) {
        this.formeEnum = formeEnum;
    }

    public CouleurEnum getCouleurEnum() {
        return couleurEnum;
    }

    public void setCouleurEnum(CouleurEnum couleurEnum) {
        this.couleurEnum = couleurEnum;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


public class Carte {
    private Valeur valeur;
    private FormeEnum formeEnum;
    private CouleurEnum couleurEnum;
    private boolean joker;

    public Carte(Valeur valeur, FormeEnum formeEnum, CouleurEnum couleurEnum, boolean joker) {
        this.valeur = valeur;
        this.formeEnum = formeEnum;
        this.couleurEnum = couleurEnum;
        this.joker = joker;
    }

    public Carte(CouleurEnum couleur, boolean joker) {
        this.couleurEnum = couleur;
        this.joker = joker;
    }
}


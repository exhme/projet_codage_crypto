public class Valeur {
    boolean isTete;
    int value;
    TeteEnum teteEnum;

    public Valeur(boolean isTete, int value) {
        this.isTete = isTete;
        this.value = value;
    }
    public Valeur(boolean isTete, int value, TeteEnum teteEnum) {
        this.isTete = isTete;
        this.value = value;
        this.teteEnum = teteEnum;
    }

    public Valeur(){

    }
}

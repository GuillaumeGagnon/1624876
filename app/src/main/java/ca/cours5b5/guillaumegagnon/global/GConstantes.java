package ca.cours5b5.guillaumegagnon.global;

import android.util.Log;

public final class GConstantes {

    private GConstantes(){}

    static{
        Log.d("Atelier04", GConstantes.class.getSimpleName() + "::static");
    }

    public static final int LARGEUR_MIN = 4;
    public static final int LARGEUR_MAX = 10;
    public static final int LARGEUR_PAR_DEFAUT = 7;

    public static final int HAUTEUR_MIN = 4;
    public static final int HAUTEUR_MAX = 10;
    public static final int HAUTEUR_PAR_DEFAUT = 6;

    public static final int POUR_GAGNER_MIN = 3;
    public static final int POUR_GAGNER_MAX = 5;
    public static final int POUR_GAGNER_PAR_DEFAUT = 4;

    public static final String SEPARATEUR_DE_CHEMIN="/";

}

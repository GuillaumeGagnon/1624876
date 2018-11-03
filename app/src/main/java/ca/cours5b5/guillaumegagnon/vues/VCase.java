package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.global.GCouleur;


public class VCase extends AppCompatButton {

    public VCase(Context context) {
        super(context);
        initialiser();
    }

    public VCase(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiser();
    }

    public VCase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialiser();
    }


    public VCase(Context context, int rangee, int colonne) {
        super(context);

        setText(""+rangee+","+colonne);

        initialiser();

    }

    private void initialiser() {

        changerCouleurDeFond(R.color.VIDE);

    }

    private void changerCouleurDeFond(int idCouleur) {

        setBackgroundColor(getResources().getColor(idCouleur, null));

    }

    public void afficherJeton(GCouleur jeton) {

        switch (jeton){

            case ROUGE:

                changerCouleurDeFond(R.color.ROUGE);
                break;

            case JAUNE:

                changerCouleurDeFond(R.color.JAUNE);
                break;
        }
    }

}

package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.modeles.MJeton;


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

        // Atelier08: afficher les indices
        setText(""+rangee+","+colonne);

        initialiser();
    }

    private void initialiser() {
        
        setEnabled(false);

        setBackgroundColor(getResources().getColor(R.color.VIDE, null));

    }

    public void afficherJeton(MJeton jeton) {

        afficherCouleurJeton(jeton);

    }


    private void afficherCouleurJeton(MJeton jeton) {
        switch (jeton.getCouleur()){

            case ROUGE:

                setBackgroundColor(getResources().getColor(R.color.ROUGE, null));

                break;

            case JAUNE:

                setBackgroundColor(getResources().getColor(R.color.JAUNE, null));

                break;

        }
    }


}

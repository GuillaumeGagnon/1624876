package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ca.cours5b5.guillaumegagnon.global.GCouleur;

public class VCase extends AppCompatButton{
    public VCase(Context context) {
        super(context);
    }

    public VCase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VCase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VCase(Context context, int rangee, int colonne){
        super(context);

        this.setText(rangee + "," + colonne);
        setBackgroundColor(Color.GRAY);
    }

    public void afficherJeton(GCouleur jeton){
        int couleur = Color.RED;

        if(jeton.equals(GCouleur.ROUGE)){
            couleur = Color.RED;
        } else if (jeton.equals(GCouleur.JAUNE)){
            couleur = Color.YELLOW;
        }

        setBackgroundColor(couleur);
    }

}

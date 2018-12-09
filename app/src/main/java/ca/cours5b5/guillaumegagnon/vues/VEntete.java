package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ca.cours5b5.guillaumegagnon.R;


public class VEntete extends AppCompatButton {

    public VEntete(Context context) {
        super(context);
    }

    public VEntete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VEntete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int colonne;

    public VEntete(Context context, int colonne) {
        super(context);

        // Atelier 08: afficher la colonne pour deboguer
        setText(colonne + "\n" + getResources().getString(R.string.entete));

        this.colonne = colonne;
    }

    public int getColonne() {
        return colonne;
    }
}

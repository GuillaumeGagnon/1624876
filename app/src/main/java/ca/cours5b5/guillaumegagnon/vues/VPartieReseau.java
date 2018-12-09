package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurObservation;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurObservation;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;
import ca.cours5b5.guillaumegagnon.modeles.Modele;


public class VPartieReseau extends VPartie {


    private VGrille grille;


    public VPartieReseau(Context context) {
        super(context);
    }

    public VPartieReseau(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VPartieReseau(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected String getNomModele(){
        return MPartieReseau.class.getSimpleName();
    }

}

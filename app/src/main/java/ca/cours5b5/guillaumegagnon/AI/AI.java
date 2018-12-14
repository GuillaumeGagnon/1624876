package ca.cours5b5.guillaumegagnon.AI;

import android.util.Log;

import java.util.ArrayList;

import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.modeles.MColonne;
import ca.cours5b5.guillaumegagnon.modeles.MGrille;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;

public class AI {

    public MParametresPartie parametres;
    //public MGrille grille;
    public MGrille grilletTest;


    public AI(MParametresPartie parametres, MGrille grille){
        this.parametres = parametres;
        //this.grille = grille;
        //this.grilletTest = new MGrille(parametres.getLargeur()); // mauvais?

        this.grilletTest = grille.clone(); // on la remplie

    }

    public boolean siCoupLegal(int colonne) {

        MColonne mColonne = grilletTest.getColonnes().get(colonne);

        return mColonne.getJetons().size() < parametres.getHauteur();

    }

    public boolean siCoupGagnant(int coup, GCouleur couleurCourante){
        grilletTest.placerJeton(coup, couleurCourante); // on joue/test un coup
        if( grilletTest.siCouleurGagne(couleurCourante, parametres.getPourGagner())){
            Log.e("debugageAI", "siCoupGagnant: gagnant: " + coup);
            return true; //si c'est un coup gagnant
        } else{
            return false; // si c,est un coup normal
        }
    }

    public void jouer(int coup, GCouleur couleurCouranteAI){
        grilletTest.placerJeton(coup, couleurCouranteAI);
    }
}

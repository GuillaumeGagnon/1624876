package ca.cours5b5.guillaumegagnon.controleurs;


import android.util.Log;

import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;

public final class ControleurPartieAI {

    private ControleurPartieAI(){}

    private static final ControleurPartieAI instance = new ControleurPartieAI();
    public static ControleurPartieAI getInstance(){return instance;}

    public void gagnerPartie(GCouleur couleurGagnante){

        Log.d("debug_AI", "ControleurPartieAI/gagnerPartie");

        Action actionTerminerPartie = ControleurAction.demanderAction(GCommande.TERMINER_PARTIE_AI);

        Action actionAfficherMessage = ControleurAction.demanderAction(GCommande.AFFICHER_MESSAGE_GAGNANT);


        actionAfficherMessage.setArguments(couleurGagnante,
                actionTerminerPartie);

        actionAfficherMessage.executerDesQuePossible();

    }


}

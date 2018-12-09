package ca.cours5b5.guillaumegagnon.controleurs;


import android.util.Log;

import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;

public final class ControleurPartie {

    private ControleurPartie(){}

    private static final ControleurPartie instance = new ControleurPartie();
    public static ControleurPartie getInstance(){return instance;}

    public void gagnerPartie(GCouleur couleurGagnante){

        Action actionTerminerPartie = ControleurAction.demanderAction(GCommande.TERMINER_PARTIE);

        Action actionAfficherMessage = ControleurAction.demanderAction(GCommande.AFFICHER_MESSAGE_GAGNANT);


        actionAfficherMessage.setArguments(couleurGagnante,
                actionTerminerPartie);

        actionAfficherMessage.executerDesQuePossible();

    }


}

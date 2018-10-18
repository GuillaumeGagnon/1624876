package ca.cours5b5.guillaumegagnon.controleurs.interfaces;

import ca.cours5b5.guillaumegagnon.modeles.Modele;

public abstract class ListenerObservateur {

    public void reagirNouveauModele(Modele modele) {
        /*
         * implementation par default = appeler reagirChangementAuModele
         *
         */
        reagirChangementAuModele(modele);
    }

    public abstract void reagirChangementAuModele(Modele modele);


}

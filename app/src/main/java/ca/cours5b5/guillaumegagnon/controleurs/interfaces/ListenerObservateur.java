package ca.cours5b5.guillaumegagnon.controleurs.interfaces;


import ca.cours5b5.guillaumegagnon.modeles.Modele;

public abstract class ListenerObservateur {

    public void reagirNouveauModele(Modele modele){
        reagirChangementAuModele(modele);
    }

    public abstract void reagirChangementAuModele(Modele modele);

}

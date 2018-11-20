package ca.cours5b5.guillaumegagnon.donnees;

import java.util.Map;


public abstract class SourceDeDonnees {

    //atelier 12 modif de la signature de la methode
    public abstract void chargerModele(final String cheminSauvegarde, final ListenerChargement listenerChargement);

    public abstract void sauvegarderModele(final String cheminSauvegarde, final Map<String, Object> objetJson);

    public abstract void detruireSauvegarde(String cheminSauvegarde);




    protected String getNomModele(String cheminSauvegarde){
        return cheminSauvegarde.split("/")[0];
    }

}

package ca.cours5b5.guillaumegagnon.donnees;


import java.util.Map;

import ca.cours5b5.guillaumegagnon.global.GConstantes;

public abstract class SourceDeDonnees {

    public abstract void chargerModele(final String cheminSauvegarde, final ListenerChargement listenerChargement);

    public abstract void sauvegarderModele(String cheminSauvegarde, Map<String, Object> objetJson);

    public abstract void detruireSauvegarde(String cheminSauvegarde);

    protected String getNomModele(String cheminSauvegarde){

        return cheminSauvegarde.split(GConstantes.SEPARATEUR_DE_CHEMIN)[0];

    }


}

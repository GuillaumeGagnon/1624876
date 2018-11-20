package ca.cours5b5.guillaumegagnon.donnees;

import java.util.Map;


public abstract class SourceDeDonnees {

    public abstract Map<String, Object> chargerModele(final String cheminSauvegarde);

    public abstract void sauvegarderModele(final String cheminSauvegarde, final Map<String, Object> objetJson);

    public abstract void detruireSauvegarde(String cheminSauvegarde);




    protected String getNomModele(String cheminSauvegarde){
        return cheminSauvegarde.split("/")[0];
    }

}

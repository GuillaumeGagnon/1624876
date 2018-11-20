package ca.cours5b5.guillaumegagnon.donnees;

import android.os.Bundle;

import java.util.Map;


import ca.cours5b5.guillaumegagnon.exceptions.ErreurModele;
import ca.cours5b5.guillaumegagnon.serialisation.Jsonification;

public class SauvegardeTemporaire extends SourceDeDonnees {

    private Bundle bundle;

    public SauvegardeTemporaire(Bundle bundle){
        this.bundle = bundle;
    }

    @Override
    public void chargerModele(final String cheminSauvegarde, final ListenerChargement listenerChargement) {
        /*atelier 12, refonte de la methode*/
        try {

            if(bundle != null && bundle.containsKey(cheminSauvegarde)){

                String json = bundle.getString(cheminSauvegarde);
                Map<String, Object> objetJson = Jsonification.aPartirChaineJson(json);
                listenerChargement.reagirSucces(objetJson);
            } else {
                listenerChargement.reagirErreur(new ErreurModele("Clé introuvable"));
            }

        }catch (Exception e){
            listenerChargement.reagirErreur(e);
        }
    }

    @Override
    public void sauvegarderModele(String cheminSauvegarde, Map<String, Object> objetJson) {
        if(bundle != null){
            /*atelier 11*/
            String json = Jsonification.enChaineJson(objetJson);
            bundle.putString( getCle( cheminSauvegarde ), json);

        }
    }

    @Override
    public void detruireSauvegarde(String cheminSauvegarde) {

    }


    private String getCle(String cheminSauvegarde){
        return cheminSauvegarde.split("/")[0];
    }

}
package ca.cours5b5.guillaumegagnon.donnees;

import android.os.Bundle;

import java.util.Map;


import ca.cours5b5.guillaumegagnon.serialisation.Jsonification;

public class SauvegardeTemporaire extends SourceDeDonnees {

    private Bundle bundle;

    public SauvegardeTemporaire(Bundle bundle){
        this.bundle = bundle;
    }

    @Override
    public Map<String, Object> chargerModele(String cheminSauvegarde) {
        /*atelier 11*/
        if(bundle != null && bundle.containsKey(getCle( cheminSauvegarde) )){

            String json = bundle.getString(getCle( cheminSauvegarde ));

            Map<String, Object> objetJson = Jsonification.aPartirChaineJson(json);

            return objetJson;

        }else{

            return null;

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
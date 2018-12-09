package ca.cours5b5.guillaumegagnon.donnees;

import android.os.Bundle;

import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurModele;
import ca.cours5b5.guillaumegagnon.serialisation.Jsonification;

public class SauvegardeTemporaire extends SourceDeDonnees {

    protected Bundle bundle;

    public SauvegardeTemporaire(){}

    public SauvegardeTemporaire(Bundle bundle){
        this.bundle = bundle;
    }

    @Override
    public void chargerModele(String cheminSauvegarde, ListenerChargement listenerChargement) {
        if(bundle == null){
            listenerChargement.reagirErreur(new ErreurModele("Le bundle est null"));
            return;
        }

        String cle = getCle(cheminSauvegarde);

        if(bundle.containsKey(cle)){

            String json = bundle.getString(cle);

            Map<String, Object> objetJson = Jsonification.aPartirChaineJson(json);

            listenerChargement.reagirSucces(objetJson);

        }else{

            listenerChargement.reagirErreur(new ErreurModele("La clé " + cheminSauvegarde + " n'est pas dans la sauvegarde temporaire"));
        }
    }


    @Override
    public void sauvegarderModele(String cheminSauvegarde, Map<String, Object> objetJson) {
        if(bundle == null){
            return;
        }

        String cle = getCle(cheminSauvegarde);

        String json = Jsonification.enChaineJson(objetJson);
        bundle.putString(cle, json);

    }


    private String getCle(String cheminSauvegarde){
        return getNomModele(cheminSauvegarde);
    }


    @Override
    public void detruireSauvegarde(String cheminSauvegarde) {
        if(bundle == null){
            return;
        }

        String cle = getCle(cheminSauvegarde);

        bundle.remove(cle);
    }


}

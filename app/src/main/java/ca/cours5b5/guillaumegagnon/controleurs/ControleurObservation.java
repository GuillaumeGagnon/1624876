package ca.cours5b5.guillaumegagnon.controleurs;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.Modele;

public class ControleurObservation {

    private static Map<Modele, ListenerObservateur> observations;
    //private : la vue doit obtenir le modele par l'observation
    private static MPartie partie;

    static{
        observations = new HashMap<>();
    }

    public static void observerModele(String nomModele, final ListenerObservateur listenerObservateur){
        //observations.put(ControleurObservation.partie, listenerObservateur); //pas sur
        //Modele modele = null;

        if(nomModele.equals("MPartie")) {
            partie = new MPartie(MParametresPartie.aPartirMParametres(MParametres.instance));
            observations.put(ControleurObservation.partie, listenerObservateur);
            lancerNouvelleObservation(ControleurObservation.partie);
        }else if(nomModele.equals("MParametres")) {
            observations.put(MParametres.instance, listenerObservateur);
            lancerNouvelleObservation(MParametres.instance);
        }else if(nomModele.equals("MParametresPartie")) {
            Log.d("Atelier06",nomModele);
        } else{
            Log.d("Atelier06","else");
        }

        //lancerObservation(modele);
    }

    public static void lancerObservation(Modele modele) {
        ListenerObservateur listenerObservateur = observations.get(modele);
        //Vérifier si le listener existe pour ce modele and than call it
        if(listenerObservateur != null) {
            listenerObservateur.reagirChangementAuModele(modele);
        }
    }


    public static void lancerNouvelleObservation(Modele modele){
        ListenerObservateur listenerObservateur = observations.get(modele);
        if(listenerObservateur != null){
            listenerObservateur.reagirNouveauModele(modele);
        }
    }

}

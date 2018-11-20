package ca.cours5b5.guillaumegagnon.controleurs;

import java.util.HashMap;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerGetModele;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.modeles.Modele;

public final class ControleurObservation {

    private ControleurObservation(){}

    private static Map<Modele, ListenerObservateur> observations;

    static {

        observations = new HashMap<>();

    }

    public static void observerModele(String nomModele, final ListenerObservateur listenerObservateur) {

        /*
        old/ atelelier 11 et moins

        Modele modele = ControleurModeles.getModele(nomModele);

        observations.put(modele, listenerObservateur);

        listenerObservateur.reagirNouveauModele(modele);*/


        //atelier 12
        ControleurModeles.getModele(nomModele, new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {
                observations.put(modele, listenerObservateur);
                listenerObservateur.reagirNouveauModele(modele);
            }
        });

    }

    public static void lancerObservation(Modele modele) {

        final ListenerObservateur listenerObservateur = observations.get(modele);

        if (listenerObservateur != null) {

            listenerObservateur.reagirChangementAuModele(modele);

        }
    }

    public static void detruireObservation(Modele modele) {

        observations.remove(modele);

    }

}

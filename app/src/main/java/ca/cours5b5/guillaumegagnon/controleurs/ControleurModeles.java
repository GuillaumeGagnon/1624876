package ca.cours5b5.guillaumegagnon.controleurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerGetModele;
import ca.cours5b5.guillaumegagnon.donnees.ListenerChargement;
import ca.cours5b5.guillaumegagnon.donnees.Serveur;
import ca.cours5b5.guillaumegagnon.donnees.SourceDeDonnees;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurModele;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.Modele;
import ca.cours5b5.guillaumegagnon.donnees.Disque;
import ca.cours5b5.guillaumegagnon.usagers.UsagerCourant;

public final class ControleurModeles {

    private ControleurModeles(){}

    private static Map<String, Modele> modelesEnMemoire;

    private static SourceDeDonnees[] sequenceDeChargement;

    private static List<SourceDeDonnees> listeDeSauvegardes;

    static {

        modelesEnMemoire = new HashMap<>();
        listeDeSauvegardes = new ArrayList<>();
        listeDeSauvegardes.add(Disque.getInstance());

        //atelier 11
        listeDeSauvegardes.add(Serveur.getInstance());

    }

    public static void setSequenceDeChargement(SourceDeDonnees... sequenceDeChargement){

        ControleurModeles.sequenceDeChargement = sequenceDeChargement;

    }

    public static void sauvegarderModeleDansCetteSource(String nomModele, SourceDeDonnees sourceDeDonnees) {

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null){

            Map<String, Object> objetJson = modele.enObjetJson();

            //modification atelier 11 (getCheminSauvegarde)
            sourceDeDonnees.sauvegarderModele(getCheminSauvegarde(nomModele), objetJson);

        }
    }

    static void getModele(String nomModele, ListenerGetModele listenerGetModele){

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele == null){
            final String nom_Modele = nomModele;
            creerModeleEtChargerDonnees(nom_Modele, listenerGetModele);
        }else{
            listenerGetModele.reagirAuModele(modele);
        }
    }




    //old atelier 11 et moins
    /*
    private static Modele chargerViaSequenceDeChargement(final String nomModele){

        Modele modele = creerModeleSelonNom(nomModele);

        modelesEnMemoire.put(nomModele, modele);

        for(SourceDeDonnees sourceDeDonnees : sequenceDeChargement){

            Map<String, Object> objetJson = sourceDeDonnees.chargerModele(nomModele);

            if(objetJson != null){

                modele.aPartirObjetJson(objetJson);
                break;

            }

        }

        return modele;
    }*/

    public static void sauvegarderModele(String nomModele) throws ErreurModele {

        for(SourceDeDonnees source : listeDeSauvegardes){

            sauvegarderModeleDansCetteSource(nomModele, source);

        }

    }


    private static void creerModeleSelonNom(String nomModele, final ListenerGetModele listenerGetModele) throws ErreurModele {

        if(nomModele.equals(MParametres.class.getSimpleName())){

            listenerGetModele.reagirAuModele(new MParametres());

        }else if(nomModele.equals(MPartie.class.getSimpleName())){

            getModele(MParametres.class.getSimpleName(), new ListenerGetModele() {

                @Override
                public void reagirAuModele(Modele modele) {

                    MParametres mParametres = (MParametres) modele;
                    listenerGetModele.reagirAuModele(new MPartie(mParametres.getParametresPartie().cloner()));
                }
            });

        }else{

            throw new ErreurModele("Modèle non-connu: " + nomModele);

        }
    }

    public static void detruireModele(String nomModele) {

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null){

            modelesEnMemoire.remove(nomModele);

            ControleurObservation.detruireObservation(modele);

            if(modele instanceof Fournisseur){

                ControleurAction.oublierFournisseur((Fournisseur) modele);

            }
        }
    }


    private static String getCheminSauvegarde(String nomModele){

        return nomModele + "/" + UsagerCourant.getId();
    }


    private static void creerModeleEtChargerDonnees(final String nomModele, final ListenerGetModele listenerGetModele) {
        creerModeleSelonNom(nomModele, new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {
                modelesEnMemoire.put(nomModele, modele);
                chargerDonnees(modele, nomModele, listenerGetModele);
            }
        });
    }
    private static void chargerDonnees(Modele modele, String nomModele, ListenerGetModele listenerGetModele) {

        chargementViaSequence(modele, getCheminSauvegarde(nomModele), listenerGetModele, 0);
    }
    private static void chargementViaSequence(Modele modele, String cheminDeSauvegarde, ListenerGetModele listenerGetModele,int indiceSourceCourante){

        if(indiceSourceCourante < sequenceDeChargement.length){

            chargementViaSourceCouranteOuSuivante(modele, cheminDeSauvegarde, listenerGetModele, indiceSourceCourante);
        }else{
            terminerChargement(modele, listenerGetModele);
        }
    }
    private static void chargementViaSourceCouranteOuSuivante(final Modele modele, final String cheminDeSauvegarde, final ListenerGetModele listenerGetModele, final int indiceSourceCourante) {

        sequenceDeChargement[indiceSourceCourante].chargerModele(cheminDeSauvegarde, new ListenerChargement() {

            @Override
            public void reagirSucces(Map<String, Object> objetJson) {
                terminerChargementAvecDonnees(objetJson,modele, listenerGetModele);
            }

            @Override
            public void reagirErreur(Exception e) {
                chargementViaSourceSuivante(modele,cheminDeSauvegarde,listenerGetModele,indiceSourceCourante);
            }
        });
    }
    private static void terminerChargementAvecDonnees(Map<String, Object> objetJson, Modele modele, ListenerGetModele listenerGetModele) {

        modele.aPartirObjetJson(objetJson);
        terminerChargement(modele, listenerGetModele);
    }
    private static void terminerChargement(Modele modele,ListenerGetModele listenerGetModele) {

        listenerGetModele.reagirAuModele(modele);
    }
    private static void chargementViaSourceSuivante(Modele modele, String cheminDeSauvegarde, ListenerGetModele listenerGetModele, int indiceSourceCourante) {

        chargementViaSequence(modele, cheminDeSauvegarde, listenerGetModele, indiceSourceCourante + 1);
    }



}

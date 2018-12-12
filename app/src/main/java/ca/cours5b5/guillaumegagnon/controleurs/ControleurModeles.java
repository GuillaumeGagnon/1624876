package ca.cours5b5.guillaumegagnon.controleurs;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerGetModele;
import ca.cours5b5.guillaumegagnon.donnees.ListenerChargement;
import ca.cours5b5.guillaumegagnon.donnees.Serveur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.donnees.SourceDeDonnees;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurModele;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.modeles.Identifiable;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartieAI;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;
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
        listeDeSauvegardes.add(Serveur.getInstance());

    }


    public static void setSequenceDeChargement(SourceDeDonnees... sequenceDeChargement){

        ControleurModeles.sequenceDeChargement = sequenceDeChargement;

    }


    public static void sauvegarderModeleDansCetteSource(String nomModele, SourceDeDonnees sourceDeDonnees) {

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null){

            String cheminSauvegarde = getCheminSauvegarde(nomModele);

            Map<String, Object> objetJson = modele.enObjetJson();

            sourceDeDonnees.sauvegarderModele(cheminSauvegarde, objetJson);

        }
    }


    public static void prechargerModele(String nomModele) {

        getModele(nomModele, new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {
                // Rien
            }
        });
    }


    static void getModele(String nomModele, ListenerGetModele listenerGetModele){

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null){

            listenerGetModele.reagirAuModele(modele);

        }else {

            creerModeleEtChargerDonnees(nomModele, listenerGetModele);
        }
    }


    private static void creerModeleEtChargerDonnees(final String nomModele,
                                                    final ListenerGetModele listenerGetModele) {

        creerModeleSelonNom(nomModele, new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                modelesEnMemoire.put(nomModele, modele);

                chargerDonnees(modele, nomModele, listenerGetModele);
            }
        });

    }

    private static void chargerDonnees(Modele modele,
                                       String nomModele,
                                       ListenerGetModele listenerGetModele) {

        String cheminSauvegarde = getCheminSauvegarde(nomModele);

        int indicePremiereSource = 0;

        chargementViaSequence(
                modele,
                cheminSauvegarde,
                listenerGetModele,
                indicePremiereSource);
    }


    private static void chargementViaSequence(Modele modele,
                                              String cheminDeSauvegarde,
                                              ListenerGetModele listenerGetModele,
                                              int indiceSourceCourante){

        if(indiceSourceCourante < sequenceDeChargement.length){

            chargementViaSourceCouranteOuSuivante(modele,
                    cheminDeSauvegarde,
                    listenerGetModele,
                    indiceSourceCourante);

        }else{

            terminerChargement(modele, listenerGetModele);

        }
    }


    private static void chargementViaSourceCouranteOuSuivante(final Modele modele,
                                                              final String cheminDeSauvegarde,
                                                              final ListenerGetModele listenerGetModele,
                                                              final int indiceSourceCourante) {

        SourceDeDonnees sourceCourante = sequenceDeChargement[indiceSourceCourante];

        sourceCourante.chargerModele(cheminDeSauvegarde,
                new ListenerChargement() {

                    @Override
                    public void reagirSucces(final Map<String, Object> objetJson) {

                        terminerChargementAvecDonnees(objetJson, modele, listenerGetModele);
                    }

                    @Override
                    public void reagirErreur(Exception e) {

                        chargementViaSourceSuivante(modele,
                                cheminDeSauvegarde,
                                listenerGetModele,
                                indiceSourceCourante);
                    }

                });
    }


    private static void terminerChargementAvecDonnees(Map<String, Object> objetJson,
                                                      Modele modele,
                                                      ListenerGetModele listenerGetModele) {

        modele.aPartirObjetJson(objetJson);

        terminerChargement(modele, listenerGetModele);

    }

    private static void terminerChargement(Modele modele,
                                           ListenerGetModele listenerGetModele) {

        listenerGetModele.reagirAuModele(modele);

    }


    private static void chargementViaSourceSuivante(Modele modele,
                                                    String cheminDeSauvegarde,
                                                    ListenerGetModele listenerGetModele,
                                                    int indiceSourceCourante) {

        int indiceSourceSuivante = indiceSourceCourante + 1;

        chargementViaSequence(modele,
                cheminDeSauvegarde,
                listenerGetModele,
                indiceSourceSuivante);

    }


    public static void sauvegarderModele(String nomModele) throws ErreurModele {
        for(SourceDeDonnees source : listeDeSauvegardes){

            sauvegarderModeleDansCetteSource(nomModele, source);

        }
    }


    private static void creerModeleSelonNom(String nomModele,
                                            final ListenerGetModele listenerGetModele) throws ErreurModele {

        if(nomModele.equals(MParametres.class.getSimpleName())){

            listenerGetModele.reagirAuModele(new MParametres());

        }else if(nomModele.equals(MPartie.class.getSimpleName())){

            creerPartie(listenerGetModele);

        }
        //AI
        else if(nomModele.equals(MPartieAI.class.getSimpleName())){

            creerPartieAI(listenerGetModele);

        }else if(nomModele.equals(MPartieReseau.class.getSimpleName())){

            creerPartieReseau(listenerGetModele);

        }else{

            throw new ErreurModele("nomModèle inconnu: " + nomModele);

        }
    }


    private static void creerPartie(final ListenerGetModele listenerGetModele) {
        getModele(MParametres.class.getSimpleName(), new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                MParametres mParametres = (MParametres) modele;

                listenerGetModele.reagirAuModele(new MPartie(mParametres.getParametresPartie().cloner()));

            }
        });
    }

    //PROJET AI
    private static void creerPartieAI(final ListenerGetModele listenerGetModele) {
        getModele(MParametres.class.getSimpleName(), new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                MParametres mParametres = (MParametres) modele;

                listenerGetModele.reagirAuModele(new MPartieAI(mParametres.getParametresPartie().cloner()));

            }
        });
    }


    private static void creerPartieReseau(final ListenerGetModele listenerGetModele) {
        getModele(MParametres.class.getSimpleName(), new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                MParametres mParametres = (MParametres) modele;

                listenerGetModele.reagirAuModele(new MPartieReseau(mParametres.getParametresPartie().cloner()));

            }
        });
    }


    public static void detruireModele(String nomModele) {

        detruireSauvegardes(nomModele);

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null){

            modelesEnMemoire.remove(nomModele);

            ControleurObservation.detruireObservation(modele);

            if(modele instanceof Fournisseur){

                ControleurAction.oublierFournisseur((Fournisseur) modele);

            }
        }
    }


    private static void detruireSauvegardes(String nomModele) {

        String cheminSauvegarde = getCheminSauvegarde(nomModele);

        for(SourceDeDonnees sourceDeDonnees : listeDeSauvegardes){

            sourceDeDonnees.detruireSauvegarde(cheminSauvegarde);

        }
    }


    static String getCheminSauvegarde(String nomModele){

        String cheminParDefaut = nomModele + GConstantes.SEPARATEUR_DE_CHEMIN + UsagerCourant.getId();

        String chemin = cheminParDefaut;

        Modele modele = modelesEnMemoire.get(nomModele);

        if(modele != null && modele instanceof Identifiable){

            chemin = getCheminSpecifique(nomModele, (Identifiable) modele);

        }

        return chemin;
    }


    @NonNull
    private static String getCheminSpecifique(String nomModele, Identifiable modele) {

        String cheminSpecifique = nomModele + GConstantes.SEPARATEUR_DE_CHEMIN + modele.getId();

        return cheminSpecifique;

    }


}

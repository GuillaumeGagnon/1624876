package ca.cours5b5.guillaumegagnon.usagers;

import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.donnees.ListenerChargement;
import ca.cours5b5.guillaumegagnon.donnees.Serveur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;
import ca.cours5b5.guillaumegagnon.proxy.ProxyValeur;


public final class JoueurInvite implements Fournisseur {

    private JoueurInvite(){}

    private static final JoueurInvite instance = new JoueurInvite();

    public static JoueurInvite getInstance(){return instance;}

    private String idJoueurInvite;
    private String idJoueurHote;

    private ProxyValeur proxyInviterJoueur;

    void setIdJoueurs(String idJoueurHote, String idJoueurInvite) {

        this.idJoueurHote = idJoueurHote;
        this.idJoueurInvite = idJoueurInvite;

        initialiser();

    }


    private void initialiser() {

        String cheminInviterJoueur = getCheminInviterJoueur();

        proxyInviterJoueur = new ProxyValeur(cheminInviterJoueur);

        proxyInviterJoueur.setActionNouvelleValeur(GCommande.RECEVOIR_JOUEUR_INVITE);

        fournirActionRecevoirInvite();

    }


    void connecterAuServeur() {

        proxyInviterJoueur.connecterAuServeur();

    }


    private void deconnecterDuServeur() {

        proxyInviterJoueur.deconnecterDuServeur();

    }


    private String getCheminPartie(){

        String chemin = MPartieReseau.class.getSimpleName();

        chemin += GConstantes.SEPARATEUR_DE_CHEMIN;

        chemin += idJoueurHote;

        return chemin;

    }


    private String getCheminInviterJoueur(){

        String chemin = getCheminPartie();

        chemin += GConstantes.SEPARATEUR_DE_CHEMIN;

        chemin += GConstantes.CLE_ID_JOUEUR_INVITE;

        return chemin;

    }


    private void demarrerPartie(){

        deconnecterDuServeur();

        String cheminPartie = getCheminPartie();

        Serveur.getInstance().chargerModele(cheminPartie, new ListenerChargement() {
            @Override
            public void reagirSucces(Map<String, Object> objetJson) {

                Action actionDemarrerPartieReseau = ControleurAction.demanderAction(GCommande.DEMARRER_PARTIE_RESEAU);
                actionDemarrerPartieReseau.setArguments(objetJson);
                actionDemarrerPartieReseau.executerDesQuePossible();

            }

            @Override
            public void reagirErreur(Exception e) {

            }
        });

    }

    private void fournirActionRecevoirInvite() {

        ControleurAction.fournirAction(this,
                GCommande.RECEVOIR_JOUEUR_INVITE,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        String idJoueurInvite = (String) args[0];

                        if(UsagerCourant.estCeUsagerCourant(idJoueurInvite)){

                            proxyInviterJoueur.detruireValeurs();

                            demarrerPartie();

                        }
                    }
                });

    }


}

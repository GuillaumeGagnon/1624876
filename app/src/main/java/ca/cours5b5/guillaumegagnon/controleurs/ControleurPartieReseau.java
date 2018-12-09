package ca.cours5b5.guillaumegagnon.controleurs;


import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerGetModele;
import ca.cours5b5.guillaumegagnon.donnees.Serveur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;
import ca.cours5b5.guillaumegagnon.modeles.Modele;
import ca.cours5b5.guillaumegagnon.proxy.ProxyListe;
import ca.cours5b5.guillaumegagnon.usagers.UsagerCourant;

public final class ControleurPartieReseau {

    private ControleurPartieReseau() {
    }

    private static final ControleurPartieReseau instance = new ControleurPartieReseau();

    public static ControleurPartieReseau getInstance() {
        return instance;
    }

    private ProxyListe proxyEmettreCoups;
    private ProxyListe proxyRecevoirCoups;

    public void creerEtDemarrerPartie(final String idJoueurHote, final String idJoueurInvite) {

        final String nomModele = MPartieReseau.class.getSimpleName();

        ControleurModeles.getModele(nomModele, new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                MPartieReseau partie = (MPartieReseau) modele;

                partie.setIdJoueurs(idJoueurHote, idJoueurInvite);

                ControleurModeles.sauvegarderModele(nomModele);

                demarrerPartie(partie);

            }
        });
    }


    private void demarrerPartie(MPartieReseau partie) {

        Action actionDemarrerPartie = ControleurAction.demanderAction(GCommande.DEMARRER_PARTIE_RESEAU);

        Map<String, Object> objetJsonPartie = partie.enObjetJson();

        actionDemarrerPartie.setArguments(objetJsonPartie);

        actionDemarrerPartie.executerDesQuePossible();

    }


    public void connecterAuServeur() {

        ControleurModeles.getModele(MPartieReseau.class.getSimpleName(), new ListenerGetModele() {
            @Override
            public void reagirAuModele(Modele modele) {

                MPartieReseau partie = (MPartieReseau) modele;

                connecterAuServeur(partie.getId());

            }
        });
    }


    private void connecterAuServeur(String idJoueurHote) {

        String cheminCoupsJoueurHote = getCheminCoupsJoueurHote(idJoueurHote);
        String cheminCoupsJoueurInvite = getCheminCoupsJoueurInvite(idJoueurHote);

        if (UsagerCourant.estCeUsagerCourant(idJoueurHote)) {

            connecterEnTantQueJoueurHote(cheminCoupsJoueurHote, cheminCoupsJoueurInvite);

        } else {

            connecterEnTantQueJoueurInvite(cheminCoupsJoueurHote, cheminCoupsJoueurInvite);

        }

        demarrerProxys();

    }


    private void demarrerProxys() {

        proxyRecevoirCoups.connecterAuServeur();
        proxyEmettreCoups.connecterAuServeur();

        proxyRecevoirCoups.setActionNouvelItem(GCommande.RECEVOIR_COUP_RESEAU);

    }


    private void connecterEnTantQueJoueurHote(String cheminCoupsJoueurHote, String cheminCoupsJoueurInvite) {

        proxyEmettreCoups = new ProxyListe(cheminCoupsJoueurHote);
        proxyRecevoirCoups = new ProxyListe(cheminCoupsJoueurInvite);

    }


    private void connecterEnTantQueJoueurInvite(String cheminCoupsJoueurHote, String cheminCoupsJoueurInvite) {

        proxyEmettreCoups = new ProxyListe(cheminCoupsJoueurInvite);
        proxyRecevoirCoups = new ProxyListe(cheminCoupsJoueurHote);

    }


    public void deconnecterDuServeur() {

        proxyEmettreCoups.detruireValeurs();

        proxyRecevoirCoups.deconnecterDuServeur();
        proxyEmettreCoups.deconnecterDuServeur();

    }


    public void transmettreCoup(Integer idColonne) {

        proxyEmettreCoups.ajouterValeur(idColonne.toString());

    }


    private String getCheminCoupsJoueurInvite(String idJoueurHote) {

        String chemin = getCheminPartie(idJoueurHote);

        chemin += GConstantes.SEPARATEUR_DE_CHEMIN;

        chemin += GConstantes.CLE_COUPS_JOUEUR_INVITE;

        return chemin;

    }


    private String getCheminCoupsJoueurHote(String idJoueurHote) {

        String chemin = getCheminPartie(idJoueurHote);

        chemin += GConstantes.SEPARATEUR_DE_CHEMIN;

        chemin += GConstantes.CLE_COUPS_JOUEUR_HOTE;

        return chemin;

    }


    private String getCheminPartie(String idJoueurHote) {

        String chemin = MPartieReseau.class.getSimpleName();

        chemin += GConstantes.SEPARATEUR_DE_CHEMIN;

        chemin += idJoueurHote;

        return chemin;

    }


    public void detruireSauvegardeServeur() {

        String nomModele = MPartieReseau.class.getSimpleName();

        String cheminSauvegarde = ControleurModeles.getCheminSauvegarde(nomModele);

        Serveur.getInstance().detruireSauvegarde(cheminSauvegarde);

    }

}

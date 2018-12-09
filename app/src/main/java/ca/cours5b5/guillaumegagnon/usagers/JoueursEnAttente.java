package ca.cours5b5.guillaumegagnon.usagers;


import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurPartieReseau;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.proxy.ProxyListe;


public final class JoueursEnAttente implements Fournisseur {
    
    private static final JoueursEnAttente instance = new JoueursEnAttente();
    public static JoueursEnAttente getInstance(){return instance;}

    private ProxyListe proxyJoueursEnAttente;
    private String __joueursEnAttente = "JoueursEnAttentes";

    private JoueursEnAttente(){

        proxyJoueursEnAttente = new ProxyListe(__joueursEnAttente);

        proxyJoueursEnAttente.setActionNouvelItem(GCommande.RECEVOIR_JOUEUR_EN_ATTENTE);

        fournirActions();

    }


    private void fournirActions() {

        fournirActionAjouterJoueurEnAttente();

    }


    private void fournirActionAjouterJoueurEnAttente() {

        ControleurAction.fournirAction(this,
                GCommande.RECEVOIR_JOUEUR_EN_ATTENTE,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        String idJoueurEnAttente = (String) args[0];

                        if(!UsagerCourant.estCeUsagerCourant(idJoueurEnAttente)){

                            deconnecterDuServeur();

                            basculerEnModeInvitation(idJoueurEnAttente);

                        }
                    }
                });
    }


    private void basculerEnModeInvitation(String idJoueurEnAttente) {

        if(UsagerCourant.getId().compareTo(idJoueurEnAttente) > 0 ){

            String idJoueurHote = UsagerCourant.getId();

            basculerEnModeHote(idJoueurHote, idJoueurEnAttente);


        }else{

            String idJoueurInvite = UsagerCourant.getId();

            basculerEnModeInvite(idJoueurEnAttente, idJoueurInvite);

        }
    }


    private void basculerEnModeHote(String idJoueurHote, String idJoueurInvite) {

        ControleurPartieReseau.getInstance().creerEtDemarrerPartie(idJoueurHote, idJoueurInvite);

    }

    private void basculerEnModeInvite(String idJoueurHote, String idJoueurInvite) {

        JoueurInvite.getInstance().setIdJoueurs(idJoueurHote, idJoueurInvite);
        JoueurInvite.getInstance().connecterAuServeur();

    }


    public void connecterAuServeur(){

        proxyJoueursEnAttente.connecterAuServeur();

    }

    public void deconnecterDuServeur() {

        proxyJoueursEnAttente.detruireValeurs();
        proxyJoueursEnAttente.deconnecterDuServeur();

    }

    public void inscrireJoueurEnAttente() {

        proxyJoueursEnAttente.ajouterValeur(UsagerCourant.getId());

    }
}

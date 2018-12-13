package ca.cours5b5.guillaumegagnon.modeles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurPartie;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurAction;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartie extends Modele implements Fournisseur {


    @AttributSerialisable
    public MParametresPartie parametres;
    private final String __parametres = "parametres";

    @AttributSerialisable
    public List<Integer> listeCoups;
    private final String __listeCoups = "listeCoups";

    //test pour acces ia private --> protected
    protected MGrille grille;
    protected GCouleur couleurCourante;

    public MPartie(MParametresPartie parametres) {

        this.parametres = parametres;

        initialiser();

        initialiserCouleurCourante();

        initialiserGrille();

        fournirActionPlacerJeton();

    }

    protected void initialiser() {
        listeCoups = new ArrayList<>();
    }

    //private --> protected
    protected void initialiserCouleurCourante() {
        couleurCourante = GCouleur.ROUGE;
    }

    //private --> protected
    protected void initialiserGrille() {
        grille = new MGrille(parametres.getLargeur());
    }


    protected void fournirActionPlacerJeton() {

        ControleurAction.fournirAction(this,
                GCommande.PLACER_JETON_ICI,
                new ListenerFournisseur() {

                    @Override
                    public void executer(Object... args) {
                        try {

                            int colonne = (Integer) args[0];

                            jouerCoup(colonne);


                        } catch (ClassCastException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }


    protected void jouerCoup(int colonne) {

        if (siCoupLegal(colonne)) {
            jouerCoupLegal(colonne);
        }
    }


    protected void jouerCoupLegal(int colonne) {

        listeCoups.add(colonne);
        grille.placerJeton(colonne, couleurCourante);
        
        if (grille.siCouleurGagne(couleurCourante, parametres.getPourGagner())) {

            ControleurPartie.getInstance().gagnerPartie(couleurCourante);

        } else {

            prochaineCouleurCourante();

        }
    }

    protected boolean siCoupLegal(int colonne) {

        MColonne mColonne = grille.getColonnes().get(colonne);

        return mColonne.getJetons().size() < parametres.getHauteur();

    }

    //private --> protected
    protected void prochaineCouleurCourante() {

        switch (couleurCourante) {

            case ROUGE:
                couleurCourante = GCouleur.JAUNE;
                break;

            case JAUNE:
                couleurCourante = GCouleur.ROUGE;
        }
    }


    public MGrille getGrille() {
        return grille;
    }

    public MParametresPartie getParametres() {
        return parametres;
    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {

        parametres.aPartirObjetJson((Map<String, Object>) objetJson.get(__parametres));

        initialiserCouleurCourante();

        initialiserGrille();

        List<String> listeCoupsObjetJson = (List<String>) objetJson.get(__listeCoups);

        if (listeCoupsObjetJson != null) {

            List<Integer> coupsARejouer = listeCoupsAPartirJson(listeCoupsObjetJson);
            rejouerLesCoups(coupsARejouer);

        }
    }

    //private --> protected
    protected List<Integer> listeCoupsAPartirJson(List<String> listeCoupsObjetJson) {
        List<Integer> listeCoups = new ArrayList<>();

        for (String coupChaine : listeCoupsObjetJson) {

            listeCoups.add(Integer.valueOf(coupChaine));

        }

        return listeCoups;
    }

    //private --> protected
    protected void rejouerLesCoups(List<Integer> coupsARejouer) {

        listeCoups.clear();

        for(Integer coup : coupsARejouer){

            jouerCoup(coup);

        }
    }


    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(__parametres, parametres.enObjetJson());
        objetJson.put(__listeCoups, listeCoupsEnObjetJson(listeCoups));

        return objetJson;

    }

    //private --> protected
    protected List<String> listeCoupsEnObjetJson(List<Integer> listeCoups) {
        List<String> listeCoupsObjetJson = new ArrayList<>();

        for (Integer coup : listeCoups) {

            listeCoupsObjetJson.add(coup.toString());

        }

        return listeCoupsObjetJson;
    }

    public GCouleur getCouleurCourante() {
        return couleurCourante;
    }


}


package ca.cours5b5.guillaumegagnon.modeles;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurPartieAI;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurAction;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartieAI extends Modele implements Fournisseur{


    private GCouleur couleurCourante;
    private MGrille grille;

    @AttributSerialisable
    public MParametresPartie parametresAI;
    private final String __parametresAI = "parametresAI";

    @AttributSerialisable
    public List<Integer> listeCoupsAI;
    private final String __listeCoupsAI = "listeCoupsAI";

    public MPartieAI(MParametresPartie parametres) {

        Log.d("debug_AI", "MPartieAI");
        this.parametresAI = parametres;

        initialiser();

        initialiserCouleurCourante();

        initialiserGrille();

        fournirActionPlacerJeton();

    }

    private void initialiser() {
        listeCoupsAI = new ArrayList<>();
    }

    private void initialiserCouleurCourante() {
        couleurCourante = GCouleur.ROUGE;
    }


    private void initialiserGrille() {
        grille = new MGrille(parametresAI.getLargeur());
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

        listeCoupsAI.add(colonne);
        grille.placerJeton(colonne, couleurCourante);

        if (grille.siCouleurGagne(couleurCourante, parametresAI.getPourGagner())) {

            ControleurPartieAI.getInstance().gagnerPartie(couleurCourante);

        } else {

            prochaineCouleurCourante();

        }
    }

    protected boolean siCoupLegal(int colonne) {

        MColonne mColonne = grille.getColonnes().get(colonne);

        return mColonne.getJetons().size() < parametresAI.getHauteur();

    }


    private void prochaineCouleurCourante() {

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
        return parametresAI;
    }

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {

        parametresAI.aPartirObjetJson((Map<String, Object>) objetJson.get(__parametresAI));

        initialiserCouleurCourante();

        initialiserGrille();

        List<String> listeCoupsObjetJson = (List<String>) objetJson.get(__listeCoupsAI);

        if (listeCoupsObjetJson != null) {

            List<Integer> coupsARejouer = listeCoupsAPartirJson(listeCoupsObjetJson);
            rejouerLesCoups(coupsARejouer);

        }
    }


    private List<Integer> listeCoupsAPartirJson(List<String> listeCoupsObjetJson) {
        List<Integer> listeCoups = new ArrayList<>();

        for (String coupChaine : listeCoupsObjetJson) {

            listeCoups.add(Integer.valueOf(coupChaine));

        }

        return listeCoups;
    }


    private void rejouerLesCoups(List<Integer> coupsARejouer) {

        listeCoupsAI.clear();

        for(Integer coup : coupsARejouer){

            jouerCoup(coup);

        }
    }

    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(__parametresAI, parametresAI.enObjetJson());
        objetJson.put(__listeCoupsAI, listeCoupsEnObjetJson(listeCoupsAI));

        return objetJson;

    }


    private List<String> listeCoupsEnObjetJson(List<Integer> listeCoups) {
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


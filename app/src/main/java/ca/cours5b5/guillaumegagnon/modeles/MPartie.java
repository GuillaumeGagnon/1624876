package ca.cours5b5.guillaumegagnon.modeles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
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

    private MGrille grille;
    private GCouleur couleurCourante;

    public MPartie(MParametresPartie parametres){

        this.parametres = parametres;

        initialiser();

        initialiserCouleurCourante();

        initialiserGrille();

        fournirActionPlacerJeton();

    }

    private void initialiser() {
        listeCoups = new ArrayList<>();
    }

    private void initialiserCouleurCourante() {
        couleurCourante = GCouleur.ROUGE;
    }


    private void initialiserGrille() {
        grille = new MGrille(parametres.getLargeur());
    }

    //atelier 13, private --> protected
    protected void fournirActionPlacerJeton() {

        ControleurAction.fournirAction(this,
                GCommande.JOUER_COUP_ICI,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {
                        try{

                            int colonne = (Integer) args[0];

                            jouerCoup(colonne);


                        }catch(ClassCastException e){

                            throw new ErreurAction(e);

                        }
                    }
                });
    }

    protected void jouerCoup(int colonne) {

        if(siCoupLegal(colonne)){

            listeCoups.add(colonne);

            grille.placerJeton(colonne, couleurCourante);

            prochaineCouleurCourante();

        }
    }

    private boolean siCoupLegal(int colonne){

        MColonne mColonne = grille.getColonnes().get(colonne);

        return mColonne.nombreDeJetons() < parametres.getHauteur();

    }

    private void prochaineCouleurCourante(){

        switch(couleurCourante){

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
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation  {

        parametres.aPartirObjetJson((Map<String, Object>)objetJson.get(__parametres));

        initialiserCouleurCourante();

        initialiserGrille();

        List<String> listeCoupsObjetJson = (List<String>) objetJson.get(__listeCoups);

        if(listeCoupsObjetJson != null){

            List<Integer> coupsARejouer = listeCoupsAPartirJson(listeCoupsObjetJson);
            rejouerLesCoups(coupsARejouer);

        }
    }


    private List<Integer> listeCoupsAPartirJson(List<String> listeCoupsObjetJson) {

        List<Integer> listeCoups = new ArrayList<>();

        for(String coupChaine : listeCoupsObjetJson){

            listeCoups.add(Integer.valueOf(coupChaine));

        }

        return listeCoups;
    }


    private void rejouerLesCoups(List<Integer> coupsARejouer) {

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

    private  List<String> listeCoupsEnObjetJson(List<Integer> listeCoups) {

        List<String> listeCoupsObjetJson = new ArrayList<>();

        for(Integer coup : listeCoups){

            listeCoupsObjetJson.add(coup.toString());

        }

        return listeCoupsObjetJson;

    }

}
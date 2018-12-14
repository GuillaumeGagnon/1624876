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
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MParametres extends Modele implements Fournisseur {

    @AttributSerialisable
    public MParametresPartie parametresPartie;
    private String __parametresPartie = "parametresPartie";

    private List<Integer> choixHauteur;
    private List<Integer> choixLargeur;
    private List<Integer> choixPourGagner;
    private List<Integer> choixForceAI;

    public MParametres() {
        super();

        this.parametresPartie = new MParametresPartie();

        fournirActions();

        genererListesDeChoix();

    }

    public List<Integer> getChoixHauteur() {
        return choixHauteur;
    }

    public List<Integer> getChoixLargeur() {
        return choixLargeur;
    }

    public List<Integer> getChoixPourGagner() {
        return choixPourGagner;
    }

    public MParametresPartie getParametresPartie() {
        return parametresPartie;
    }

    //AI
    public List<Integer> getChoixForceAI() {
        return choixForceAI;
    }

    private void fournirActions() {

        fournirActionHauteur();
        fournirActionLargeur();
        fournirActionPourGagner();

        //AI
        fournirActionAI();

    }

    //***************************************************
    // AI
    private void fournirActionAI() {

        ControleurAction.fournirAction(this,
                GCommande.CHOISIR_FORCE_AI,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        try {

                            getParametresPartie().setForceAI((Integer) args[0]);
                            genererListeChoixPourGagner();

                        } catch (ClassCastException
                                | IndexOutOfBoundsException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }






    private void fournirActionHauteur() {

        ControleurAction.fournirAction(this,
                GCommande.CHOISIR_HAUTEUR,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        try {

                            getParametresPartie().setHauteur((Integer) args[0]);
                            genererListeChoixPourGagner();

                        } catch (ClassCastException
                                | IndexOutOfBoundsException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }


    private void fournirActionLargeur() {

        ControleurAction.fournirAction(this,
                GCommande.CHOISIR_LARGEUR,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        try {

                            getParametresPartie().setLargeur((int) args[0]);
                            genererListeChoixPourGagner();

                        } catch (ClassCastException
                                | IndexOutOfBoundsException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }


    private void fournirActionPourGagner() {

        ControleurAction.fournirAction(this,
                GCommande.CHOISIR_POUR_GAGNER,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        try {

                            getParametresPartie().setPourGagner((Integer) args[0]);

                        } catch (ClassCastException
                                | IndexOutOfBoundsException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }


    private void genererListesDeChoix() {

        genererListeChoixHauteur();
        genererListeChoixLargeur();
        genererListeChoixPourGagner();

        //AI
        genererListeChoixForeceAI();

    }

    private List<Integer> genererListeChoix(int min, int max) {
        List<Integer> listeChoix = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            listeChoix.add(i);
        }

        return listeChoix;
    }


    private void genererListeChoixHauteur() {

        choixHauteur = genererListeChoix(GConstantes.HAUTEUR_MIN, GConstantes.HAUTEUR_MAX);

    }


    private void genererListeChoixLargeur() {

        choixLargeur = genererListeChoix(GConstantes.LARGEUR_MIN, GConstantes.LARGEUR_MAX);

    }


    //AI
    private void genererListeChoixForeceAI() {

        List<Integer> listeChoix = new ArrayList<>();

        listeChoix.add(0);
        for (int i = GConstantes.FORCEAI_MIN; i <= GConstantes.FORCEAI_MAX; i += 1000) {
            listeChoix.add(i);
        }

        choixForceAI = listeChoix;

    }


    private void genererListeChoixPourGagner() {

        int pourGagnerMax = calculerPourGagnerMax();

        ajusterPourGagnerAuBesoin(pourGagnerMax);

        choixPourGagner = genererListeChoix(GConstantes.POUR_GAGNER_MIN, pourGagnerMax);

    }


    private int calculerPourGagnerMax() {

        return Math.max(parametresPartie.getHauteur(), parametresPartie.getLargeur()) * 75 / 100;

    }


    private void ajusterPourGagnerAuBesoin(int pourGagnerMax) {

        if (parametresPartie.getPourGagner() >= pourGagnerMax) {
            parametresPartie.setPourGagner(pourGagnerMax);
        }

    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        parametresPartie.aPartirObjetJson((Map<String, Object>) objetJson.get(__parametresPartie));
    }


    @Override
    public Map<String, Object> enObjetJson() {

        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(__parametresPartie, parametresPartie.enObjetJson());

        return objetJson;

    }


}

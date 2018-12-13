package ca.cours5b5.guillaumegagnon.activites;

import android.os.Bundle;
import android.util.Log;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurModeles;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurPartieAI;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.donnees.SauvegardeTemporaire;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartieAI;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;


public class APartieAI extends Activite implements Fournisseur {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie_ai);

        Log.d("debug_AI", "APartieAI/onCreate");

        fournirActionTerminerPartieAI();

    }


    private void fournirActionTerminerPartieAI() {

        ControleurAction.fournirAction(this,
                GCommande.TERMINER_PARTIE,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        quitterCetteActivite();

                    }
                });
    }


    @Override
    protected void onPause() {
        super.onPause();
        sauvegarderPartie();
        Log.d("debug_AI", "APartieAI/onPause");


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String nomModele = MPartieAI.class.getSimpleName();

        ControleurModeles.sauvegarderModeleDansCetteSource(nomModele,
                new SauvegardeTemporaire(outState));

    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        String nomModele = MPartieAI.class.getSimpleName();
        ControleurModeles.detruireModele(nomModele);
    }*/

    protected void sauvegarderPartie(){
        ControleurModeles.sauvegarderModele(MPartieAI.class.getSimpleName());
    }





}

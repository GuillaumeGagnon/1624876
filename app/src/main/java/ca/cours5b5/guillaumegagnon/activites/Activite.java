package ca.cours5b5.guillaumegagnon.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurModeles;
import ca.cours5b5.guillaumegagnon.donnees.Disque;
import ca.cours5b5.guillaumegagnon.donnees.SauvegardeTemporaire;
import ca.cours5b5.guillaumegagnon.donnees.Serveur;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;
import ca.cours5b5.guillaumegagnon.donnees.Transition;


public abstract class Activite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiserControleurModeles(savedInstanceState);
        initialiserApplication();

    }

    protected void initialiserControleurModeles(Bundle savedInstanceState) {

        ControleurModeles.setSequenceDeChargement( new Transition(getIntent().getExtras()),new SauvegardeTemporaire(savedInstanceState), Serveur.getInstance(), Disque.getInstance());
        
    }

    protected void initialiserApplication(){

        Disque.getInstance().setRepertoireRacine(getFilesDir());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ControleurModeles.sauvegarderModeleDansCetteSource(MParametres.class.getSimpleName(),
                new SauvegardeTemporaire(outState));
    }

}

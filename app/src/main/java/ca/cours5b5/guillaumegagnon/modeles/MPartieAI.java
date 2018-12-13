package ca.cours5b5.guillaumegagnon.modeles;

import android.util.Log;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurAction;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartieAI extends MPartie implements Fournisseur{

    @AttributSerialisable
    public String partieAI;
    private String __partieAI = "partieAI";


    public MPartieAI(MParametresPartie parametres) {
        super(parametres);
        Log.d("debug_AI", "MPartieAI");

    }

    @Override
    protected void fournirActionPlacerJeton() {
        ControleurAction.fournirAction(this,
                GCommande.PLACER_JETON_ICI,
                new ListenerFournisseur() {

                    @Override
                    public void executer(Object... args) {
                        try {

                            int colonne = (Integer) args[0];

                            jouerCoup(colonne);
                            Log.d("debug_ai", "executer: joueur " + colonne);
                            // on fait jouer l'IA apres chaque coup du joueur
                            //ce n'est pas 100% optimal car l'ia tente de jouer meme si le joueur gagne mais ca marche
                            jouerCoup(ai());


                        } catch (ClassCastException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }

    // json

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        super.aPartirObjetJson(objetJson);
        partieAI = (String) objetJson.get(__partieAI);

    }
    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = super.enObjetJson();
        objetJson.put(__partieAI, partieAI);

        return objetJson;

    }

    //IA
    private int ai(){
        int coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());
        Log.d("debug_ai", "avant while (ia): " + coup_ai);
        while(!siCoupLegal(coup_ai)) {
            coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());
            Log.d("debug_ai", "dans while: (ia)" + coup_ai);
        }
        Log.d("debug_ai", "epres while (ia): " + coup_ai);
        return coup_ai;
    }

    private int ai(int mode){
        int coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());

        while(!siCoupLegal(coup_ai)) {
            coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());
        }
        return coup_ai;
    }



}


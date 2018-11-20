package ca.cours5b5.guillaumegagnon.modeles;

import android.util.Log;

import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurPartieReseau;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurAction;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartieReseau extends MPartie implements Fournisseur, Identifiable {

    @AttributSerialisable
    public String idJoueurInvite;
    private String __idJoueurInvite = "idJoueurInvite";

    @AttributSerialisable
    public String idJoueurHote;
    private String __idJoueurHote = "idJoueurHote";


    @Override
    public String getId() {
        return idJoueurHote;
    }

    public MPartieReseau(MParametresPartie parametres) {
        super(parametres);
        fournirActionRecevoirCoup();
    }


    private void recevoirCoupReseau(int colonne){ jouerCoup(colonne); }


    private void fournirActionRecevoirCoup() {

        ControleurAction.fournirAction(this, GCommande.RECEVOIR_COUP_RESEAU, new ListenerFournisseur() {

            @Override
            public void executer(Object... args) {
                try{
                    int coup = ((Long) args[0]).intValue();
                    recevoirCoupReseau(coup);
                    Log.d("help","pk c jamais caller");
                } catch(Exception ex) {
                    throw  new ErreurAction(ex);
                }
            }
        });
    }



    @Override
    protected void fournirActionPlacerJeton() {
        ControleurAction.fournirAction(this, GCommande.JOUER_COUP_ICI, new ListenerFournisseur() {

            @Override
            public void executer(Object... args) {
                try{
                    int col = (Integer) args[0];
                    jouerCoup(col);
                    ControleurPartieReseau.getInstance().transmettreCoup(col);

                    }catch(ClassCastException e){
                        throw new ErreurAction(e);
                }
            }
        });
    }


    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = super.enObjetJson();
        objetJson.put(__idJoueurHote, idJoueurHote);
        objetJson.put(__idJoueurInvite, idJoueurInvite);

        return objetJson;
    }

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        super.aPartirObjetJson(objetJson);

        idJoueurHote = (String) objetJson.get(__idJoueurHote);
        idJoueurInvite = (String) objetJson.get(__idJoueurInvite);
    }
}

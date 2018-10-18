package ca.cours5b5.guillaumegagnon.modeles;

import java.util.HashMap;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartie extends Modele implements Fournisseur{

    private MGrille grille;
    private GCouleur couleurCourante;

    @AttributSerialisable
    public MParametresPartie parametres;
    private final String __parametres = "parametres";

    public MPartie(MParametresPartie parametres){
        this.parametres = parametres;
        grille = new MGrille(parametres.getLargeur());
        fournirActionPlacerJeton();
        initialiserCouleurCourante();
    }

    public MParametresPartie getParametres(){
        return parametres;
    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        //atelier07
        for(Map.Entry<String, Object> entry : objetJson.entrySet()){
            String cle = entry.getKey();
            Object valeur = entry.getValue();

            if(cle.equals(__parametres)){
                parametres.aPartirObjetJson((Map<String, Object>) valeur);
            }
        }
    }

    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        //atelier 07
        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(__parametres, parametres.toString());

        return  objetJson;
    }


    public MGrille getGrille(){

        return grille;
    }

    private void initialiserCouleurCourante(){
        couleurCourante = GCouleur.ROUGE;
    }

    private void fournirActionPlacerJeton(){
        //Appeler fournirAction
        ControleurAction.fournirAction(this, GCommande.JOUER_COUP_ICI, new ListenerFournisseur() {
            @Override
            public void executer(Object... args) {
                jouerCoup((int)args[0]);
            }
        });
    }

    protected void jouerCoup(int colonne){
        grille.placerJeton(colonne, couleurCourante);
        prochaineCouleurCourante();
    }

    private void prochaineCouleurCourante(){
        if(couleurCourante.equals(GCouleur.ROUGE)){
            couleurCourante = GCouleur.JAUNE;
        } else if(couleurCourante.equals(GCouleur.JAUNE)){
            couleurCourante = GCouleur.ROUGE;
        }
    }
}

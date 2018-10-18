package ca.cours5b5.guillaumegagnon.modeles;

import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.global.GCouleur;

public class MColonne extends Modele{

    private List<GCouleur> jetons;

    public MColonne(){

    }

    public List<GCouleur> getJetons(){

        return jetons;
    }

    public void placerJeton(GCouleur couleur){

    }

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) {
        //Rien faire
    }

    @Override
    public Map<String, Object> enObjetJson() {
        //Rien faire
        return null;
    }
}

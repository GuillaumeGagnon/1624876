package ca.cours5b5.guillaumegagnon.modeles;

import java.util.HashMap;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MParametresPartie extends Modele {

    //ia
    @AttributSerialisable
    public Integer forceAI;
    protected final String __forceAI = "forceAI";

    @AttributSerialisable
    public Integer hauteur;
    protected final String __hauteur = "hauteur";

    @AttributSerialisable
    public Integer largeur;
    protected final String __largeur = "largeur";

    @AttributSerialisable
    public Integer pourGagner;
    protected final String __pourGagner = "pourGagner";

    public MParametresPartie cloner() {

        MParametresPartie mParametresPartie = new MParametresPartie();

        mParametresPartie.setHauteur(hauteur);
        mParametresPartie.setLargeur(largeur);
        mParametresPartie.setPourGagner(pourGagner);
        mParametresPartie.setForceAI(forceAI);


        return mParametresPartie;

    }


    public MParametresPartie(){
        super();

        hauteur = GConstantes.HAUTEUR_PAR_DEFAUT;
        largeur = GConstantes.LARGEUR_PAR_DEFAUT;
        pourGagner = GConstantes.POUR_GAGNER_PAR_DEFAUT;

        forceAI = GConstantes.FORCEAI;

    }

    public Integer getHauteur() { return hauteur; }

    public Integer getLargeur() {
        return largeur;
    }

    public Integer getPourGagner() {
        return pourGagner;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setPourGagner(int pourGagner) {
        this.pourGagner = pourGagner;
    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation  {

        for(Map.Entry<String, Object> entry : objetJson.entrySet()){

            String chaineValeur = (String) entry.getValue();

            switch (entry.getKey()){

                case __hauteur:

                    hauteur = Integer.valueOf(chaineValeur);
                    break;

                case __largeur:

                    largeur = Integer.valueOf(chaineValeur);
                    break;


                case __pourGagner:

                    pourGagner = Integer.valueOf(chaineValeur);
                    break;

                //AI
                case __forceAI:
                    forceAI = Integer.valueOf(chaineValeur);
                    break;

                default:

                    throw new ErreurSerialisation("Attribut inconnu: " + entry.getKey());
            }
        }
    }


    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation  {

        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(__hauteur, hauteur.toString());
        objetJson.put(__largeur, largeur.toString());
        objetJson.put(__pourGagner, pourGagner.toString());
        //AI
        objetJson.put(__forceAI, forceAI.toString());

        return objetJson;

    }


    public void setForceAI(Integer forceAI) {
        this.forceAI = forceAI;
    }

    public Integer getForceAI() {
        return forceAI;
    }
}

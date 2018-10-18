package ca.cours5b5.guillaumegagnon.modeles;

import java.util.HashMap;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MParametresPartie extends Modele{

    @AttributSerialisable
    public Integer hauteur;
    protected final String __hauteur = "hauteur";

    @AttributSerialisable
    public Integer largeur;
    protected final String __largeur = "largeur";

    @AttributSerialisable
    public Integer pourGagner;
    protected final String __pourGagner = "pourGagner";


    public static MParametresPartie aPartirMParametres(MParametres mParametres){
        //utiliser cloner()???

        /*essai*/
        /*
        MParametresPartie partieTemp = new MParametresPartie();

        partieTemp.setHauteur(mParametres.getHauteur());
        partieTemp.setLargeur(mParametres.getLargeur());
        partieTemp.setPourGagner(mParametres.getPourGagner());

        return partieTemp;*/
        MParametresPartie mParametresPartie = mParametres.getParametresPartie().cloner();
        return mParametresPartie;
    }

    private MParametresPartie cloner() {
        /*MParametresPartie partieTemp = new MParametresPartie();

        partieTemp.setHauteur(this.getHauteur());
        partieTemp.setLargeur(this.getLargeur());
        partieTemp.setPourGagner(this.getPourGagner());

        return partieTemp;*/

        //plus simple
        MParametresPartie mParametresPartie = this;
        return mParametresPartie;
    }

    public MParametresPartie(){
        /*constructeur*/
        setHauteur(GConstantes.HAUTEUR_PAR_DEFAUT);
        setLargeur(largeur = GConstantes.LARGEUR_PAR_DEFAUT);
        setPourGagner(pourGagner = GConstantes.POUR_GAGNER_PAR_DEFAUT);

    }


    /******************************************************************/

    /*GET*/
    public Integer getHauteur() {
        return hauteur;
    }
    public Integer getLargeur() {
        return largeur;
    }
    public Integer getPourGagner() {
        return pourGagner;
    }
    /*SET*/
    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
    public void setPourGagner(int pourGagner) {
        this.pourGagner = pourGagner;
    }

    /******************************************************************/

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        for(Map.Entry<String, Object> entry: objetJson.entrySet()){
            String cle = entry.getKey();
            Object  valeur = entry.getValue();

            if(cle == this.__hauteur){
                this.setHauteur((Integer)valeur);
            } else if(cle == this.__largeur){
                this.setLargeur((Integer)valeur);
            }else if(cle == this.__pourGagner){
                this.setPourGagner((Integer)valeur);
            }
        }
    }

    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = new HashMap<>();

        objetJson.put(this.__hauteur, this.hauteur);
        objetJson.put(this.__largeur, this.largeur);
        objetJson.put(this.__pourGagner, this.pourGagner);

        return objetJson;
    }
}

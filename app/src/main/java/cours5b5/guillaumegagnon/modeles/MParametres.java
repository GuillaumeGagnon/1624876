package cours5b5.guillaumegagnon.modeles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cours5b5.guillaumegagnon.global.GConstantes;
import cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MParametres extends Modele {

    //FIXME : temporaire; écriture d'un gestionnaire de modèle à l'atelier #07

    public static  MParametres instance = new MParametres();
    private List<Integer> choixHauteur;
    private List<Integer> choixLargeur;
    private List<Integer> choixPourGagner;

    @AttributSerialisable
    public Integer hauteur = Integer.valueOf(GConstantes.hDefault);
    private final String __hauteur = "hauteur";

    @AttributSerialisable
    public Integer largeur = Integer.valueOf(GConstantes.lDefault);;
    private final String __largeur = "largeur";

    @AttributSerialisable
    public Integer pourGagner = Integer.valueOf(GConstantes.gagnerDefault);;
    private final String __pourGagner = "pourGagner";


    public MParametres() {
        creerChoix();
    }

    private void creerChoix() {
        this.choixHauteur = creerListChoix(GConstantes.hMin, GConstantes.hMax);
        this.choixLargeur = creerListChoix(GConstantes.lMin, GConstantes.lMax);
        this.choixPourGagner = creerListChoix(GConstantes.gagnerMin, GConstantes.gagnerMax);
    }

    private List<Integer> creerListChoix(int min, int max) {

        List<Integer> list = new ArrayList<>();
        for(int i = min; i <= max; i++){
            list.add(Integer.valueOf(i));
        }

        return list;
    }

    /*GETTER*/
    public List<Integer> getChoixHauteur(){
        return this.choixHauteur;
    }
    public List<Integer> getChoixLargeur(){
        return this.choixLargeur;
    }
    public List<Integer> getChoixPourGagner(){
        return this.choixPourGagner;
    }



    /*GETTER*/
    public Integer getHauteur() {
        return this.hauteur;
    }
    public Integer getLargeur() {
        return this.largeur;
    }
    public Integer getPourGagner() {
        return this.pourGagner;
    }

    /*SETTER*/
    public void setHauteur(int hauteur) {
        this.hauteur = Integer.valueOf(hauteur);
    }
    public void setLargeur(int largeur) {
        this.largeur = Integer.valueOf(largeur);
    }
    public void setPourGagner(int pourGagner) {
        this.pourGagner = Integer.valueOf(pourGagner);
    }

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) {
        if(objetJson != null){

        } else {
//            ArrayAdapter<Integer> adapterHauteur = new ArrayAdapter<>();
        }
    }

    @Override
    public Map<String, Object> enObjetJson() {
        return null;
    }
}

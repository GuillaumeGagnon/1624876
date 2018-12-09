package ca.cours5b5.guillaumegagnon.modeles;

import com.google.gson.internal.bind.ObjectTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.global.GDirection;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MGrille extends Modele  {

    private List<MColonne> colonnes;


    public MGrille(int largeur){

        colonnes = new ArrayList<>();

        initialiserColonnes(largeur);

    }


    private void initialiserColonnes(int largeur) {

        for(int i=0; i<largeur; i++){

            colonnes.add(new MColonne());

        }
    }


    public List<MColonne> getColonnes() {
        return colonnes;
    }


    public void placerJeton(int colonne, GCouleur couleur) {

        colonnes.get(colonne).placerJeton(couleur);

    }


    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {

        throw new UnsupportedOperationException();

    }


    @Override
    public Map<String, Object> enObjetJson()  {

        throw new UnsupportedOperationException();

    }


    public boolean siCouleurGagne(GCouleur couleur, int pourGagner){

        for(int idColonne = 0; idColonne < colonnes.size(); idColonne++){

            if(siCouleurGagneCetteColonne(couleur, idColonne, pourGagner)){

                return true;

            }
        }

        return false;
    }


    private boolean siCouleurGagneCetteColonne(GCouleur couleur, int idColonne, int pourGagner){

        MColonne colonne = colonnes.get(idColonne);

        for(int idRangee = 0; idRangee <  colonne.getJetons().size(); idRangee++){

            if(siCouleurGagneCetteCase(couleur, idColonne, idRangee, pourGagner)){

                return true;

            }

        }

        return false;
    }


    private boolean siCouleurGagneCetteCase(GCouleur couleur, int idColonne, int idRangee, int pourGagner) {
            for(GDirection direction : GDirection.directions){
                if(siCouleurGagneDansCetteDirection(couleur, idColonne, idRangee, direction, pourGagner)){

                    return true;

                }
            }

        return false;
    }


    private boolean siCouleurGagneDansCetteDirection(GCouleur couleur, int idColonne, int idRangee, GDirection direction, int pourGagner) {
       if(pourGagner == 0){

           return true;

       }else if(siMemeCouleurCetteCase(couleur, idColonne, idRangee)){

           int nouvelleColonne = idColonne + direction.incrementHorizontal;
           int nouvelleRangee = idRangee + direction.incrementVertical;

           int nouveauPourGagner = pourGagner - 1;

           if(siCouleurGagneDansCetteDirection(couleur, nouvelleColonne, nouvelleRangee, direction, nouveauPourGagner)){

               return true;
           }

       }

       return false;
    }


    private boolean siMemeCouleurCetteCase(GCouleur couleur, int idColonne, int idRangee){

        if(idColonne >= 0 && idColonne < colonnes.size()){

            MColonne colonne = colonnes.get(idColonne);

            if(idRangee >= 0 && idRangee < colonne.getJetons().size()){

                return colonne.getJetons().get(idRangee).siMemeCouleur(couleur);

            }
        }

        return false;
    }


}

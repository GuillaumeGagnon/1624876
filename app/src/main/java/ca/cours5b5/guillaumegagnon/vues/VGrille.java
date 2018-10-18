package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.modeles.MGrille;

public class VGrille extends GridLayout{

    public VGrille(Context context) {
        super(context);
    }

    public VGrille(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VGrille(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VGrille(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private int nombreRangees;

    //memoriser les en-tête ajoutées
    private List<VEntete> entetes;

    // mémoriser les VCase ajouté (tab[][])
    private VCase[][] lesCases;



    //mémoriser les VCase ajoutées avec un tableau a 2 dimensions
    //private VCase[][] lesCase;
    //ou
    private class Colonne extends ArrayList<VCase> {

    }
    private List<Colonne> lesColonne;


    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
    }

    void creerGrille(int hauteur, int largeur){
        entetes = new ArrayList<>();
        lesColonne = new ArrayList<>();
        nombreRangees = hauteur;

        initialiserColonnes(largeur);
        ajouterEnTetes(largeur);
        ajouterCases(hauteur, largeur);
    }

    //mémoriser les VCase ajoutées avec un tableau
   /* private void initialiserTableauDeCases(int hauteur, int largeur){

    }
    */

    private void initialiserColonnes(int largeur){

        for(int i = 0; i < largeur; i++){
            lesColonne.add(new Colonne());
        }

    }

    private void ajouterEnTetes(int largeur){
        for(int i = 0; i < largeur; i++) {
            VEntete entete = new VEntete(getContext(),i);

            //Vue
            this.addView(entete, getMiseEnPageEntete(i));

            //Ajout de entête
            entetes.add(entete);

            //Pour chaque en-tete, on veut installer le listener
            installerListenerEntete(entete, i);

        }
    }

    private LayoutParams getMiseEnPageEntete(int colonne){

        Spec specRangee = GridLayout.spec(0, 1.0f);
        Spec specColonne = GridLayout.spec(colonne, 1.0f);

        LayoutParams mesParams = new LayoutParams(specRangee, specColonne);

        mesParams.width = 0;
        mesParams.height = 0;
        mesParams.setGravity(Gravity.FILL);

        mesParams.rightMargin = 5;
        mesParams.leftMargin = 5;

        return mesParams;
    }

    private void ajouterCases(int hauteur, int largeur){
        for(int i = 0; i < largeur; i++){
            Colonne colonne = new Colonne();
            for(int j = hauteur; j > 0; j--){
                VCase vCase = new VCase(getContext(), j-(2 * (j - hauteur)) - hauteur, i);

                this.addView(vCase, getMiseEnPageCase(j,i));

                colonne.add(vCase);
            }
            lesColonne.add(i, colonne);
        }
    }

    private LayoutParams getMiseEnPageCase(int rangee, int colonne){
        Spec specRangee = GridLayout.spec(rangee, 1.0f);
        Spec specColonne = GridLayout.spec(colonne, 1.0f);

        LayoutParams mesParams = new LayoutParams(specRangee, specColonne);

        mesParams.width = 0;
        mesParams.height = 0;
        mesParams.setGravity(Gravity.FILL);

        mesParams.rightMargin = 5;
        mesParams.leftMargin = 5;

        return mesParams;
    }


    private void initialiserTableauDeCases(int hauteur, int largeur){

    }

    private void demanderActionEntete(){

    }

    private void installerListenerEntete(VEntete entete, final int colonne){

    }

    void afficherJetons(MGrille grille){

    }

    private void afficherJeton(int colonne, int rangee, GCouleur jeton){

    }


}

package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.modeles.MColonne;
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

    /*public VGrille(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    private VCase[][] lesCases;

    //private class Colonne extends ArrayList<VCase> {}

    //atelier07
    private Action jouer;




    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        demanderActionEntete();
    }

    void creerGrille(int hauteur, int largeur){
        //entetes = new ArrayList<>();
        //lesColonne = new ArrayList<>();
        //nombreRangees = hauteur;

        initialiserTableauDeCases(hauteur, largeur);
        ajouterEnTetes(largeur);
        ajouterCases(hauteur, largeur);
    }

    //mémoriser les VCase ajoutées avec un tableau
   /* private void initialiserTableauDeCases(int hauteur, int largeur){

    }
    */

    private void initialiserColonnes(int largeur){

        /*for(int i = 0; i < largeur; i++){
            lesColonne.add(new Colonne());
        }*/

    }

    private void ajouterEnTetes(int largeur){
        for(int i = 0; i < largeur; ++i) {
            VEntete entete = new VEntete(getContext(),i);

            //Pour chaque en-tete, on veut installer le listener
            installerListenerEntete(entete, i);

            //Vue
            addView(entete, getMiseEnPageEntete(i));
        }
    }

    private LayoutParams getMiseEnPageEntete(int colonne){
        Spec specRangee = GridLayout.spec(0, 2f);
        Spec specColonne = GridLayout.spec(2, 7f);



        Spec rangeeBoutonHaut = GridLayout.spec(0, 1f);
        Spec colonneBoutonHaut = GridLayout.spec(2, 7f);

        Spec rangeeBoutonGauche = GridLayout.spec(1, 3f);
        Spec colonneBoutonGauche = GridLayout.spec(0, 2f);

        Spec rangeeBoutonCentre = GridLayout.spec(2, 3f);
        Spec colonneBoutonCentre = GridLayout.spec(2, 7f);

        Spec rangeeBoutonDroit = GridLayout.spec(1, 3f);
        Spec colonneBoutonDroit = GridLayout.spec(9, 2f);

        Spec rangeeBoutonBas = GridLayout.spec(4, 1f);
        Spec colonneBoutonBas = GridLayout.spec(2, 7f);


        //LayoutParams mesParams = new LayoutParams(specRangee, specColonne);

        LayoutParams mesParams = new LayoutParams(rangeeBoutonHaut, colonneBoutonHaut);
        /*mesParams.width = 0;
        mesParams.height = 0;
        mesParams.rightMargin = 5;
        mesParams.leftMargin = 5;
        mesParams.setGravity(Gravity.FILL);*/

        return mesParams;
    }

    private void ajouterCases(int hauteur, int largeur){



        // maths simplified
        // i-> h     et    j-> l
        //(plus logique/lisible)
        for(int h = 0; h < hauteur; ++h){
            for(int l = 0; l < largeur; ++l){
                VCase vCase = new VCase(getContext(), h, l);
                //addView(vCase, getMiseEnPageCase(hauteur - h - 1, l));
                lesCases[h][l] = vCase;
                //lesCases[h][l].setBackgroundColor(Color.BLUE);
            }
        }
    }

    private LayoutParams getMiseEnPageCase(int rangee, int colonne){
        Spec specRangee = GridLayout.spec(rangee + 1, 1.0f);
        Spec specColonne = GridLayout.spec(colonne, 1.0f);

        LayoutParams mesParams = new LayoutParams(specRangee, specColonne);

        mesParams.width = 0;
        mesParams.height = 0;
        mesParams.rightMargin = 5;
        mesParams.leftMargin = 5;
        mesParams.setGravity(Gravity.FILL);

        return mesParams;
    }


    private void initialiserTableauDeCases(int hauteur, int largeur){
        lesCases = new VCase[hauteur][largeur];
    }

    private void demanderActionEntete(){
        jouer = ControleurAction.demanderAction(GCommande.JOUER_COUP_ICI);
    }

    private void installerListenerEntete(VEntete entete, final int colonne){
        entete.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                demanderActionEntete();
                jouer.setArguments(colonne);
                jouer.executerDesQuePossible();
            }
        });
    }

    void afficherJetons(MGrille grille){
        List<MColonne> colonnes = grille.getColonnes();
        for(int colonne = 0; colonne < colonnes.size(); ++colonne){
            for(int largeur = 0; largeur < colonnes.get(colonne).getJetons().size(); ++largeur){
                //lesCases[largeur][colonne].setBackgroundColor(Color.YELLOW);
                afficherJeton(colonne, largeur, colonnes.get(colonne).getJetons().get(largeur));
            }
        }
    }

    private void afficherJeton(int colonne, int large, GCouleur jeton){
        lesCases[large][colonne].afficherJeton(jeton);
        //lesCases[large][colonne].setBackgroundColor(Color.YELLOW);
    }


}

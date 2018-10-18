package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
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

    public VGrille(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private int nombreRangees;

    //memoriser les en-tête ajoutées
    //old
    //private List<VEntete> entetes;

    // mémoriser les VCase ajouté (tab[][])
    private VCase[][] lesCases;



    //mémoriser les VCase ajoutées avec un tableau a 2 dimensions
    //private VCase[][] lesCase;
    //ou
    private class Colonne extends ArrayList<VCase> {

    }

    //old
    private List<Colonne> lesColonne;


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

            //Ajout de entête
            //entetes.add(entete);
        }
    }

    private LayoutParams getMiseEnPageEntete(int colonne){

        float poidsColonne = 3;
        float poidsRangee = 1;

        Spec specRangee = GridLayout.spec(0, poidsColonne);
        Spec specColonne = GridLayout.spec(colonne, poidsRangee);

        LayoutParams mesParams = new LayoutParams(specRangee, specColonne);

        mesParams.width = 0;
        mesParams.height = 0;
        mesParams.rightMargin = 5;
        mesParams.leftMargin = 5;
        mesParams.setGravity(Gravity.FILL);

        return mesParams;
    }

    private void ajouterCases(int hauteur, int largeur){
        /*for(int i = 0; i < largeur; i++){
            Colonne colonne = new Colonne();
            for(int j = hauteur; j > 0; j--){
                VCase vCase = new VCase(getContext(), j-(2 * (j - hauteur)) - hauteur, i);

                this.addView(vCase, getMiseEnPageCase(j,i));

                colonne.add(vCase);
            }
            lesColonne.add(i, colonne);
        }*/

        // maths simplified
        // i-> h     et    j-> l
        //(plus logique)
        for(int h = 0; h < hauteur; ++h){
            for(int l = 0; l < largeur; ++l){
                VCase vCase = new VCase(getContext(), h, l);
                addView(vCase, getMiseEnPageCase(hauteur - h - 1, l));
                lesCases[h][l] = vCase;
            }
        }
    }

    private LayoutParams getMiseEnPageCase(int rangee, int colonne){
        Spec specRangee = GridLayout.spec(rangee + 1, 1.0f);
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
            for(int large = 0; large < colonnes.get(colonne).getJetons().size(); ++large){
                afficherJeton(colonne, large, colonnes.get(colonne).getJetons().get(large));
            }
        }
    }

    private void afficherJeton(int colonne, int rangee, GCouleur jeton){
        lesCases[rangee][colonne].afficherJeton(jeton);
    }


}

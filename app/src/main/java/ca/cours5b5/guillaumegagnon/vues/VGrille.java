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
import ca.cours5b5.guillaumegagnon.modeles.MJeton;


public class VGrille extends GridLayout {

    public VGrille(Context context) {
        super(context);
    }

    public VGrille(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VGrille(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int nombreRangees;

    private class Colonne extends ArrayList<VCase> {}

    private List<Colonne> colonnesDeCases;

    private Action actionEntete;

    private List<VEntete> entetes;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initialiser();

        demanderActionEntete();


    }

    private void demanderActionEntete() {

        actionEntete = ControleurAction.demanderAction(GCommande.PLACER_JETON_ICI);

    }

    private void initialiser() {

        colonnesDeCases = new ArrayList<>();

        entetes = new ArrayList<>();

    }


    void creerGrille(int hauteur, int largeur) {

        // +1 pour la rangee des en-têtes
        this.nombreRangees = hauteur + 1;

        this.setRowCount(nombreRangees);
        this.setColumnCount(largeur);

        initialiserColonnes(largeur);

        ajouterEnTetes(largeur);
        ajouterCases(hauteur, largeur);


    }

    private void initialiserColonnes(int largeur){

        for(int i=0; i<largeur; i++){

            colonnesDeCases.add(new Colonne());

        }
    }

    private void ajouterEnTetes(int largeur){

        for(int colonne=0; colonne<largeur; colonne++){

            VEntete entete = new VEntete(getContext(), colonne);

            LayoutParams miseEnPageEntete = getMiseEnPageEntete(colonne);

            this.addView(entete, miseEnPageEntete);

            entetes.add(entete);

            installerListenerEntete(entete, colonne);

        }
    }


    private LayoutParams getMiseEnPageEntete(int colonne){

        Spec specRangee = GridLayout.spec(0, 3f);
        Spec specColonne = GridLayout.spec(colonne, 1f);

        LayoutParams paramsEntete = new LayoutParams(specRangee, specColonne);

        paramsEntete.width = 0;
        paramsEntete.height = 0;
        paramsEntete.setGravity(Gravity.FILL);
        paramsEntete.rightMargin = 5;
        paramsEntete.leftMargin = 5;


        return paramsEntete;
    }

    private void installerListenerEntete(VEntete entete, final int colonne) {
        entete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                actionEntete.setArguments(colonne);
                actionEntete.executerDesQuePossible();
                
            }
        });
    }


    private void ajouterCases(int hauteur, int largeur) {
        for (int colonne = 0; colonne < largeur; colonne++) {
            for (int rangee = 0; rangee < hauteur; rangee++) {


                VCase vCase = new VCase(getContext(), rangee, colonne);
                LayoutParams miseEnPageCase = getMiseEnPageCase(rangee, colonne);

                this.addView(vCase, miseEnPageCase);

                colonnesDeCases.get(colonne).add(vCase);

            }
        }
    }

    private LayoutParams getMiseEnPageCase(int rangee, int colonne){

        // Pour nous, la rangée 0 est en bas
        int dernierIndiceRangee = nombreRangees -1;
        int indiceRangeeCetteCase = dernierIndiceRangee -rangee;

        Spec specRangee = GridLayout.spec(indiceRangeeCetteCase, 1f);
        Spec specColonne = GridLayout.spec(colonne, 1f);

        LayoutParams paramsCase = new LayoutParams(specRangee, specColonne);

        paramsCase.width = 0;
        paramsCase.height = 0;
        paramsCase.setGravity(Gravity.FILL);
        paramsCase.leftMargin = 5;
        paramsCase.rightMargin = 5;
        paramsCase.topMargin = 5;
        paramsCase.bottomMargin = 5;

        return paramsCase;
    }

    void afficherJetons(MGrille grille){

        List<MColonne> colonnes = grille.getColonnes();

        for(int numeroColonne=0; numeroColonne < colonnes.size(); numeroColonne++){

            List<MJeton> jetons = colonnes.get(numeroColonne).getJetons();

            for(int numeroRangee=0; numeroRangee < jetons.size(); numeroRangee++){

                MJeton jeton = jetons.get(numeroRangee);

                afficherJeton(numeroColonne, numeroRangee, jeton);

            }
        }
    }

    private void afficherJeton(int colonne, int rangee, MJeton jeton){

        colonnesDeCases.get(colonne).get(rangee).afficherJeton(jeton);

    }

}

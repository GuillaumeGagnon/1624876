package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurObservation;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurObservation;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.Modele;


public class VPartie extends Vue {

    private VGrille grille;

    public VPartie(Context context) {
        super(context);
    }

    public VPartie(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VPartie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initialiser();
        observerPartie();
    }

    private void initialiser() {

        grille = findViewById(R.id.grille);

    }

    private void observerPartie() {

        ControleurObservation.observerModele(/*Atelier 13*/getNomModele(),
                new ListenerObservateur() {
                    @Override
                    public void reagirNouveauModele(Modele modele) {

                        MPartie mPartie = getPartie(modele);
                        preparerAffichage(mPartie);
                        miseAJourGrille(mPartie);

                    }

                    @Override
                    public void reagirChangementAuModele(Modele modele) {

                        MPartie mPartie = getPartie(modele);
                        miseAJourGrille(mPartie);

                    }
                });
    }

    private void preparerAffichage(MPartie partie) {

        MParametresPartie parametresPartie = partie.getParametres();
        grille.creerGrille(parametresPartie.getHauteur(), parametresPartie.getLargeur());

    }

    private MPartie getPartie(Modele modele){

        try{

            return (MPartie) modele;

        }catch(ClassCastException e){

            throw new ErreurObservation(e);

        }

    }

    private void miseAJourGrille(MPartie partie){

        grille.afficherJetons(partie.getGrille());

    }

    protected String getNomModele(){
        return MPartie.class.getSimpleName();
    }

}

package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurObservation;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.Modele;

public class VPartie extends Vue{

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
    protected void onFinishInflate(){
        super.onFinishInflate();

        grille = findViewById(R.id.layout_vgrille);

        observerPartie();
    }

    private void observerPartie(){
        /*appeler observer pour obtenir le modele
        *
        * ensuite, créer la grille d'affichage*/
        //ControleurObservation.observerModele();
        ControleurObservation.observerModele(MPartie.class.getSimpleName(),
                new ListenerObservateur() {
                    @Override
                    public void reagirChangementAuModele(Modele modele) {
                        //initialiserGrille(getPartie(modele));

                        /*a vérifier*/
                        //miseAJourGrille((MPartie) modele);
                        miseAJourGrille((getPartie(modele)));
                    }

                    @Override
                    public void reagirNouveauModele(Modele modele) {
                        super.reagirNouveauModele(modele);
                        MPartie partie = (MPartie) modele;
                        initialiserGrille(partie);
                    }
                });

    }

    private MPartie getPartie(Modele modele){
        /************************************************/

        MPartie mPartie = (MPartie) modele;
        return mPartie;
    }

    private void initialiserGrille(MPartie partie){
        /*
        MParametresPartie parametres = partie.getParametres();
        grille.creerGrille(parametres.getHauteur(), parametres.getLargeur());
        */
        // plus concis
        grille.creerGrille(partie.parametres.hauteur, partie.parametres.largeur);
    }

    public void miseAJourGrille(MPartie partie){
        this.grille.afficherJetons(partie.getGrille());
    }

}

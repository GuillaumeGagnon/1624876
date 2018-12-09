package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurObservation;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerObservateur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurObservation;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;
import ca.cours5b5.guillaumegagnon.modeles.MPartie;
import ca.cours5b5.guillaumegagnon.modeles.Modele;


public class VPartie extends Vue {


    private VGrille grille;

    private TextView texteJoueurUn;
    private TextView texteJoueurDeux;


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

        adapterTexteNomJoueurSiPaysage();

        observerPartie();

    }


    private void initialiser() {

        grille = findViewById(R.id.grille);

        texteJoueurUn = findViewById(R.id.texte_joueur_un);
        texteJoueurDeux = findViewById(R.id.texte_joueur_deux);



    }


    private void adapterTexteNomJoueurSiPaysage() {

        if(!getResources().getBoolean(R.bool.si_portrait)){

            adapterTexteNomJoueurSiPaysage(texteJoueurUn);
            adapterTexteNomJoueurSiPaysage(texteJoueurDeux);
        }

    }

    private void adapterTexteNomJoueurSiPaysage(TextView texteJoueur) {

        CharSequence nomJoueur = texteJoueur.getText();

        String nomJoueurPaysage = texteEnPaysage(nomJoueur);

        texteJoueur.setText(nomJoueurPaysage);

    }

    private String texteEnPaysage(CharSequence texte){
        String textePaysage = "";

        for(int i=0; i<texte.length(); i++){
            char c = texte.charAt(i);

            textePaysage += c;

            if(i < texte.length()){
                textePaysage += "\n";
            }

        }

        return textePaysage;
    }




    private void observerPartie() {

        ControleurObservation.observerModele(getNomModele(),
                new ListenerObservateur() {
                    @Override
                    public void reagirNouveauModele(Modele modele) {

                        MPartie partie = getPartie(modele);
                        MParametresPartie parametresPartie = partie.getParametres();

                        grille.creerGrille(parametresPartie.getHauteur(), parametresPartie.getLargeur());

                        miseAJourGrille(partie);

                        miseAJourNomJoueur(partie);
                    }

                    @Override
                    public void reagirChangementAuModele(Modele modele) {

                        MPartie partie = getPartie(modele);

                        miseAJourNomJoueur(partie);

                        miseAJourGrille(partie);


                    }
                });

    }

    protected String getNomModele(){
        return MPartie.class.getSimpleName();
    }

    private void miseAJourNomJoueur(MPartie partie) {

        switch(partie.getCouleurCourante()){

            case ROUGE:

                texteJoueurDeux.setVisibility(INVISIBLE);
                texteJoueurUn.setVisibility(VISIBLE);
                break;

            case JAUNE:

                texteJoueurUn.setVisibility(INVISIBLE);
                texteJoueurDeux.setVisibility(VISIBLE);
                break;

        }
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

}

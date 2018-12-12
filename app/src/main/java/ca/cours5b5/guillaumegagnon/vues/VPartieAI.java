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
import ca.cours5b5.guillaumegagnon.modeles.MPartieAI;
import ca.cours5b5.guillaumegagnon.modeles.Modele;


public class VPartieAI extends Vue {


    private VGrille grille;

    private TextView texteJoueurUn;
    private TextView texteJoueurDeux;


    public VPartieAI(Context context) {
        super(context);
    }

    public VPartieAI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VPartieAI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.d("debug_AI", "VPartieAI/onFinishInflate");

        initialiser();

        adapterTexteNomJoueurSiPaysage();

        observerPartie();

    }


    private void initialiser() {

        grille = findViewById(R.id.grille_ai);

        texteJoueurUn = findViewById(R.id.texte_joueur_un_ai);
        texteJoueurDeux = findViewById(R.id.texte_joueur_deux_ai);



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

                        MPartieAI partieAI = getPartie(modele);
                        MParametresPartie parametresPartie = partieAI.getParametres();

                        grille.creerGrille(parametresPartie.getHauteur(), parametresPartie.getLargeur());

                        miseAJourGrille(partieAI);

                        miseAJourNomJoueur(partieAI);
                    }

                    @Override
                    public void reagirChangementAuModele(Modele modele) {

                        MPartieAI partieAI = getPartie(modele);

                        miseAJourNomJoueur(partieAI);

                        miseAJourGrille(partieAI);


                    }
                });

    }

    protected String getNomModele(){
        return MPartieAI.class.getSimpleName();
    }

    private void miseAJourNomJoueur(MPartieAI partie) {

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

    private MPartieAI getPartie(Modele modele){
        try{

            return (MPartieAI) modele;

        }catch(ClassCastException e){

            throw new ErreurObservation(e);

        }
    }

    private void miseAJourGrille(MPartieAI partieAI){

        grille.afficherJetons(partieAI.getGrille());

    }



}

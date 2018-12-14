package ca.cours5b5.guillaumegagnon.modeles;

import android.util.Log;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import ca.cours5b5.guillaumegagnon.AI.AI;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurAction;
import ca.cours5b5.guillaumegagnon.exceptions.ErreurSerialisation;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GCouleur;
import ca.cours5b5.guillaumegagnon.serialisation.AttributSerialisable;

public class MPartieAI extends MPartie implements Fournisseur{

    @AttributSerialisable
    public String partieAI;
    private String __partieAI = "partieAI";
    int nombreCoupPartieAI = 0;
    int coup_gagnant;
    int coupAJouer = 1;
    int joueurActuel = 0; //0 == homme; 1 = IA
    GCouleur couleurCouranteAI = couleurCourante;
    boolean onRemonte = false;
    int profondeur = 0;
    int [] resultat;


    public MPartieAI(MParametresPartie parametres) {
        super(parametres);
        Log.d("debug_AI", "MPartieAI");

    }

    @Override
    protected void fournirActionPlacerJeton() {
        ControleurAction.fournirAction(this,
                GCommande.PLACER_JETON_ICI,
                new ListenerFournisseur() {

                    @Override
                    public void executer(Object... args) {
                        try {

                            int colonne = (Integer) args[0];

                            jouerCoup(colonne);
                            nombreCoupPartieAI++; // un coup a été joué (utile pour algo de IA)
                            Log.d("debug_ai", "executer: joueur " + colonne + " \n nombre coup : " + nombreCoupPartieAI);
                            // on fait jouer l'IA apres chaque coup du joueur
                            //ce n'est pas 100% optimal car l'ia tente de jouer meme si le joueur gagne mais ca marche
                            if(!grille.siCouleurGagne(couleurCourante, parametres.getPourGagner())){
                                if(parametres.getForceAI() == 0){
                                    jouerCoup(aiAleatoire());
                                } else{
                                    jouerCoup(ai());// on laisse l'IA jouer seulement si le joueur ne gagne pas
                                }
                                nombreCoupPartieAI++;
                            }



                        } catch (ClassCastException e) {

                            throw new ErreurAction(e);

                        }
                    }
                });
    }

    // json

    @Override
    public void aPartirObjetJson(Map<String, Object> objetJson) throws ErreurSerialisation {
        super.aPartirObjetJson(objetJson);
        partieAI = (String) objetJson.get(__partieAI);

    }
    @Override
    public Map<String, Object> enObjetJson() throws ErreurSerialisation {
        Map<String, Object> objetJson = super.enObjetJson();
        objetJson.put(__partieAI, partieAI);

        return objetJson;

    }

    //IA
    private int aiAleatoire(){
        Log.d("debug", "aiAleatoire: ");
        int coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());
        while(!siCoupLegal(coup_ai)) {
            coup_ai = ThreadLocalRandom.current().nextInt(0, parametres.getLargeur());
        }
        return coup_ai;
    }

    private int ai(){
        nombreCoupPartieAI = 0;
        coup_gagnant = 0;
        coupAJouer = 1;
        onRemonte = false;
        profondeur = 0;
        resultat = null;

        Log.d("FORCE_AI","ForceAI : " + parametres.getForceAI());

        AI objetAI = new AI(parametres, grille);
        int temps_debug = recursif(objetAI)[1];
        Log.d("debugageAI", "ai: " + temps_debug);
        return temps_debug;
    }

    private int[] recursif(AI objetAI){
        profondeur++;
        if(profondeur == parametres.getForceAI()){ //on limite à 100/1'000/10'000 pour éviter les StackOverflow exemple "java.lang.StackOverflowError: stack size 8MB"
            onRemonte = true;
        }
        if(onRemonte){
            Log.d("debugageAI", "recursif:/onRemonte ");
            return resultat;
        } else{
            Log.d("debugageAI", "recursif: debut code IA");

            if(nombreCoupPartieAI == objetAI.parametres.largeur*objetAI.parametres.hauteur) { // on verifie si null
                Log.d("debugageAI", "recursif: if null");
                resultat = new int[]{0, -1, profondeur}; // match null = 0 pour l'IA
                return resultat;
            }
            for(int x = 0; x < objetAI.parametres.largeur; x++) { // on check si l'IA peut gagner avec le prochain coup
                if (objetAI.siCoupLegal(x) && objetAI.siCoupGagnant(x, couleurCouranteAI)) {
                    coup_gagnant = x;
                    Log.e("debugageAI", "recursif/coup gagnant: " + x + " BRAVO!!!");
                    resultat = new int[]{(objetAI.parametres.largeur*objetAI.parametres.hauteur+1 - nombreCoupPartieAI)/2, x, profondeur}; // on retourne la valeur du coup gagnant (donc ici l'IA gagne)
                    return resultat;
                }
            }
            int meilleurScorePourLeMoment = -objetAI.parametres.largeur*objetAI.parametres.hauteur; // initialise au pire score possible

            for(int i = 0; i < objetAI.parametres.largeur; i++) {
                if (objetAI.siCoupLegal(i)) {
                    Log.d("debugageAI", "recursif/for#2: ");
                    AI objetAI_recursif = new AI(parametres, grille);
                    objetAI_recursif.jouer(i, couleurCouranteAI); //on simule un coup du joueur
                    switch (couleurCouranteAI) {

                        case ROUGE:
                            couleurCouranteAI = GCouleur.JAUNE;
                            break;

                        case JAUNE:
                            couleurCouranteAI = GCouleur.ROUGE;
                    }
                    int[] temp = recursif(objetAI_recursif);
                    if(temp != null){
                        int score = -temp[0];
                        if (score > meilleurScorePourLeMoment) {
                            meilleurScorePourLeMoment = score; // keep track of best possible score so far.
                            coupAJouer = temp[1];
                            Log.d("debugageAI", "recursif/for#2/if: ");
                            this.resultat = new int[] {meilleurScorePourLeMoment, coupAJouer, profondeur};
                        }
                    }

                }
            }
            resultat = new int[]{meilleurScorePourLeMoment, coupAJouer, profondeur};
            Log.d("debogageAI", "recursif: resultat "+ resultat[0] + " " + resultat[1] + " " + resultat[2]);
            return resultat;
        }


    }


}


package ca.cours5b5.guillaumegagnon.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.modeles.MPartieReseau;
import ca.cours5b5.guillaumegagnon.usagers.JoueursEnAttente;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurModeles;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;

import static ca.cours5b5.guillaumegagnon.global.GConstantes.CODE_CONNEXION_FIREBASE;

public class AMenuPrincipal extends Activite implements Fournisseur {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Log.d("debug_AI", "AMenuPrincipal/onCreate");

        fournirActions();

    }


    private void fournirActions() {

        fournirActionParametres();

        fournirActionDemarrerPartie();

        fournirActionConnexion();

        fournirActionDeconnexion();

        fournirActionJoindreOuCreerPartieReseau();

        fournirActionDemarrerPartieAI();

    }

    // projet AI
    private void fournirActionDemarrerPartieAI() {

        Log.d("debug_AI", "AMenuPrincipal/fournirActionDemarrerPartieAI");

        ControleurAction.fournirAction(this,
                GCommande.DEMARRER_PARTIE_AI,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {
                        Log.d("debug_AI", "AMenuPrincipal/fournirActionDemarrerPartieAI/GCommande");
                        transitionPartieAI();

                    }
                });
    }

    private void transitionPartieAI() {
        Log.d("debug_AI", "AMenuPrincipal/transitionPartieAI");
        Intent intentionPartieAI = new Intent(this, APartieAI.class);
        Log.d("debug_AI", "AMenuPrincipal/transitionPartieAI/intention");
        startActivity(intentionPartieAI);

    }



    private void fournirActionJoindreOuCreerPartieReseau() {

        ControleurAction.fournirAction(this,
                GCommande.JOINDRE_OU_CREER_PARTIE_RESEAU,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                            transitionAttendreAdversaire();
                    }
                });
    }


    private void fournirActionDeconnexion() {

        ControleurAction.fournirAction(this,
                GCommande.DECONNEXION,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        effectuerDeconnexion();

                    }
                });
    }


    private void fournirActionConnexion() {

        ControleurAction.fournirAction(this,
                GCommande.CONNEXION,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        effectuerConnexion();

                    }
                });
    }

    private void fournirActionDemarrerPartie() {

        ControleurAction.fournirAction(this,
                GCommande.DEMARRER_PARTIE,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        transitionPartie();

                    }
                });
    }

    private void fournirActionParametres() {
        ControleurAction.fournirAction(this,
                GCommande.OUVRIR_MENU_PARAMETRES,
                new ListenerFournisseur() {

                    @Override
                    public void executer(Object... args) {

                        transitionParametres();

                    }
                });
    }

    private void transitionAttendreAdversaire() {

        Intent intentionAttendreAdversaire = new Intent(this, AEnAttenteAdversaire.class);

        startActivity(intentionAttendreAdversaire);

    }


    private void transitionParametres() {

        Intent intentionParametres = new Intent(this, AParametres.class);
        startActivity(intentionParametres);

    }


    private void transitionPartie() {

        Intent intentionPartie = new Intent(this, APartie.class);
        startActivity(intentionPartie);

    }


    private void effectuerConnexion() {

        List<AuthUI.IdpConfig> fournisseursDeConnexion = new ArrayList<>();

        fournisseursDeConnexion.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        fournisseursDeConnexion.add(new AuthUI.IdpConfig.EmailBuilder().build());
        fournisseursDeConnexion.add(new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent intentionConnexion = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(fournisseursDeConnexion)
                .build();

        this.startActivityForResult(intentionConnexion, CODE_CONNEXION_FIREBASE);

    }


    public void effectuerDeconnexion() {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        // Rien

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_CONNEXION_FIREBASE) {

            //IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                // Connexion réussie

            } else {

                // connexion échouée
            }
        }
    }


}

package ca.cours5b5.guillaumegagnon.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;

public class AMenuPrincipal extends Activite implements Fournisseur {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        fournirActions();

    }

    private void fournirActions() {

        fournirActionOuvrirMenuParametres();
        fournirActionDemarrerPartie();

        //atelier 11
        fournirActionConnexion();
        fournirActionDeconnexion();
    }

    private void fournirActionOuvrirMenuParametres() {

        ControleurAction.fournirAction(this,
                GCommande.OUVRIR_MENU_PARAMETRES,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        transitionParametres();

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



    //atelier 11
    private void fournirActionConnexion() {
        ControleurAction.fournirAction(this,
                GCommande.CONNEXION,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {
                        connexion();
                    }
                });
    }

    private void fournirActionDeconnexion() {
        ControleurAction.fournirAction(this,
                GCommande.DECONNEXION,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {
                        deconnexion();
                    }
                });
    }



    private void transitionParametres(){

        Intent intentionParametres = new Intent(this, AParametres.class);
        startActivity(intentionParametres);

    }

    private void transitionPartie(){

        Intent intentionParametres = new Intent(this, APartie.class);
        startActivity(intentionParametres);

    }


    //atelier 11

    private void connexion(){
        List<AuthUI.IdpConfig> fournisseursDeConnexion = new ArrayList<>();

        //different login
        //google
        fournisseursDeConnexion.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        //email
        fournisseursDeConnexion.add(new AuthUI.IdpConfig.EmailBuilder().build());
        //phone
        fournisseursDeConnexion.add(new AuthUI.IdpConfig.PhoneBuilder().build());


        Intent intentionConnexion = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(fournisseursDeConnexion).build();


        this.startActivityForResult(intentionConnexion, GConstantes.CODE_CONNEXION);
    }

    private void deconnexion(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //deconnecté
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GConstantes.CODE_CONNEXION) {
            if (resultCode == RESULT_OK) {
                //ca marche
            } else {
                //ou pas
            }
        }
    }



}

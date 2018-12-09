package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.ListenerFournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;
import ca.cours5b5.guillaumegagnon.global.GCouleur;

public abstract class Vue extends ConstraintLayout implements Fournisseur {
    public Vue(Context context) {
        super(context);
    }

    public Vue(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Vue(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ControleurAction.fournirAction(this,
                GCommande.AFFICHER_MESSAGE_GAGNANT,
                new ListenerFournisseur() {
                    @Override
                    public void executer(Object... args) {

                        GCouleur couleur = (GCouleur) args[0];

                        String message = getMessageAuGagnant(couleur);

                        Action actionApresMessage = (Action) args[1];

                        afficherMessagePuisExecuterAction(message, actionApresMessage);

                    }
                });
    }

    private void afficherMessagePuisExecuterAction(String message, final Action actionApresMessage) {

        Snackbar fenetreMessage = Snackbar.make(this, message, GConstantes.DELAIS_MESSAGE_AVEC_ACTION);

        fenetreMessage.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {

                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    actionApresMessage.executerDesQuePossible();
                }

            }

        });

        fenetreMessage.show();

    }


    private void afficherMessage(String message) {

        Snackbar fenetreMessage = Snackbar.make(this, message, Snackbar.LENGTH_SHORT);
        fenetreMessage.show();

    }

    public String getMessageAuGagnant(GCouleur couleur) {

        String message = getResources().getString(R.string.message_au_gagnant);

        String nomCouleur = couleur.name();

        int idNomTraduit = getResources().getIdentifier(nomCouleur, "string", getContext().getPackageName());

        String nomTraduit = getResources().getString(idNomTraduit);

        message = message.replace("@@", nomTraduit);

        return message;

    }


}

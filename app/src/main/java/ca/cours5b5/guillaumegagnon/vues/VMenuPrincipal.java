package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.global.GCommande;

import static ca.cours5b5.guillaumegagnon.global.GCommande.JOINDRE_OU_CREER_PARTIE_RESEAU;


public class VMenuPrincipal extends Vue {

    private Button boutonParametres;
    private Action actionParametres;

    private Button boutonPartie;
    private Action actionPartie;


    //atelier 11
    private Button boutonConnection;
    private Action actionConnection;

    private Button boutonDeconnection;
    private Action actionDeconnection;


    private Button boutonOnline;
    private Action actionOnline;


    public VMenuPrincipal(Context context) {
        super(context);
    }

    public VMenuPrincipal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VMenuPrincipal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

        recupererControles();

        demanderActions();

        installerListeners();

    }


    private void recupererControles() {

        boutonParametres = findViewById(R.id.bouton_parametres);
        boutonPartie = findViewById(R.id.bouton_partie);

        boutonConnection = findViewById(R.id.bouton_connection);
        boutonDeconnection = findViewById(R.id.bouton_deconnection);

        boutonOnline = findViewById(R.id.button_online);

    }

    private void demanderActions() {

        actionParametres = ControleurAction.demanderAction(GCommande.OUVRIR_MENU_PARAMETRES);
        actionPartie = ControleurAction.demanderAction(GCommande.DEMARRER_PARTIE);


        //atelier 11
        actionConnection =  ControleurAction.demanderAction(GCommande.CONNEXION);
        actionDeconnection =  ControleurAction.demanderAction(GCommande.DECONNEXION);


        actionOnline = ControleurAction.demanderAction(JOINDRE_OU_CREER_PARTIE_RESEAU);

    }


    private void installerListeners() {

        installerListenerParametres();
        installerListenerPartie();


        //atelier 11
        installerListenerConnexion();
        installerListenerDeconnexion();

        installerListenerOnline();

    }

    private void installerListenerOnline() {
        boutonOnline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionOnline.executerDesQuePossible();
            }
        });
    }

    private void installerListenerPartie() {
        boutonPartie.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionPartie.executerDesQuePossible();
            }
        });

    }

    private void installerListenerParametres() {

        boutonParametres.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionParametres.executerDesQuePossible();
            }
        });

    }

    private void installerListenerConnexion() {

        boutonConnection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionConnection.executerDesQuePossible();
            }
        });
    }
    private void installerListenerDeconnexion() {
        boutonDeconnection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionDeconnection.executerDesQuePossible();
            }
        });
    }




}

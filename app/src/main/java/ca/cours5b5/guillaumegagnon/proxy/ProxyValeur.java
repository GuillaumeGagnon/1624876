package ca.cours5b5.guillaumegagnon.proxy;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;

public class ProxyValeur extends Proxy {

    private ValueEventListener valueEventListener;

    private Action actionNouvelleValeur;

    private boolean valeurEcrite = false;

    public ProxyValeur(String cheminServeur) {
        super(cheminServeur);
    }

    public void setActionNouvelleValeur(GCommande commande){

        actionNouvelleValeur = ControleurAction.demanderAction(commande);

    }

    @Override
    public void connecterAuServeur(){
        super.connecterAuServeur();

        creerListener();

        noeudServeur.addValueEventListener(valueEventListener);

    }


    public void deconnecterDuServeur(){

        noeudServeur.removeEventListener(valueEventListener);

        noeudServeur = null;

        valueEventListener = null;

    }


    @Override
    public void detruireValeurs() {

        if(valeurEcrite){

            noeudServeur.removeValue();
        }
    }


    private void creerListener(){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Object valeur = dataSnapshot.getValue();

                actionNouvelleValeur.setArguments(valeur);
                actionNouvelleValeur.executerDesQuePossible();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


}

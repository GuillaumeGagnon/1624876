package ca.cours5b5.guillaumegagnon.proxy;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import ca.cours5b5.guillaumegagnon.controleurs.Action;
import ca.cours5b5.guillaumegagnon.controleurs.ControleurAction;
import ca.cours5b5.guillaumegagnon.controleurs.interfaces.Fournisseur;
import ca.cours5b5.guillaumegagnon.global.GCommande;
import ca.cours5b5.guillaumegagnon.global.GConstantes;

public class ProxyListe extends Proxy implements Fournisseur {

    private ChildEventListener childEventListener;

    private Query requete;

    private Action actionNouvelItem;
    private Action actionItemDetruit;

    private List<DatabaseReference> noeudsAjoutes;

    public ProxyListe(String cheminServeur) {
        super(cheminServeur);

        noeudsAjoutes = new ArrayList<>();

    }


    public void setActionNouvelItem(GCommande commande){

        this.actionNouvelItem = ControleurAction.demanderAction(commande);

    }


    public void setActionItemDetruit(GCommande commande){

        this.actionItemDetruit = ControleurAction.demanderAction(commande);

    }


    public void ajouterValeur(Object valeur) {

        DatabaseReference noeudAjoute = noeudServeur.push();

        noeudAjoute.setValue(valeur);

        noeudsAjoutes.add(noeudAjoute);

    }


    @Override
    public void connecterAuServeur(){
        super.connecterAuServeur();

        creerListener();

        requete = getRequete();

        requete.addChildEventListener(childEventListener);

    }


    private void creerListener(){

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Object valeur = dataSnapshot.getValue();

                if(actionNouvelItem != null){

                    actionNouvelItem.setArguments(valeur);

                    actionNouvelItem.executerDesQuePossible();

                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Object valeur = dataSnapshot.getValue();

                if(actionItemDetruit != null){

                    actionItemDetruit.setArguments(valeur);

                    actionItemDetruit.executerDesQuePossible();

                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


    protected Query getRequete(){

        return noeudServeur.orderByKey().limitToFirst(GConstantes.NOMBRE_DE_VALEURS_A_CHARGER_DU_SERVEUR_PAR_DEFAUT);

    }


    @Override
    public void deconnecterDuServeur() {

        noeudServeur.removeEventListener(childEventListener);

        noeudsAjoutes.clear();

        super.deconnecterDuServeur();

    }


    @Override
    public void detruireValeurs() {

        for(DatabaseReference noeud : noeudsAjoutes){
            noeud.removeValue();
        }
    }


}

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
    private List<DatabaseReference> databaseReferenceList;



    public ProxyListe(String cheminServeur) {
        super(cheminServeur);
        databaseReferenceList = new ArrayList<>();
    }

    public void setActionNouvelItem(GCommande commande){
        actionNouvelItem = ControleurAction.demanderAction(commande);
    }



    public void ajouterValeur( Object valeur) {
        DatabaseReference noeud = super.databaseReference.push();
        noeud.setValue(valeur);
        databaseReferenceList.add(noeud);
    }



    @Override
    public void connecterAuServeur(){
        super.connecterAuServeur();

        creerListener();
        requete = getRequete();
        requete.addChildEventListener(childEventListener);
    }



    @Override
    public void detruireValeurs( ) {
        for (DatabaseReference data: databaseReferenceList) {
            data.removeValue();
        }
    }

    private void creerListener(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("help", "onChildAdded");
                if(actionNouvelItem != null){
                    actionNouvelItem.setArguments(dataSnapshot.getValue());
                    actionNouvelItem.executerDesQuePossible();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

    }

    protected Query getRequete(){
        return super.databaseReference.orderByKey().limitToFirst(GConstantes.NOMBRE_DE_VALEURS_A_CHARGER_DU_SERVEUR_PAR_DEFAUT);
    }

    @Override
    public void deconnecterDuServeur() {
        super.deconnecterDuServeur();

        requete.removeEventListener(childEventListener);
    }



}

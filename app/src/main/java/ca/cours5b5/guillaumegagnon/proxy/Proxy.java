package ca.cours5b5.guillaumegagnon.proxy;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Proxy {
    private String cheminServeur;
    protected DatabaseReference databaseReference;
    public Proxy(String cheminServeur){ this.cheminServeur = cheminServeur; }

    public void connecterAuServeur(){
        databaseReference = FirebaseDatabase.getInstance().getReference(cheminServeur);
        Log.d("help","databaseReference: " + databaseReference);
    }


    public void deconnecterDuServeur(){
        databaseReference = null;
        detruireValeurs();
    }


    public abstract void detruireValeurs();
}

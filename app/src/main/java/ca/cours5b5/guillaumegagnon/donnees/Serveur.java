package ca.cours5b5.guillaumegagnon.donnees;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import ca.cours5b5.guillaumegagnon.exceptions.ErreurModele;
import ca.cours5b5.guillaumegagnon.serialisation.Jsonification;

public class Serveur extends SourceDeDonnees {
    
    /*
     * Serveur est un singleton
     */
    
    private Serveur(){}
    
    private static final Serveur instance = new Serveur();

    public static Serveur getInstance() {
        return instance;
    }
            
    
    
    @Override
    public void chargerModele(final String cheminSauvegarde, final ListenerChargement listenerChargement) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(cheminSauvegarde);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> objetJson = (Map<String, Object>) snapshot.getValue();
                    listenerChargement.reagirSucces(objetJson);
                }else{
                    listenerChargement.reagirErreur(new ErreurModele("Clé introuvable"));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void sauvegarderModele(String cheminSauvegarde, Map <String, Object> objetJson) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(cheminSauvegarde);

        databaseReference.setValue(objetJson);
    }

    @Override
    public void detruireSauvegarde(String cheminSauvegarde) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(cheminSauvegarde);

        databaseReference.removeValue();
    }
}

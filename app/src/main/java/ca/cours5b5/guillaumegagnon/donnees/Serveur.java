package ca.cours5b5.guillaumegagnon.donnees;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

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
    public Map<String, Object> chargerModele(String cheminSauvegarde) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(cheminSauvegarde);
        return Jsonification.aPartirChaineJson(databaseReference.toString());
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

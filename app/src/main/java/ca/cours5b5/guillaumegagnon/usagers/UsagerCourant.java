package ca.cours5b5.guillaumegagnon.usagers;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UsagerCourant {
    public static boolean siUsagerConnecte(){

        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public static String getId(){
        String retour = null;

        if(siUsagerConnecte()){
            retour = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        } else { retour = "0"; }

        return retour;
    }
}

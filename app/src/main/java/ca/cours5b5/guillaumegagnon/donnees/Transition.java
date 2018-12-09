package ca.cours5b5.guillaumegagnon.donnees;

import android.os.Bundle;
import android.widget.TextView;

public class Transition extends SauvegardeTemporaire {

    public Transition(Bundle bundle) {
        super(bundle);
    }

    public Transition(){
        this.bundle = new Bundle();
    }

    public Bundle getBundle() {
        return bundle;
    }

}

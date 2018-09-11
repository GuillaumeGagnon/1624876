package cours5b5.guillaumegagnon.activites;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class Activite extends AppCompatActivity {
    static {
        Log.d("Atelier04", Activite.class.getSimpleName() + "::static");
    }
}

package cours5b5.guillaumegagnon.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cours5b5.guillaumegagnon.R;

public class AParametres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        Log.d("@",this.getResources().getString(R.string.messageLog));
        Log.d("@@",this.getResources().getString(R.string.messageOrientationLog));

    /*
    NE FONCTIONNE PAS CAR LA RESSOURCE EXISTE SEULEMENT EN PORTRAIT

    if(this.getResources().getBoolean(R.bool.portrait)){
        Log.d("@@@", "PORTRAIT");
    } else{
        Log.d("@@@", "LANDSCAPE");
    }


    */


    }
}

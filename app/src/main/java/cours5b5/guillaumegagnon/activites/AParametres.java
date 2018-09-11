package cours5b5.guillaumegagnon.activites;

import android.os.Bundle;
import android.util.Log;
import cours5b5.guillaumegagnon.R;

public class AParametres extends Activite {
    static{
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::static");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onCreate");

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

    @Override
    protected void onResume() {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onPause");
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onDestroy");
        super.onDestroy();
    }


}

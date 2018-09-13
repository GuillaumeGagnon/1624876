package cours5b5.guillaumegagnon.activites;

import android.os.Bundle;
import android.util.Log;

import java.util.Map;

import cours5b5.guillaumegagnon.R;
import cours5b5.guillaumegagnon.modeles.MParametres;
import cours5b5.guillaumegagnon.modeles.Modele;
import cours5b5.guillaumegagnon.serialisation.Jsonification;

public class AParametres extends Activite {
    static{
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::static");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        if(savedInstanceState != null){

//            String json = savedInstanceState.getString("testKey");
//
//            Map<String, Object> objetJson = Jsonification.enObjetJson(json);
//
//            MParametres.instance.aPartirObjetJson(objetJson);
        }

        Log.d("@",this.getResources().getString(R.string.messageLog));
        Log.d("@@",this.getResources().getString(R.string.messageOrientationLog));



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

    protected  void restaurerParametre (Bundle savedInstanceState){

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        Log.v("Atelier04", AParametres.class.getSimpleName() + "::onSaveInstanceState");
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onSaveInstanceState");
        super.onSaveInstanceState(outState);

//        Map<String, Object> objetJson = MParametres.instance.enObjetJson();
//
//        String json = Jsonification.enChaine(objetJson);
//
//        outState.putString("testKey", json);
    }

    protected  void  sauvegarderParametres (Bundle outState){

    }

    @Override
    protected void onDestroy() {
        Log.d("Atelier04", AParametres.class.getSimpleName() + "::onDestroy");
        super.onDestroy();
    }


}

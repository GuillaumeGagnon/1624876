package ca.cours5b5.guillaumegagnon.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ca.cours5b5.guillaumegagnon.R;


public abstract class Activite extends AppCompatActivity {

    static{
        Log.d("Atelier04", Activite.class.getSimpleName() + "::static");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onCreate");

        affichageAtelier02();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onDestroy");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onSaveInstanceState");
    }


    private void affichageAtelier02(){

        String message = this.getResources().getString(R.string.bonjour);

        Log.d("Atelier01", message);

        String ajoutOrientation = " (";

        if(getResources().getBoolean(R.bool.si_portrait)){

            ajoutOrientation += this.getResources().getString(R.string.portrait);

        }else{

            ajoutOrientation += this.getResources().getString(R.string.paysage);

        }

        message += ajoutOrientation + ")";

        Log.d("Atelier02", message);
    }
}

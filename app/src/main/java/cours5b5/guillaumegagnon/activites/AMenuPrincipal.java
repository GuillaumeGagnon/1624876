package cours5b5.guillaumegagnon.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import cours5b5.guillaumegagnon.R;

public class AMenuPrincipal extends Activite {
    static{
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::static");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenu_principal);

       Button menu_button_parametre = this.findViewById(R.id.menu_button_parametre);
       menu_button_parametre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent lancer_activite_paramatre = new Intent(AMenuPrincipal.this, AParametres.class);
               AMenuPrincipal.this.startActivities(new Intent[]{lancer_activite_paramatre});

               /*Autre facon de faire*/
//               Intent[] lancer_activite_paramatre = new Intent[]{new Intent(AMenuPrincipal.this, AParametres.class)};
//               AMenuPrincipal.this.startActivities(lancer_activite_paramatre);
           }
       });

//        test.setText("pwlgwprlgprwlgp");
    }

    @Override
    protected void onResume() {
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::onPause");
        super.onPause();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::onDestroy");
        super.onDestroy();
    }
}

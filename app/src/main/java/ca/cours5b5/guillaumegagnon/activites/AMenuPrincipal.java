package ca.cours5b5.guillaumegagnon.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ca.cours5b5.guillaumegagnon.R;

public class AMenuPrincipal extends Activite {

    static{
        Log.d("Atelier04", AMenuPrincipal.class.getSimpleName() + "::static");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        // FIXME: c'est temporaire, ça va dans une action (MVC)
        Button bouton = this.findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionParametres();
            }
        });

        Button boutonJouer = this.findViewById(R.id.button2);
        boutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //transitionJouer();
                Intent intentionPartie = new Intent(getApplicationContext(), APartie.class);
                startActivity(intentionPartie);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void transitionParametres(){
        Intent intentionParametres = new Intent(this, AParametres.class);
        startActivity(intentionParametres);
    }

    //old
    /*private void transitionJouer(){
        Intent intentionJouer = new Intent(this, APartie.class);
        startActivity(intentionJouer);
    }*/

}

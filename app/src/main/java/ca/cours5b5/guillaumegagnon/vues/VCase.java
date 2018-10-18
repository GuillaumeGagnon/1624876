package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ca.cours5b5.guillaumegagnon.global.GCouleur;

public class VCase extends AppCompatButton{
    public VCase(Context context) {
        super(context);
    }

    public VCase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VCase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VCase(Context context, int rangee, int colonne){
        super(context);

        this.setText(rangee + "," + colonne);
    }

    public void afficherJeton(GCouleur jeton){
        if(jeton.name().equals("ROUGE")){
            setBackgroundColor(Color.red(255));
        } else{
            setBackgroundColor(Color.rgb(255, 255, 0));
        }
    }

}

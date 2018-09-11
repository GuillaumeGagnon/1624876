package cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import cours5b5.guillaumegagnon.R;
import cours5b5.guillaumegagnon.global.GConstantes;

public class VParametres extends Vue {
    static{
        Log.d("Atelier04", VParametres.class.getSimpleName() + "::static");
    }


    public VParametres(Context context) {
        super(context);
    }

    public VParametres(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VParametres(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        Log.d("Atelier04", VParametres.class.getSimpleName() + "::onFinishInflate");
        super.onFinishInflate();

//        Log.d("@@@","pslpslpslpslpsl");


        Spinner spinnerHauteur = this.findViewById(R.id.spinnerHauteur);
        Spinner spinnerLargeur = this.findViewById(R.id.spinnerLargeur);
        Spinner spinnerGagner = this.findViewById(R.id.spinnerGagner);

        /*SPINNER HAUTEUR*/
        ArrayAdapter<Integer> adapterHauteur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);

        for (int i = GConstantes.hMin; i <= GConstantes.hMax; i++){
            adapterHauteur.add(i);
        }
        spinnerHauteur.setAdapter(adapterHauteur);
        spinnerHauteur.setSelection(adapterHauteur.getPosition(GConstantes.hDefault));


        /*SPINNER LARGEUR*/
        ArrayAdapter<Integer> adapterLargeur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);

        for (int i = GConstantes.lMin; i <= GConstantes.lMax; i++){
            adapterLargeur.add(i);
        }
        spinnerLargeur.setAdapter(adapterLargeur);
        spinnerLargeur.setSelection(adapterLargeur.getPosition(GConstantes.lDefault));


        /*SPINNER GAGNER*/
        ArrayAdapter<Integer> adapterGagner = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);

        for (int i = GConstantes.gagnerMin; i <= GConstantes.gagnerMax; i++){
            adapterGagner.add(i);
        }
        spinnerGagner.setAdapter(adapterGagner);
        spinnerGagner.setSelection(adapterGagner.getPosition(GConstantes.gagnerDefault));
        //spinnerGagner.setGravity(CENTER_HORIZONTAL);





    }

    /*private ArrayAdapter<Integer> initiate_adapter() {
        ArrayAdapter<Integer> adapterHauteur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
        for (int i = GConstantes.hMin; i <= GConstantes.hMax; i++){
            adapterHauteur.add(i);
        }
        return null;
    }*/
}

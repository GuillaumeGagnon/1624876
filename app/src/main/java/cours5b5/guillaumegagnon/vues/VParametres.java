package cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import cours5b5.guillaumegagnon.R;
import cours5b5.guillaumegagnon.global.GConstantes;

public class VParametres extends ConstraintLayout {
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
        super.onFinishInflate();

        Log.d("@@@","pslpslpslpslpsl");


        Spinner spinnerHauteur = this.findViewById(R.id.spinnerHauteur);
        Spinner spinnerLargeur = this.findViewById(R.id.spinnerLargeur);
        Spinner spinnerGagner = this.findViewById(R.id.spinnerGagner);

        ArrayAdapter<Integer> adapterHauteur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
        //spinnerHauteur.setAdapter(adapterHauteur);

        ArrayAdapter<Integer> adapterLargeur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
        spinnerHauteur.setAdapter(adapterLargeur);

        ArrayAdapter<Integer> adapterGagner = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
        spinnerHauteur.setAdapter(adapterGagner);

        for (int i = GConstantes.hMin; i <= GConstantes.hMax; i++){
            adapterHauteur.add(i);
        }
        spinnerHauteur.setAdapter(adapterHauteur);
        spinnerHauteur.setSelection(adapterHauteur.getPosition(GConstantes.hDefault));



    }
}

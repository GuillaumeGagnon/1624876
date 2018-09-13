package cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import cours5b5.guillaumegagnon.R;
import cours5b5.guillaumegagnon.global.GConstantes;
import cours5b5.guillaumegagnon.modeles.MParametres;

public class VParametres extends Vue {

    Spinner spinnerHauteur = this.findViewById(R.id.spinnerHauteur);
    Spinner spinnerLargeur = this.findViewById(R.id.spinnerLargeur);
    Spinner spinnerGagner = this.findViewById(R.id.spinnerGagner);

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
        initialiserSpinners();
//        Log.d("@@@","pslpslpslpslpsl");


//        Spinner spinnerHauteur = this.findViewById(R.id.spinnerHauteur);
//        Spinner spinnerLargeur = this.findViewById(R.id.spinnerLargeur);
//        Spinner spinnerGagner = this.findViewById(R.id.spinnerGagner);
//
//
//
//        /*SPINNER HAUTEUR*/
//        ArrayAdapter<Integer> adapterHauteur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
//
//        for (int i = GConstantes.hMin; i <= GConstantes.hMax; i++){
//            adapterHauteur.add(i);
//        }
//        spinnerHauteur.setAdapter(adapterHauteur);
//        spinnerHauteur.setSelection(adapterHauteur.getPosition(GConstantes.hDefault));
//
//
//        /*SPINNER LARGEUR*/
//        ArrayAdapter<Integer> adapterLargeur = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
//
//        for (int i = GConstantes.lMin; i <= GConstantes.lMax; i++){
//            adapterLargeur.add(i);
//        }
//        spinnerLargeur.setAdapter(adapterLargeur);
//        spinnerLargeur.setSelection(adapterLargeur.getPosition(GConstantes.lDefault));
//
//
//        /*SPINNER GAGNER*/
//        ArrayAdapter<Integer> adapterGagner = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
//
//        for (int i = GConstantes.gagnerMin; i <= GConstantes.gagnerMax; i++){
//            adapterGagner.add(i);
//        }
//        spinnerGagner.setAdapter(adapterGagner);
//        spinnerGagner.setSelection(adapterGagner.getPosition(GConstantes.gagnerDefault));
//        //spinnerGagner.setGravity(CENTER_HORIZONTAL);
    }

    private void initialiserSpinners() {
        this.spinnerHauteur = this.findViewById(R.id.spinnerHauteur);
        this.spinnerLargeur = this.findViewById(R.id.spinnerLargeur);
        this.spinnerGagner = this.findViewById(R.id.spinnerGagner);

        ajouterAdapter(this.spinnerHauteur);
        ajouterAdapter(this.spinnerLargeur);
        ajouterAdapter(this.spinnerGagner);

        ajouterListenerEvenement();


        updateSpinner(this.spinnerHauteur, MParametres.instance.getChoixHauteur(), MParametres.instance.getHauteur().intValue());
        updateSpinner(this.spinnerLargeur, MParametres.instance.getChoixLargeur(), MParametres.instance.getLargeur().intValue());
        updateSpinner(this.spinnerGagner, MParametres.instance.getChoixPourGagner(), MParametres.instance.getPourGagner().intValue());

    }

    private void ajouterListenerEvenement() {
        /*Hauteur*/
        this.spinnerHauteur.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MParametres.instance.setHauteur(((Integer) adapterView.getAdapter().getItem(i)).intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Largeur*/
        this.spinnerLargeur.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MParametres.instance.setLargeur(((Integer) adapterView.getAdapter().getItem(i)).intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*PourGagner*/
        this.spinnerGagner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MParametres.instance.setPourGagner(((Integer) adapterView.getAdapter().getItem(i)).intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ajouterAdapter(Spinner spinner) {
        spinner.setAdapter(new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item));
    }

    private void updateSpinner(Spinner spinner, List<Integer> choix, int selection) {
        ArrayAdapter<Integer> adapter = (ArrayAdapter) spinner.getAdapter();
        adapter.clear();
        for (int i = 0; i < choix.size(); i++) {
            int leChoix = ((Integer) choix.get(i)).intValue();
            adapter.add(Integer.valueOf(leChoix));
            if (leChoix == selection) {
                spinner.setSelection(i);
            }
        }
    }


}

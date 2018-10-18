package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import ca.cours5b5.guillaumegagnon.R;
import ca.cours5b5.guillaumegagnon.modeles.MParametres;
import ca.cours5b5.guillaumegagnon.modeles.MParametresPartie;


public class VParametres extends Vue {

    static{

        Log.d("Atelier04", VParametres.class.getSimpleName() + "::static");

    }

    private MParametresPartie mParametresPartie;
    private Spinner spinnerHauteur;
    private Spinner spinnerLargeur;
    private Spinner spinnerPourGagner;

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

        mParametresPartie = MParametres.instance.getParametresPartie();

        initialiser();
        afficherLesChoix();
    }

    private void initialiser(){
        spinnerHauteur = this.findViewById(R.id.spinner_hauteur);
        spinnerLargeur = this.findViewById(R.id.spinner_largeur);
        spinnerPourGagner = this.findViewById(R.id.spinner_pour_gagner);

        initialiserSpinner(spinnerHauteur);
        initialiserSpinner(spinnerLargeur);
        initialiserSpinner(spinnerPourGagner);

        installerListeners();
    }

    private void initialiserSpinner(Spinner spinner){
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void installerListeners() {
        installerListenerHauteur();
        installerListenerLargeur();
        installerListenerPourGagner();
    }

    private void installerListenerHauteur(){
        spinnerHauteur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int leChoix = (int) parent.getAdapter().getItem(position);

                mParametresPartie.setHauteur(leChoix);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void installerListenerLargeur(){
        spinnerLargeur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int leChoix = (int) parent.getAdapter().getItem(position);

                mParametresPartie.setLargeur(leChoix);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void installerListenerPourGagner(){
        spinnerPourGagner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int leChoix = (int) parent.getAdapter().getItem(position);

                mParametresPartie.setPourGagner(leChoix);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void afficherLesChoix(){
        afficherChoixHauteur();
        afficherChoixLargeur();
        afficherChoixPourGagner();
    }

    private void afficherChoixHauteur(){
        mettreAJourSpinner(spinnerHauteur,
                MParametres.instance.getChoixHauteur(),
                mParametresPartie.getHauteur());
    }

    private void afficherChoixLargeur(){
        mettreAJourSpinner(spinnerLargeur,
                MParametres.instance.getChoixLargeur(),
                mParametresPartie.getLargeur());
    }

    private void afficherChoixPourGagner(){
        mettreAJourSpinner(spinnerPourGagner,
                MParametres.instance.getChoixPourGagner(),
                mParametresPartie.getPourGagner());
    }

    private void mettreAJourSpinner(Spinner spinner, List<Integer> choix, int selectionCourante){
        ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) spinner.getAdapter();
        adapter.clear();

        for(int i=0; i < choix.size(); i++){
            int leChoix = choix.get(i);
            adapter.add(leChoix);

            if(leChoix == selectionCourante){
                spinner.setSelection(i);
            }
        }
    }
}

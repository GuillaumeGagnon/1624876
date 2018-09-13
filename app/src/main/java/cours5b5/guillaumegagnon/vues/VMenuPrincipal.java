package cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class VMenuPrincipal extends Vue {
    static{
        Log.v("Atelier04", VMenuPrincipal.class.getSimpleName() + "::static");
        Log.d("Atelier04", VMenuPrincipal.class.getSimpleName() + "::static");
    }


    public VMenuPrincipal(Context context) {
        super(context);
    }

    public VMenuPrincipal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VMenuPrincipal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        Log.d("Atelier04", VMenuPrincipal.class.getSimpleName() + "::onFinishInflate");
        super.onFinishInflate();

//        Button test = (Button) this.findViewById(R.id.menu_button_parametre);
//        test.setText("959595959595");



    }
}

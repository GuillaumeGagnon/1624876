package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;


public class VMenuPrincipal extends Vue {

    static{
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
}

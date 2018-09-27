package ca.cours5b5.guillaumegagnon.vues;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

public abstract class Vue extends ConstraintLayout {
    public Vue(Context context) {
        super(context);
    }

    public Vue(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Vue(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.d("Atelier04", this.getClass().getSimpleName() + "::" +  "onFinishInflate");
    }
}

package professional.wellness.health.com.employeeapp.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class RegularButton extends Button {

    public RegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "gotham-rounded-medium.ttf");
        setTypeface(tf);
    }
}

package professional.wellness.health.com.employeeapp.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class RegularCheckBox  extends CheckBox
{
    public RegularCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularCheckBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "gotham-rounded-medium.ttf");
        setTypeface(tf);
    }

}

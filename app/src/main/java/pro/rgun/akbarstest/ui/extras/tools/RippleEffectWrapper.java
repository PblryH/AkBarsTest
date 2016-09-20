package pro.rgun.akbarstest.ui.extras.tools;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;

/**
 * Created by rgun on 25.06.16.
 */
public class RippleEffectWrapper {

    public ViewGroup addWrapper(int layoutRes, ViewGroup viewGroup){
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.on(inflater.inflate(layoutRes, viewGroup, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(0xFF585858)
                .rippleHover(true)
                .create();
        return materialRippleLayout;
    }
}

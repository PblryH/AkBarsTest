package pro.rgun.akbarstest.ui.screen.note_detail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import jp.wasabeef.richeditor.RichEditor;
import timber.log.Timber;

/**
 * Created by railkamalov on 09.09.16.
 */
public class MyRichEditor extends RichEditor {

    private HideKeyboardListener mListener;

    public MyRichEditor(Context context) {
        super(context);
    }

    public MyRichEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Timber.d("onKeyPreIme");
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mListener != null) {
                mListener.onKeyboardHide();
            }
            return true;  // So it is not propagated.
        }
        return super.dispatchKeyEvent(event);
    }

    public void setHideKeyboardListener(HideKeyboardListener listener){
        mListener = listener;
    }

    public interface HideKeyboardListener{

        void onKeyboardHide();

    }
}

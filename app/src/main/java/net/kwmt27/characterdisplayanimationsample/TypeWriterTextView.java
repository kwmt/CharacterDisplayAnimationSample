package net.kwmt27.characterdisplayanimationsample;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * タイプライターのように一文字ずつ出現するTextView
 */
public class TypeWriterTextView extends TextView {
    // 参考
    // Android character by character display text animation
    // http://stackoverflow.com/a/6700718/2520998

    private static final String TAG = TypeWriterTextView.class.getSimpleName();

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500;


    public TypeWriterTextView(Context context) {
        super(context);
    }

    public TypeWriterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    /**
     * 文字の出現する間隔を設定する。
     * @param millis
     */
    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

    /**
     * アニメーションをスタートする。
     */
    public void startAnimation() {
        mText = getText();
        if (mText == null || mText == "") {
            Log.w(TAG, "text is not set.");
        }

        start();
    }

    /**
     * テキストを設定してアニメーションをスタートする。
     * @param text
     */
    public void startAnimationText(CharSequence text) {
        mText = text;
        start();
    }



    private void start() {
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

}

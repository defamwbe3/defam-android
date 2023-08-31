package Medium.DeFam.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import java.lang.reflect.Field;
import java.util.Arrays;

import Medium.DeFam.app.R;

/**
 * Created by Allen on 2017/3/16.
 */

public class GradientTextView extends AppCompatTextView {
    private static final int HORIZENTAL = 0;
    private static final int VERTICAL = 1;

    private int[] mGradientColor;
    private int mStrokeWidth;
    private int mStrokeColor = Color.BLACK;
    private LinearGradient mGradient;
    private boolean gradientChanged;
    private int mTextColor;
    private TextPaint mPaint;
    private int mGradientOrientation;


    public GradientTextView(Context context) {
        super(context);
        init(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = getPaint();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
            mStrokeColor = a.getColor(R.styleable.GradientTextView_strokeColor, Color.BLACK);
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.GradientTextView_strokeWidth, 0);
            mGradientOrientation = a.getInt(R.styleable.GradientTextView_gradientOrientation, HORIZENTAL);

            setStrokeColor(mStrokeColor);
            setStrokeWidth(mStrokeWidth);
            setGradientOrientation(mGradientOrientation);
            a.recycle();
        }
    }

    public void setGradientOrientation(int orientation) {
        if (mGradientOrientation != orientation) {
            mGradientOrientation = orientation;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setGradientColor(int[] gradientColor) {
        if (!Arrays.equals(gradientColor, mGradientColor)) {
            mGradientColor = gradientColor;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setStrokeColor(int color) {
        if (mStrokeColor != color) {
            mStrokeColor = color;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mStrokeWidth > 0) {
            /**
             * 绘制描边
             */
//            mTextColor = getCurrentTextColor(); //保存文本的颜色
//            mPaint.setStrokeWidth(mStrokeWidth); //设置描边宽度
//            mPaint.setFakeBoldText(true); //设置粗体
//            mPaint.setShadowLayer(mStrokeWidth, 0, 0, 0);
//            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//            setColor(mStrokeColor); // 设置描边颜色
//            mPaint.setShader(null);  //清空shader
//            super.onDraw(canvas);
            /**
             * 绘制文本
             */
            if (gradientChanged) { //如果有渐变颜色，则设置LinearGradient
                if (mGradientColor != null) {
                    mGradient = getGradient();
                }
                gradientChanged = false;
            }
            if (mGradient != null) { //设置渐变Shader
                mPaint.setShader(mGradient);
                mPaint.setColor(Color.WHITE);
            } else {
                setColor(mTextColor);
            }

            mPaint.setStrokeWidth(0);
//            mPaint.setFakeBoldText(false);
            mPaint.setShadowLayer(0, 0, 0, 0);
            super.onDraw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
        invalidate();
    }

    private LinearGradient getGradient() {
        LinearGradient gradient;
        if (mGradientOrientation == HORIZENTAL) {
            gradient = new LinearGradient(0, 0, getWidth(), 0, mGradientColor, null, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(0, 0, 0, getHeight(), mGradientColor, null, Shader.TileMode.CLAMP);
        }
        return gradient;
    }

    private void setColor(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPaint.setColor(color);
    }
}

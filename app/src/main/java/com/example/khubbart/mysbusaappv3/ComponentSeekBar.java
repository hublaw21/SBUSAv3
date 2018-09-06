package com.example.khubbart.mysbusaappv3;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.SeekBar;

public class ComponentSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    private TextPaint mTextPaint;
    private boolean isTextVisible;
    private int multiplier = 1;
    private String progressText;
    public int tempInt;
    public Double tempDouble;
    public String tempString;
    public float tempFloat;
    public float thumbX;
    public float thumbY;
    public float padLeft;
    public float padRight;
    public float textWidth;
    public float radius;
    // This means there is no formatting
    private String formatter = "1";
    AnimatedVectorDrawableCompat seekBarThumb;
    AnimatedVectorDrawableCompat seekBarThumbBackwards;
    VectorDrawableCompat seekBarThumbNotAnimated;

    public ComponentSeekBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        isTextVisible = false;

        Log.i("SeekerBar 333", formatter);

        mTextPaint = new TextPaint();
        tempInt = ContextCompat.getColor(context, R.color.black);
        mTextPaint.setColor(tempInt);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textTiny));
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        final SeekBar thisSeekbar = this;

        /*
        //Use this stuff for animation
        seekBarThumbNotAnimated = VectorDrawableCompat.create(getResources(), R.drawable.seekerbar_thumb_vector, null);

        seekBarThumb = AnimatedVectorDrawableCompat.create(context, R.drawable.seekerbar_thumb_animation);
        seekBarThumb.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (thisSeekbar.getThumb() == seekBarThumb) {
                    isTextVisible = true;
                    Log.i("CATs", "AnimationForwards:TRUE9");
                }
            }
        });
        seekBarThumbBackwards = AnimatedVectorDrawableCompat.create(context, R.drawable.seekerbar_thumb_animation_backwards);
        seekBarThumbBackwards.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationStart(Drawable drawable) {
                super.onAnimationStart(drawable);
            }
            @Override
            public void onAnimationEnd (Drawable drawable) {
                super.onAnimationEnd(drawable);
            }
        });
        */

        /*
        // Use this stuff to control actions on moving from here-globally
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //here you can change progress if you want (at your own risk :) )
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float normalizedProgress = (float) getProgress()/getMax();
        tempDouble = (Double)(normalizedProgress*100.0);
        tempDouble = Math.round(tempDouble)/10.0;
        progressText = Double.toString(tempDouble);
        Rect bounds = new Rect();
        tempInt = getResources().getColor(R.color.authui_colorPrimary);
        mTextPaint.setColor(tempInt);
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), bounds); // Length of string
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        int width = this.getWidth();
        int height = getHeight();
        radius = (float) height/2;
        padLeft = getPaddingLeft();
        padRight = getPaddingRight();
        textWidth = mTextPaint.measureText(progressText);
        canvas.save();
        canvas.translate(padLeft, 0);
        //canvas.drawCircle(bounds.centerX(),bounds.centerY(),radius,mTextPaint);
        thumbX = (width-padRight-padLeft)*normalizedProgress;
        thumbY = this.getHeight()/2;
        //tempString = String.valueOf(width) + " " + String.valueOf(normalizedProgress) + " "+ String.valueOf(tempDouble) + " " +String.valueOf(tempFloat);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textSmall));
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
        /*
        if (isTextVisible) {
            canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
        }
        */
    }
}
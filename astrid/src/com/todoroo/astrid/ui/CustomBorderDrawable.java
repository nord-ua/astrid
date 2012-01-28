package com.todoroo.astrid.ui;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class CustomBorderDrawable extends ShapeDrawable {
    private final Paint fillpaint, strokepaint;
    private final float strokeWidth;

    public CustomBorderDrawable(Shape s, int fill, int stroke, int strokeWidth) {
        super(s);
        fillpaint = new Paint(this.getPaint());
        fillpaint.setColor(fill);
        strokepaint = new Paint(fillpaint);
        strokepaint.setStyle(Paint.Style.STROKE);
        strokepaint.setStrokeWidth(strokeWidth);
        strokepaint.setColor(stroke);
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        shape.resize(canvas.getWidth(), canvas.getHeight());
        shape.draw(canvas, fillpaint);

        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()),
                new RectF(strokeWidth/2, strokeWidth/2, canvas.getWidth() - strokeWidth/2, canvas.getHeight() - strokeWidth/2),
                Matrix.ScaleToFit.FILL);
        canvas.concat(matrix);
        shape.draw(canvas, strokepaint);
    }

    public static StateListDrawable customButton(int tl, int tr, int br, int bl, int onColor, int offColor, int borderColor, int strokeWidth) {
        Shape shape = new RoundRectShape(new float[] { tl, tl, tr, tr, br, br, bl, bl}, null, null);
        ShapeDrawable sdOn = new CustomBorderDrawable(shape, onColor, borderColor, strokeWidth);
        ShapeDrawable sdOff = new CustomBorderDrawable(shape, offColor, borderColor, strokeWidth);

        StateListDrawable stld = new StateListDrawable();
        stld.addState(new int[] { android.R.attr.state_pressed }, sdOn);
        stld.addState(new int[] { android.R.attr.state_checked }, sdOn);
        stld.addState(new int[] { android.R.attr.state_enabled }, sdOff);
        return stld;
    }
}

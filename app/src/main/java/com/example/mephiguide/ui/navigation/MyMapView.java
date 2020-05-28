package com.example.mephiguide.ui.navigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.mephiguide.MainActivity;
import com.example.mephiguide.MyLog;
import com.example.mephiguide.R;

public class MyMapView extends View {

    float [] points = null;

    private Paint mPaint = new Paint();

    DisplayMetrics metrics;

    Bitmap image;

    Rect img, real;

    double multiplier;

    public MyMapView(Context context) {
        super(context);

        myInit(context);
    }

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        myInit(context);
    }

    public void myInit(Context context){
        metrics = ((MainActivity)context).getDisplayMetrics();
        image = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        img = new Rect(0,0,700,577);

        multiplier = metrics.widthPixels / 720.0;
        int realHeight = 577 * metrics.widthPixels / 700;
        real = new Rect(0, 0, metrics.widthPixels,realHeight);
    }

    public void drawWay(float [] pts){
        this.points = pts;

        for (int i = 0; i < points.length; i++){
            points[i] *= multiplier;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        if (image != null)
            canvas.drawBitmap(image,null, real,null);
        else
            MyLog.w("Image not found!");
        mPaint.setStrokeWidth(7);

        if (points != null){
            canvas.drawLines(points, mPaint);
            canvas.drawLines(points, 2, points.length-2, mPaint);
        }
    }
}

package com.example.shaderapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BitmapShaderView extends View {
    private Paint paint;//画笔工具
    private Bitmap bgBitmap,bitmap;//隐藏图像以及原图像
    private int mX=-1,mY=-1;//捕获手指的位置坐标
    public BitmapShaderView(Context context) {
        super(context);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paint=new Paint();//初始化画笔工具
        this.bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.background);//获取原图像
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.bgBitmap==null){
            this.bgBitmap=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);//创建一个新空白位图
            Canvas canvasBg=new Canvas(this.bgBitmap);
            //然后对背景图拉升后，画到上面的位图中
            canvasBg.drawBitmap(this.bitmap,null,new Rect(0,0,getWidth(),getHeight()),this.paint);
        }
        if(this.mX!=-1 && this.mY!=-1){
            //填充模式为上面讲的第二种，就是复制粘贴的填充模式，但这里不会执行
            //因为我们上面强制设置了图片的大小为整个屏幕，所以屏幕没有空白区域
            this.paint.setShader(new BitmapShader(this.bgBitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT));
            canvas.drawCircle(this.mX,this.mY,200,this.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://手指按下事件
                this.mX=(int)event.getX();
                this.mY=(int)event.getY();
                postInvalidate();//重绘
                return true;
            case MotionEvent.ACTION_MOVE://手指移动事件
                this.mX=(int)event.getX();
                this.mY=(int)event.getY();
                break;
            case MotionEvent.ACTION_UP://手指抬起
            case MotionEvent.ACTION_CANCEL://手指离开事件
                this.mX=-1;
                this.mY=-1;
                break;

        }
        postInvalidate();//重绘
        return super.onTouchEvent(event);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

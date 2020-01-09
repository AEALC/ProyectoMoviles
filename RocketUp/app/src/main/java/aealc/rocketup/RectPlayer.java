package aealc.rocketup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation moveRight;
    private Animation moveLeft;
    private AnimationManager animManager;

    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRRENT_CONTEXT.getResources(), R.drawable.img);
        Bitmap move1 = bf.decodeResource(Constants.CURRRENT_CONTEXT.getResources(), R.drawable.img);
        Bitmap move2 = bf.decodeResource(Constants.CURRRENT_CONTEXT.getResources(), R.drawable.img);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        moveRight = new Animation(new Bitmap[]{move1,move2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        move1 = Bitmap.createBitmap(move1, 0, 0, move1.getWidth(), move1.getHeight(), m, false);
        move2 = Bitmap.createBitmap(move2, 0, 0, move2.getWidth(), move2.getHeight(), m, false);

        moveLeft = new Animation(new Bitmap[]{move1,move2}, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, moveRight, moveLeft});
    }

    @Override
    public void draw(Canvas canvas){
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update (){
        animManager.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;
        if (rectangle.left - oldLeft > 5)
            state = 1;
        else if (rectangle.left - oldLeft < -5 )
            state = 2;

        animManager.playAnim(state);
        animManager.update();
    }
}

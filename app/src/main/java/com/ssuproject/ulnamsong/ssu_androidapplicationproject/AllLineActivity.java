package com.ssuproject.ulnamsong.ssu_androidapplicationproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Ulnamsong on 2015-11-30.
 */
public class AllLineActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_line_map);
    }

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // 드래그시 좌표 저장
    int posX1 = 0, posX2 = 0, posY1 = 0, posY2 = 0;

    // 핀치시 두좌표간의 거리 저장
    float oldDist = 1f;
    float newDist = 1f;

    public boolean onTouchEvent(MotionEvent event) {
        int act = event.getAction();
        String strMsg = "";
        switch (act & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            //첫번째 손가락 터치(드래그 용도)
                posX1 = (int) event.getX();
                posY1 = (int) event.getY();
                Log.d("zoom", "mode=DRAG");
                mode = DRAG;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) { // 드래그 중
                    posX2 = (int) event.getX();
                    posY2 = (int) event.getY();
                    if (Math.abs(posX2 - posX1) > 20 || Math.abs(posY2 - posY1) > 20) {
                        posX1 = posX2;
                        posY1 = posY2;
                        strMsg = "drag";
                        Toast toast = Toast.makeText(this, strMsg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (mode == ZOOM) {
                // 핀치 중
                    newDist = spacing(event);
                    Log.d("zoom", "newDist=" + newDist);
                    Log.d("zoom", "oldDist=" + oldDist);
                    if (newDist - oldDist > 20) { // zoom in
                        oldDist = newDist;
                        strMsg = "zoom in";
                        Toast toast = Toast.makeText(this, strMsg, Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (oldDist - newDist > 20) { // zoom out
                        oldDist = newDist;
                        strMsg = "zoom out";
                        Toast toast = Toast.makeText(this, strMsg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            // 첫번째 손가락을 떼었을 경우
            case MotionEvent.ACTION_POINTER_UP:
            // 두번째 손가락을 떼었을 경우
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //두번째 손가락 터치(손가락 2개를 인식하였기 때문에 핀치 줌으로 판별)
                mode = ZOOM;
                newDist = spacing(event);
                oldDist = spacing(event);
                Log.d("zoom", "newDist=" + newDist);
                Log.d("zoom", "oldDist=" + oldDist);
                Log.d("zoom", "mode=ZOOM");
                break;
            case MotionEvent.ACTION_CANCEL:
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }
}

package com.dejota.petadote.arrastar;


import android.view.MotionEvent;
import android.view.View;

public class ArrastarDetecta implements View.OnTouchListener {

    /*
    class Class implements ArrastarDirecao {}
    ArrastarDetecta arrasto = new ArrastarDetecta(this);
    RelativeLayout layout = (RelativeLayout)this.findViewById(R.id.lowestLayout);
    layout.setOnTouchListener(arrasto);
     */

    private ArrastarDirecao context;
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    public ArrastarDetecta(ArrastarDirecao context){
        this.context = context;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();

                float deltaX = downX - upX;
                if(Math.abs(deltaX) > MIN_DISTANCE){
                    if(deltaX > 0) {context.arrastouPraDireita(); return true; }
                    if(deltaX < 0) { context.arrastouPraEsquerda(); return true; }
                } else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}

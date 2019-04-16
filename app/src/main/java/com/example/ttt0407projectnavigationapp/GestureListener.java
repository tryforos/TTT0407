package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.ttt0407projectnavigationapp.fragments.WatchListFragment;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private final String TAG = "pubHoller";
    private Context context;

    public GestureListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

        int SWIPE_MIN_DISTANCE = 120;
        int SWIPE_THRESHOLD_VELOCITY = 200;


        Log.d(TAG, "onFling: " + event1.toString() + event2.toString());

        if(event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Toast.makeText(context, "Left Swipe", Toast.LENGTH_SHORT).show();
        }  else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Toast.makeText(context, "Right Swipe", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        //WatchListFragment wlf = new WatchListFragment();
        //wlf.navigationAddCompany();
        Toast.makeText(context, "onDown triggered", Toast.LENGTH_SHORT).show();
        return false;
    }

/*

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(context, "onSingleTapUp triggered", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(context, "onLongPress triggered", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(context, "onDoubleTap triggered", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }



    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        return false;
    }
*/
}

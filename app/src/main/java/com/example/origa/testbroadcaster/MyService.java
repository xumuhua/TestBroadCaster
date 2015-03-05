package com.example.origa.testbroadcaster;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Origa on 2015/3/4.
 */
public class MyService extends Service {

    private boolean quit;
    private int count;
    private MyBinder mBinder = new MyBinder();

    public Intent intent;
    private DataChangeListener mListener;

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyService","onBind service");
        quit = false;
        count = 0;
        new Thread(){
            @Override
            public void run() {
                Log.e("MyService.Thread","run");
                while (!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){

                    }
                    count ++;
                    sedMessage(1);
                    mListener.onDataChanged(count);

                }
            }
        }.start();
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mListener = null;
    }

    private void sedMessage(int ID) {
        Log.e("MyService","sedMessage");
        intent = new Intent("android.intent.action.MYRECEIVER");
        intent.setAction("Service BroadCast");
        intent.putExtra("eventID",ID);
        sendBroadcast(intent);
    }

    public void setListener(DataChangeListener listener){
        mListener = listener;
    }

    public class MyBinder extends Binder {

        public int getCount() {
            return count;
        }

        public MyService getService(){
            return MyService.this;
        }
    }
}

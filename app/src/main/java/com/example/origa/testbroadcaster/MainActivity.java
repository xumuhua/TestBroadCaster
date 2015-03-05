package com.example.origa.testbroadcaster;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;


public class MainActivity extends ActionBarActivity implements PlaceholderFragment.FragmentCallBack{

    private int mdata;
    private PlaceholderFragment mFragment;
    public MyService.MyBinder mBinder;

    public MyHandler mHandler;
    public MyReceiver mReceiver;

    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyService.MyBinder)service;
            mBinder.getService().setListener(new DataChangeListener() {
                @Override
                public void onDataChanged(int data) {
                    mdata = data;
                    Bundle b = new Bundle();
                    b.putInt("eventID",1);
                    Message msg = new Message();
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ServiceConnected","Disconnected");
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragment = new PlaceholderFragment();
        mFragment.setOnListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }

        mReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MYRECEIVER");
        registerReceiver(mReceiver, intentFilter);

        mHandler = new MyHandler();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataByBroadCast(int ID){
        mdata = ID;
        mFragment.setData(mdata);
        mFragment.freshView();
    }

    @Override
    public void onStartService() {
        final Intent intent = new Intent(MainActivity.this,MyService.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("MyReceiver","getMessage");
            int mID;
            mID = intent.getIntExtra("eventID",0);
            if(mID == 1){
            }
        }

    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle b = msg.getData();
            int eventID = b.getInt("eventID");
            if (eventID == 1){
                mdata = mBinder.getCount();
                mFragment.setData(mdata);
                mFragment.freshView();
            }

        }
    }

}

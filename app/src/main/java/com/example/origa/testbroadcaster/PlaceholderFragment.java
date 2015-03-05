package com.example.origa.testbroadcaster;

/**
 * Created by Origa on 2015/3/5.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */

public class PlaceholderFragment extends Fragment {

    private TextView mTextView;
    private Button mButton;
    private int mdata;
    private FragmentCallBack mCallBack;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Fragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView)rootView.findViewById(R.id.tv_main_text);
        mButton = (Button)rootView.findViewById(R.id.bt_main_text);
        mdata = 0;
        mTextView.setText("Service At " + mdata);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onStartService();
            }
        });
        return rootView;
    }

    public void setOnListener (FragmentCallBack callBack){
        mCallBack = callBack;
    }
    public void setData(int data){
        mdata = data;
    }

    public void freshView(){
        mTextView.setText("Service At "+mdata);
        mTextView.invalidate();
    }

    public interface FragmentCallBack{
        public void  onStartService();
    }
}



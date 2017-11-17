package com.samsung.muhammad.polisi.activity;

/**
 * Created by ihsan on 25/09/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by wira on 1/1/16.
 */
public class HelpActivity extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("Help Layout");
        setContentView(textView);
    }
}
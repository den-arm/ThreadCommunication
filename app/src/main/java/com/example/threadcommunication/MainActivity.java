package com.example.threadcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Thread t;
    private Handler h;
    private String str;
    private Button btn,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        t = new Thread(new Task());
        t.start();

    }

    class Task implements Runnable {
        public void run(){
            Looper.prepare();
            h=new Handler(){
                public void handleMessage(Message msg){
                    str = Thread.currentThread().getName() +
                            ", value=" + String.valueOf(msg.arg1);
                }
            };
            Looper.loop();
        }
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                Message m = h.obtainMessage(1,33,1,null);
                h.sendMessage(m);
                break;
            case R.id.button2:
                setTitle(str);
                break;
            case R.id.button3:
                h.getLooper().quit();
                finish();
                break;
        }
    }
}
package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.aiyakeji.aiyapusher.MqttTraceHandler;
import com.aiyakeji.aiyapusher.manager.Connection;
import com.aiyakeji.mytest.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2017/11/15 0015.
 * mqtt测试
 */

public class MqttActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatEditText et_service;
    private AppCompatEditText et_clientid;
    private AppCompatEditText et_topic;
    private AppCompatButton btn_connect;
    private AppCompatTextView tv_state;
    private AppCompatTextView tv_rec_message;
    private AppCompatEditText et_message;
    private AppCompatButton btn_send;

    private boolean newConnection = true;
    private Connection connection;
    private String topic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);
        findViews();

    }


    private void findViews() {
        et_service = (AppCompatEditText) findViewById(R.id.mqtt_et_service);
        et_clientid = (AppCompatEditText) findViewById(R.id.mqtt_et_clientid);
        et_topic = (AppCompatEditText) findViewById(R.id.mqtt_et_topic);
        btn_connect = (AppCompatButton) findViewById(R.id.mqtt_btn_connect);
        tv_state = (AppCompatTextView) findViewById(R.id.mqtt_tv_state);
        tv_rec_message = (AppCompatTextView) findViewById(R.id.mqtt_tv_rec_message);
        et_message = (AppCompatEditText) findViewById(R.id.mqtt_et_message);
        btn_send = (AppCompatButton) findViewById(R.id.mqtt_btn_send);

        btn_connect.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mqtt_btn_connect://连接
                saveConnection();
                break;
            case R.id.mqtt_btn_send://发送
                sendMessage();
                break;
        }
    }


    private void saveConnection() {
        String service = et_service.getText().toString().trim();
        if (TextUtils.isEmpty(service)) {
            Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String clientid = et_clientid.getText().toString().trim();
        if (TextUtils.isEmpty(clientid)) {
            Toast.makeText(this, "ClientID不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        topic = et_topic.getText().toString().trim();
        if (TextUtils.isEmpty(topic)) {
            Toast.makeText(this, "Topic不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newConnection) {
            connection = Connection.createConnection(clientid, service, 1883, this, false);
            connection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTING);

            connection.getClient().setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });


            connection.getClient().setTraceCallback(new MqttTraceHandler() {
                @Override
                public void traceDebug(String tag, String message) {

                }

                @Override
                public void traceError(String tag, String message) {

                }

                @Override
                public void traceException(String tag, String message, Exception e) {

                }
            });


            try {
                //连接
                connection.getClient().connect(new MqttConnectOptions(), null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken iMqttToken) {
                        newConnection = false;
                        connection.changeConnectionStatus(Connection.ConnectionStatus.CONNECTED);
                        //这里做订阅
                        try {
                            connection.getClient().subscribe(topic, 0);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }


        } else {

        }
    }


    private void sendMessage() {
        try {
            connection.getClient().publish(topic, et_message.getText().toString().trim().getBytes(), 0, false, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    Toast.makeText(MqttActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    Toast.makeText(MqttActivity.this, "发送失败！", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

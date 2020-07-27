package com.aiyakeji.mytest.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aiyakeji.aiyapusher.MqttTraceHandler;
import com.aiyakeji.aiyapusher.manager.PublishManager;
import com.aiyakeji.mytest.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;

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
    private AppCompatButton btn_jump;

    private boolean newConnection = true;
    private PublishManager mPublishManager;
    private String topic;

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();
    private static final int length = 8;

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
        btn_jump = (AppCompatButton) findViewById(R.id.mqtt_btn_jump);

        btn_connect.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_jump.setOnClickListener(this);
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
            case R.id.mqtt_btn_jump://跳转
                startActivity(new Intent(this, CircleProgressActivity.class));
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
            // Generate a new Client Handle
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(AB.charAt(random.nextInt(AB.length())));
            }
            String clientHandle = sb.toString() + '-' + service + '-' + clientid;

            mPublishManager = PublishManager.createConnection(clientHandle,clientid, service, 1883, this, false);
            mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.CONNECTING);

            mPublishManager.getClient().setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.DISCONNECTED);
                    Log.i("MqttActivity测试", "丢失连接");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    Log.i("MqttActivity测试", "收到消息，s:" + s + ",msg:" + mqttMessage.toString());
                    tv_rec_message.setText("topic:" + s + "msg.toString:" + mqttMessage.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    Log.i("MqttActivity测试", "发送成功");
                }
            });


            mPublishManager.getClient().setTraceCallback(new MqttTraceHandler() {
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


            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setConnectionTimeout(80);
            options.setKeepAliveInterval(200);
            try {
                //连接
                mPublishManager.getClient().connect(options, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken iMqttToken) {
                        Log.i("MqttActivity测试", "连接成功！");
                        tv_state.setText("连接成功！");
                        newConnection = false;
                        mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.CONNECTED);
                        //这里做订阅
                        try {
                            mPublishManager.getClient().subscribe(topic, 0);
                            Log.i("MqttActivity测试", "订阅");
                            tv_state.setText(tv_state.getText().toString() + "订阅成功！");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                        Log.i("MqttActivity测试", "连接失败！token:" + iMqttToken.toString() + "throwable:" + throwable.toString());
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }


        } else {
            try {
                if (mPublishManager.isConnected()) {
                    mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.DISCONNECTING);
                    mPublishManager.getClient().disconnect();
                }

                mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.CONNECTING);
                mPublishManager.getClient().setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        Log.i("MqttActivity测试", "丢失连接");
                    }

                    @Override
                    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                        Log.i("MqttActivity测试", "收到消息，s:" + s + ",msg:" + mqttMessage.toString());
                        tv_rec_message.setText("topic:" + s + "msg.toString:" + mqttMessage.toString());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        Log.i("MqttActivity测试", "发送成功");
                    }
                });


                mPublishManager.getClient().setTraceCallback(new MqttTraceHandler() {
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

                MqttConnectOptions options = new MqttConnectOptions();
                options.setCleanSession(true);
                options.setConnectionTimeout(80);
                options.setKeepAliveInterval(200);
                //连接
                mPublishManager.getClient().connect(options, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken iMqttToken) {
                        Log.i("MqttActivity测试", "连接成功！");
                        tv_state.setText("连接成功！");
                        newConnection = false;
                        mPublishManager.changeConnectionStatus(PublishManager.ConnectionStatus.CONNECTED);
                        //这里做订阅
                        try {
                            mPublishManager.getClient().subscribe(topic, 0);
                            Log.i("MqttActivity测试", "订阅");
                            tv_state.setText(tv_state.getText().toString() + "订阅成功！");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                        Log.i("MqttActivity测试", "连接失败！token:" + iMqttToken.toString() + "throwable:" + throwable.toString());
                    }
                });

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    private void sendMessage() {
        try {
            mPublishManager.getClient().publish(topic, et_message.getText().toString().trim().getBytes(), 0, false, null, new IMqttActionListener() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPublishManager) {
            mPublishManager.getClient().unregisterResources();
        }
    }
}

package com.aiyakeji.mytest.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.listener.IPermissionListenerWrap;
import com.aiyakeji.mytest.utils.Permission;
import com.aiyakeji.mytest.utils.PermissionHelper;

/**
 * Author：CWQ
 * Date：2019/4/4
 * Desc:权限申请封装测试
 */
public class PermissionRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissionrequest);

        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean granted = PermissionHelper.checkPermission(PermissionRequestActivity.this, Manifest.permission.CALL_PHONE);
                if (!granted) {
                    PermissionHelper.init(PermissionRequestActivity.this).requestEachPermissions(new String[]{Manifest.permission.CALL_PHONE}, new IPermissionListenerWrap.IEachPermissionListener() {
                        @Override
                        public void onAccepted(Permission permission) {
                            if (permission.isGranted()) {
                                Toast.makeText(PermissionRequestActivity.this, "成功获取权限！~", Toast.LENGTH_SHORT).show();
                            } else {
                                if (permission.isShouldShowRequestPermissionRationale()) {
                                    Toast.makeText(PermissionRequestActivity.this, "权限拒绝，可以再次申请~", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PermissionRequestActivity.this, "权限拒绝，不再询问~", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(PermissionRequestActivity.this, "已有权限~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

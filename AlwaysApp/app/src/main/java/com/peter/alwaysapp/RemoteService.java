package com.peter.alwaysapp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.peter.inter.ProcessSerivce;

/**
 * Created by peter on 17/2/5.
 */
public class RemoteService extends Service {

    private MyBinder binder;
    private MyConn conn;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        binder = new MyBinder();
        if (conn == null) {
            conn = new MyConn();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        //远程服务启动时 - 绑定本地服务
        RemoteService.this.bindService(
                new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
    }

    class MyBinder extends ProcessSerivce.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "RemoteService";
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("Info", "本地服务链接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //服务异常断开链接回调
            Log.i("Info", "本地服务被杀死-断开binding");
            //重启 本地服务
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
            //绑定 本地服务
            RemoteService.this.bindService(
                    new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
        }
    }
}

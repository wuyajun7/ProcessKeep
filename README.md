# ProcessKeep

/**
* 双进程守护 - 创建杀不死的APP
*
* 1、创建 两个service 一个在本进程、另一个设置为第二个进程（android:process="第二个进程Name"）
*  1.1、创建 AIDL 通过 Stub 相互绑定服务（bindService）
*  1.2、通过 ServiceConnection 进行服务绑定监听
*  1.3、如果 在两个服务中 监听到服务断开链接 则进行对应服务启动-双绑定
* 2、启动两个服务
*/
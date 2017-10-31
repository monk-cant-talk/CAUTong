package tong.cau.com.cautong.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import tong.cau.com.cautong.MainActivity;
import tong.cau.com.cautong.R;

public class AlarmService extends Service {
	NotificationManager Notifi_M;
	ServiceThread thread;
	Notification Notifi ;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		AlaermHandler handler = new AlaermHandler();
		thread = new ServiceThread(handler);
		thread.start();
		return START_STICKY;
	}

	//서비스가 종료될 때 할 작업

	public void onDestroy() {
		thread.stopForever();
		thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
	}

	private class AlaermHandler extends Handler {

		@Override
		public void handleMessage(android.os.Message msg) {
			boolean find = true;
			String title = "empty";
			String content = "empty";
			//// TODO: 2017-10-31 여기에 새로 올라온 사이트를 확인하는 코드를 넣는다.
			if (find) {
				pushAlarm(title, content);
			}
		}

		private void pushAlarm(String title, String content){
			Intent intent = new Intent(AlarmService.this, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notifi = new Notification.Builder(getApplicationContext())
					.setContentTitle(title)
					.setContentText(content)
					.setSmallIcon(R.drawable.c)
					.setTicker("알림!!!")
					.setContentIntent(pendingIntent)
					.build();

			//확인하면 자동으로 알림이 제거 되도록
			Notifi.flags = Notification.FLAG_AUTO_CANCEL;

			Notifi_M.notify(777, Notifi);
		}
	}

	private class ServiceThread extends Thread{
		AlaermHandler handler;
		boolean isRun = true;

		public ServiceThread(AlaermHandler handler){
			this.handler = handler;
		}

		public void stopForever(){
			synchronized (this) {
				this.isRun = false;
			}
		}

		public void run(){
			//반복적으로 수행할 작업을 한다.
			while(isRun){
				handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
				try{
					Thread.sleep(6000); //10분씩 쉰다. 600 -> 6초
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {

	static final String TAG = "UpdaterService";
	static final int DELAY = 30000;
	Boolean running = false;
	Twitter twitter;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreated");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		running = true;

		new Thread() {
			public void run() {

				while (running) {
					try {
						List<Status> timeline = twitter.getPublicTimeline();

						for (Status status : timeline) {
							Log.d(TAG, "Post:" + status.getUser() + ": "
									+ status.getText());
						}
						Thread.sleep(DELAY);
					} catch (TwitterException e) {
						Log.d(TAG, "Failed getting Timeline:");
						e.printStackTrace();
					} catch (InterruptedException e) {
						Log.d(TAG, "Interrupted: " + e);
					}

				}
			}
		}.start();
		Log.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
		Log.d(TAG, "onDestroy");
	}

}

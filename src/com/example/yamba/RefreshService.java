package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {
	static final String TAG = "RefreshService";
	Twitter twitter;

	public RefreshService() {
		super(TAG);

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			List<Status> timeline = twitter.getPublicTimeline();

			for (Status status : timeline) {
				Log.d(TAG, "Post:" + status.getUser() + ": " + status.getText());
			}
		} catch (TwitterException e) {
			Log.d(TAG, "Failed getting Timeline:");
			e.printStackTrace();
		}
		Log.d(TAG, "onHandleIntent");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreated");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

}

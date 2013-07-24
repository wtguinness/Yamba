package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {

	static final String TAG = "StatusActivity";
	Button buttonUpdate;
	EditText editStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Debug.startMethodTracing("Yamba.trace");

		setContentView(R.layout.status);

		buttonUpdate = (Button) findViewById(R.id.button_update);
		editStatus = (EditText) findViewById(R.id.edit_status);

		buttonUpdate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		Log.d(TAG, "onCreateOptionsMenu");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, UpdaterService.class);
		
		switch (item.getItemId()) {
		case R.id.item_start_service:
			Log.d(TAG, "Start Service");
			startService(intent);
			return true;
		case R.id.item_stop_service:
			Log.d(TAG, "Stop Service");
			stopService(intent);
			return true;
		case R.id.item_refresh:
			Log.d(TAG, "Refresh");
			startService(new Intent(this, RefreshService.class));
		default:
			return false;
		}

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, "OnClicked!");

		/*
		 * OAuthSignpostClient authClient = new
		 * OAuthSignpostClient(JTWITTER_OAUTH_KEY
		 * ,JTWITTER_OAUTH_SECRET,'callbackUrl'); java.net.URI jUrl =
		 * authClient.authorizeUrl(); Uri.Builder uriBuilder = new
		 * Uri.Builder(); uriBuilder.encodedPath(jUrl.toString());
		 * 
		 * Intent myIntent = new Intent(Intent.ACTION_VIEW,
		 * Uri.parse(jUrl.toString())); startActivity(myIntent);
		 */

		final String statusText = editStatus.getText().toString();

		new PostToTwitter().execute(statusText);

	}

	class PostToTwitter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(params[0]);
				return "Succeffully Posted: " + params[0];

			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "WTF", e);
				return "Failed! WTF!!";
			}

		}
		// BACK TO UI THREAD
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			editStatus.setText(null);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();

		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		// Debug.stopMethodTracing();
	}

}

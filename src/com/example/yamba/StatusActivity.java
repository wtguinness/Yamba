package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	public final String TAG = "StatusActivity";
	Button buttonUpdate;
	EditText editStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);

		buttonUpdate = (Button) findViewById(R.id.button_update);
		editStatus = (EditText) findViewById(R.id.edit_status);

		buttonUpdate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	
		class PostToTwitter extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {
				try {
					Twitter twitter = new Twitter("student", "password");
					twitter.setAPIRootUrl("http://yamba.marakana.com/api");
					twitter.setStatus(params[0]);
					return "Succeffully Poseted: " + params[0];
					
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "WTF", e);
					return "Failed! WTF!!";
				}
				
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
				
			}
			
		}




}

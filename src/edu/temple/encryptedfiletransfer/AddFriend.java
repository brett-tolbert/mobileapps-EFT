package edu.temple.encryptedfiletransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AddFriend extends Activity {
	
	TextView txtUserMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		txtUserMessage = (TextView) findViewById(R.id.txtVwAddFriendWelcome);
		
		Intent prevIntent = getIntent();

		String welcomeMessage = "Welcome " + prevIntent.getStringExtra("username") + "!";

		txtUserMessage.setText(welcomeMessage);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.main, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int endIndex = txtUserMessage.getText().toString().indexOf("!");

		switch (item.getItemId()) {
		case R.id.Logout:

			Intent logoutIntent = new Intent(AddFriend.this,
					MainActivity.class);
			// logoutIntent.putExtra("username",txtNewUserUsername.getText().toString());
			logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(logoutIntent);
			finish();
			showToast("You have logged out!");

			return true;
		case R.id.Home:
			Intent homeIntent = new Intent(AddFriend.this,
					HomeActivity.class);
			homeIntent.putExtra("username", txtUserMessage.getText().toString()
					.substring(8, endIndex));
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
			return true;
		case R.id.AddFriend:
			Intent addFriendIntent = new Intent(AddFriend.this,
					AddFriend.class);
			addFriendIntent.putExtra("username", txtUserMessage.getText()
					.toString().substring(8, endIndex));
			addFriendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			addFriendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(addFriendIntent);
			return true;
		case R.id.SendFile:
			Intent fileIntent = new Intent(AddFriend.this, SendFile.class);
			fileIntent.putExtra("username", txtUserMessage.getText().toString()
					.substring(8, endIndex));
			fileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			fileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(fileIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void showToast(String message) {

		Toast toast = Toast.makeText(getApplicationContext(), message,
				Toast.LENGTH_SHORT);

		toast.show();
	}
}

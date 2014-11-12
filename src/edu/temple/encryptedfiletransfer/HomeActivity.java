package edu.temple.encryptedfiletransfer;

import java.io.File;
/*import edu.temple.soundgram.RecordAudio;
/*import edu.temple.soundgram.R;
import edu.temple.soundgram.util.UploadSoundGramService;
import edu.temple.soundgram.R;*/
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//this is where the user selects their friend to send a file or accepts file send requests 
public class HomeActivity extends Activity {
	
	TextView txtUserMessage;
	TextView txtInstructions;
	Button btnAddFriend;
	Button btnViewLog;
	Button btnSendFile;
	EditText txtFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		txtUserMessage = (TextView) findViewById(R.id.txtVwWelcome);
		txtInstructions = (TextView) findViewById(R.id.txtVwHomeInstructions);
		btnAddFriend = (Button) findViewById(R.id.btnAddNewFriend);
		btnViewLog = (Button) findViewById(R.id.btnViewLog);
		btnSendFile = (Button) findViewById(R.id.SendFile);
		txtFileName = (EditText) findViewById(R.id.FileName);

		Intent loggedInIntent = getIntent();

		String welcomeMessage = "Welcome " + loggedInIntent.getStringExtra("username") + "!";

		txtUserMessage.setText(welcomeMessage);

		String instructions = "You can now use EFT to add a friend/file recipient, send files, and/or "
				+ "view a log of your previous transfers."
				+ " Click a button below or use the menu above to get started.";

		txtInstructions.setText(instructions);
		
		final File storageDirectory = new File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.encrypted_file_directory));

		if (!storageDirectory.exists()) 
		{
			storageDirectory.mkdir();
		}

		btnSendFile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try
				{
					String fileName = txtFileName.getText().toString();
					
					Thread t = new Thread(){
						 
						 @Override
						 public void run (){
							 uploadFile();
						 }
					 };
					
					 if (!fileName.equals(""))
					 {
						 File tempFile = new File(storageDirectory, fileName);

						 File[] fileArray = storageDirectory.listFiles();
						 File targetFile = null;

						 for(int i = 0; i < fileArray.length; i++)
						 {
							 if (tempFile.getAbsolutePath().equals(fileArray[i].getName().toString()))
							 {
								 targetFile = fileArray[i];
								 break;
							 }

							 else
							 {
								 continue;
							 }
						 }
				        	
						 if (targetFile != null)
						 {
							 t.start();
						 }
				        	
						 else
						 {
							 //display a message box saying that the file name entered does not exist in the directory
							 AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this).create();
							 dialog.setMessage("The file name that you have entered does not exist in the directory. Please reenter the file name.");
						 }
					 }
					 
					 else
					 {
						 //print a message box saying that you need to enter a file name
						 AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this).create();
						 dialog.setMessage("You have not entered a file name.");
					 }
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	} //This is the end of the onCreate method.  Might have to be placed further down

		public boolean onCreateOptionsMenu(Menu menu) {

			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.main, menu);

			return true;

		}

		public boolean onOptionsItemSelected(MenuItem item) {


			switch (item.getItemId()) {
			case R.id.Logout:

				Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
				//logoutIntent.putExtra("username",txtNewUserUsername.getText().toString());
				startActivity(logoutIntent);

				showToast("You have logged out!");

				return true;
			case R.id.Home:

				Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
				int endIndex = txtUserMessage.getText().toString().indexOf("!");
				homeIntent.putExtra("username", txtUserMessage.getText().toString().substring(8, endIndex));
				startActivity(homeIntent);

				return true;
			default:
				return super.onOptionsItemSelected(item);

			}
		}
		
		private void uploadFile(){
			
			Intent uploadFileIntent = new Intent(this, UploadSoundGramService.class);
			uploadFileIntent.putExtra(UploadSoundGramService.directory, Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));

			startService(uploadFileIntent);
			Toast.makeText(this, "Uploading File", Toast.LENGTH_SHORT).show();
		}

		public void showToast(String message) {

			Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);

			toast.show();

		}
	}
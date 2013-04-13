package com.appsvl;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class PictureActivity extends Activity {
	
	SharedPreferences sharedPref;
	Bitmap mImageBitmap;
	ImageView mImageView;
	
	public static final int TAKING_PIC = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
		return true;
	}
	

	public void takePicture(View view){
		takePic(TAKING_PIC);
	}
	public void takePic(int actionCode){
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePicture,actionCode);
		
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		if(requestCode == TAKING_PIC){
			handleSmallPhoto(data);
			
		}
	}
	private void handleSmallPhoto(Intent intent){
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
	}


	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	public void backPage(View view){
		Intent backPage = new Intent(this, MainForm.class);
		startActivity(backPage);
	}
	
	public void submitForm(View view){	//Submit button just prints out values submitted from form.
		sharedPref = MainForm.getPreferenceValues();
		TextView text = new TextView(this);
		text.setText(sharedPref.getString("Date", null) + sharedPref.getString("Impact", null) + sharedPref.getString("Contribution", null)+ sharedPref.getString("Hours", null)+ sharedPref.getString("Service", null));
		setContentView(text);		
	}
	
}

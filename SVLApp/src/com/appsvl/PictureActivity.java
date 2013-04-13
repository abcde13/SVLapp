package com.appsvl;

import java.util.List;

import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PictureActivity extends Activity {

	Bitmap mImageBitmap;
	ImageView mImageView;
	Button submitButton;
	
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
	@SuppressLint("ResourceAsColor")
	private void handleSmallPhoto(Intent intent){
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
		if(submitButton == null)
		{
			submitButton = new Button(this);
			submitButton.setText("Submit");
			submitButton.setTextSize(30.0f);
			submitButton.setBackgroundColor(Color.CYAN);
			LinearLayout x = (LinearLayout) findViewById(R.id.imglinearlayout);
			x.addView(submitButton);
		}
	}


	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	
	}
	
}

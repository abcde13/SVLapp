package com.appsvl;

import java.util.List;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PictureActivity extends Activity {

	Bitmap mImageBitmap;
	ImageView mImageView;
	LinearLayout imageLayout;
	
	public static final int TAKING_PIC = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		imageLayout = (LinearLayout)findViewById(R.id.imageLayout);
		if(mImageView!= null){
			imageLayout.addView(mImageView);	
		}
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
			resizeImage();
			mImageView = new ImageView(this);
			mImageView.setImageBitmap(mImageBitmap);
			imageLayout.addView(mImageView);	
			
		}
	}
	
	private void resizeImage(){
		int width = mImageBitmap.getWidth();
	    int height = mImageBitmap.getHeight();
	    float scaleWidth = ((float) width*3) / width;
	    float scaleHeight = ((float) height*3) / height;
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    // recreate the new Bitmap
	    mImageBitmap = Bitmap.createBitmap(mImageBitmap, 0, 0, width, height, matrix, false);
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
	
}

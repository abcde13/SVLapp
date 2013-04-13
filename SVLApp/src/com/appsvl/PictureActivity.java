package com.appsvl;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PictureActivity extends Activity {
	
	SharedPreferences sharedPref;
	Bitmap mImageBitmap;
	ImageView mImageView;
	LinearLayout imageLayout;
	Button submitButton;
	
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
		if(imageLayout.getChildCount() > 0)
		    imageLayout.removeAllViews();
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
			submitButton.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View view) {
			        submitForm(view);
			    }
			});
		}
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
		Intent i = new Intent(Intent.ACTION_SEND);
		String studentName = "Arjun";
		String studentID = "8140272";
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"lakshmipathyarjun6@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "STUDENT SERVICE HOUR APPROVAL REQUEST");
		i.putExtra(Intent.EXTRA_TEXT   , "A student has requested to update his or her service hour count.\n\n" +
				"Name: " + studentName + "\n" + "ID #: " + studentID);
		try {
		    startActivity(Intent.createChooser(i, "Send mail via"));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(PictureActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}

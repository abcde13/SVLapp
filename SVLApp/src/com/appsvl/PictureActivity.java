package com.appsvl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
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
	public static Bitmap mImageBitmap;
	ImageView mImageView;
	LinearLayout imageLayout;
	Button submitButton;
	TextView errorCase;
	File file;
	
	public static final int TAKING_PIC = 1;
	public static final int SENDING_EMAIL = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPref = MainForm.getPreferenceValues();
		setContentView(R.layout.activity_picture);
		imageLayout = (LinearLayout)findViewById(R.id.imageLayout);
		if(mImageBitmap != null){
			mImageView = new ImageView(this);
			imageLayout.addView(mImageView);
			mImageView.setImageBitmap(mImageBitmap);
			if(submitButton == null)
			{
				errorCase = new TextView(this);
				submitButton = new Button(this);
				submitButton.setText("Submit");
				submitButton.setTextSize(30.0f);
				submitButton.setBackgroundResource(R.color.light_orange);
				LinearLayout x = (LinearLayout) findViewById(R.id.imglinearlayout);
				x.addView(errorCase);
				x.addView(submitButton);
				submitButton.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View view) {
				        submitForm(view);
				    }
				});
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
		return true;
	}
	

	public void takePicture(View view) throws FileNotFoundException{
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
			createExternalStoragePrivateFile();
			
		}
		else if(requestCode == SENDING_EMAIL){
			Toast.makeText(PictureActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
			Intent nextPage = new Intent(this, MainForm.class);
			MainForm.setPreferenceValuesToNull();
			mImageBitmap = null;
			mImageView = null;
			ViewGroup layout = (ViewGroup) submitButton.getParent();
			if(null!=layout) //for safety only  as you are doing onClick
			  layout.removeView(submitButton);
			submitButton = null;
			startActivity(nextPage);
		}
	}
	
	void createExternalStoragePrivateFile() {
	    // Create a path where we will place our private file on external
	    // storage.
		String root = Environment.getExternalStorageDirectory().toString();
		String fname = "service.png";
	    file = new File (root, fname);
	    if (file.exists ()) file.delete (); 

	    try {
	    		FileOutputStream out = new FileOutputStream(file);
	        	mImageBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
	        	out.flush();
	        	out.close();

	    } catch (Exception e) {
	           e.printStackTrace();
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
			errorCase = new TextView(this);
			submitButton = new Button(this);
			submitButton.setText("Submit");
			submitButton.setTextSize(30.0f);
			submitButton.setBackgroundResource(R.color.light_orange);
			LinearLayout x = (LinearLayout) findViewById(R.id.imglinearlayout);
			x.addView(errorCase);
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
		String n;
		if(sharedPref != null){
			errorCase.setGravity(Gravity.CENTER);
			errorCase.setTextColor(Color.RED);
			errorCase.setTextSize(20);
			n = sharedPref.getString("Date", null);
			if(n.equals(""))
			{
				errorCase.setText(getString(R.string.error_empty_field));
				return;
			}
			n = sharedPref.getString("Organization", null);
			if(n.equals(""))
			{
				errorCase.setText(getString(R.string.error_empty_field));
				return;
			}
			n = sharedPref.getString("Contribution", null);
			if(n.equals(""))
			{
				errorCase.setText(getString(R.string.error_empty_field));
				return;
			}
			n = sharedPref.getString("Impact", null);
			if(n.equals(""))
			{
				errorCase.setText(getString(R.string.error_empty_field));
				return;
			}
			n = sharedPref.getString("Hours", null);
			if(n.equals(""))
			{
				errorCase.setText(getString(R.string.error_empty_field));
				return;
			}
		}
		else if(sharedPref == null){
				errorCase.setText(getString(R.string.error_empty_field));
				return;	
		}
		

		Intent i = new Intent(Intent.ACTION_SEND);
		String studentName = "Arjun Lakshmipathy";
		String studentID = "8140272";
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"lakshmipathyarjun6@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "STUDENT SERVICE HOUR APPROVAL REQUEST");
		i.putExtra(Intent.EXTRA_TEXT   , "A student has requested to update his or her service hour count.\n\n" +
				"Name: " + studentName + "\n" + "ID #: " + studentID);
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

		try {
		    startActivityForResult(Intent.createChooser(i, "Send mail via"), SENDING_EMAIL);
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(PictureActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}

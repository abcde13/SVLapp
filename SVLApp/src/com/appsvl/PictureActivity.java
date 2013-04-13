package com.appsvl;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class PictureActivity extends Activity {

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
	
	private void takePicture(View view){
		//takePic(TAKING_PIC)
	}

}

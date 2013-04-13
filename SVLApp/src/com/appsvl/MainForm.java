package com.appsvl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainForm extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_form);
		populateServiceTypeSpinner();
	}
	
	private void populateServiceTypeSpinner(){
		
		Spinner type =(Spinner)findViewById(R.id.service_type_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.service_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_form, menu);
		return true;
	}
	
	public void nextPage(View view){
		Intent nextPage = new Intent(this, PictureActivity.class);
		//EditText editText = (EditText) findViewById(R.id.edit_message);
		//String data = editText.getText().toString();
		//nextPage.putExtra();
		//alwkejf]]fuck
		startActivity(nextPage);
	}

}

package com.appsvl;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainForm extends Activity {

	public static SharedPreferences sharedPref = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_form);
		populateServiceTypeSpinner();
		EditText value = (EditText) findViewById(R.id.date);
		String n;
		Spinner service;
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.service_types, android.R.layout.simple_spinner_item);
		if(sharedPref != null){
			n = sharedPref.getString("Date", null);
			if(!n.equals(null))
				value.setText(n);
			value = (EditText) findViewById(R.id.organization);
			n = sharedPref.getString("Organization", null);
			if(!n.equals(null))
				value.setText(n);
			service = (Spinner) findViewById(R.id.service_type_spinner);
			populateServiceTypeSpinner();
			service.setSelection(adapter.getPosition(sharedPref.getString("Service", "0")));
			value = (EditText) findViewById(R.id.contribution_field);
			n = sharedPref.getString("Contribution", null);
			if(!n.equals(null))
				value.setText(n);
			value = (EditText) findViewById(R.id.impact_field);
			n = sharedPref.getString("Impact", null);
			if(!n.equals(null))
				value.setText(n);
			value = (EditText) findViewById(R.id.hours_field);
			n = sharedPref.getString("Hours", null);
			if(!n.equals(null))
				value.setText(n);
		}
		sharedPref = getPreferences(Context.MODE_PRIVATE);
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
	
	public void savePreferences(String key, String value){
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
		editor.commit();
	}
	
	public static SharedPreferences getPreferenceValues(){
		return(sharedPref);
	}
	
	public void nextPage(View view){
		EditText date = (EditText) findViewById(R.id.date);
		EditText organization = (EditText) findViewById(R.id.organization);
		EditText contribution = (EditText) findViewById(R.id.contribution_field);
		EditText impact = (EditText) findViewById(R.id.impact_field);
		EditText hours = (EditText) findViewById(R.id.hours_field);
		Spinner service_spinner = (Spinner) findViewById(R.id.service_type_spinner);
		String service = service_spinner.getSelectedItem().toString();
		Intent nextPage = new Intent(this, PictureActivity.class);
		savePreferences("Date", date.getText().toString());
		savePreferences("Organization", organization.getText().toString());
		savePreferences("Contribution", contribution.getText().toString());
		savePreferences("Impact", impact.getText().toString());
		savePreferences("Hours", hours.getText().toString());
		savePreferences("Service", service);
		startActivity(nextPage);
		//CHANGE
	}
}

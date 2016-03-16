/**
Root Verifier - Android App
Copyright (C) 2014 Madhav Kanbur

This file is a part of Root Verifier.

Root Verifier is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

Root Verifier is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Root Verifier. If not, see <http://www.gnu.org/licenses/>.*/

package com.abcdjdj.rootverifier;

import static com.abcdjdj.rootverifier.Utils.MiscFunctions.activity;
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.rateOnPS;
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.setDeviceName;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author - Madhav Kanbur (abcdjdj)
 * @version - V1.5
 */

public class MainActivity extends ActionBarActivity {

	static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		onStartUp();
	}

	public void onStartUp() {

		activity = this;// activity is a static field in the Utils.MiscFunctions
						// class

		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		setDeviceName();// Calling the function to display the current device
						// model on startup of the app.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.exit:
			finish();
			break;

		case R.id.rate:
			rateOnPS();
			break;

		case R.id.about:
			about_app();
			break;

		default:
			break;

		}
		return true;
	}

	public void Check(View v) {

		dialog = ProgressDialog.show(this, getString(R.string.verifying),
				getString(R.string.wait), false);
		dialog.setCanceledOnTouchOutside(false);
		new CheckRoot(new CheckBusyBox().t, new CheckSuApp().t);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (dialog != null) {
			dialog.dismiss();
		}
	}

	private void about_app() {
		StringBuilder msg = new StringBuilder(
				"Root Verifier is a free software: you can redistribute it and/or modify ")
				.append("it under the terms of the GNU General Public License as published by ")
				.append("the Free Software Foundation, either version 2 of the License, or ")
				.append("(at your option) any later version.\n\nGithub - https://github.com/abcdjdj/RootVerifier-APP\n\n")
				.append("Credits:-\n--> SArnab@XDA\n--> Androlover98@XDA\n--> android1999@XDA\n--> Abhinav2@XDA\n--> ZANKRUT.DOSHI@XDA");

		Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("About the app");
		alert.setMessage(msg);
		alert.setPositiveButton("OK", null);
		alert.show();
	}

}
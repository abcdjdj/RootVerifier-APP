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

package com.abcdjdj.rootverifier.Utils;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.abcdjdj.rootverifier.MainActivity;
import com.abcdjdj.rootverifier.R;

public class MiscFunctions {
	public static MainActivity activity;

	public synchronized static void setText(final TextView t, final CharSequence x) {
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				t.setText(x);
				t.invalidate();

			}
		};
		activity.runOnUiThread(r);
		try {
			Thread.sleep(800);
		} catch (Exception e) {
		}
	}

	public synchronized static void showToast(final CharSequence x) {
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Toast.makeText(activity, x, Toast.LENGTH_LONG).show();
			}
		};
		activity.runOnUiThread(r);
	}

	public static void setDeviceName() {
		TextView c = (TextView) activity.findViewById(R.id.devicemodel);

		StringBuilder x = new StringBuilder(activity.getString(R.string.dev_name));
		x.append(" ").append(android.os.Build.MANUFACTURER).append(" ")
				.append(android.os.Build.MODEL);

		c.setText(x);

	}

	public static void rateOnPS() {
		Intent intent = null;
		try {
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=com.abcdjdj.rootverifier"));
			activity.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			intent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.abcdjdj.rootverifier"));
			activity.startActivity(intent);
		} catch (Exception ex) {
			Toast.makeText(activity, "Unknown error occured", Toast.LENGTH_LONG)
					.show();

		}
	}
}

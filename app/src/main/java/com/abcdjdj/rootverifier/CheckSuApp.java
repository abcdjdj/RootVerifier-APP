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
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.setText;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

public class CheckSuApp implements Runnable {
	Thread t;

	CheckSuApp() {
		t = new Thread(this, "CheckSuApp");
		t.start();
	}

	@Override
	public void run() {
		su_app();
	}

	private static void su_app() {
		TextView su_app = (TextView) activity.findViewById(R.id.su_app);

		final String[] packages = { "eu.chainfire.supersu",
				"eu.chainfire.supersu.pro", "com.koushikdutta.superuser",
				"com.noshufou.android.su", "com.dianxinos.superuser", "com.kingouser.com",
				"com.mueskor.superuser.su" , "org.masteraxe.superuser", "com.yellowes.su" ,
				"com.kingroot.kinguser"};
		PackageManager pm = activity.getPackageManager();
		int i, l = packages.length;
		String superuser = null;

		for (i = 0; i < l; i++) {
			try {
				ApplicationInfo info = pm.getApplicationInfo(packages[i], 0);
				PackageInfo info2 = pm.getPackageInfo(packages[i], 0);
				superuser = pm.getApplicationLabel(info).toString() + " "
						+ info2.versionName;
				break;
			} catch (PackageManager.NameNotFoundException e) {
				continue;
			}
		}

		if (superuser != null) {
			setText(su_app, activity.getString(R.string.su_app) + " " + superuser);
		} else {
			su_alternative(su_app);
		}
	}

	private static void su_alternative(TextView su_app) {
		String line;
		try {
			Process p = Runtime.getRuntime().exec("su -v");// Superuser version
			InputStreamReader t = new InputStreamReader(p.getInputStream());
			BufferedReader in = new BufferedReader(t);
			line = in.readLine();

			char[] chars = line.toCharArray();
			boolean flag = false;// Check if su -v returns the package name
									// instead of just the version number
			for (char c : chars) {
				if (Character.isLetter(c)) {
					flag = true;
				}
			}
			if (!flag) {
				line = activity.getString(R.string.app_unknown);
                                setText(su_app, line);
			}
			else {
                               setText(su_app, activity.getString(R.string.su_app) + " " +  line);
                        }
		} catch (Exception e) {
			line =  activity.getString(R.string.app_unknown);
                        setText(su_app, line);
		}
	}

}

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
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.showToast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import android.widget.TextView;

public class CheckRoot implements Runnable {

	Thread t, check_su_app, check_busybox;

	CheckRoot(Thread t1, Thread t2) {
		t = new Thread(this, "CheckRoot");
		t.start();
		check_busybox = t1;
		check_su_app = t2;
	}

	@Override
	public void run() {
		checkRoot();
		try {
			check_busybox.join();
			check_su_app.join();
		} catch (InterruptedException e) {
		}
		MainActivity.dialog.dismiss();
		showToast(activity.getString(R.string.check_complete));

	}

	private static void checkRoot() {
		TextView root = (TextView) activity.findViewById(R.id.status);

		if (suAvailable()) { // Checks if su binary is available

			try {

				Process process = Runtime.getRuntime().exec("su");
				PrintWriter pw = new PrintWriter(process.getOutputStream(),
						true);

				// CREATING A DUMMY FILE in / called abc.txt
				pw.println("mount -o remount,rw /");
				pw.println("cd /");
				pw.println("echo \"ABC\" > abc.txt");
				pw.println("exit");
				pw.close();
				process.waitFor();

				if (checkFile()) { // Checks if the file has been successfully
									// created

					setText(root, activity.getString(R.string.rooted));

				} else {

					setText(root, activity.getString(R.string.permission_denied));
							
				}

				// DELETES THE DUMMY FILE IF PRESENT
				process = Runtime.getRuntime().exec("su");
				pw = new PrintWriter(process.getOutputStream());
				pw.println("cd /");
				pw.println("rm abc.txt");
				pw.println("mount -o ro,remount /");
				pw.println("exit");
				pw.close();
				process.waitFor();
				process.destroy();

			} catch (Exception e) {

				setText(root, activity.getString(R.string.permission_denied));
				
			}
		} else {

			setText(root, activity.getString(R.string.not_rooted));
		}

	}

	private static boolean suAvailable() {
		boolean flag;
		try {
			Process p = Runtime.getRuntime().exec("su");
			p.destroy();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	private static boolean checkFile() throws IOException {
		boolean flag = false;
		try {
			File x = new File("/abc.txt");
			flag = x.exists();

		} catch (SecurityException e) {
			showToast(activity.getString(R.string.alt_method));
			Process p = Runtime.getRuntime().exec("ls /");
			Scanner sc = new Scanner(p.getInputStream());
			String line = null;

			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.contains("abc.txt")) {
					flag = true;
					break;
				}
			}
			sc.close();

		}
		return flag;
	}

}

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

import java.util.Scanner;

import android.widget.TextView;

public class CheckBusyBox implements Runnable {
	Thread t;

	CheckBusyBox() {
		t = new Thread(this, "CheckBusyBox");
		t.start();
	}

	@Override
	public void run() {
		busybox();
	}

	private static void busybox() {
		TextView z = (TextView) activity.findViewById(R.id.busybox);
		char n[] = null;
		String line = null;

		try {

			Process p = Runtime.getRuntime().exec("busybox");
			Scanner in = new Scanner(p.getInputStream());

			busybox: while (in.hasNextLine()) {
				line = in.nextLine();
				n = line.toCharArray();

				for (char c : n) {

					if (Character.isDigit(c)) {
						break busybox;

					}
				}

			}

			in.close();
			setText(z, new StringBuilder(activity.getString(R.string.yes_busybox)).append(" ").append(line));

		} catch (Exception e) {
			setText(z, activity.getString(R.string.no_busybox));
		}
	}

}

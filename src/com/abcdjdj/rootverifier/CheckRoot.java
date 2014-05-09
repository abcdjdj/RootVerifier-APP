/**
Root Verifier - Android App
Copyright (C) 2013 Madhav Kanbur

This file is part of Root Verifier.

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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.TextView;

public class CheckRoot implements Runnable
{
	
	private static ProgressDialog dialog;
	Thread t,check_su_app,check_busybox;
	
	CheckRoot(Thread t1,Thread t2)
	{
		t = new Thread(this,"CheckRoot");
		t.start();
		check_busybox=t1;
		check_su_app=t2;
	}

	@Override
	public void run()
	{
		checkRoot();
		try
		{
			check_busybox.join();
			check_su_app.join();
		}
		catch(InterruptedException e)
		{}
		dialog.dismiss();
		showToast("Checking complete.");

	}

	public static void checkRoot()
	{
		TextView root = (TextView) activity.findViewById(R.id.status);

		if (suAvailable())// Checks if su binary is available
		{

			try
			{

				Process process = Runtime.getRuntime().exec("su");
				PrintWriter pw = new PrintWriter(process.getOutputStream(),true);

				// CREATING A DUMMY FILE in /system called abc.txt
				pw.println("mount -o remount rw /system/");
				pw.println("cd system");
				pw.println("echo \"ABC\" > abc.txt");
				pw.println("exit");
				pw.close();
				process.waitFor();

				if (checkFile())// Checks if the file has been successfully created
				{
					setText(root, "DEVICE IS ROOTED");

				} 
				else
				{

					setText(root,
							"ROOT PERMISSION NOT GRANTED OR SUPERUSER APP MISSING");

				}
			
				// DELETES THE DUMMY FILE IF PRESENT
				process = Runtime.getRuntime().exec("su");
				pw = new PrintWriter(process.getOutputStream());
				pw.println("cd system");
				pw.println("rm abc.txt");
				pw.println("exit");
				pw.close();
				process.waitFor();
				process.destroy();

			} 
			catch (Exception e)
			{

				setText(root,
						"ROOT PERMISSION NOT GRANTED OR SUPERUSER APP MISSING");
			}
		} 
		else
		{

			setText(root, "NOT ROOTED");
		}

	}

	public static boolean suAvailable()
	{
		boolean flag;
		try
		{
			Process p = Runtime.getRuntime().exec("su");
			p.destroy();
			flag = true;
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	
	public static boolean checkFile() throws IOException
	{
		boolean flag = false;
		try
		{
			File x = new File("/system/abc.txt");
			flag = x.exists();

		} 
		catch (SecurityException e)
		{
			showToast("Checking by alternate method..");
			Process p = Runtime.getRuntime().exec("ls /system");
			InputStream a = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(a));
			String line;
			while ((line = in.readLine()) != null)
			{
				Log.d("FILE=", line);
				if (line.contains("abc.txt"))
				{
					flag = true;
					break;
				}
			}

		}
		return flag;
	}


	static void setActivity(MainActivity a, ProgressDialog d)
	{
		//activity variable is from the Utils.MiscFunctions class
		activity = a;
		dialog = d;
	}
	
	
	
}

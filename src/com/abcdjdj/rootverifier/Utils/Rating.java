package com.abcdjdj.rootverifier.Utils;

import static com.abcdjdj.rootverifier.Utils.MiscFunctions.activity;

import java.io.File;
import java.io.PrintWriter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Rating
{
	private static File file;
	
	static
	{
		 file = new File(activity.getFilesDir().getPath() + "/flag.txt");
	}
	
	public static void exit_rating()
	{
		if (readFlag())
		{
			activity.finish();
		} 
		else
		{
			showDialog();
		}
	}

	public static void rateOnPS()
	{
		Intent intent = null;
		try
		{
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=com.abcdjdj.rootverifier"));
			activity.startActivity(intent);
		} 
		catch (ActivityNotFoundException e)
		{
			intent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.abcdjdj.rootverifier"));
			activity.startActivity(intent);
		} 
		catch (Exception ex)
		{
			Toast.makeText(activity, "Unknown error occured", Toast.LENGTH_LONG)
					.show();

		}
		writeFlag(true);
	}

	private static void writeFlag(boolean create)
	{
		try
		{

			if (create)
			{
				file.createNewFile();
				PrintWriter pw = new PrintWriter(file);
				pw.println("Flag created!");
				pw.close();
			} 
			else
			{
				file.delete();
			}
		} 
		catch (Exception e)
		{}
	}

	private static boolean readFlag()
	{
		boolean ans;
		try
		{
			ans = file.exists();
		} 
		catch (Exception e)
		{
			ans = false;// If any error occurs, anyway prompt the user to rate
		}

		return ans;
	}

	private static void showDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setTitle("Rate on Play Store?");
		builder.setMessage("If you enjoyed using my app, then please support me by rating it on Play Store. Thanks:)");
		builder.setPositiveButton("Rate now", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{

				rateOnPS();
			}
		});
		builder.setNegativeButton("Later", new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				writeFlag(true);
				activity.finish();
			}

		});
		builder.setCancelable(false);
		builder.show();

	}


}

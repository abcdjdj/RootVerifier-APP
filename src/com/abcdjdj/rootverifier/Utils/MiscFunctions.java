package com.abcdjdj.rootverifier.Utils;

import android.widget.TextView;
import android.widget.Toast;

import com.abcdjdj.rootverifier.MainActivity;
import com.abcdjdj.rootverifier.R;

public class MiscFunctions
{
	public static MainActivity activity;
	public static TextView txtview;
	public static CharSequence msg, msg2;
	
	
	public synchronized static void setText(TextView t, CharSequence x)
	{
		txtview = t;
		msg = x;
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				txtview.setText(msg);
				txtview.invalidate();

			}
		};
		activity.runOnUiThread(r);
		try
		{
			Thread.sleep(800);
		} catch (Exception e)
		{
		}
	}
	
	public synchronized static void showToast(CharSequence x)
	{
		msg2 = x;
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(activity, msg2, Toast.LENGTH_LONG).show();
			}
		};
		activity.runOnUiThread(r);
	}
	
	public static void setDeviceName()
	{
	     TextView c = (TextView)activity.findViewById(R.id.devicemodel);

	     StringBuilder x = new StringBuilder("DEVICE:- ");
	     x.append(android.os.Build.MANUFACTURER).append(" ").append(android.os.Build.MODEL);

	     c.setText(x);

    }
}

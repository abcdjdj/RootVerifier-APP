package com.abcdjdj.rootverifier;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.*;
import android.widget.TextView;

public class CheckBusyBox implements Runnable
{
	Thread t;
	CheckBusyBox()
	{
		t = new Thread(this,"CheckBusyBox");
		t.start();
	}
	
	@Override
	public void run()
	{
		busybox();
	}
	
	public static void busybox()
	{
		TextView z = (TextView)activity.findViewById(R.id.busyboxid);
		String line = null;
		char n[] = null;

		try
		{

			Process p = Runtime.getRuntime().exec("busybox");
			InputStream a = p.getInputStream();
			InputStreamReader read = new InputStreamReader(a);
			BufferedReader in = new BufferedReader(read);

			busybox: while ((line = in.readLine()) != null)
			{
				n = line.toCharArray();

				for (char c : n)
				{

					if (Character.isDigit(c))
					{
						break busybox;

					}
				}

			}

			setText(z, new StringBuilder("BUSYBOX INSTALLED - ").append(line));

		} catch (Exception e)
		{
			setText(z, "BUSYBOX NOT INSTALLED OR NOT SYMLINKED");
		}
	}

}

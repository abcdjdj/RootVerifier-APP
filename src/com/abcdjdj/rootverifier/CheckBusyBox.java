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
		t = new Thread(this, "CheckBusyBox");
		t.start();
	}

	@Override
	public void run()
	{
		busybox();
	}

	private static void busybox()
	{
		TextView z = (TextView) activity.findViewById(R.id.busyboxid);
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

		}
		catch (Exception e)
		{
			setText(z, "BUSYBOX NOT INSTALLED OR NOT SYMLINKED");
		}
	}

}

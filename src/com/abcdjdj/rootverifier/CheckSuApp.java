package com.abcdjdj.rootverifier;

import static com.abcdjdj.rootverifier.Utils.MiscFunctions.activity;
import static com.abcdjdj.rootverifier.Utils.MiscFunctions.setText;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

public class CheckSuApp implements Runnable
{	
	Thread t;
	
	CheckSuApp()
	{
		t = new Thread(this,"CheckSuApp");
		t.start();
	}

	@Override
	public void run()
	{
		su_app();
	}
	
	private static void su_app()
	{
		TextView su_app=(TextView)activity.findViewById(R.id.su_app);
		
		String[] packages = {"eu.chainfire.supersu", "eu.chainfire.supersu.pro", "com.koushikdutta.superuser", "com.noshufou.android.su"};
		PackageManager pm = activity.getPackageManager();
		int i,l=packages.length;String superuser=null;
		
		for(i=0;i<l;i++)
		{
			try
			{
				ApplicationInfo info = pm.getApplicationInfo(packages[i], 0);//Testing method by SArnab©®@XDA. Tweaked by me. Thanks:)
				PackageInfo info2 = pm.getPackageInfo(packages[i], 0);
				superuser=pm.getApplicationLabel(info).toString() + " " + info2.versionName;
				break;
			}
			catch(PackageManager.NameNotFoundException e)
			{
				continue;
			}
		}
		
		if(superuser!=null)
		{
			setText(su_app,"SUPERUSER APP : "+superuser);
		}
		else
		{
			su_alternative(su_app);
		}
	}
	

	private static void su_alternative(TextView su_app)
	{
		String line;
		try
		{
			Process p = Runtime.getRuntime().exec("su -v");//Superuser version
			InputStreamReader t = new InputStreamReader(p.getInputStream());			
			BufferedReader in = new BufferedReader(t);
			line=in.readLine();
			
			char[] chars=line.toCharArray();
			boolean flag=false;//Check if su -v returns the package name instead of just the version number
			for(char c:chars)
			{
				if(Character.isLetter(c))
				{
					flag=true;
				}
			}
			if(!flag)
			{
				line="Unknown Superuser";
			}
		}
		catch(Exception e)
		{
			line="Unknown Superuser";
		}
		
		setText(su_app,"SUPERUSER APP : "+line);
		
	}
	
	
}

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

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author - Madhav Kanbur (abcdjdj)
 * @version - V1.3
 */

public class MainActivity extends Activity 
{
    private static String path;
    private static ProgressDialog dialog;
    private static File file;
    //If the file exists, then no need to ask again else ask.
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onStartUp();
    }

    public void onStartUp()
    {

        path=getFilesDir().getPath();

        file = new File(path+"/flag.txt");

        CheckRoot.setActivity(this, dialog);

        setDeviceName();// Calling the function to display the current device model on startup of the app.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle item selection
        switch (item.getItemId()) 
        {
            case R.id.exit:
            finish();
            break;

            case R.id.rate:
            rateOnPS();            
            break;
            
            case R.id.about:
            about_app();
            break;

            default:
            break;

        }
        return true;
    }

    public void Check(View v) 
    {

        dialog = ProgressDialog.show(this, "Verifying root..","Checking. Please wait...", false);
        dialog.setCanceledOnTouchOutside(false);
        CheckRoot.setActivity(this, dialog);

        CheckRoot r = new CheckRoot();
        r.start();

    }
    public void setDeviceName()
    {
        TextView c = (TextView) findViewById(R.id.devicemodel);

        StringBuilder x = new StringBuilder("DEVICE:- ");
        x.append(android.os.Build.MANUFACTURER).append(" ").append(android.os.Build.MODEL);

        c.setText(x);

    }

    @Override
    public void onBackPressed()
    {
        if(readFlag())
        {
            finish();
        }
        else
        {
            showDialog();
        }
    }

    public void showDialog() 
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Rate on Play Store?");
        builder.setMessage("If you enjoyed using my app, then please support me by rating it on Play Store. Thanks:)");
        builder.setPositiveButton("Rate now", new OnClickListener(){

                @Override
                public void onClick(DialogInterface arg0, int arg1) 
                {

                    rateOnPS();
                }
            });
        builder.setNegativeButton("Later", new OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) 
                {
                    writeFlag(true);
                    finish();				
                }

            });
        builder.setCancelable(false);
        builder.show();

    }

    private static void rateOnPS()
    {
        Intent intent=null;
        try
        {
            intent= new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.abcdjdj.rootverifier"));
            CheckRoot.activity.startActivity(intent);
        }
        catch(ActivityNotFoundException e)
        {
            intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.abcdjdj.rootverifier"));
            CheckRoot.activity.startActivity(intent);
        }
        catch(Exception ex)
        {
            Toast.makeText(CheckRoot.activity, "Unknown error occured", Toast.LENGTH_LONG).show();

        }
        writeFlag(true);
    }

    private static void writeFlag(boolean create)
    {
        try
        {

            if(create)
            {
                file.createNewFile();
            }
            else
            {
                file.delete();
            }
        }
        catch(Exception e){}
    }

    private static boolean readFlag()
    {
        boolean ans;
        try
        {
            ans= file.exists();
        }
        catch(Exception e)
        {
            ans=false;//If any error occurs, anyway prompt the user to rate
        }

        return ans;
    }
    
    @Override
	public void onDestroy()
	{
		super.onDestroy();
		
		if(dialog!=null)
		{
			dialog.dismiss();
		}
	}
    
    private void about_app()
    {
    	String msg="Root Verifier is a free software: you can redistribute it and/or modify " +
    			"it under the terms of the GNU General Public License as published by " +
    			"the Free Software Foundation, either version 2 of the License, or " +
    			"(at your option) any later version.\n\nGithub - https://github.com/abcdjdj/RootVerifier-APP\n\n" + 
    			"Credits:-\n-->SArnab©®@XDA\n-->Androlover98@XDA\n-->android1999@XDA\n-->Abhinav2@XDA\n-->ZANKRUT.DOSHI@XDA";
    	
    	Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("About the app");
    	alert.setMessage(msg);
    	alert.setPositiveButton("OK",null);
    	alert.show();  
    }

}
package com.sooria.aadhavan;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.sooria.utils.UnicodeUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowContent extends Activity {
	
	Context cont;
	AssetManager assetManager;
	TextView txtContent;
	TextView Next;
	TextView Previous;
	TextView Home;
	ArrayList<String> list;
	Iterator<String> listIt;
	String text;
	TextView skipAd;
	String lit[] = null;

	private AdView adView;
	private AdRequest req;
	private RelativeLayout ll;
	int check = 0;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_content);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    Typeface tf = Typeface.createFromAsset(getAssets(), MainActivity.fontPath);

        int width  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  
                (float) 120, getResources().getDisplayMetrics()); 
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  
                (float) 50, getResources().getDisplayMetrics()); 

	    setTitle(new MainActivity().getTitleString( MainActivity.title));
        
	    ll = (RelativeLayout)findViewById(R.id.comedy);
	    final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
        final RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width,height);
        final RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width,height);
        final RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(width,height);

		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		
		params1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		params1.setMargins(0, 55, 0, 0);
		params1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params2.setMargins(0, 55, 0, 0);
		params2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		
		params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params3.setMargins(0, 55, 0, 0);
		params3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		params3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		
	    adView = new AdView(this, AdSize.SMART_BANNER, "ca-app-pub-0836982278450182/7422712972");
	    ll.addView(adView);
	    req = new AdRequest();
	    adView.loadAd(req);

		txtContent 	= 	(TextView) findViewById(R.id.test);
		txtContent.setTextColor(Color.WHITE);
		txtContent.setTypeface(tf);
		
        skipAd = new TextView(getBaseContext());
        skipAd.setText("SkipAd");
        skipAd.setTextColor(Color.RED);
        skipAd.setTextSize(10);
        skipAd.setLayoutParams(params);
        skipAd.setGravity(Gravity.CENTER);
        ll.addView(skipAd);
        
		Home		=	new TextView(getBaseContext());
		Next		=	new TextView(getBaseContext());
		Previous	=	new TextView(getBaseContext());
		
		Home.setText("Home");
		Home.setTextColor(Color.RED);
		Home.setTextSize(20);
		Home.setLayoutParams(params1);
	    ll.addView(Home);
	    
	    Next.setText("Next");
	    Next.setTextColor(Color.RED);
	    Next.setTextSize(20);
	    Next.setLayoutParams(params2);
	    ll.addView(Next);
	    
	    Previous.setText("Previous");
	    Previous.setTextColor(Color.RED);
	    Previous.setTextSize(20);
	    Previous.setLayoutParams(params3);
	    ll.addView(Previous);
		
		assetManager = getAssets();
		try {
			lit = assetManager.list("txts");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = new ArrayList<String>();
		list.addAll(Arrays.asList(lit));
		listIt = list.iterator();
		files(String.valueOf(MainActivity.status+1));
		
		Next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				MainActivity.next = true;
			}
		});
		
		Previous.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				MainActivity.previous = true;
			}
		});
		
		Home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				MainActivity.home = true;
			}
		});
		
	    skipAd.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					adView.setVisibility(View.GONE);
					skipAd.setVisibility(View.GONE);
				}
			});
	    
	    txtContent.setTextSize(MainActivity.Fsize);

		}

	void files(String fname)
	{
    // To load text file
    InputStream input;
    try {
        input = assetManager.open("txts/"+fname);
         
         int size = input.available();
         byte[] buffer = new byte[size];
         input.read(buffer);
         input.close();

         // byte buffer into a string
         text = new String(buffer);
 		if (MainActivity.Version >= 21)
 			txtContent.setText(text);
		else {
         txtContent.setText(UnicodeUtil.unicode2tsc(text));
		}
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
        case R.id.action_settings:
        	AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                    ShowContent.this);
            builderSingle.setIcon(R.drawable.ic_launcher);
            builderSingle.setTitle("Select Font Size");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            		ShowContent.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Small");
            arrayAdapter.add("Medium");
            arrayAdapter.add("Large");
            arrayAdapter.add("Xlarge");
            builderSingle.setNegativeButton("cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        	Toast.makeText(getBaseContext(), ""+(which-1), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        	if (which == 0){
                        		MainActivity.Fsize = 15;
                	        	txtContent.setTextSize(MainActivity.Fsize);
                        	}
                        	else if(which == 1){
                        		MainActivity.Fsize = 20;
                	        	txtContent.setTextSize(20);
                        	}
                        	else if (which == 2){
                        		MainActivity.Fsize = 30;
                	        	txtContent.setTextSize(30);
                        	}
                        	else if (which == 3){
                        		MainActivity.Fsize = 35;
                	        	txtContent.setTextSize(40);
                        	}
                        }
                    });
            builderSingle.show();
        	return true;
	    }
		return false;
	}
}

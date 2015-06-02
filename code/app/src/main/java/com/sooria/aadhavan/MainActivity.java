package com.sooria.aadhavan;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.sooria.utils.CustomAdapter;
import com.sooria.utils.TypefaceSpan;
import com.sooria.utils.UnicodeUtil;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements AdListener {
	
	static int status;
	static String title;
	static boolean next;
	static boolean previous;
	static boolean home;
	SharedPreferences preferences;
	static int Fsize;
    public static final String fontPath = "fonts/mylai.ttf";
    final ArrayList<String> planetList = new ArrayList<String>();
	private InterstitialAd interstitialAds = null;
	AdRequest adr = new AdRequest();
	private StartAppAd startAppAd = new StartAppAd(this);
	  private ListView mainListView ;
	  private ArrayAdapter<String> listAdapter ;
	  private AdView adView;
	  private AdRequest req;
	  private RelativeLayout ll;
	public static int Version;
	SpannableString s;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setTitle(getTitleString(getString(R.string.hello_world)));
	    
		    Version = android.os.Build.VERSION.SDK_INT;
	    //StartAppSDK implementation
	    
	    StartAppSDK.init(this, "108044780", "203727010", false);
	    StartAppAd.init(this, "108044780", "203727010");
//	    StartAppAd.showSplash(this, savedInstanceState);
	    StartAppAd.showSlider(this);
	    
		this.interstitialAds = new InterstitialAd(this, "ca-app-pub-0836982278450182/8899446175");
		this.interstitialAds.setAdListener(this);
		interstitialAds.loadAd(adr);
	      
	    ll = (RelativeLayout)findViewById(R.id.mainactivity);
	    adView = new AdView(this, AdSize.SMART_BANNER, "ca-app-pub-0836982278450182/7422712972");
	    ll.addView(adView);
	    req = new AdRequest();
	    adView.loadAd(req);
	    
	    preferences = PreferenceManager.getDefaultSharedPreferences(this); 
		status = preferences.getInt("status", 0); 
		Fsize = preferences.getInt("Fsize", 20);

	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );

	    // Create and populate a List of planet names.
	    String[] planets = new String[] {
	    		"",
	    		"ஒரு அறையில் இரண்டு நாற்காலிகள்",
	    		"புதுமைப்பித்தனின் துரோகம்",
	    		"புகைச்சல்கள்",
	    		"இறந்தவன்",
	    		"இண்டர்வியூ",
	    		"ஒரு தற்கொலை",
	    		"அப்பர் பெர்த்",
	    		"ஒரு பழைய கிழவரும், ஒரு புதிய உலகமும்",
	    		"சிவப்பாக, உயரமாக, மீசை வச்சுக்காமல்",
	    		"கால்வலி",
	    		"ஞாயிற்றுக் கிழமைகளும் பெரிய நகரமும் அறையில் ஓர் இளைஞனும்",
	    		"நிழல்கள்",
	    		"ககருப்பை",
	    		"மூன்றாமவன்",
	    		"முதலில் இரவு வரும்"
	    		
	    		};
	    planetList.addAll( Arrays.asList(planets) );
	    
	    listAdapter = new CustomAdapter(this, R.layout.simplerow, planetList, fontPath);
	    
	    mainListView.setAdapter( listAdapter );      
	    
	    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2!=0){
				Toast.makeText(getBaseContext(), planetList.get(arg2).toString()+"----"+(arg2), Toast.LENGTH_SHORT).show();
					MainActivity.status = (arg2)-1;
					MainActivity.title = planetList.get(arg2).toString();
				startActivity(new Intent(MainActivity.this, ShowContent.class));
//				.putExtra("url", sitesList.get(arg2).toString()).putExtra("name",planetList.get(arg2).toString()));
				}
			}
		});
	  }
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	public void onResume(){
		super.onResume();
	    startAppAd.onResume();
	    if(MainActivity.next)
	    {
	    	if(MainActivity.status+1!=listAdapter.getCount())
		    	{
		    	MainActivity.next = false;
		    	MainActivity.status = MainActivity.status+1;
		    	MainActivity.title = listAdapter.getItem(MainActivity.status).toString();
				startActivity(new Intent(MainActivity.this, ShowContent.class));
		    	}
	    }
	    if(MainActivity.previous)
	    {
	    	if(MainActivity.status!=0){
		    	MainActivity.previous = false;
		    	MainActivity.status = MainActivity.status-1;
		    	MainActivity.title = listAdapter.getItem(MainActivity.status).toString();
				startActivity(new Intent(MainActivity.this, ShowContent.class));
			}
		}
	    
	    if(MainActivity.home)
	    {
	    	MainActivity.home = false;
		}
	    
	}

	@Override
	public void onPause() {
	    super.onPause();
	    startAppAd.onPause();
	}
	
	@Override
	public void onBackPressed() {
	    startAppAd.onBackPressed();
	    super.onBackPressed();
	}
	
	public void onStop(){
		super.onStop();
//		startAppAd.showAd(); // show the ad
//		startAppAd.loadAd(); // load the next ad
	}
	
	public SpannableString getTitleString(String TitleString){
 		if (MainActivity.Version >= 21)
 		    s = new SpannableString(TitleString);
		else 
			s = new SpannableString(UnicodeUtil.unicode2tsc(TitleString));
	    s.setSpan(new TypefaceSpan(this , "mylai.ttf"), 0, s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return s;
	}
	
	public void onDestroy(){
		super.onDestroy();
		startAppAd.showAd(); // show the ad
		startAppAd.loadAd(); // load the next ad
	}
	
	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		if (interstitialAds.isReady()) {
			interstitialAds.show();
		}
	}
}

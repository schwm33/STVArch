package ch.stv_arch.stv_arch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import ch.stv_arch.stv_arch.Utils.Utils;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Define Fragment News
 */

public class NewsFragment extends Fragment {

	View rootView;
	LinearLayout llScroll;

	private static final int GRIDLAYOUT_MARGIN_SIDE = 20;
	private static final int GRIDLAYOUT_MARGIN_TOP_BOTTOM = 10;
	private static final int GRIDLAYOUT_PADDING = 20;
	private static final int GRIDLAYOUT_COLUMNS = 1;
	private static final int GRIDLAYOUT_ROWS = 2;
	
	private String link = "";
	
	/**
	 * Constructs an empty NewsFragment
	 */
	public NewsFragment() {
	}

	/**
	 * The onCreateView method
	 * @param inflater a LayoutInflater
	 * @param container a ViewGroup
	 * @return rootView a View
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		StrictMode.enableDefaults(); //STRICT MODE ENABLED  

		rootView = inflater.inflate(R.layout.fragment_news, container, false);

		//helper method
		getData();

		return rootView;
	}

	/**
	 * Get data from a link and add textviews to the linearlayout
	 */
	private void getData() {

		//define progressbar
		ProgressDialog progress = new ProgressDialog(getActivity());
		progress.setTitle(R.string.progress_bar_title);
		progress.setMessage(getResources().getString(R.string.progress_bar_message));
		progress.show();

		String result = "";
		InputStream isr = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(getResources().getString(R.string.link_news_php)); //YOUR PHP SCRIPT ADDRESS 
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
		}
		catch(Exception e){
			Log.e("Exception HTTP Client", e.toString());
			//dismiss the progressbar
			progress.dismiss();
		}
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"utf-8"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			isr.close();

			result=sb.toString();
		}
		catch(Exception e){
			Log.e("Exception Convert", "Error  converting result "+ e.toString());
			//dismiss the progressbar
			progress.dismiss();
		}

		//parse json data
		try {
			llScroll = (LinearLayout) rootView.findViewById(R.id.llView);

			JSONArray jArray = new JSONArray(result);

			//Define a gridlayout array (contains textviews)
			GridLayout[] gl = new GridLayout[jArray.length()];	   
			
			//define a textview array
			TextView[] tv = new TextView[GRIDLAYOUT_ROWS * GRIDLAYOUT_COLUMNS];

			for(int i=0; i<jArray.length();i++){
				JSONObject json = jArray.getJSONObject(i);
				String stDate = json.getString("title");
				String action = json.getString("introtext");

				//check introtext and adapt it if necessary
				
				
				//Create gridlayout
				gl[i] = createGridLayout();
				
				//create textviews
		        tv[0] = createTitleTextView(stDate);
				tv[1] = createContentTextView(action);


				gl[i].addView(tv[0]);
				gl[i].addView(tv[1]);
				
				//add textview to the scrollview
				llScroll.addView(gl[i]);
			}
			//dismiss the progressbar
			progress.dismiss();

		}  catch (ParseException e) {  
			Log.e("Parse Exception", "Error Parsing Data "+e.toString());

			//Add a textview to the content
			TextView tv = new TextView(getActivity());
			tv.setText(getResources().getString(R.string.exception_parse));
			llScroll.addView(tv);

			//dismiss the progressbar
			progress.dismiss();

		} catch (Exception e) {
			Log.e("Exception", "Exception "+e.toString());

			//add a textview to the content
			TextView tv = new TextView(getActivity());
			tv.setText(getResources().getString(R.string.exception_http_message));
			llScroll.addView(tv);

			//dismiss the progressbar
			progress.dismiss();
		}
	}
	
	/**
	 * Create a textview title
	 * @param text a string with the text of the textview
	 * @return tv a textview formatted
	 */
	public TextView createTitleTextView(String text) {
		TextView tv = new TextView(getActivity());
		tv.setTextColor(getResources().getColor(R.color.content_text));
		SpannableString spanString = new SpannableString(text);
		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
		//spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
		tv.setText(spanString);
		tv.setTextSize(getResources().getDimension(R.dimen.title_content_size));
		
		return tv;
	}
	
	/**
	 * Create a textview content
	 * @param text a string with the text of the textview
	 * @return tv a textview formatted
	 */
	public TextView createContentTextView(String text) {
		TextView tv = new TextView(getActivity());
		tv.setTextColor(getResources().getColor(R.color.content_text));
		tv.setText(text);
		
		return tv;
	}
	
	/**
	 * Create a gridlayout
	 * @return gl a gridlayout formatted
	 */
	public GridLayout createGridLayout() {
		GridLayout gl = new GridLayout(getActivity());
		//define the margin of the GridLayout
		TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(GRIDLAYOUT_MARGIN_SIDE,GRIDLAYOUT_MARGIN_TOP_BOTTOM,GRIDLAYOUT_MARGIN_SIDE,GRIDLAYOUT_MARGIN_TOP_BOTTOM);
		gl.setLayoutParams(params);
		gl.setPadding(GRIDLAYOUT_PADDING,GRIDLAYOUT_PADDING,GRIDLAYOUT_PADDING,GRIDLAYOUT_PADDING);
		gl.setBackgroundResource(R.color.content);
		gl.setOrientation(0);
		gl.setColumnCount(GRIDLAYOUT_COLUMNS);
		gl.setRowCount(GRIDLAYOUT_ROWS);
		
		return gl;
	}
}
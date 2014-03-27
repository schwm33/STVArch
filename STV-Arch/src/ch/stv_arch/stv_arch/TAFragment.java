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
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Define Fragment Taetigkeitsprogramm
 */

public class TAFragment extends Fragment {

	View rootView;
	LinearLayout llScroll;

	private static final int TEXTVIEW_MARGIN_SIDE = 20;
	private static final int TEXTVIEW_MARGIN_TOP_BOTTOM = 10;
	private static final int TEXTVIEW_PADDING = 20;

	/**
	 * Constructs an empty TAFragment
	 */
	public TAFragment() {
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

		rootView = inflater.inflate(R.layout.fragment_ta, container, false);

		//Title
		TextView tvTitle =  ((TextView) rootView.findViewById(R.id.tvTitle));  
		tvTitle.setTextSize(getResources().getDimension(R.dimen.title_size));
		Utils.TypeFace(tvTitle, getActivity().getAssets());
		String [] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		tvTitle.setText(navMenuTitles[3]);

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
			HttpPost httppost = new HttpPost(getResources().getString(R.string.link_ta_php)); //YOUR PHP SCRIPT ADDRESS 
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

			//Define a textview array
			TextView[] tx = new TextView[jArray.length()];	   

			//define the margin of the textview
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(TEXTVIEW_MARGIN_SIDE,TEXTVIEW_MARGIN_TOP_BOTTOM,TEXTVIEW_MARGIN_SIDE,TEXTVIEW_MARGIN_TOP_BOTTOM);

			//Content titles with tabulators
			String content_title_date = getResources().getString(R.string.content_title_date) + getString(R.string.tab) + getString(R.string.tab);
			String content_title_what = getResources().getString(R.string.content_title_what)+ getString(R.string.tab)+ getString(R.string.tab)+ getString(R.string.tab) + getString(R.string.tab);
			String content_title_where = getResources().getString(R.string.content_title_where)+ getString(R.string.tab)+ getString(R.string.tab)+ getString(R.string.tab)+ getString(R.string.tab) + getString(R.string.tab);

			for(int i=0; i<jArray.length();i++){
				JSONObject json = jArray.getJSONObject(i);
				String stDate = json.getString("date");
				String action = json.getString("what");
				String where = json.getString("where");

				//define date format
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat outputFormat = new SimpleDateFormat("dd. MMM yyyy");
				Date date = inputFormat.parse(stDate);
				String outputDateStr = outputFormat.format(date);


				String s =  content_title_date + outputDateStr + "\n" 
						+ content_title_what + action + "\n"
						+ content_title_where + where;


				//Create textview
				tx[i] = new TextView(getActivity());
				tx[i].setText(s);
				tx[i].setTextColor(getResources().getColor(R.color.content_text));
				tx[i].setLayoutParams(params);
				tx[i].setPadding(TEXTVIEW_PADDING,TEXTVIEW_PADDING,TEXTVIEW_PADDING,TEXTVIEW_PADDING);
				tx[i].setBackgroundResource(R.color.content);

				//add textview to the scrollview
				llScroll.addView(tx[i]);
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
}
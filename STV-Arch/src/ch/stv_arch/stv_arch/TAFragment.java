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
import android.graphics.Color;
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

public class TAFragment extends Fragment {

	View rootView;
	LinearLayout llScroll;
	
	public TAFragment() {
	}
	
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
		
        getData();
         
		return rootView;
	}
	
	private void getData() {
		
		String result = "";
		InputStream isr = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://stv-arch.ch/android/get_ta.php"); //YOUR PHP SCRIPT ADDRESS 
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
		}
		catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
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
			Log.e("log_tag", "Error  converting result "+e.toString());
		}

		//parse json data
		try {
			llScroll = (LinearLayout) rootView.findViewById(R.id.llView);

			JSONArray jArray = new JSONArray(result);

			TextView[] tx = new TextView[jArray.length()];	   

			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 10, 10, 10);


			for(int i=0; i<jArray.length();i++){
				JSONObject json = jArray.getJSONObject(i);
				String stDate = json.getString("date");
				String action = json.getString("what");
				String where = json.getString("where");
				
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat outputFormat = new SimpleDateFormat("dd. MMM yyyy");
				Date date = inputFormat.parse(stDate);
				String outputDateStr = outputFormat.format(date);
				
				
				String s = "Datum: " + outputDateStr + "\n" 
						+ "Was: " + action + "\n"
						+ "Wo: " + where;
				
				
				
				tx[i] = new TextView(getActivity());
				tx[i].setText(s);


				tx[i].setTextColor(Color.BLACK);
				tx[i].setLayoutParams(params);
				tx[i].setBackgroundResource(R.color.content);
				llScroll.addView(tx[i]);
			}


		}  catch (ParseException e) {  
			Log.e("log_tag", "Error Parsing Data "+e.toString());
			TextView tv = new TextView(getActivity());
			tv.setText("Error. Couldn't parse data!");
			llScroll.addView(tv);

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("log_tag", "Exception "+e.toString());
			TextView tv = new TextView(getActivity());
			tv.setText("Error. Couldn't load data!");
			llScroll.addView(tv);
		}

	}


}
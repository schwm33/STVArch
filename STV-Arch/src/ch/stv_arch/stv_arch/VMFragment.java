package ch.stv_arch.stv_arch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import ch.stv_arch.stv_arch.Utils.Utils;
import ch.stv_arch.stv_arch.db.DatabaseHandler;
import ch.stv_arch.stv_arch.db.Termin;
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

public class VMFragment extends Fragment {

	View rootView;
	LinearLayout llScroll;

	public VMFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		rootView = inflater.inflate(R.layout.fragment_ta, container, false);

		//Title
		TextView tvTitle =  ((TextView) rootView.findViewById(R.id.tvTitle));  
		tvTitle.setTextSize(getResources().getDimension(R.dimen.title_size));
		Utils.TypeFace(tvTitle, getActivity().getAssets());
		String [] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		tvTitle.setText(navMenuTitles[2]);

		loadData();
		
		return rootView;
	}

	private void loadData() {
		DatabaseHandler db = new DatabaseHandler(getActivity());

		// Reading all contacts
		Log.d("Reading: ", "Reading all contacts.."); 
		List<Termin> termins = db.getAllDatesVM();       

		for (Termin cn : termins) {
			String log = "Id: "+cn.getDate()+" ,Name: " + cn.getWhat() + " ,Phone: " + cn.getWhere();
			// Writing Contacts to log
			Log.d("Name: ", log);

		}

	}
}

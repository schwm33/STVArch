package ch.stv_arch.stv_arch.Utils;

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

import ch.stv_arch.stv_arch.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

//Easy way to apply font style more textview:

//Create Utils class in your package

//Put font file in assets

public class Utils {

	private static String font1 = "fonts/Brush Script Bold.ttf"; //oder definieren in strings
	public static Typeface fontsStyle;

	static View rootView;
	static LinearLayout llScroll;

	public static void TypeFace(TextView tv, AssetManager asm){

		fontsStyle=Typeface.createFromAsset(asm, font1 );
		tv.setTypeface(fontsStyle);
	}
}



package ch.stv_arch.stv_arch;

import ch.stv_arch.stv_arch.Utils.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * A info activity with no actionbar
 */

public class InfoActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		//hide actionbar
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		//Title
		TextView tvTitle =  ((TextView) findViewById(R.id.tvTitle));  
		tvTitle.setTextSize(getResources().getDimension(R.dimen.title_size));
		Utils.TypeFace(tvTitle, getAssets());
		tvTitle.setText(getString(R.string.action_info));
	}

}

package ch.stv_arch.stv_arch;

import ch.stv_arch.stv_arch.Utils.Utils;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

	public SettingsFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

		//Title
		TextView tvTitle =  ((TextView) rootView.findViewById(R.id.tvTitle));  
		tvTitle.setTextSize(getResources().getDimension(R.dimen.title_size));
        Utils.TypeFace(tvTitle, getActivity().getAssets());
        String [] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        tvTitle.setText(navMenuTitles[4]);
		
		return rootView;
	}

}

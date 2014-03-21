package ch.stv_arch.stv_arch;

import ch.stv_arch.stv_arch.Utils.Utils;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NewsFragment extends Fragment {

	public NewsFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_news, container, false);
		
		//Title
		TextView tvTitle =  ((TextView) rootView.findViewById(R.id.tvTitle));
		tvTitle.setTextSize(getResources().getDimension(R.dimen.title_size));
        Utils.TypeFace(tvTitle, getActivity().getAssets());
        String [] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        tvTitle.setText(navMenuTitles[0]);
        
        FrameLayout f1 = new FrameLayout(getActivity());
        TextView t1 = new TextView(getActivity());
        t1.setText("Das ist ein Test");
        f1.addView(t1);
        
        
		return rootView;
	}

}
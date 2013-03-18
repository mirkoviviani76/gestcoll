package mirko.viviani.gestcoll.tabFragments.tabWelcome;

import mirko.viviani.gestcoll.R;
import mirko.viviani.gestcoll.data.CoinData;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView()
	 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_welcome_layout, container, false);
    }

	/* (non-Javadoc)
	 * @see android.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();

		((TextView) getActivity().findViewById(R.id.tab_welcome_layout_titolo)).setText(CoinData.getInfo().getTitolo());
		((TextView) getActivity().findViewById(R.id.tab_welcome_layout_proprietario)).setText(CoinData.getInfo().getProprietario());
		((TextView) getActivity().findViewById(R.id.tab_welcome_layout_statcoins)).setText(""+CoinData.getIds().size());
	}
	
	

}

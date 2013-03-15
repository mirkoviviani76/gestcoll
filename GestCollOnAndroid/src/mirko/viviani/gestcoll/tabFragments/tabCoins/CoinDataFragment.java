package mirko.viviani.gestcoll.tabFragments.tabCoins;

import mirko.viviani.gestcoll.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoinDataFragment extends Fragment {
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_coins_layout, container, false);
    }
	
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        CoinFragment cf = (CoinFragment) getFragmentManager().findFragmentById(R.id.coin_fragment);
        if (cf != null) 
        	getFragmentManager().beginTransaction().remove(cf).commit();
        
        CoinListFragment cl = (CoinListFragment) getFragmentManager().findFragmentById(R.id.coinlist_fragment);
        if (cl != null) 
        	getFragmentManager().beginTransaction().remove(cl).commit();
    }    

}

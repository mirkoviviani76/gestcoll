/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mirko.viviani.gestcoll.tabFragments.tabCoins;

import java.util.ArrayList;
import java.util.HashMap;

import mirko.viviani.gestcoll.R;
import mirko.viviani.gestcoll.data.CoinData;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class CoinListFragment extends Fragment {
    OnCoinSelectedListener mCallback;
    
    private View currentView;
    private ListView coinListView;
    private EditText editText;
    private SimpleAdapter adapter;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnCoinSelectedListener {
        /** Called by CoinListFragment when a list item is selected */
        public void onArticleSelected(String id);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	currentView = inflater.inflate(R.layout.coinlist, container, false);
    	coinListView = (ListView) currentView.findViewById(R.id.coin_list_view);
    	editText = (EditText) currentView.findViewById(R.id.coin_list_filter);
        return currentView;
    }
    
    
    

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
        String[] columnTags = new String[] {"tagId", "tagPaese"};
        int[] columnIds = new int[] {R.id.coinlist_id, R.id.coinlist_other};

        for (String id: CoinData.getIds()) {
        	HashMap<String,String> map = new HashMap<String, String>();
        	//initialize row data
        	map.put(columnTags[0], id);
        	try {
				map.put(columnTags[1], CoinData.getData(id).getSimpleName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	mylistData.add(map);
        }
        adapter = new SimpleAdapter(currentView.getContext(), mylistData, R.layout.coinlist_item, columnTags , columnIds);
        coinListView.setAdapter(adapter);
        
        
        coinListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				// TODO Auto-generated method stub
		    	@SuppressWarnings("unchecked")
				HashMap<String, String> data = (HashMap<String, String>) (l.getItemAtPosition(position));
		    	String coinSelectedId = (String) data.get("tagId");
		        mCallback.onArticleSelected(coinSelectedId);
		        
		        // Set the item as checked to be highlighted when in two-pane layout
		        coinListView.setItemChecked(position, true);
				
			}
		});
        
        
        editText.addTextChangedListener(new TextWatcher() {

        	@Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
        		adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }        	
        });
        
        //sa.getFilter().filter("ccc");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnCoinSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCoinSelectedListener");
        }
    }
}


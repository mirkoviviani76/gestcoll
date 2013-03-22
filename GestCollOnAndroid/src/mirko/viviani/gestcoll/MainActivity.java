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
package mirko.viviani.gestcoll;

import mirko.viviani.gestcoll.data.BiblioData;
import mirko.viviani.gestcoll.data.CoinData;
import mirko.viviani.gestcoll.settings.Settings;
import mirko.viviani.gestcoll.tabFragments.tabCoins.CoinDataFragment;
import mirko.viviani.gestcoll.tabFragments.tabLibrary.LibraryDataFragment;
import mirko.viviani.gestcoll.tabFragments.tabWelcome.WelcomeFragment;
import mirko.viviani.xmlData.biblio.Biblioteca;
import mirko.viviani.xmlData.coins.Moneta;
import mirko.viviani.xmlData.coins.Monete;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import sheetrock.panda.changelog.ChangeLog;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity  implements CoinDataFragment.OnCoinSelectedListener {
	
	ProgressDialog dialog;
	
	private static final int FILE_SELECT_CODE = 0;

    //create the two fragments we want to use for display content
    private WelcomeFragment welcomeFragment;
    private CoinDataFragment coinDataFragment;
    private LibraryDataFragment libraryFragment;
	
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
        intent.setType("*/*"); 
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select collezione.xml"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", 
                    Toast.LENGTH_SHORT).show();
        }
    }	

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch (requestCode) {
    	case FILE_SELECT_CODE:      
    		if (resultCode == RESULT_OK) {  
    			// Get the Uri of the selected file 
    			String uri =  data.getData().getPath();
    			
    			SharedPreferences preferences = this.getPreferences(MODE_PRIVATE);
    			Settings.getInstance().setPreferences(preferences);
    			Settings.getInstance().UpdateXmlFile(uri);
    			
    			// Load data
    			dialog = ProgressDialog.show(this, "", "Carico i dati... " + Settings.getInstance().getXmlFile().getParent(), true);
    	        new XmlLoader().execute();
    			
    		}           
    		break;
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }    
	
	private class XmlLoader extends AsyncTask<Void, String, Void> {

		
		
		@Override
		protected Void doInBackground(Void... params) {
			dialog.show();
	        //load coin data from xml 
	        
			Serializer serial = new Persister();
			
			try {
				Monete collezioneXml = serial.read(Monete.class, Settings.getInstance().getXmlFile());
				CoinData.Clear();
				CoinData.setInfo(collezioneXml.getInfo());
				for (Moneta m : collezioneXml.getMonete()) {
					CoinData.addCoin(m);
					publishProgress(m.getId());
					Thread.sleep(1);
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				publishProgress("Carico la biblioteca...");

				Biblioteca bibliotecaXml = serial.read(Biblioteca.class, Settings.getInstance().getXmlBiblio());
				
				for (mirko.viviani.xmlData.biblio.Libro l : bibliotecaXml.getLibri()) {
					BiblioData.addLibro(l);
					publishProgress(l.getId());
					Thread.sleep(1);
				}
				for (mirko.viviani.xmlData.biblio.Catalogo c : bibliotecaXml.getCataloghi()) {
					BiblioData.addCatalogo(c);
					publishProgress(c.getNumero());
					Thread.sleep(1);
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				


			return null;
		}

		
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			dialog.setMessage(values[0]);
		}



		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			
			/* setup della action bar */
	        ActionBar actionbar = getActionBar();
	        actionbar.setDisplayHomeAsUpEnabled(false);
	        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	        //initiating both tabs and set text to it.
	        ActionBar.Tab welcomeTab = actionbar.newTab();
	        welcomeTab.setText(R.string.tab_welcome);
	        ActionBar.Tab coinsTab = actionbar.newTab().setText(R.string.tab_coins);
	        coinsTab.setText(R.string.tab_coins);
	        ActionBar.Tab libraryTab = actionbar.newTab().setText(R.string.tab_library);
	        libraryTab.setText(R.string.tab_library);

	        //create the two fragments we want to use for display content
	        welcomeFragment = new WelcomeFragment();
	        coinDataFragment = new CoinDataFragment();
	        libraryFragment = new LibraryDataFragment();
	        

	        //set the Tab listener. Now we can listen for clicks.
	        welcomeTab.setTabListener(new MainTabListener(welcomeFragment));
	        coinsTab.setTabListener(new MainTabListener(coinDataFragment));
	        libraryTab.setTabListener(new MainTabListener(libraryFragment));

	        //add the two tabs to the actionbar
	        actionbar.addTab(welcomeTab);
	        actionbar.addTab(coinsTab);
	        actionbar.addTab(libraryTab);
			
		}
		
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* abilitare se si vuol mostrare ad ogni nuova versione il log
        ChangeLog cl = new ChangeLog(this);
        if (cl.firstRun())
            cl.getLogDialog().show();
            */        
        
        Settings.getInstance().setPreferences(this.getPreferences(MODE_PRIVATE));
        

		if (Settings.getInstance().getXmlFile().exists() == false) {
			showFileChooser();
		} else {
			dialog = ProgressDialog.show(this, "", "Carico i dati... " + Settings.getInstance().getXmlFile().getAbsolutePath(), true);
			new XmlLoader().execute();
		}
		
		setContentView(R.layout.main_container);

        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }    
    
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	switch (item.getItemId()) {
    	case R.id.menu_about:
            ChangeLog cl = new ChangeLog(this);
            cl.getFullLogDialog().show();
    		return true;
    	case R.id.menu_settings:
    		showFileChooser();
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }    
    

    public void onCoinIdSelected(String coinId) {
    	/* TODO se necessario fare qualcosa. Per ora non serve far niente, però così il parametro viene passato
    	 * eventualmente anche ad altri frammenti
    	*/
    	
    }

    
}


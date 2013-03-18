package mirko.viviani.gestcoll.tabFragments.tabCoins;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import mirko.viviani.gestcoll.R;
import mirko.viviani.gestcoll.customadapter.LetteraturaDettagli;
import mirko.viviani.gestcoll.customadapter.LetteraturaGroup;
import mirko.viviani.gestcoll.customadapter.LetteraturaListAdapter;
import mirko.viviani.gestcoll.data.BiblioData;
import mirko.viviani.gestcoll.data.CoinData;
import mirko.viviani.gestcoll.settings.Settings;
import mirko.viviani.utils.Utils;
import mirko.viviani.xmlData.coins.Ambito;
import mirko.viviani.xmlData.coins.DatiAcquisto;
import mirko.viviani.xmlData.coins.DatiFisici;
import mirko.viviani.xmlData.coins.Descrizioni;
import mirko.viviani.xmlData.coins.Documento;
import mirko.viviani.xmlData.coins.Legenda;
import mirko.viviani.xmlData.coins.Moneta;
import mirko.viviani.xmlData.coins.Zecchiere;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CoinDataFragment extends Fragment {
	
    OnCoinSelectedListener mCallback;
    
    private View currentView;
    private ListView coinListView;
    private EditText editText;
    private SimpleAdapter adapter;

    final static String ARG_COIN_ID = "lastSelectedCoin";
    final static String ARG_COIN_POS = "lastSelectedPos";
    
    String mCurrentId = "";
    int mPositionCurrentId = -1;
    
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnCoinSelectedListener {
        /** Called by CoinListFragment when a list item is selected */
        public void onCoinIdSelected(String id);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

    	// If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
    	if (savedInstanceState != null) {
    		mCurrentId = savedInstanceState.getString(ARG_COIN_ID);
    		mPositionCurrentId = savedInstanceState.getInt(ARG_COIN_POS);
    	}

    	currentView = inflater.inflate(R.layout.tab_coins_layout, container, false);
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
		    	@SuppressWarnings("unchecked")
				HashMap<String, String> data = (HashMap<String, String>) (l.getItemAtPosition(position));
		    	String coinSelectedId = (String) data.get("tagId");
		        mCallback.onCoinIdSelected(coinSelectedId);
		        
		        // Set the item as checked to be highlighted when in two-pane layout
		        coinListView.setItemChecked(position, true);
		        updateCoinView(coinSelectedId);
     
		        mPositionCurrentId = position;

		        Bundle args = getArguments();
		        
	            if (args != null) {
	                // Set article based on argument passed in
	            	args.putString(ARG_COIN_ID, mCurrentId);
	            	args.putInt(ARG_COIN_POS, position);
	            }
		        
			}
		});
        
        
        editText.addTextChangedListener(new TextWatcher() {

        	@Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        		adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }        	
        });
        
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        
        if (args != null) {
            // Set article based on argument passed in
        	mCurrentId = args.getString(ARG_COIN_ID);
        	mPositionCurrentId = args.getInt(ARG_COIN_POS);
        }
        
        if (mCurrentId != "") {
        	updateCoinView(mCurrentId);
        }
        
        if (mPositionCurrentId != -1) {
        	coinListView.setItemChecked(mPositionCurrentId, true);
        }
        
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


    public void updateCoinView(String coinId) {
    	System.gc();

    	try {
    		Moneta coin = CoinData.getData(coinId);

    		setDatiGenerali((View) getActivity().findViewById(R.id.datiGenerali), coin);

    		setDatiFisici((View) getActivity().findViewById(R.id.datiFisici), coin.getDatiFisici());
    		setDatiAcquisto((View) getActivity().findViewById(R.id.datiAcquisto), coin.getDatiAcquisto());
    		setDatiExtra((View) getActivity().findViewById(R.id.datiExtra), coin);


    		setDescrizione(getActivity().findViewById(R.id.datiArtisticiDritto), coin.getDatiArtistici().getDritto());
    		setDescrizione(getActivity().findViewById(R.id.datiArtisticiRovescio), coin.getDatiArtistici().getRovescio());
    		setDescrizione(getActivity().findViewById(R.id.datiArtisticiTaglio), coin.getDatiArtistici().getTaglio());

    		mCurrentId = coinId;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    /**
     * Riempie i dati extra
     * @param findViewById
     * @param coin
     */
    private void setDatiExtra(View layout, Moneta coin) {
    	ListView noteList = (ListView) layout.findViewById(R.id.note);
    	//Spinner noteList = (Spinner) layout.findViewById(R.id.note);
    	if (coin.getNote() != null && coin.getNote().size() > 0)
    	{
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"col1"};
    		int[] columnIds = new int[] {R.id.notaTesto};

    		for (String n : coin.getNote())
    		{
    			HashMap<String,String> map = new HashMap<String, String>();
    			map.put(columnTags[0], n);
    			mylistData.add(map);
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(layout.getContext(), mylistData, R.layout.note_list_view, columnTags , columnIds);
    		noteList.setAdapter(arrayAdapter);
    	}
    	else
    	{
    		noteList.setAdapter(null);	
    	}
        


    	
    	ExpandableListView letteraturaList = (ExpandableListView) layout.findViewById(R.id.letteratura);
    	//Spinner letteraturaList = (Spinner) layout.findViewById(R.id.letteratura);
    	LetteraturaListAdapter adapter = null;
    	if (coin.getLetteratura() != null && coin.getLetteratura().getLibro() != null && coin.getLetteratura().getLibro().size() > 0)
    	{
    		
        	ArrayList<LetteraturaGroup> listaGruppi = new ArrayList<LetteraturaGroup>();

        	/* per tutti i libri presenti nella letteratura della moneta */
    		for (mirko.viviani.xmlData.coins.Libro libro : coin.getLetteratura().getLibro())
    		{
    			ArrayList<LetteraturaDettagli> dettagliList = new ArrayList<LetteraturaDettagli>();
    			LetteraturaGroup gr = new LetteraturaGroup();
    			gr.setNumero(libro.getNumero());
    			gr.setSigla(libro.getSigla());
    			
    			/* aggiunge i dettagli ottenendoli dalla biblioteca (se presenti) */
		    	mirko.viviani.xmlData.biblio.Libro libroBiblio = BiblioData.getLibro(libro.getSigla());
		    	if (libroBiblio != null) {
	    			LetteraturaDettagli dettagli = new LetteraturaDettagli();
		    		dettagli.setTitolo(libroBiblio.getTitolo());
		    		dettagli.setId(libroBiblio.getId());
		    		dettagli.setFilename(libroBiblio.getFilename());
		    		dettagli.setAutori(libroBiblio.getAutori());
			    	//aggiunge i dettagli alla lista dettagli
			    	dettagliList.add(dettagli);
		    	}
		    	//aggiunge i dettagli al gruppo principale
		    	gr.setItems(dettagliList);
		    	listaGruppi.add(gr);
    		}

    		adapter = new LetteraturaListAdapter(getActivity(), listaGruppi); 		
    	}
    	letteraturaList.setAdapter(adapter);

    	letteraturaList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				//ottiene l'adapter
				LetteraturaListAdapter myListAdapter = (LetteraturaListAdapter) parent.getExpandableListAdapter();
				//ottiene i dettagli
				LetteraturaDettagli myDettagli = (LetteraturaDettagli) myListAdapter.getChild(groupPosition, childPosition);
				//estrae il filename
				if (myDettagli.getFilename() != null && myDettagli.getFilename() != "") {
					//estrae il filename e se esiste prova ad aprirlo
					File libroFile = new File(Settings.getInstance().getBiblioDir() + File.separator + myDettagli.getFilename());
					try {
						startActivity(Utils.getViewFileIntent(libroFile));
					} catch (Exception ex) {
			    		Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
			    		ex.printStackTrace();
					}
				}
				return false;
			}
		});

    	
    	ListView itemsList = (ListView) layout.findViewById(R.id.itemAddizionali);
    	//Spinner itemsList = (Spinner) layout.findViewById(R.id.itemAddizionali);
    	if (coin.getItemAddizionali() != null && coin.getItemAddizionali().getDocumento() != null && coin.getItemAddizionali().getDocumento().size() > 0)
    	{
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"col1", "col2"};
    		int[] columnIds = new int[] {R.id.itemFilename, R.id.itemDescrizione};

    		for (Documento d : coin.getItemAddizionali().getDocumento())
    		{
    			HashMap<String,String> map = new HashMap<String, String>();
    			//initialize row data
    			map.put(columnTags[0], d.getFilename());
    			map.put(columnTags[1], d.getDescrizione());
    			mylistData.add(map);
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(layout.getContext(), mylistData, R.layout.items_addizionali_list_view, columnTags , columnIds);
    		itemsList.setAdapter(arrayAdapter);
    		
    		itemsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> listview, View v, int position, long id) {
					
					/* estrae il file del documento richiesto */
					@SuppressWarnings("unchecked")
					HashMap<String, String> data = (HashMap<String, String>) (listview.getItemAtPosition(position));
			    	String itemFilename = (String) data.get("col1");
			    	File itemFile = new File(Settings.getInstance().getDocDir() + File.separator + itemFilename);
			    	try {
			    		startActivity(Utils.getViewFileIntent(itemFile));
			    	} catch (Exception ex) {
			    		Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
			    		ex.printStackTrace();
			    	}

				}
    			
			});
    	}
    	else
    	{
    		itemsList.setAdapter(null);	
    	}

		
	}

	/**
     * Riempie i campi dei dati generali
     * @param findViewById
     * @param coin
     */
    private void setDatiGenerali(View layout, Moneta coin) {
        ((TextView) layout.findViewById(R.id.id)).setText(coin.getId());
        ((TextView) layout.findViewById(R.id.paese)).setText(coin.getPaese());
        ((TextView) layout.findViewById(R.id.anno)).setText(coin.getAnno());
        ((TextView) layout.findViewById(R.id.valore)).setText(coin.getNominale().getValore());
        ((TextView) layout.findViewById(R.id.valuta)).setText(coin.getNominale().getValuta());
        ((TextView) layout.findViewById(R.id.contenitore)).setText(coin.getPosizione().getContenitore().toString());
        ((TextView) layout.findViewById(R.id.vassoio)).setText(coin.getPosizione().getVassoio().toString());
        ((TextView) layout.findViewById(R.id.riga)).setText(coin.getPosizione().getRiga().toString());
        ((TextView) layout.findViewById(R.id.colonna)).setText(coin.getPosizione().getColonna().toString());
        if (coin.getZecca() != null)
        {
          ((TextView) layout.findViewById(R.id.nomeZecca)).setText(coin.getZecca().getNome());
          ((TextView) layout.findViewById(R.id.segnoZecca)).setText(coin.getZecca().getSegno());
        }

        ListView autoritaList = (ListView) layout.findViewById(R.id.autorita);
    	//Spinner autoritaList = (Spinner) layout.findViewById(R.id.autorita);
    	
        if (coin.getAutorita() != null && coin.getAutorita().size() > 0) {
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"col1"};
    		int[] columnIds = new int[] {R.id.autoritaText};

    		for (String s : coin.getAutorita())
    		{
    			HashMap<String,String> map = new HashMap<String, String>();
    			//initialize row data
    			if (s != null) {
    				map.put(columnTags[0], s);
    				mylistData.add(map);
    			}
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(layout.getContext(), mylistData, R.layout.autorita_list_view, columnTags , columnIds);
    		//arrayAdapter.setDropDownViewResource(R.id.autorita);
    		autoritaList.setAdapter(arrayAdapter);
        } else
        {
        	//nessun contenuto presente
        	autoritaList.setAdapter(null);
        }

    	ListView zecchieriList = (ListView) layout.findViewById(R.id.zecchieri);
        //Spinner zecchieriList = (Spinner) layout.findViewById(R.id.zecchieri);
    	if (coin.getZecchieri() != null && coin.getZecchieri().size() > 0)
    	{
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"col1", "col2", "col3"};
    		int[] columnIds = new int[] {R.id.zecchiereNome, R.id.zecchiereSegno, R.id.zecchiereRuolo};

    		for (Zecchiere z : coin.getZecchieri())
    		{
    			HashMap<String,String> map = new HashMap<String, String>();
    			//initialize row data
    			map.put(columnTags[0], z.getNome());
    			map.put(columnTags[1], z.getSegno());
    			map.put(columnTags[2], z.getRuolo());
    			mylistData.add(map);
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(layout.getContext(), mylistData, R.layout.zecchieri_list_view, columnTags , columnIds);
    		zecchieriList.setAdapter(arrayAdapter);
    	}
    	else
    	{
    		zecchieriList.setAdapter(null);	
    	}

    	ListView ambitiList = (ListView) layout.findViewById(R.id.ambiti);
    	//Spinner ambitiList = (Spinner) layout.findViewById(R.id.ambiti);
    	if (coin.getAmbito() != null && coin.getAmbito().size() > 0)
    	{
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"col1"};
    		int[] columnIds = new int[] {R.id.ambitoText};

    		for (Ambito a : coin.getAmbito())
    		{
    			HashMap<String,String> map = new HashMap<String, String>();
    			//initialize row data
    			map.put(columnTags[0], a.getTitolo());
    			mylistData.add(map);
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(layout.getContext(), mylistData, R.layout.ambiti_list_view, columnTags , columnIds);
    		ambitiList.setAdapter(arrayAdapter);
    	}
    	else
    	{
    		ambitiList.setAdapter(null);	
    	}
    	
    	///TODO va finito  con il colore
    	if (coin.getStato() != null) 
    	{
    		String text = "XXXX";
    		if (coin.getStato().getMotivo() != null)
    		{
    			text = coin.getStato().getMotivo();
    		}
    		TextView status = (TextView) layout.findViewById(R.id.status);
    		status.setText(text);
    	}
    	
	}

	/**
     * Riempie i campi dei dati di acquisto
     * @param layout
     * @param datiAcquisto
     */
    private void setDatiAcquisto(View layout, DatiAcquisto datiAcquisto) {
        if (datiAcquisto != null) {
        	((TextView) layout.findViewById(R.id.luogo)).setText(datiAcquisto.getLuogo());
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        	((TextView) layout.findViewById(R.id.data)).setText(sdf.format(datiAcquisto.getData()));
        	((TextView) layout.findViewById(R.id.prezzoValore)).setText(datiAcquisto.getPrezzo().getValore().toString());
        	((TextView) layout.findViewById(R.id.prezzoValuta)).setText(datiAcquisto.getPrezzo().getUnita());
        }
	}

	/**
     * Riempie i campi della descrizione per il dritto, il rovescio e il taglio
     * @param layout il layout in cui scrivere
     * @param descr i dati della descrizione
     */
    private void setDescrizione(View layout, Descrizioni descr) {
    	
    	String descrizione = "";
    	String fileImmagine = "";
    	
    	if (descr != null)	{
    		descrizione = descr.getDescrizione();
    	}
    	
    	((TextView) layout.findViewById(R.id.datiArtisticiDescr)).setText(descrizione);


    	ListView legendeList = (ListView) layout.findViewById(R.id.datiArtisticiLegende);
    	if (descr != null && descr.getLegenda() != null && descr.getLegenda().size() > 0) {
    		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();
    		String[] columnTags = new String[] {"row1", "row2"};
    		int[] columnIds = new int[] {R.id.legendaTesto, R.id.legendaScioglimento};

    		for (Legenda legenda : descr.getLegenda()) {
    			HashMap<String,String> map = new HashMap<String, String>();
    			//initialize row data
    			map.put(columnTags[0], legenda.getTesto());
    			map.put(columnTags[1], legenda.getScioglimento());
    			mylistData.add(map);
    		}
    		SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(), mylistData, R.layout.legende_list_view, columnTags , columnIds);
    		legendeList.setAdapter(arrayAdapter);
    	} else {
    		legendeList.setAdapter(null);	
    	}

    	ImageView immagine = ((ImageView) layout.findViewById(R.id.datiArtisticiImmagine));
    	if (descr != null) {
    		fileImmagine = Settings.getInstance().getImgDir()+"/"+descr.getFileImmagine();
    		final File imgFile = new File(fileImmagine);
    		if (imgFile.exists() && imgFile.isFile()) {
    			immagine.setImageBitmap(decodeSampledBitmapFromResource(fileImmagine, 200, 200));
    			/* long click causes image to be viewed by external viewer */
    			immagine.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					
    			    	try {
    			    		startActivity(Utils.getViewFileIntent(imgFile));
    			    	} catch (Exception ex) {
    			    		Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
    			    		ex.printStackTrace();
    			    	}				
    				}
    			});
    		} else {
    			immagine.setImageDrawable(null);
    		}
    		
    	} else {
    		immagine.setImageDrawable(null);
    	}


    }
    

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    	// Raw height and width of image
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;

    	if (height > reqHeight || width > reqWidth) {

    		// Calculate ratios of height and width to requested height and width
    		final int heightRatio = Math.round((float) height / (float) reqHeight);
    		final int widthRatio = Math.round((float) width / (float) reqWidth);

    		// Choose the smallest ratio as inSampleSize value, this will guarantee
    		// a final image with both dimensions larger than or equal to the
    		// requested height and width.
    		inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    	}

    	return inSampleSize;
    }    
    
    private static Bitmap decodeSampledBitmapFromResource(String fileImmagine, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileImmagine, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fileImmagine, options);
    }

    /**
     * Riempie i valori dei dati fisici
     * @param layout
     * @param dati
     */
    private void setDatiFisici(View layout, DatiFisici dati) {
        ((TextView) layout.findViewById(R.id.diametroValore)).setText(dati.getDiametro().getValore().toString());
        ((TextView) layout.findViewById(R.id.diametroUnita)).setText(dati.getDiametro().getUnita());
        ((TextView) layout.findViewById(R.id.pesoValore)).setText(dati.getPeso().getValore().toString());
        ((TextView) layout.findViewById(R.id.pesoUnita)).setText(dati.getPeso().getUnita());
        ((TextView) layout.findViewById(R.id.forma)).setText(dati.getForma());
        ((TextView) layout.findViewById(R.id.metallo)).setText(dati.getMetallo());
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putSerializable(ARG_COIN_ID, mCurrentId);
    }
    
    
    
}

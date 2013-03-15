package mirko.viviani.gestcoll.customadapter;

import java.io.File;
import java.util.ArrayList;

import mirko.viviani.gestcoll.R;
import mirko.viviani.gestcoll.settings.Settings;
import mirko.viviani.utils.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class LetteraturaListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<LetteraturaGroup> groups;
	
	
	public LetteraturaListAdapter(Context context, ArrayList<LetteraturaGroup> groups) {
		this.context = context;
		this.groups = groups;
	}
	
	public void addItem(LetteraturaDettagli item, LetteraturaGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		ArrayList<LetteraturaDettagli> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<LetteraturaDettagli> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		LetteraturaDettagli child = (LetteraturaDettagli) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.letteratura_libri_dettagli, null);
		}
		TextView tvTitolo = (TextView) view.findViewById(R.id.libroTitolo);
		tvTitolo.setText(child.getTitolo().toString());
		File libroFile = new File(Settings.getInstance().getBiblioDir() + File.separator + child.getFilename());
		/* mette l'icona del libro (se il file esiste) */
		if (libroFile.exists()) {
			tvTitolo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.small_book, 0, 0, 0);
		} else {
			tvTitolo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		}
		
		
		TextView tvAutori = (TextView) view.findViewById(R.id.libroAutori);
		tvAutori.setText(Utils.concatStringsWSep(child.getAutori(), ", "));
		
		return view;
	}

	public int getChildrenCount(int groupPosition) {
		ArrayList<LetteraturaDettagli> chList = groups.get(groupPosition).getItems();

		return chList.size();

	}

	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		LetteraturaGroup group = (LetteraturaGroup) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.letteratura_libri_list_view, null);
		}
		TextView tvNumero = (TextView) view.findViewById(R.id.libroNumero);
		tvNumero.setText(group.getNumero());
		TextView tvSigla = (TextView) view.findViewById(R.id.libroSigla);
		tvSigla.setText(group.getSigla());
		return view;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}



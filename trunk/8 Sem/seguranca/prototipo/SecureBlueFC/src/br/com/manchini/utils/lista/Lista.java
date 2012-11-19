/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.utils.lista;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class Lista<E> implements OnItemClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {

    private ListView listView;
    private final Context context;
    private ListAdapter listAdapter;
    private ArrayList<ItemClickListener> itemClickListeners;
//    private ArrayList<ListItem> itens;

    public Lista(Context context, ListAdapter<E> listAdapter) {
        this(context, listAdapter, null);
    }
    public Lista(Context context, ListAdapter<E> listAdapter,Button buttonCabecalho) {
        this.context = context;
        itemClickListeners = new ArrayList<ItemClickListener>();
        listView = new ListView(context);
        if (listAdapter==null) {
            listAdapter = new ListAdapter(context, true);
        }
        if(buttonCabecalho!=null){
            listView.addHeaderView(buttonCabecalho);
        }
        listView.setAdapter(listAdapter);
        this.listAdapter = listAdapter;
        init();
    }

    public void clear(){
        listAdapter.clear();
        listView.invalidateViews();
    }
    public ListItem<E> remove(int i){
        ListItem<E> remove = listAdapter.remove(i);
        listView.invalidateViews();
        return remove;
    }
    
    public Lista(Context context) {
        this(context, null);
    }

    private void init() {
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemSelectedListener(this);

    }

    public void addItemClickListener(ItemClickListener itemClickListener) {
        if (!itemClickListeners.contains(itemClickListener)) {
            itemClickListeners.add(itemClickListener);
        }
    }

    public void addToList(ListItem listItem) {
        listAdapter.addToList(listItem);
    }

    public void onItemClick(AdapterView<?> av, View view, int i, long l) {
        for (int j = 0; j < itemClickListeners.size(); j++) {
            itemClickListeners.get(j).onItemClick(i);
        }
    }

    public boolean onItemLongClick(AdapterView<?> av, View view, int i, long l) {
        for (int j = 0; j < itemClickListeners.size(); j++) {
            itemClickListeners.get(j).onItemLongClick(i);
        }
        return true;
    }

    public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
        if (listAdapter.getItem(i) instanceof ListItem) {
            ListItem item = (ListItem) listAdapter.getItem(i);
            Log.d("TRUCO", item.toString() + " Selected");
        }
    }

    public void onNothingSelected(AdapterView<?> av) {
        Log.d("TRUCO", "Nothing Selected");

    }

    public View getContentView() {
        return listView;
    }
    public void setAdapter(ListAdapter<E> adapter){
        listView.setAdapter(adapter);
        this.listAdapter=adapter;
    }

    public ListAdapter getAdapter() {
        return listAdapter;
    }
    
}

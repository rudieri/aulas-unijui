/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.utils.lista;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.manchini.secureBlueFC.R;
import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class ListAdapter<E> implements  android.widget.ListAdapter {

    private final Context context;
    private final ArrayList<ListItem<E>> lista;
    private final boolean comImagem;

    public ListAdapter(Context context, boolean comImagem) {
        lista = new ArrayList<ListItem<E>>();
        this.context = context;
        this.comImagem = comImagem;
    }

    public ListAdapter(Context context, ArrayList<ListItem<E>> lista, boolean comImagem) {
        this.context = context;
        this.lista = lista;
        this.comImagem = comImagem;
    }

    public void addToList(ListItem<E> listItem) {
        lista.add(listItem);
    }

    public void addToList(int icone, String name, String path) {
        ListItem listItem = new ListItem(context);
        listItem.addView(icone, name, path);
        addToList(listItem);
    }
    public void addToList( String name, String path) {
        ListItem listItem = new ListItem(context);
        listItem.addView(name, path);
        addToList(listItem);
    }
    public void addToList( String name, Object obj) {
        ListItem listItem = new ListItem(context);
        listItem.addView(name, obj);
        addToList(listItem);
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int i) {
        return lista.get(i);
    }

    public ListItem<E> getItemAt(int index){
        return lista.get(index);
    }

    public long getItemId(int i) {
        // Sem id
        return i;
    }

    public View getView(int i, View view, ViewGroup vg) {
        ListItem item = lista.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (comImagem) {
            v = inflater.inflate(R.layout.list_item_image, null);
            ImageView img = (ImageView) v.findViewById(R.item.img);
            img.setImageResource(item.getIcone());

        } else {
            v = inflater.inflate(R.layout.list_item, null);
        }
        TextView textNome = (TextView) v.findViewById(R.item.nome);
        textNome.setText(item.getNome());
        return v;
    }

    public void clear() {
        lista.clear();
    }
    public ListItem<E> remove(int index){
        return lista.remove(index);
    }

    public void registerDataSetObserver(DataSetObserver dso) {
//        System.out.println("WTF??? Observer (add)");
    }

    public void unregisterDataSetObserver(DataSetObserver dso) {
//        System.out.println("WTF??? Observer (remove)");
    }

    public boolean hasStableIds() {
        return true;
    }

    public int getItemViewType(int i) {
       return Adapter.NO_SELECTION;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int i) {
       return true;
    }
}

 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.utils.lista;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author rudieri
 */
public class ListItem<E> extends android.widget.LinearLayout {

    private final Context context;
    private String nome;
    private Object objeto;
    private int icone;
    private View contentView;

    public ListItem(Context context) {
        super(context);
        this.context = context;
        contentView = new View(context);
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        this.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
            }
        });
    }

    private void addView(ImageView icone, String descricao, Object obj) {
        this.objeto = obj;
        this.nome = descricao;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(HORIZONTAL);
        TextView textView = new TextView(context);
        textView.setText(descricao + "          ");
        textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        // add to layout
        if (icone != null) {
            layout.addView(icone);
        }
        layout.addView(textView);

        this.addView(layout);
    }

    public void addView(int icone, String descricao, Object obj) {
        this.icone = icone;
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(icone);
        addView(imageView, descricao, obj);
    }

    public void addView(String descricao, Object objeto) {
        addView(null, descricao, objeto);
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getNome() {
        return nome;
    }

    public int getIcone() {
        return icone;
    }

    public Object getObjeto() {
        return  objeto;
    }
}

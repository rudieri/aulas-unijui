/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author manchini
 */
public class ParametrosActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.parametros);
        
        
        Button btFritar = (Button)findViewById(R.dados.btTela);
        btFritar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               Intent it = new Intent(ParametrosActivity.this,TelaActivity.class);
               startActivity(it);
            }
        });
    }
}

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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 *
 * @author manchini
 */
public class ParametrosActivity extends Activity {
    static boolean comFlash = true;

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
        Button btCaseiro = (Button)findViewById(R.dados.btCaseiro);
        btCaseiro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               Intent it = new Intent(ParametrosActivity.this,TelaActivity.class);
               it.putExtra("modoCaseiro", true);
               startActivity(it);
            }
        });
        Button btHough = (Button)findViewById(R.dados.btHoug);
        btHough.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               Intent it = new Intent(ParametrosActivity.this,TelaActivity.class);
               it.putExtra("modoCaseiro", false);
               startActivity(it);
            }
        });
        ToggleButton btFlash = (ToggleButton)findViewById(R.dados.comFlash);
        btFlash.setChecked(comFlash);
        btFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                comFlash = arg1;
            }
        });
    }
}

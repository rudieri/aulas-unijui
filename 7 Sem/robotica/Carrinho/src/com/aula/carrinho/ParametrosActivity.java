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
import android.widget.EditText;
import android.widget.ToggleButton;

/**
 *
 * @author manchini
 */
public class ParametrosActivity extends Activity {

    public static boolean comFlash = true;
    public static boolean manual = false;
    public static boolean preview = false;
    public static Integer potencia = 99;
    public static Integer ignorarLinhas = 3;
    public static boolean rgb;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.parametros);

        final EditText editPotencia = (EditText) findViewById(R.dados.potencia);
        final EditText editIgnorar = (EditText) findViewById(R.dados.ignorarLinhas);

        Button btFritar = (Button) findViewById(R.dados.btTela);
        btFritar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                potencia = new Integer(editPotencia.getText().toString());
                ignorarLinhas = new Integer(editIgnorar.getText().toString());
                
                Intent it = new Intent(ParametrosActivity.this, TelaActivity.class);
                startActivity(it);
            }
        });
        ToggleButton btFlash = (ToggleButton) findViewById(R.dados.comFlash);
        btFlash.setChecked(comFlash);
        btFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                comFlash = arg1;
            }
        });
        ToggleButton btManual = (ToggleButton) findViewById(R.dados.manual);
        btManual.setChecked(manual);
        btManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                manual = arg1;
            }
        });
        ToggleButton btPreview = (ToggleButton) findViewById(R.dados.preview);
        btPreview.setChecked(preview);
        btPreview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                preview = arg1;
            }
        });
        ToggleButton btBrg = (ToggleButton) findViewById(R.dados.rgb);
        btBrg.setChecked(rgb);
        btBrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                rgb = arg1;
            }
        });

    }
}

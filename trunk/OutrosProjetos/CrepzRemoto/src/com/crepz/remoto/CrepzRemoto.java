package com.crepz.remoto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrepzRemoto extends Activity {

    private EditText editTextIP;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layoutVertical = new LinearLayout(this);
        layoutVertical.setOrientation(LinearLayout.VERTICAL);
        setContentView(layoutVertical);

        LinearLayout layoutIp = new LinearLayout(this);
        layoutVertical.addView(layoutIp);

        TextView textView = new TextView(this);
        textView.setText("IP do Crepz: ");
        editTextIP = new EditText(this);
        editTextIP.setText("192.168.43.62");
        layoutIp.addView(textView);
        layoutIp.addView(editTextIP, 200, 50);
        LinearLayout layoutBotoes = new LinearLayout(this);
        layoutVertical.addView(layoutBotoes);

        Button play = new Button(this);
        play.setText("Tocar/Pausar");
        Button next = new Button(this);
        next.setText("Próxima");
        Button prev = new Button(this);
        prev.setText("Anteiror");
        
        // adiciona os botões
        layoutBotoes.addView(play);
        layoutBotoes.addView(prev);
        layoutBotoes.addView(next);

        play.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                enviaMensagem("--play");
            }
        });
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                enviaMensagem("--next");
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                enviaMensagem("--prev");
            }
        });
   }

    private void enviaMensagem(String msg) {
        try {
            Socket socket = new Socket(editTextIP.getText().toString(), 3586);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(msg);
            printWriter.close();
            socket.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(CrepzRemoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrepzRemoto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

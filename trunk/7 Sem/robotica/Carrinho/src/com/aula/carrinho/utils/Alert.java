/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho.utils;

import android.app.AlertDialog;
import android.content.Context;


/**
 *
 * @author rudieri
 */
public class Alert extends AlertDialog {

    private OnShowListener showListener;
    private byte buttonCount = 0;

    public Alert(Context context) {
        super(context);
    }

    public Alert(Context context, int theme) {
        super(context, theme);
    }

    public Alert(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        if (showListener != null) {
            showListener.onShow(this);
        }

        super.show();
    }

    public void setOnShowListener(OnShowListener showListener) {
        this.showListener = showListener;
    }

    public OnShowListener getShowListener() {
        return showListener;
    }

//    public void setPositiveButton(String texto, OnClickListener listener){
//        setButton(texto, listener);
//    }
    @Override
    @Deprecated
    public void setButton(CharSequence text, OnClickListener listener) {
        super.setButton(text, listener);
    }

    public void addButton(CharSequence text, OnClickListener listener) {
        switch (buttonCount) {
            case 0:
                setButton(BUTTON1, text, listener);
                break;
            case 1:
                setButton(BUTTON2, text, listener);
                break;
            case 2:
                setButton(BUTTON3, text, listener);
                break;
        }
        buttonCount++;
    }

    @Override
    @Deprecated
    public void setButton(int whichButton, CharSequence text, OnClickListener listener) {
        super.setButton(whichButton, text, listener);
    }

    class Builder {
    }
}

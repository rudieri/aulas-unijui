/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.carregador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 */
public class CrepzTray {

    private final SystemTray tray;
    private final Carregador carregador;
    private final Image image;
    private ActionListener listenerRestaurar;
    private ActionListener listenerFechar;
    private final TrayIcon trayIcon;
    private PopupMenu popup;
    private MouseAdapter trayMouseEvents;
    private boolean onTray;
    private ActionListener listenerGravar;
    private MenuItem itemGravar;
    private MenuItem itemRestaurar;
    private MenuItem itemFechar;

    public CrepzTray(final Carregador carregador) throws Exception {
        if (!SystemTray.isSupported()) {
            throw new Exception("SystemTray não suportado.");
        }
        tray = SystemTray.getSystemTray();
        this.carregador = carregador;
        image = new ImageIcon(getClass().getResource("/com/crepz/img/icon.png")).getImage();
        createPopupEvents();
        createMouseEvents();
        trayIcon = new TrayIcon(image, "Crepz Recorder", popup);
        trayIcon.setImageAutoSize(true);
    }

    public void toTray() {
        try {
            trayIcon.addMouseListener(trayMouseEvents);
            trayIcon.addActionListener(listenerRestaurar);
            tray.add(trayIcon);
            onTray = true;
        } catch (AWTException ex) {
            Logger.getLogger(CrepzTray.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void someTray() {
        tray.remove(trayIcon);
        onTray = false;
    }

    public void someTray(int x, int y) {
        someTray();
        throw new UnsupportedOperationException("Não implementado ainda.");
    }

    public boolean isOnTray() {
        return onTray;
    }

    public void setToolTipText(String text) {
        trayIcon.setToolTip(text);
    }

    private void createPopupEvents() {
        listenerRestaurar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregador.ocultarIconeTray();
            }
        };
        listenerGravar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregador.gravar();

            }
        };
        listenerFechar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregador.sair();

            }
        };

        popup = new PopupMenu();

        itemGravar = new MenuItem("Gravar");
        itemRestaurar = new MenuItem("Restaurar");
        itemFechar = new MenuItem("Sair");

        itemGravar.addActionListener(listenerGravar);
        itemRestaurar.addActionListener(listenerRestaurar);
        itemFechar.addActionListener(listenerFechar);
        popup.add(itemGravar);
        popup.add(itemRestaurar);
        popup.add(itemFechar);
    }

    private void createMouseEvents() {
        trayMouseEvents = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    if (evt.getClickCount() == 1) {
                        carregador.ocultarIconeTray();
                    }
                }
            }
        };
    }

    public void setMenuGravarBloqueado(boolean bloqueado) {
        itemGravar.setEnabled(!bloqueado);
    }
}

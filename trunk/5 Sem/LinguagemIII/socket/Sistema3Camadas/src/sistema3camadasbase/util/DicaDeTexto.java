package sistema3camadasbase.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class DicaDeTexto  {

    private MyListModel listModel;
    private JList jList;
    private int linCont;
    private JScrollPane scrollArea;
    private Container parentPane;
    private JTextField parentTextField;
    private boolean existo;
    private boolean mouseIsOnList;
    public boolean mouseIsHoverList;

    public DicaDeTexto(Container painel) {
        parentPane = painel;
        createBox();


    }

    public void paintBox(JTextField base, ArrayList<String> lista) {
        mouseIsOnList = false;
        parentTextField = (JTextField) base;

        linCont = -1;
        Container pane = getContentPane();
        if (!(pane.getComponent(pane.getComponentCount() - 1).equals(scrollArea) || pane.getComponent(0).equals(scrollArea))) {
            pane.add(scrollArea, pane.getComponentCount());
            //   getContentPane().setComponentZOrder(scrollArea, getContentPane().getComponentCount() - 1);
            pane.setComponentZOrder(scrollArea, 0);
            int y = base.getLocationOnScreen().y - pane.getLocationOnScreen().y;
            scrollArea.setBounds(base.getX(), y + base.getHeight(), base.getWidth(), 60);
            //base.getParent().getLocationOnScreen().y
            base.addFocusListener(textFocus);
            base.addKeyListener(textAreaKeyEvent);
            scrollArea.setViewportView(jList);
            ///setAllEnable(false);
            getContentPane().repaint();
            jList.clearSelection();
        }
        jList.setModel(listModel);
        jList.setListData(lista.toArray());
        existo = true;

    }

    private void setAllEnable(boolean b) {
        Container pane = getContentPane();
        for (int i = 0; i < pane.getComponentCount(); i++) {
            Component c = pane.getComponent(i);
            if (!(c.equals(scrollArea) || c.equals(parentTextField))) {
                c.setEnabled(b);
            }
        }
    }

    private void createBox() {
        //textArea = new JTextArea();
        listModel = new MyListModel();
        jList = new JList();
        scrollArea = new JScrollPane();
        scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jList.setVisibleRowCount(5);
        scrollArea.setViewportView(jList);
        scrollArea.setBorder(new BevelBorder(BevelBorder.RAISED));
        jList.addMouseListener(listMouseClicked);
        jList.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jList.addMouseMotionListener(listMotionAdapter);


    }
    MouseAdapter listMouseClicked = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1) {
                if (e.getClickCount() == 2) {
                    seleciona();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                mouseIsOnList = true;
            }
        }
    };
    MouseMotionAdapter listMotionAdapter = new MouseMotionAdapter() {

        @Override
        public void mouseMoved(MouseEvent e) {
            jList.setSelectedIndex(jList.locationToIndex(e.getPoint()));
        }
    };
    KeyAdapter textAreaKeyEvent = new KeyAdapter() {

        @Override
        public void keyReleased(KeyEvent e) {
            //super.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                seleciona();
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (linCont != jList.getModel().getSize() - 1) {
                    linCont++;
                }
                jList.setSelectedIndex(linCont);
                jList.ensureIndexIsVisible(linCont);
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (linCont > 0) {
                    linCont--;
                }
                jList.setSelectedIndex(linCont);
                jList.ensureIndexIsVisible(linCont);
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                remove();
                return;
            }
        }
    };
    FocusAdapter textFocus = new FocusAdapter() {

        @Override
        public void focusLost(FocusEvent e) {
            super.focusLost(e);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(DicaDeTexto.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (mouseIsOnList) {
                seleciona();
            } else {
                remove();
            }



        }
    };

    public boolean existe() {
        return existo;
    }

    public void remove() {
//        setAllEnable(true);
        if (!existe() || parentTextField == null || parentTextField.getKeyListeners().length < 1) {
            return;
        }
        parentTextField.removeKeyListener(textAreaKeyEvent);
        parentTextField.removeFocusListener(textFocus);
        getContentPane().remove(scrollArea);
        getContentPane().repaint();
        existo = false;
    }

    private void seleciona() {
        if (jList.getSelectedIndex() != -1) {
            parentTextField.setText((String) jList.getModel().getElementAt(jList.getSelectedIndex()));
        }
        remove();
    }

    private Container getContentPane() {
        return parentPane;
    }
}

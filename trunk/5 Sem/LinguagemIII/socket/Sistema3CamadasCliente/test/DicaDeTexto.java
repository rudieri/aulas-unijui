
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class DicaDeTexto {

    private MyListModel listModel;
    private JList jList;
    private int linCont;
    private JScrollPane scrollArea;
    private Container parentPane;
    private JTextField parentTextField;

    public DicaDeTexto(Container painel) {
        parentPane = painel;
        createBox();


    }

    public void paintBox(JTextField base, ArrayList<String> lista) {
        parentTextField = (JTextField) base;
        linCont = -1;
        Container pane=getContentPane();
        if (!(pane.getComponent(pane.getComponentCount() - 1).equals(scrollArea)||pane.getComponent(0).equals(scrollArea))) {
            getContentPane().getComponents();
            getContentPane().add(scrollArea,getContentPane().getComponentCount());
            //   getContentPane().setComponentZOrder(scrollArea, getContentPane().getComponentCount() - 1);
             getContentPane().setComponentZOrder(scrollArea, 0);
            scrollArea.setBounds(base.getX(), base.getY() + base.getHeight(), base.getWidth(), 60);
            base.addKeyListener(textAreaKeyEvent);
            scrollArea.setViewportView(jList);
            ///setAllEnable(false);
            getContentPane().repaint();
        }
        jList.setModel(listModel);
        jList.setListData(lista.toArray());

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

    private void remove() {
//        setAllEnable(true);
        parentTextField.removeKeyListener(textAreaKeyEvent);
        getContentPane().remove(scrollArea);
        getContentPane().repaint();
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

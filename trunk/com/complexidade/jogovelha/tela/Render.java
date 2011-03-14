/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.complexidade.jogovelha.tela;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import com.complexidade.jogovelha.marcacao.Marca;

/**
 *
 * @author rudieri
 */
public class Render extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        Marca panel = null;
        try {
            panel = (Marca) value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (panel != null) {
            panel.setBounds(table.getCellRect(0, 0, true));
            add(panel, BorderLayout.CENTER);
        }
        return this;
    }
}

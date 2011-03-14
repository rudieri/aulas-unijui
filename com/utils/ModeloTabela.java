/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rudieri
 */
public class ModeloTabela extends DefaultTableModel {

    private HashMap<Integer, Boolean> cols;

    @Override
    public boolean isCellEditable(int row, int column) {
        return cols.get(column);
    }

    @Override
    public void addColumn(Object columnName) {
        addColumn(columnName, true);
    }

    public void addColumn(Object columnName, boolean editable) {
        super.addColumn(columnName);
        cols.put(getColumnCount() - 1, editable);
    }

    @Override
    public void setColumnCount(int columnCount) {
        super.setColumnCount(columnCount);
        cols = new HashMap<Integer, Boolean>();
        for (int i = 0; i < columnCount; i++) {
            cols.put(i, true);
        }
    }

    public void setColumnEditable(int column, boolean editable) {
        if (column >= getColumnCount()) {
            throw new IndexOutOfBoundsException(String.valueOf(column));
        }
        cols.put(column, editable);
    }
}

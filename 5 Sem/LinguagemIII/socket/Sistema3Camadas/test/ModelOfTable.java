
import javax.swing.table.AbstractTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class ModelOfTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private Object[] linhas;
    private String[] colunas;
    private int nroLinhas;
    private int maxLinhas=10;
    private int incrementador=8;

    public ModelOfTable(String[] colunas) {
        this.colunas = colunas;
        init();
    }

    public ModelOfTable(int maxLinhas) {
        this.maxLinhas = maxLinhas;
        init();
    }

    public ModelOfTable(int nroLinhas, int incrementador) {
        this.nroLinhas = nroLinhas;
        this.incrementador = incrementador;
    }
    
    

    public ModelOfTable() {
        init();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    private void init() {
        nroLinhas = 0;
        linhas = new Object[maxLinhas];
        colunas = new String[0];
    }

    public void addColumn(String st) {
        String[] oldString = colunas.clone();
        colunas = new String[colunas.length + 1];
        System.arraycopy(oldString, 0, colunas, 0, oldString.length);
        colunas[colunas.length - 1] = st;
    }

    public void addRow(Object[] rowData) {
        if (nroLinhas >= linhas.length) {
            Object[] oldLinhas = linhas.clone();
            if (nroLinhas > maxLinhas) {
                throw new ArrayIndexOutOfBoundsException("Limite máximo de linhas atingido: " + nroLinhas + " >=" + maxLinhas);
            }
            linhas = new Object[linhas.length * incrementador];

            System.arraycopy(oldLinhas, 0, linhas, 0, oldLinhas.length);

        }
        linhas[nroLinhas] = rowData;
        nroLinhas++;

    }

    public void addRowFast(Object[] rowData) {
        linhas[nroLinhas] = rowData;
        nroLinhas++;
    }

    public int getRowCount() {
        return nroLinhas;
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public void setNumeroMaximoDeLinhas(int num) {
        maxLinhas = num;
    }

    public Object getValueAt(int linha, int coluna) {
        if (linha >= linhas.length) {
            throw new IndexOutOfBoundsException("Número limite de linhas atingido: " + linha + " >= " + linhas.length);
        }
        if (coluna >= colunas.length) {
            throw new IndexOutOfBoundsException("Número limite de colunas atingido: " + coluna + " >= " + colunas.length);
        }
        return ((Object[]) linhas[linha])[coluna];
    }

    public Object getValueAtFast(int linha, int coluna) {
        return ((Object[]) linhas[linha])[coluna];
    }

    public boolean isEmpty() {
        return linhas.length == 0;
    }
}

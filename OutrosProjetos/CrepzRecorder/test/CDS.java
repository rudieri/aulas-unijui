
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c90
 */
public class CDS {
    private final ArrayList<String> cols;
    private final ArrayList<Object[]> dados = new ArrayList<>();
    private final int[] pk;

    public CDS(ArrayList<String> colunas, int... pk) {
        this.cols = colunas;
        this.pk = pk;
    }
    public CDS(String[] colunas, int... pk) {
        cols = new ArrayList<>(colunas.length);
        Collections.addAll(cols, colunas);
        this.pk = pk;
    }
    
    public void insertRow(Object... row){
        checkRowSize(row);
        dados.add(row);
    }
    
    public Object getValueAt(int linha, int coluna){
        return dados.get(linha)[coluna];
    }

    
    
    private void checkRowSize(Object[] row){
        if(row.length != cols.size()){
            throw new IllegalArgumentException("O número de colunas deve ser igual.");
        }
    }
    
    
    public static void main(String[] args) {
        CDS cds = new CDS(new String[]{"xxx", "YYY"}, 0);
        cds.insertRow(1, 2, 3);
    }
}

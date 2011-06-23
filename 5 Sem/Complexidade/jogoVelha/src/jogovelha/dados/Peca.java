/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

/**
 *
 * @author manchini
 */
public enum Peca {
    NULO(0),JOGADOR(1),COMPUTADOR(2);
    
    private int id;
    
    
    private Peca(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    
    
}

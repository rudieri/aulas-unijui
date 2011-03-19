/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogovelha.marcacao;

/**
 *
 * @author rudieri
 */
public class Ponto {
    public byte linha;
    public byte coluna;
    private byte limiteLinhas=3;
    private byte limiteColunas=3;

    public Ponto(byte linha, byte coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
     public Ponto(int linha, int coluna) {
        this.linha = (byte) linha;
        this.coluna = (byte) coluna;
    }
    public void somar(byte n){
        n+=coluna+getLimiteLinhas()*linha;
        byte nLinha=(byte) (n / limiteLinhas);
        byte nColuna=(byte) (n % limiteColunas);
        if (nLinha>=limiteLinhas) {
            nLinha=(byte) (nLinha % limiteLinhas);
        }
        linha=nLinha;
        coluna=nColuna;

    }
    public void somar(int n){
        somar((byte)n);
    }

    /**
     * @return the limiteLinhas
     */
    public byte getLimiteLinhas() {
        return limiteLinhas;
    }

    /**
     * @param limiteLinhas the limiteLinhas to set
     */
    public void setLimiteLinhas(byte limiteLinhas) {
        this.limiteLinhas = limiteLinhas;
    }

    /**
     * @return the limiteColunas
     */
    public byte getLimiteColunas() {
        return limiteColunas;
    }

    /**
     * @param limiteColunas the limiteColunas to set
     */
    public void setLimiteColunas(byte limiteColunas) {
        this.limiteColunas = limiteColunas;
    };
    
     
    
    
}

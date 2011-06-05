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
    private final byte limiteLinhas = 3;
    private final byte limiteColunas = 3;

    public Ponto(byte linha, byte coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public Ponto(int linha, int coluna) {
        this.linha = (byte) linha;
        this.coluna = (byte) coluna;
    }

    public void somar(byte n) {
        n += Math.abs(coluna) + getLimiteLinhas() * Math.abs(linha);
        byte nLinha = (byte) (n / limiteLinhas);
        byte nColuna = (byte) (n % limiteColunas);
        if (nLinha >= limiteLinhas) {
            nLinha = (byte) (nLinha % limiteLinhas);
        }
        linha = nLinha;
        coluna = nColuna;
    }
    public byte getValor(){
        if (linha==coluna) {
            return 3;
        }else{
            return 1;
        }
    }
    public Ponto somar(int n){
        somar((byte)n);
        return this;
    }
    public boolean isCanto(){
        return linha%2==0&&coluna%2==0;
    }
    public boolean isCenter(){
        return linha==1&&coluna==1;
    }
    public boolean isTranposta(){
        return linha>0&&coluna>0;
    }

    /**
     * @return the limiteLinhas
     */
    public byte getLimiteLinhas() {
        return limiteLinhas;
    }

 

    /**
     * @return the limiteColunas
     */
    public byte getLimiteColunas() {
        return limiteColunas;
    }

 

    ;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ponto)) {
            return false;
        }
        Ponto p = (Ponto) o;
        if (this.coluna == p.coluna && this.linha == p.linha) {
            return true;
        } else {
            return false;
    
        }
    }

    @Override
    public String toString() {
        return "["+linha+","+coluna+"]";
    }
    
    
}

package com.crepz.log;

/**
 *
 * @author c90
 */
public enum NivelLog {
    DEBUG(0, "Debug"),
    INFO(1, "Info"),
    ERROR(2, "Error");
    private int nivel;
    private String nome;

    private NivelLog(int nivel, String nome) {
        this.nivel = nivel;
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }
    public static String[] getNomesFakes() {
        NivelLog[] values = values();
        String[] nomes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            NivelLog nivelLog = values[i];
            nomes[i] = nivelLog.nome;
        }
        return nomes;
    }
    
}

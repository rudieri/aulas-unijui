package com.crepz.log;

/**
 *
 * @author c90
 */
public enum NivelLog {
    DEBUG(0),
    INFO(1),
    ERROR(2);
    private int nivel;

    private NivelLog(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
    
}

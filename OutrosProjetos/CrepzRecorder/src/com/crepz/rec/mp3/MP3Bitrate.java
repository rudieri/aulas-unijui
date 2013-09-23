/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.rec.mp3;

/**
 *
 * @author rudieri
 */
public enum MP3Bitrate {
     _32("32"),
     _40("40"),
     _48("48"), 
     _56("56"),
     _64("64"),
     _80("80"),
     _96("96"),
     _112("112"), 
     _128("128"), 
     _160("160"), 
     _192("192"),
     _224("224"),
     _256("256"),
     _320("320");
     
     private String biterate;

    private MP3Bitrate(String biterate) {
        this.biterate = biterate;
    }

    /**
     * @return the biterate
     */
    public String getBiterate() {
        return biterate;
    }

    @Override
    public String toString() {
        return biterate;
    }

     public static MP3Bitrate fromBitrate(String bitrate){
        MP3Bitrate[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getBiterate().equals(bitrate)) {
                return values[i];
            }
        }
        return null;
    }
    
     
}

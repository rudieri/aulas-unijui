/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.rec.mp3;

/**
 *
 * @author rudieri
 */
public enum MP3Quality {
    LOWEST("lowest"), LOW("low"), MIDDLE("middle"), HIGH("high"), HIGHEST("highest");
    private String quality;

    private MP3Quality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return quality;
    }
    
    public static MP3Quality fromQuality(String quality){
        MP3Quality[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getQuality().equals(quality)) {
                return values[i];
            }
        }
        return null;
    }
    
    
    
}

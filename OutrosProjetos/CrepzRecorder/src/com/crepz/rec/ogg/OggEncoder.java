/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.rec.ogg;

import com.crepz.log.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.xiph.libogg.ogg_packet;
import org.xiph.libogg.ogg_page;
import org.xiph.libogg.ogg_stream_state;
import org.xiph.libvorbis.vorbis_block;
import org.xiph.libvorbis.vorbis_comment;
import org.xiph.libvorbis.vorbis_dsp_state;
import org.xiph.libvorbis.vorbis_info;
import org.xiph.libvorbis.vorbisenc;

/**
 *
 * @author rudieri
 */
public class OggEncoder {
    private static final int READ = 1024;
    private static final byte[] readbuffer = new byte[READ * 4 + 44];
    
    public static void converter(File origem){
        String pathOrigem = origem.getAbsolutePath();
        File destino = new File(pathOrigem.substring(0, pathOrigem.lastIndexOf('.'))+".ogg");
        boolean eos = false;

        vorbis_info vi = new vorbis_info();

        vorbisenc encoder = new vorbisenc();

        if (!encoder.vorbis_encode_init_vbr(vi, 2, 44100, .3f)) {
            Log.error("Failed to Initialize vorbisenc");
            return;
        }

        vorbis_comment vc = new vorbis_comment();
        vc.vorbis_comment_add_tag("ENCODER", "Java Vorbis Encoder");

        vorbis_dsp_state vd = new vorbis_dsp_state();

        if (!vd.vorbis_analysis_init(vi)) {
            Log.error("Failed to Initialize vorbis_dsp_state");
            return;
        }

        vorbis_block vb = new vorbis_block(vd);

        java.util.Random generator = new java.util.Random();  // need to randomize seed
        ogg_stream_state os = new ogg_stream_state(generator.nextInt(256));

        Log.debug("Writing header.");
        ogg_packet header = new ogg_packet();
        ogg_packet header_comm = new ogg_packet();
        ogg_packet header_code = new ogg_packet();

        vd.vorbis_analysis_headerout(vc, header, header_comm, header_code);

        os.ogg_stream_packetin(header); // automatically placed in its own page
        os.ogg_stream_packetin(header_comm);
        os.ogg_stream_packetin(header_code);

        ogg_page og = new ogg_page();
        ogg_packet op = new ogg_packet();

        try {

            FileOutputStream fos = new FileOutputStream(destino);

            while (!eos) {

                if (!os.ogg_stream_flush(og)) {
                    break;
                }

                fos.write(og.header, 0, og.header_len);
                fos.write(og.body, 0, og.body_len);
            }
            Log.debug("Done.\n");

            FileInputStream fin = new FileInputStream(origem);

            Log.debug("Encoding.");
            while (!eos) {

                int i;
                int bytes = fin.read(readbuffer, 0, READ * 4); // stereo hardwired here

                int break_count = 0;

                if (bytes == 0) {

					// end of file.  this can be done implicitly in the mainline,
                    // but it's easier to see here in non-clever fashion.
                    // Tell the library we're at end of stream so that it can handle
                    // the last frame and mark end of stream in the output properly
                    vd.vorbis_analysis_wrote(0);

                } else {

					// data to encode
                    // expose the buffer to submit data
                    float[][] buffer = vd.vorbis_analysis_buffer(READ);

                    // uninterleave samples
                    for (i = 0; i < bytes / 4; i++) {
                        buffer[0][vd.pcm_current + i] = ((readbuffer[i * 4 + 1] << 8) | (0x00ff & (int) readbuffer[i * 4])) / 32768.f;
                        buffer[1][vd.pcm_current + i] = ((readbuffer[i * 4 + 3] << 8) | (0x00ff & (int) readbuffer[i * 4 + 2])) / 32768.f;
                    }

                    // tell the library how much we actually submitted
                    vd.vorbis_analysis_wrote(i);
                }

				// vorbis does some data preanalysis, then divvies up blocks for more involved 
                // (potentially parallel) processing.  Get a single block for encoding now
                while (vb.vorbis_analysis_blockout(vd)) {

					// analysis, assume we want to use bitrate management
                    vb.vorbis_analysis(null);
                    vb.vorbis_bitrate_addblock();

                    while (vd.vorbis_bitrate_flushpacket(op)) {

                        // weld the packet into the bitstream
                        os.ogg_stream_packetin(op);

                        // write out pages (if any)
                        while (!eos) {

                            if (!os.ogg_stream_pageout(og)) {
                                break_count++;
                                break;
                            }

                            fos.write(og.header, 0, og.header_len);
                            fos.write(og.body, 0, og.body_len);

							// this could be set above, but for illustrative purposes, I do
                            // it here (to show that vorbis does know where the stream ends)
                            if (og.ogg_page_eos() > 0) {
                                eos = true;
                            }
                        }
                    }
                }
            }

            fin.close();
            fos.close();

            Log.debug("Done.\n");
        }catch(Exception ex){
            Log.error("Erro ao converter para Ogg. ", ex);
        }
    }
}

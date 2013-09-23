package com.crepz.rec.mp3;

/*
 *	Mp3Encoder.java
 */

/*
 *  Copyright (c) 2000 by Florian Bomers <florian@bome.com>
 *
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
import com.crepz.log.Log;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.TDebug;
import org.tritonus.share.sampled.AudioFileTypes;
import org.tritonus.share.sampled.Encodings;

public class Mp3Encoder {

    private static boolean dumpExceptions = true;
    // currently, there is no convenient method in Java Sound to specify non-standard Encodings.
    // using tritonus' proposal to overcome this.
    private static final AudioFormat.Encoding MPEG1L3 = Encodings.getEncoding("MPEG1L3");
    private static final AudioFileFormat.Type MP3 = AudioFileTypes.getType("MP3", "mp3");

    private static AudioInputStream getInStream(String filename)
            throws IOException {
        File file = new File(filename);
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            if (dumpExceptions) {
                e.printStackTrace(System.err);
            }
        }
        if (ais == null) {
            throw new IOException("Cannot open \"" + filename + "\"");
        }
        return ais;
    }

    public static String stripExtension(String filename) {
        int ind = filename.lastIndexOf('.');
        if (ind == -1
                || ind == filename.length()
                || filename.lastIndexOf(File.separator) > ind) {
            // when dot is at last position,
            // or a slash is after the dot, there isn't an extension
            return filename;
        }
        return filename.substring(0, ind);
    }

    /* first version. Remains here for documentation how to
     * get a stream with complete description of the target format.
     */
    public static AudioInputStream getConvertedStream2(
            AudioInputStream sourceStream,
            AudioFormat.Encoding targetEncoding)
            throws Exception {
        AudioFormat sourceFormat = sourceStream.getFormat();
            Log.debug("Input format: " + sourceFormat);
        // build the output format
        AudioFormat targetFormat = new AudioFormat(
                targetEncoding,
                sourceFormat.getSampleRate(),
                AudioSystem.NOT_SPECIFIED,
                sourceFormat.getChannels(),
                AudioSystem.NOT_SPECIFIED,
                AudioSystem.NOT_SPECIFIED,
                false); // endianness doesn't matter
        // construct a converted stream
        AudioInputStream targetStream = null;
        if (!AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
                Log.debug("Direct conversion not possible.");
                Log.debug("Trying with intermediate PCM format.");
            AudioFormat intermediateFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(),
                    16,
                    sourceFormat.getChannels(),
                    2 * sourceFormat.getChannels(), // frameSize
                    sourceFormat.getSampleRate(),
                    false);
            if (AudioSystem.isConversionSupported(intermediateFormat, sourceFormat)) {
                // intermediate conversion is supported
                sourceStream = AudioSystem.getAudioInputStream(intermediateFormat, sourceStream);
            }
        }
        targetStream = AudioSystem.getAudioInputStream(targetFormat, sourceStream);
        if (targetStream == null) {
            throw new Exception("conversion not supported");
        }
            Log.debug("Got converted AudioInputStream: " + targetStream.getClass().getName());
        Log.info("Output format: " + targetStream.getFormat());
        return targetStream;
    }

    public static AudioInputStream getConvertedStream(
            AudioInputStream sourceStream,
            AudioFormat.Encoding targetEncoding)
            throws Exception {
        AudioFormat sourceFormat = sourceStream.getFormat();
        Log.debug("Input format: " + sourceFormat);

        // construct a converted stream
        AudioInputStream targetStream = null;
        if (!AudioSystem.isConversionSupported(targetEncoding, sourceFormat)) {
            Log.debug("Direct conversion not possible.");
            Log.debug("Trying with intermediate PCM format.");
            AudioFormat intermediateFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(),
                    16,
                    sourceFormat.getChannels(),
                    2 * sourceFormat.getChannels(), // frameSize
                    sourceFormat.getSampleRate(),
                    false);
            if (AudioSystem.isConversionSupported(intermediateFormat, sourceFormat)) {
                // intermediate conversion is supported
                sourceStream = AudioSystem.getAudioInputStream(intermediateFormat, sourceStream);
            }
        }
        targetStream = AudioSystem.getAudioInputStream(targetEncoding, sourceStream);
        if (targetStream == null) {
            throw new Exception("conversion not supported");
        }
        Log.debug("Got converted AudioInputStream: " + targetStream.getClass().getName());
        Log.info("Output format: " + targetStream.getFormat());
        return targetStream;
    }

    public static int writeFile(String inFilename) {
        int writtenBytes = -1;
        try {
            AudioFileFormat.Type targetType = MP3;
            AudioInputStream ais = getInStream(inFilename);
            ais = getConvertedStream(ais, MPEG1L3);

            // construct the target filename
            String outFilename = stripExtension(inFilename) + "." + targetType.getExtension();

            // write the file
            Log.debug("Writing " + outFilename + "...");
            writtenBytes = AudioSystem.write(ais, targetType, new File(outFilename));
            ais.close();
            Log.debug("Effective parameters of output file:");
            try {
                String version = System.getProperty("tritonus.lame.encoder.version", "");
                if (!"".equals(version)) {
                    Log.debug("  Version      = " + version);
                }
                Log.debug("  Quality      = " + System.getProperty("tritonus.lame.effective.quality", "<none>"));
                Log.debug("  Bitrate      = " + System.getProperty("tritonus.lame.effective.bitrate", "<none>"));
                Log.debug("  Channel Mode = " + System.getProperty("tritonus.lame.effective.chmode", "<none>"));
                Log.debug("  VBR mode     = " + System.getProperty("tritonus.lame.effective.vbr", "<none>"));
                Log.debug("  Sample rate  = " + System.getProperty("tritonus.lame.effective.samplerate", "<none>"));
                Log.debug("  Encoding     = " + System.getProperty("tritonus.lame.effective.encoding", "<none>"));
            } catch (Throwable t1) {
            }
        } catch (Throwable t) {
            Log.error("Erro!", t);
            Log.debug("Error: " + t.getMessage());
        }
        return writtenBytes;
    }

    public static void setDumpExceptions(boolean dumpExceptions) {
        Mp3Encoder.dumpExceptions = dumpExceptions;
    }

    public static boolean isDumpExceptions() {
        return dumpExceptions;
    }

    public static void setTraceConverters(boolean traceConverters) {
        TDebug.TraceAudioConverter = traceConverters;
    }

    public static boolean isTraceConverters() {
        return TDebug.TraceAudioConverter;
    }

    public static void setVBR(boolean vbr) {
        if (vbr) {
            System.setProperty("tritonus.lame.vbr", "true");
        } else {
            System.setProperty("tritonus.lame.vbr", "false");
        }
    }

    public static boolean isVBR() {
        return System.getProperty("tritonus.lame.vbr", "false").equals("true");
    }

    public static void setQuality(MP3Quality mP3Quality) {
        System.setProperty("tritonus.lame.quality", mP3Quality.getQuality());
    }

    public static MP3Quality getQuality() {
        String property = System.getProperty("tritonus.lame.quality");
        if (property != null) {
            return MP3Quality.fromQuality(property);
        }
        return null;
    }

    public static void setBitrate(MP3Bitrate mP3Bitrate) {
        System.setProperty("tritonus.lame.bitrate", mP3Bitrate.getBiterate());
    }

    public static MP3Bitrate getBitrate() {
        String property = System.getProperty("tritonus.lame.bitrate");
        if (property != null) {
            return MP3Bitrate.fromBitrate(property);
        }
        return null;
    }

    // returns the first index in args where the files start
    public static int parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }
        // parse options
        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.equals("--help")) {
                    usage();
                }
                if (arg.length() > 3 || arg.length() < 2 || !arg.startsWith("-")) {
                    return i;
                }
                char cArg = arg.charAt(1);
                // options without parameter
                if (cArg == 'v') {
                    //DEBUG = true;
                    continue;
                } else if (cArg == 'e') {
                    dumpExceptions = true;
                    continue;
                } else if (cArg == 't') {
                    org.tritonus.share.TDebug.TraceAudioConverter = true;
                    continue;
                } else if (cArg == 's') {
//                    quiet = true;
                    continue;
                } else if (cArg == 'V') {
                    try {
                        System.setProperty("tritonus.lame.vbr", "true");
                    } catch (Throwable t1) {
                    }
                    continue;
                } else if (cArg == 'h') {
                    usage();
                }
                // options with parameter
                if (args.length < i + 2) {
                    throw new Exception("Missing parameter or unrecognized option " + arg + ".");
                }
                String param = args[i + 1];
                i++;
                switch (cArg) {
                    case 'q':
                        try {
                            System.setProperty("tritonus.lame.quality", param);
                        } catch (Throwable t2) {
                        }
                        break;
                    case 'b':
                        try {
                            System.setProperty("tritonus.lame.bitrate", param);
                        } catch (Throwable t3) {
                        }
                        break;
                    default:
                        throw new Exception("Unrecognized option " + arg + ".");
                }
            }
            throw new Exception("No input file(s) are given.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return 0; // statement not reached
    }

    public static void converter(String nomeArquivo) {
//    String fileNameIn = "/home/rudieri/Música/teste.wav";

//        String[] args = new String[3];
//        args[0] = "-v";
//        args[1] = "-t";
//        args[2] = nomeArquivo;
//                args[3] = "";
//                args[4] = "";
//        int firstFileIndex = parseArgs(args);
        int inputFiles = 0;
        int success = 0;
        long totalTime = System.currentTimeMillis();
        int bytes = writeFile(nomeArquivo);
        inputFiles++;
        if (bytes >= 0) {
            if (bytes > 0) {
                success++;
            }

        }
        totalTime = System.currentTimeMillis() - totalTime;
        Log.debug("O arquivo " + nomeArquivo + " foi convertido com sucesso em "
                + (totalTime / 60000) + "m " + ((totalTime / 1000) % 60) + "s  ("
                + (totalTime / 1000) + "s).");
    }

    public static void main(String[] args) {
        //try {
        //	System.out.println("Librarypath=" + System.getProperty("java.library.path", ""));
        //} catch (Throwable t) {}

        String fileNameIn = "/home/rudieri/Música/teste.wav";
        args = new String[3];
        args[0] = "-v";
        args[1] = "-t";
        args[2] = fileNameIn;
//                args[3] = "";
//                args[4] = "";
        int firstFileIndex = parseArgs(args);
        int inputFiles = 0;
        int success = 0;
        long totalTime = System.currentTimeMillis();
        for (int i = firstFileIndex; i < args.length; i++) {
            long time = System.currentTimeMillis();
            int bytes = writeFile(args[i]);
            time = System.currentTimeMillis() - time;
            inputFiles++;
            if (bytes >= 0) {
                if (bytes > 0) {
                    success++;
                }
                Log.debug("Wrote " + bytes + " bytes in "
                        + (time / 60000) + "m " + ((time / 1000) % 60) + "s "
                        + (time % 1000) + "ms ("
                        + (time / 1000) + "s).");
            }
        }
        totalTime = System.currentTimeMillis() - totalTime;
        Log.debug("From " + inputFiles + " input file" + (inputFiles == 1 ? "" : "s") + ", "
                + success + " file" + (success == 1 ? " was" : "s were") + " converted successfully in "
                + (totalTime / 60000) + "m " + ((totalTime / 1000) % 60) + "s  ("
                + (totalTime / 1000) + "s).");
        System.exit(0);
    }

    /**
     * Display a message of how to call this program.
     */
    public static void usage() {
        Log.info("Mp3Encoder - convert audio files to mp3 (layer III of MPEG 1, MPEG 2 or MPEG 2.5");
        Log.info("java Mp3Encoder <options> <source file> [<source file>...]");
        Log.info("The output file(s) will be named like the source file(s) but");
        Log.info("with mp3 file extension.");
        Log.info("");
        Log.info("You need LAME 3.88 or later. Get it from http://sourceforge.net/projects/lame/");
        Log.info("");
        Log.info("<options> may be a combination of the following:");
        Log.info("-q <quality>  Quality of output mp3 file. In VBR mode, this affects");
        Log.info("              the size of the mp3 file. (Default middle)");
        Log.info("              One of: lowest, low, middle, high, highest");
        Log.info("-b <bitrate>  Bitrate in KBit/s. Useless in VBR mode. (Default 128)");
        Log.info("              One of: 32 40 48 56 64 80 96 112 128 160 192 224 256 320 (MPEG1)");
        Log.info("              Or: 8 16 24 32 40 48 56 64 80 96 112 128 144 160 (MPEG2 and MPEG2.5");
        Log.info("-V            VBR (variable bit rate) mode. Slower, but potentially better");
        Log.info("              quality. (Default off)");
        Log.info("-v            Be verbose.");
        Log.info("-s            Be silent.");
        Log.info("-e            Debugging: Dump stack trace of exceptions.");
        Log.info("-t            Debugging: trace execution of converters.");
        Log.info("-h | --help   Show this message.");
        System.exit(1);
    }
}
/**
 * * Mp3Encoder.java **
 */

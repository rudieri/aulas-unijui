/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.utils;

import java.io.*;

/**
 *
 * @author manchini
 */
public class FileUtils {

    /**
     * Metodo Que le um Arquivo é retorna seu conteudo Generico
     * @param arquivo
     * @return
     * @throws Exception
     */
    public static StringBuilder leArquivo(File arquivo) throws Exception {
        return leArquivoCodificacao(arquivo, "UTF-8");

    }

    /**
     * Metodo Que grava um Arquivo Generico
     * @param conteudo
     * @param destino
     * @throws Exception
     */
    public static void gravaArquivo(CharSequence conteudo, File destino) throws Exception {
        gravaArquivo(conteudo, destino, false);
    }

    /**
     * Metodo Que grava um Arquivo Generico
     * @param conteudo
     * @param destino
     * @param addFinal
     * @return
     * @throws IOException
     */
    public static void gravaArquivo(CharSequence conteudo, File destino, boolean addFinal) throws Exception {
        gravaArquivoCodificacao(conteudo, destino, "UTF-8", addFinal);
    }

    /**
     * Le Arquivo Passando Codificação
     * @param arquivo
     * @param codificacao
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static StringBuilder leArquivoCodificacao(File arquivo, String codificacao) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(1024);
        FileInputStream leitor = new FileInputStream(arquivo);
        InputStreamReader in = new InputStreamReader(leitor, codificacao);
        BufferedReader leitorBuf = new BufferedReader(in);
        String line;
        while ((line = leitorBuf.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        in.close();
        leitor.close();
        return stringBuilder;
    }

      public static StringBuilder leArquivoCodificacao(InputStream arquivo, String codificacao) throws Exception {
        StringBuilder stringBuffer = new StringBuilder(1024);

        InputStreamReader in = new InputStreamReader(arquivo, codificacao);
        BufferedReader leitorBuf = new BufferedReader(in);
        String line = null;
        while ((line = leitorBuf.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }
        in.close();
        return stringBuffer;
    }

    /**
     * Grava Arquivo passando Codificacao
     * @param conteudo
     * @param destino
     * @param codificacao
     * @return
     * @throws Exception
     */
    public static boolean gravaArquivoCodificacao(CharSequence conteudo, File destino, String codificacao, boolean addFinal) throws Exception {
        if (!destino.getParentFile().exists()) {
            destino.getParentFile().mkdirs();
        }
        if (!destino.exists()) {
            destino.createNewFile();
        }
        FileOutputStream escritor = new FileOutputStream(destino, addFinal);
        OutputStreamWriter escritorBuf = new OutputStreamWriter(escritor, codificacao);
        escritorBuf.append(conteudo);
        escritorBuf.close();
        escritor.close();
        return true;
    }

    public static void main(String arqs[]) {
        try {
            StringBuilder st = leArquivo(new File("C:/JPlayer/teste.mp3"));
            System.out.println(st);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

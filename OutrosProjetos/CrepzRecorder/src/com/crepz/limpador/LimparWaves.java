/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.limpador;

import com.crepz.config.Configuracaoes;
import com.crepz.tela.JGravador;
import com.crepz.utils.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rudieri
 */
public class LimparWaves {

    private static ArrayList<File> agendaDeletar;
    private static final FileFilter waveFileFilter;
    private static final Thread thread;
    private static final File fileBase;
    private static boolean estaRodando = false;
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat("dd");
    public static final SimpleDateFormat simpleDateFormatAno = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat simpleDateFormatNomeArquivo = new SimpleDateFormat("HH-mm-ss");

    public static String getAno(Calendar calendario) {
        return simpleDateFormatAno.format(calendario.getTime());
    }

    public static String getDia(Calendar calendario) {
        return simpleDateFormatDia.format(calendario.getTime());
    }

    public static String getMes(Calendar calendario) {
        return calendario.getDisplayName(Calendar.MONTH, Calendar.ALL_STYLES, Locale.getDefault());
    }

    public static String getData(Calendar calendar) {
        return simpleDateFormat.format(calendar.getTime());
    }
    public static String getNomeArquivo(Calendar calendar) {
        return simpleDateFormatNomeArquivo.format(calendar.getTime());
    }

    private LimparWaves() {
    }

    static {
        fileBase = new File(Configuracaoes.getString(Configuracaoes.CONF_PASTA_SALVAR));
        agendaDeletar = new ArrayList();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    excluirMesesAnteirores();
                    verificarDiretorios(fileBase);
                    excluirArquivosAgendados();
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LimparWaves.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("Erro no sleep!!!");
                    }
                }
            }
        });
        waveFileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() || !pathname.getName().endsWith(".mp3");
            }
        };

    }

    public synchronized static void startThread() {
        if (!estaRodando) {
            estaRodando = true;
            thread.start();
        }
    }

    private static void verificarDiretorios(File fileBase) {
        if (fileBase.exists()) {
            File info = new File(fileBase.getAbsolutePath() + "/info.txt");
            if (info.exists()) {
                try {
                    StringBuilder dados = FileUtils.leArquivo(info);
                    String data = dados.substring(0, 10);

                    if (data.equals(getData(Calendar.getInstance()))) {
                        System.out.println("Ignorando pasta de data atual " + fileBase.getAbsolutePath());
                        return;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LimparWaves.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
            File[] listFiles = fileBase.listFiles(waveFileFilter);
            for (int i = 0; i < listFiles.length; i++) {
                File file = listFiles[i];
                if (file.isDirectory()) {
                    verificarDiretorios(file);
                } else if (file.getName().endsWith(".wav")) {
                    agendaDeletar.add(file);
                }

            }
        }

    }

    private static void excluirMesesAnteirores() {
        Calendar calendario = null;
        for (int i = -33; i > -90; i--) {
            calendario = Calendar.getInstance();
            calendario.add(Calendar.DAY_OF_MONTH, i);
            File pastaPelaData = new File(getPastaPelaData(calendario));
            if (pastaPelaData.exists() && pastaPelaData.isDirectory()) {
                File[] arquivos = pastaPelaData.listFiles();
                for (File file : arquivos) {
                    if (!file.delete()) {
                        System.err.println("Erro ao apagar arquivo: " + file.getAbsolutePath());
                    }
                }
                if (!pastaPelaData.delete()) {
                    System.err.println("Erro ao apagar arquivo: " + pastaPelaData.getAbsolutePath());

                }
            }
        }
        File pastaPeloMes = new File(getPastaPeloMes(calendario));
        if (pastaPeloMes.exists() && pastaPeloMes.isDirectory()) {
            if (pastaPeloMes.list().length == 0) {
                if (!pastaPeloMes.delete()) {
                    System.err.println("Erro ao apagar a pasta: " + pastaPeloMes.getAbsolutePath());
                }
            }
        }
    }

    private static void excluirArquivosAgendados() {
        int count = 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(LimparWaves.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!agendaDeletar.isEmpty()) {
            File apagar = agendaDeletar.remove(count);
            if (!apagar.delete()) {
                System.err.println("Erro ao apagar arquivo: " + apagar.getAbsolutePath());
            }
        }
    }

    public static String getPastaPelaData(Calendar calendario) {
        return Configuracaoes.getString(Configuracaoes.CONF_PASTA_SALVAR).replace('\\', '/')
                + "/" + getAno(calendario)
                + "/" + getMes(calendario)
                + "/" + getDia(calendario);
    }

    public static String getPastaPeloMes(Calendar calendario) {
        return Configuracaoes.getString(Configuracaoes.CONF_PASTA_SALVAR).replace('\\', '/')
                + "/" + getAno(calendario)
                + "/" + getMes(calendario);
    }

    public static void main(String[] args) {
        excluirMesesAnteirores();
    }
}

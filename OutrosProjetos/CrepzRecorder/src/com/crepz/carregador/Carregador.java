package com.crepz.carregador;

import com.crepz.config.Configuracaoes;
import com.crepz.config.JConfiguracao;
import com.crepz.config.constantes.TelaPadrao;
import com.crepz.log.Log;
import com.crepz.log.NivelLog;
import com.crepz.tela.JGravador;
import java.awt.SystemTray;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author rudieri
 */
public final class Carregador {

    private CrepzTray crepzTray;
    private JGravador jGravador;
    private JConfiguracao jConfiguracao;

    public Carregador() {


        Aguarde aguarde = new Aguarde();
        try {
            aguarde.mostrar();
            initLookAndFeel();
            createLog();

            try {
                crepzTray = new CrepzTray(this);
            } catch (Exception ex) {
                crepzTray = null;
                JOptionPane.showMessageDialog(aguarde, "System Tray não suportado por esse Sistema Operacional.");
                Log.error("System Tray não suportado por esse Sistema Operacional.", ex);
            }
            jGravador = new JGravador(this);
            jConfiguracao = new JConfiguracao(jGravador, false);
            TelaPadrao telaPadrao = (TelaPadrao) Configuracaoes.getEnum(Configuracaoes.CONF_TELA_PADRAO);
            // fim do carregamento, remover tela de aguarde e mostrar a de gravação
            aguarde.dispose();
            switch (telaPadrao) {
                case J_MINI:
                    mostrarIconeTray();
                    break;
                case J_MINI_GRAVANDO:
                    mostrarIconeTray();
                    gravar();
                    break;
                case J_PRINCIPAL:
                    jGravador.setVisible(true);
                    break;
                default:
                    jGravador.setVisible(true);
                    break;
            }
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        } finally {
            aguarde.dispose();
        }
//        new AtualizaTempoMusicas().iniciar();
    }

    public void sair() {
        System.exit(0);
    }

    private void createLog() {
        Log.setNivelLog((NivelLog) Configuracaoes.getEnum(Configuracaoes.CONF_NIVEL_LOG));
        Log.info("Programa inicializado " + DateFormat.getDateTimeInstance().format(new Date()));
//        File mk = new File("nbproject");
//        if (!mk.exists()) {
//            SimpleDateFormat formatAnoMes = new SimpleDateFormat("yyyy-MM");
//            mk = new File("log/"+formatAnoMes.format(new Date()));
//            if (!mk.exists()) {
//                mk.mkdirs();
//            }
//            
//            
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//
//            File f = new File(mk.getAbsolutePath() + "/" + format.format(new Date().getTime()) + ".txt");
//            if (!f.exists()) {
//                try {
//
//                    f.createNewFile();
//                    PrintStream saida = new PrintStream(f);
//                    System.setOut(saida);
//                    System.setErr(saida);
//                } catch (IOException ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }
    }

    public void mostrarIconeTray() {
        if (SystemTray.isSupported()) {
            crepzTray.toTray();
            jGravador.setVisible(false);
        }
    }

    public void ocultarIconeTray() {
        if (SystemTray.isSupported()) {
            crepzTray.someTray();
        }
        jGravador.setVisible(true);
    }

    public void setToolTipOnCrepzTray(String text) {
        crepzTray.setToolTipText(text);
    }
    
    public void setMenuGravarBloqueado(boolean bloqueado){
        crepzTray.setMenuGravarBloqueado(bloqueado);
    }

    private void initLookAndFeel() {


        try {
            //"javax.swing.plaf.nimbus.NimbusLookAndFeel"
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Carregador carregador = new Carregador();
    }

    public void mostrarTelaConfiguracoes() {
        jConfiguracao.setVisible(true);
    }

    void gravar() {
        jGravador.gravar();
    }
}

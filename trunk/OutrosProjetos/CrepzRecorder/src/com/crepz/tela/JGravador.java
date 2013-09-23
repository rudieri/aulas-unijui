package com.crepz.tela;

import com.crepz.carregador.Carregador;
import com.crepz.config.Configuracaoes;
import com.crepz.limpador.LimparWaves;
import com.crepz.log.Log;
import com.crepz.rec.SimpleAudioRecorder;
import com.crepz.rec.mp3.MP3Bitrate;
import com.crepz.rec.mp3.Mp3Encoder;
import com.crepz.utils.FileUtils;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author rudieri
 */
public class JGravador extends javax.swing.JFrame {

    private File base;
    private SimpleAudioRecorder recorder;
    private final Carregador carregador;
    private boolean sair;
    private File info;

    /**
     * Creates new form JGravador
     * @param carregador 
     */
    public JGravador(Carregador carregador) {
        initComponents();
        this.carregador = carregador;
        Mp3Encoder.setTraceConverters(true);
        Mp3Encoder.setBitrate(MP3Bitrate._64);
        Mp3Encoder.setVBR(false);
        try {
            setIconImage(ImageIO.read(getClass().getResource("/com/crepz/img/icon.png").toURI().toURL()));
        } catch (Exception ex) {
            Log.error("Erro ao definir icone!", ex);
        }
        
    }

    public void gravar() {
        if (!jButtonGravar.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Já esta gravando!!!");
            return;
        }
        gravarJa();

    }

    private void gravarJa() {
        LimparWaves.startThread();
        Calendar date = Calendar.getInstance();
        base = new File(LimparWaves.getPastaPelaData(Calendar.getInstance()));
        if (!base.exists()) {
            base.mkdirs();
            info = new File(base.getAbsolutePath() + "/info.txt");
            if (!info.exists()) {
                try {
                    FileUtils.gravaArquivo(LimparWaves.getData(date), info);
                } catch (Exception ex) {
                    Logger.getLogger(JGravador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        recorder = SimpleAudioRecorder.getIntance();
        final File ultimoArquivo = new File(base.getAbsolutePath() + "/" + LimparWaves.getNomeArquivo(date) + ".wav");
        jButtonGravar.setEnabled(false);
        carregador.setMenuGravarBloqueado(true);
        jButtonGravar.setText("Gravando...");
        carregador.setToolTipOnCrepzTray("Gravando...");
        Log.info("Gravando em: " + ultimoArquivo.getAbsolutePath());
        recorder.start(ultimoArquivo);
        new Thread(new Runnable() {

            @Override
            @SuppressWarnings("SleepWhileInLoop")
            public void run() {
                try {
                    int tempoConfiguradoMinutos = Configuracaoes.getInteger(Configuracaoes.CONF_TEMPO_SALVAR_ARQUIVOS);
                    if (tempoConfiguradoMinutos <= 0) {
                        tempoConfiguradoMinutos = 1;
                    }
                    int totalSegundos = 60 * tempoConfiguradoMinutos;
                    for (int contaSegundos = 0; contaSegundos < totalSegundos; contaSegundos++) {
                        if (sair) {
                            setTarefa("Salvando os dados...");
                            if (recorder != null) {
                                recorder.stopRecording();
                                Mp3Encoder.converter(ultimoArquivo.getAbsolutePath());
                                if (!ultimoArquivo.delete()) {
                                    ultimoArquivo.deleteOnExit();
                                }
                            }
                            System.exit(0);
                        }
                        Thread.sleep(1000);
                        int segundos = contaSegundos;
                        int horas = segundos / 3600;
                        segundos -= (horas * 3600);
                        int minutos = segundos / 60;
                        segundos -= minutos * 60;
                        jLabelTempo.setText(String.valueOf(horas < 10 ? "0" + horas : horas)
                                + ":" + String.valueOf(minutos < 10 ? "0" + minutos : minutos)
                                + ":" + String.valueOf(segundos < 10 ? "0" + segundos : segundos));
                    }
                    recorder.stopRecording();
                    Log.info("Gravação completa: " + ultimoArquivo.getAbsolutePath());
                    gravarJa();
                    Log.info("Convertendo o arquivo: " + ultimoArquivo.getAbsolutePath());
                    setTarefa("Convertendo o arquivo: " + ultimoArquivo.getAbsolutePath());
                    Mp3Encoder.converter(ultimoArquivo.getAbsolutePath());
                    Log.info("Arquivo convertido: " + ultimoArquivo.getAbsolutePath());

                    setTarefa("");

                    Log.info("Apagando WAVE: " + ultimoArquivo.getAbsolutePath());
                    if (!ultimoArquivo.delete()) {
                        setTarefa("Erro ao apagar WAVE...");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(JGravador.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(JGravador.this, "Erro na gravação... (sleep não está funcionando)", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private void setTarefa(String tarefa) {
        jLabel1.setText(tarefa);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonGravar = new javax.swing.JButton();
        jPanelStatus = new javax.swing.JPanel();
        jLabelTempo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemOcultarTray = new javax.swing.JMenuItem();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemConfiguracoes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Crepz Recorder");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButtonGravar.setMnemonic('G');
        jButtonGravar.setText("Gravar");
        jButtonGravar.setToolTipText("Começar a gravação...");
        jButtonGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonGravar, java.awt.BorderLayout.CENTER);

        jPanelStatus.setLayout(new javax.swing.BoxLayout(jPanelStatus, javax.swing.BoxLayout.LINE_AXIS));

        jLabelTempo.setText("00:00:00");
        jPanelStatus.add(jLabelTempo);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Nada...");
        jLabel1.setToolTipText("");
        jPanel1.add(jLabel1);

        jPanelStatus.add(jPanel1);

        getContentPane().add(jPanelStatus, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("Arquivo");

        jMenuItemOcultarTray.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOcultarTray.setText("Ocultar");
        jMenuItemOcultarTray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOcultarTrayActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOcultarTray);

        jMenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSair.setText("Sair");
        jMenu1.add(jMenuItemSair);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItemConfiguracoes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemConfiguracoes.setText("Configurações");
        jMenuItemConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConfiguracoesActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemConfiguracoes);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-240)/2, (screenSize.height-150)/2, 240, 150);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed
        gravarJa();
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (jButtonGravar.isEnabled()) {
            System.exit(0);
        } else {
            sair = true;
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConfiguracoesActionPerformed
        // TODO add your handling code here:
        carregador.mostrarTelaConfiguracoes();
    }//GEN-LAST:event_jMenuItemConfiguracoesActionPerformed

    private void jMenuItemOcultarTrayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOcultarTrayActionPerformed
        carregador.mostrarIconeTray();
    }//GEN-LAST:event_jMenuItemOcultarTrayActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            //"javax.swing.plaf.nimbus.NimbusLookAndFeel"
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JGravador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JGravador(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTempo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemConfiguracoes;
    private javax.swing.JMenuItem jMenuItemOcultarTray;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelStatus;
    // End of variables declaration//GEN-END:variables

  
}

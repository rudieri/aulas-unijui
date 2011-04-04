/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJDialog.java
 *
 * Created on 14/03/2011, 19:15:13
 */
package sistema3camadascliente.telas;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import sistema3camadasbase.conexao.Mensagem;
import sistema3camadasbase.musica.Musica;
import sistema3camadasbase.musica.album.Album;
import sistema3camadasbase.musica.artista.Artista;
import sistema3camadasbase.musica.capas.Capa;
import sistema3camadasbase.musica.genero.Genero;
import sistema3camadasbase.util.DicaDeTexto;
import sistema3camadasbase.util.Nomeavel;
import sistema3camadascliente.conexao.Cliente;

/**
 *
 * @author manchini
 */
public class JMusicaNova extends javax.swing.JDialog {

    private Artista artista;
    private Album album;
    private Genero genero;
    private ArrayList<Nomeavel> lista;
    private final DicaDeTexto dicaDeTexto;
    private Capa capa;
    private Capa capaTela;

    /** Creates new form NewJDialog */
    public JMusicaNova(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Preferencias p = new Preferencias(parent, true);
        HashMap mostrar = p.mostrar();
        Cliente.setIp((String) mostrar.get("ip"));
        Cliente.setPorta(Integer.parseInt(mostrar.get("porta").toString()));

        initComponents();
        jTable1.getColumnModel().removeColumn(jTable1.getColumn("Objeto"));
        atualizarTabela();
        dicaDeTexto = new DicaDeTexto(this.getContentPane());
        capaTela = new Capa();
    }

    private void atualizarTabela() {
        try {
            Musica musica = new Musica();
            musica.setNome(jTextField_Filtro.getText());
            Mensagem msg = (Mensagem) Cliente.comando(Mensagem.TIPO_LISTAR, musica);
            ArrayList<Musica> list = (ArrayList<Musica>) msg.getObjeto();
            DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
            tm.setNumRows(0);
            for (Musica object : list) {

                Object row[] = new Object[3];
                row[0] = object.getId();
                row[1] = object.getNome();
                row[2] = object;
                tm.addRow(row);
            }

        } catch (Exception ex) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarDica(JTextField source, KeyEvent evt) {
        int cd = evt.getKeyCode();
        if ((cd < 48 || cd > 95) && cd != KeyEvent.VK_BACK_SPACE && cd != KeyEvent.VK_DELETE) {
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        if (lista == null || lista.isEmpty()) {
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().toLowerCase().startsWith(source.getText().toLowerCase())) {
                list.add(lista.get(i).getNome());
            }
        }
        if (list.isEmpty()) {
            dicaDeTexto.remove();
            return;
        }
        dicaDeTexto.paintBox(source, list);
    }

    private void buscarMusica(boolean limpar) {
        Musica objeto = new Musica();
        objeto.setNome(jTextField_Nome.getText());
        buscar(objeto, limpar);
    }

    private void buscarGenero(boolean limpar) {
        Genero objeto = new Genero();
        objeto.setNome(jTextField_NomeGenero.getText());
        buscar(objeto, limpar);
    }

    private void buscarArtista(boolean limpar) {
        Artista objeto = new Artista();
        objeto.setNome(jTextField_NomeArtista.getText());
        buscar(objeto, limpar);
    }

    private void buscarAlbum(boolean limpar) {
        Album objeto = new Album();
        objeto.setNome(jTextField_NomeAlbum.getText());
        buscar(objeto, limpar);
    }

    private void buscarCapa(boolean limpar) {
        Capa objeto = new Capa();
        objeto.setNome(capaTela.getNome());
        buscar(objeto, limpar);
    }

    private Musica carregarMusicaPorNome(String nome) {
        buscarMusica(true);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                return (Musica) lista.get(i);
            }
        }
        return null;
    }

    private Genero carregarGeneroPorNome(String nome) throws Exception {
        buscarGenero(true);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                return (Genero) lista.get(i);
            }
        }
        return null;
    }

    private Artista carregarArtistaPorNome(String nome) throws Exception {
        buscarArtista(true);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                return (Artista) lista.get(i);
            }
        }
        return null;
    }

    private Album carregarAlbumPorNome(String nome) throws Exception {
        buscarAlbum(true);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                return (Album) lista.get(i);
            }
        }
        return null;
    }

    private Capa carregarCapaPorNome(String nome) throws Exception {
        buscarCapa(true);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                return (Capa) lista.get(i);
            }
        }
        return null;
    }

    private void buscar(Serializable objeto, boolean limparArray) {
        try {
            Mensagem msg = (Mensagem) Cliente.comando(Mensagem.TIPO_LISTAR, objeto);
            if (limparArray) {
                lista = (ArrayList) msg.getObjeto();
            } else {
                lista.addAll((ArrayList) msg.getObjeto());
            }

        } catch (Exception ex) {
            Logger.getLogger(JMusicaNova.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limparTela() {
        jTextField_Cod.setText("");
        jTextField_Nome.setText("");

        jTextField_NomeArtista.setText("");
        artista = null;

        jTextField_NomeAlbum.setText("");
        album = null;

        jTextField_NomeGenero.setText("");
        genero = null;

        capaTela = new Capa();
        jLabel_Capa.setIcon(null);
    }

    private Artista incluirArtista(String nome) throws Exception {
        artista = carregarArtistaPorNome(jTextField_NomeArtista.getText());
        if (artista == null) {
            artista = new Artista();
            artista.setNome(jTextField_NomeArtista.getText());
            Cliente.comando(Mensagem.TIPO_INCLUIR, artista);
            artista = carregarArtistaPorNome(jTextField_NomeArtista.getText());
        }
        return artista;
    }

    private Album incluirAlbum(String nome) throws Exception {
        album = carregarAlbumPorNome(jTextField_NomeAlbum.getText());
        if (album == null) {
            album = new Album();
            album.setNome(jTextField_NomeAlbum.getText());
            Cliente.comando(Mensagem.TIPO_INCLUIR, album);
            album = carregarAlbumPorNome(jTextField_NomeAlbum.getText());
        }
        return album;
    }

    private Genero incluirGenero(String nome) throws Exception {
        genero = carregarGeneroPorNome(jTextField_NomeGenero.getText());
        if (genero == null) {
            genero = new Genero();
            genero.setNome(jTextField_NomeGenero.getText());
            Cliente.comando(Mensagem.TIPO_INCLUIR, genero);
            genero = carregarGeneroPorNome(jTextField_NomeGenero.getText());
        }
        return genero;
    }

    private Capa incluirCapa(String nome) throws Exception {
        capa = carregarCapaPorNome(capaTela.getNome());
        if (capa == null) {
            capa = new Capa();
            capa.setNome(capaTela.getNome());
            Cliente.comando(Mensagem.TIPO_INCLUIR, capa);
            capa = carregarCapaPorNome(capaTela.getNome());
        }
        return capa;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Topo = new javax.swing.JPanel();
        jPanel_Center = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Cod = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_Nome = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_NomeArtista = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_NomeAlbum = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_NomeGenero = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton_Delete = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel_Capa = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Filtro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Musica");

        jPanel_Topo.setPreferredSize(new java.awt.Dimension(400, 20));
        getContentPane().add(jPanel_Topo, java.awt.BorderLayout.PAGE_START);

        jPanel_Center.setLayout(new javax.swing.BoxLayout(jPanel_Center, javax.swing.BoxLayout.Y_AXIS));

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Codigo:");
        jLabel1.setPreferredSize(new java.awt.Dimension(60, 18));
        jPanel1.add(jLabel1);

        jTextField_Cod.setEditable(false);
        jTextField_Cod.setEnabled(false);
        jTextField_Cod.setFocusable(false);
        jTextField_Cod.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel1.add(jTextField_Cod);

        jPanel10.add(jPanel1);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Nome:");
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 18));
        jPanel2.add(jLabel2);

        jTextField_Nome.setPreferredSize(new java.awt.Dimension(300, 25));
        jTextField_Nome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NomeFocusGained(evt);
            }
        });
        jTextField_Nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_NomeKeyReleased(evt);
            }
        });
        jPanel2.add(jTextField_Nome);

        jPanel10.add(jPanel2);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setText("Artista:");
        jLabel4.setPreferredSize(new java.awt.Dimension(60, 18));
        jPanel6.add(jLabel4);

        jTextField_NomeArtista.setPreferredSize(new java.awt.Dimension(245, 25));
        jTextField_NomeArtista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NomeArtistaFocusGained(evt);
            }
        });
        jTextField_NomeArtista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_NomeArtistaKeyReleased(evt);
            }
        });
        jPanel6.add(jTextField_NomeArtista);

        jPanel10.add(jPanel6);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText("Album:");
        jLabel5.setPreferredSize(new java.awt.Dimension(60, 18));
        jPanel7.add(jLabel5);

        jTextField_NomeAlbum.setPreferredSize(new java.awt.Dimension(245, 25));
        jTextField_NomeAlbum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NomeAlbumFocusGained(evt);
            }
        });
        jTextField_NomeAlbum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_NomeAlbumKeyReleased(evt);
            }
        });
        jPanel7.add(jTextField_NomeAlbum);

        jPanel10.add(jPanel7);

        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setText("Genero:");
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 18));
        jPanel8.add(jLabel6);

        jTextField_NomeGenero.setPreferredSize(new java.awt.Dimension(245, 25));
        jTextField_NomeGenero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField_NomeGeneroFocusGained(evt);
            }
        });
        jTextField_NomeGenero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_NomeGeneroKeyReleased(evt);
            }
        });
        jPanel8.add(jTextField_NomeGenero);

        jPanel10.add(jPanel8);

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton_Delete.setText("Delete");
        jButton_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_Delete);

        jButton3.setText("Limpar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        jPanel10.add(jPanel4);

        jPanel9.add(jPanel10, java.awt.BorderLayout.WEST);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel_Capa.setMaximumSize(new java.awt.Dimension(101, 101));
        jLabel_Capa.setPreferredSize(new java.awt.Dimension(101, 101));
        jLabel_Capa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_CapaMouseClicked(evt);
            }
        });
        jPanel12.add(jLabel_Capa, java.awt.BorderLayout.NORTH);

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel_Center.add(jPanel9);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Artistas"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setText("Filtro:");
        jLabel3.setPreferredSize(new java.awt.Dimension(55, 18));
        jPanel5.add(jLabel3);

        jTextField_Filtro.setPreferredSize(new java.awt.Dimension(300, 30));
        jTextField_Filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_FiltroKeyPressed(evt);
            }
        });
        jPanel5.add(jTextField_Filtro);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Objeto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel_Center.add(jPanel3);

        getContentPane().add(jPanel_Center, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-537)/2, (screenSize.height-540)/2, 537, 540);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Musica musica = new Musica();
        if (!jTextField_Cod.getText().equals("")) {
            musica.setId(Integer.valueOf(jTextField_Cod.getText()));
        }

        musica.setNome(jTextField_Nome.getText());
        try {
            if (!(jTextField_NomeArtista.getText() == null || jTextField_NomeArtista.getText().equals(""))) {
                artista = incluirArtista(jTextField_NomeArtista.getText());
                musica.setAutor(artista);
            }
            if (!(jTextField_NomeGenero.getText() == null || jTextField_NomeGenero.getText().equals(""))) {
                genero = incluirGenero(jTextField_NomeGenero.getText());
                musica.setGenero(genero);
            }
            if (!(jTextField_NomeAlbum.getText() == null || jTextField_NomeAlbum.getText().equals(""))) {
                album = incluirAlbum(jTextField_NomeAlbum.getText());
                musica.setAlbum(album);
            }

            capa = incluirCapa(capaTela.getNome());
            musica.setCapa(capa);


        } catch (Exception ex) {
            Logger.getLogger(JMusicaNova.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            Mensagem msg = (Mensagem) Cliente.comando(Mensagem.TIPO_INCLUIR, musica);
            JOptionPane.showMessageDialog(this, msg.getObjeto());

        } catch (Exception ex) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            atualizarTabela();
            limparTela();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limparTela();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField_FiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_FiltroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            atualizarTabela();
        }

    }//GEN-LAST:event_jTextField_FiltroKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            limparTela();
            Musica musica = (Musica) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), jTable1.getColumnCount());
            if (musica != null) {
                try {
                    jTextField_Cod.setText(String.valueOf(musica.getId()));
                    jTextField_Nome.setText(musica.getNome());
                    if (musica.getAutor() != null) {
                        artista = musica.getAutor();
                        //                 jTextField_CodArtista.setText(String.valueOf(artista.getId()));
                        jTextField_NomeArtista.setText(artista.getNome());
                    }
                    if (musica.getAlbum() != null) {
                        album = musica.getAlbum();
                        //    jTextField_CodAlbum.setText(String.valueOf(album.getId()));
                        jTextField_NomeAlbum.setText(album.getNome());
                    }
                    if (musica.getGenero() != null) {
                        genero = musica.getGenero();
                        //     jTextField_CodGenero.setText(String.valueOf(genero.getId()));
                        jTextField_NomeGenero.setText(genero.getNome());
                    }
                    if (musica.getCapa() != null) {
                        capaTela = musica.getCapa();
                       // capaTela = new JCapas(null, true).selecionarCapa();
                        ImageIcon icon = capaTela.getImage();
                        jLabel_Capa.setIcon(icon);

                    }
                } catch (Exception ex) {
                    Logger.getLogger(JMusicaNova.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DeleteActionPerformed
        Musica musica = new Musica();
        if (!jTextField_Cod.getText().equals("")) {
            musica.setId(Integer.valueOf(jTextField_Cod.getText()));
        }
        musica.setNome(jTextField_Nome.getText());
        try {
            Mensagem msg = (Mensagem) Cliente.comando(Mensagem.TIPO_EXCLUIR, musica);
            JOptionPane.showMessageDialog(this, msg.getObjeto().toString());
        } catch (Exception ex) {
            Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            atualizarTabela();
            limparTela();
        }
    }//GEN-LAST:event_jButton_DeleteActionPerformed

    private void jTextField_NomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NomeFocusGained
        // TODO add your handling code here:
        buscarMusica(true);
    }//GEN-LAST:event_jTextField_NomeFocusGained

    private void jTextField_NomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomeKeyReleased
        // TODO add your handling code here:
        mostrarDica(jTextField_Nome, evt);
    }//GEN-LAST:event_jTextField_NomeKeyReleased

    private void jTextField_NomeArtistaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NomeArtistaFocusGained
        // TODO add your handling code here:
        buscarArtista(true);
    }//GEN-LAST:event_jTextField_NomeArtistaFocusGained

    private void jTextField_NomeArtistaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomeArtistaKeyReleased
        // TODO add your handling code here:
        mostrarDica(jTextField_NomeArtista, evt);
    }//GEN-LAST:event_jTextField_NomeArtistaKeyReleased

    private void jTextField_NomeAlbumFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NomeAlbumFocusGained
        // TODO add your handling code here:
        buscarAlbum(true);
    }//GEN-LAST:event_jTextField_NomeAlbumFocusGained

    private void jTextField_NomeAlbumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomeAlbumKeyReleased
        // TODO add your handling code here:
        mostrarDica(jTextField_NomeAlbum, evt);
    }//GEN-LAST:event_jTextField_NomeAlbumKeyReleased

    private void jTextField_NomeGeneroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_NomeGeneroFocusGained
        // TODO add your handling code here:
        buscarGenero(true);
    }//GEN-LAST:event_jTextField_NomeGeneroFocusGained

    private void jTextField_NomeGeneroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomeGeneroKeyReleased
        // TODO add your handling code here:
        mostrarDica(jTextField_NomeGenero, evt);
    }//GEN-LAST:event_jTextField_NomeGeneroKeyReleased

    private void jLabel_CapaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_CapaMouseClicked
        // TODO add your handling code here:
        capaTela = new JCapas(null, true).selecionarCapa();
        if (capaTela == null) {
            jLabel_Capa.setIcon(null);
            return;
        }
        ImageIcon icon = new ImageIcon(capaTela.getNome());
        jLabel_Capa.setIcon(icon);
    }//GEN-LAST:event_jLabel_CapaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JMusicaNova dialog = new JMusicaNova(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_Delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_Capa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Center;
    private javax.swing.JPanel jPanel_Topo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField_Cod;
    private javax.swing.JTextField jTextField_Filtro;
    private javax.swing.JTextField jTextField_Nome;
    private javax.swing.JTextField jTextField_NomeAlbum;
    private javax.swing.JTextField jTextField_NomeArtista;
    private javax.swing.JTextField jTextField_NomeGenero;
    // End of variables declaration//GEN-END:variables
}

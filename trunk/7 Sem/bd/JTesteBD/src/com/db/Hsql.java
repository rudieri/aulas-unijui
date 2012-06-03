/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;
import com.objetos.Componente;
import com.objetos.Computador;
import com.objetos.Marca;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author manchini
 */
public class Hsql implements PersistenciaInterface {
    private Connection conn;
    @Override
    public void insert(Object obj) {
        try {
            Statement st = conn.createStatement();

            if (obj instanceof Marca) {
                Marca marca = (Marca) obj;
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT into marca values(null,'").append(marca.getNome()).append("') ");
                st.executeUpdate(sql.toString());
            }
            if (obj instanceof Computador) {
                Computador comp = (Computador) obj;
                //Busca Sequencia;
                StringBuilder sql = new StringBuilder();
                sql.append("select next value for comp_seq from INFORMATION_SCHEMA.SEQUENCES ");
                ResultSet rs = st.executeQuery(sql.toString());
                Integer id = 1;
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                sql = new StringBuilder();
                sql.append("INSERT into comp values(" + id.toString() + ",'").append(comp.getNome()).append("') ");
                st.executeUpdate(sql.toString());
                ArrayList<Componente> componentes = comp.getComponentes();
                for (int i = 0; i < componentes.size(); i++) {
                    Componente componente = componentes.get(i);
                    sql = new StringBuilder();
                    sql.append("INSERT into comp_comp values(null,'").append(componente.getNome()).append("', " + componente.getMarca().getId() + " ," + id + " ) ");
//                    System.out.println(sql);
                    st.executeUpdate(sql.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void update(Object obj) {
    }
    @Override
    public void delete(Object object) {
        try {
            String tabela = "";
            Integer id = null;
            if (object instanceof Marca) {
                tabela = "marca";
                id = ((Marca) object).getId();
            }
            if (object instanceof Computador) {
                tabela = "comp";
                id = ((Computador) object).getId();
            }
            if (object instanceof Componente) {
                tabela = "comp_comp";
                id = ((Componente) object).getId();
            }
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE  FROM " + tabela + " WHERE id = " + id);
            Statement st = conn.createStatement();
            st.executeUpdate(sql.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void carregar(Object id) {
    }
    @Override
    public ArrayList listarTudo(Class classe) {
        ArrayList lista = new ArrayList();
        try {
            Statement st = conn.createStatement();
            if (classe.equals(Marca.class)) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * from marca");
                ResultSet rs = st.executeQuery(sql.toString());
                while (rs.next()) {
                    Marca marca = new Marca(rs.getInt("id"));
                    marca.setNome(rs.getString("nome"));
                    lista.add(marca);
                }
            }
            if (classe.equals(Componente.class)) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT cc.* from marca m INNER JOIN comp_comp cc ON m.id = cc.id_marca");
                ResultSet rs = st.executeQuery(sql.toString());
                while (rs.next()) {
                    Componente componente = new Componente(rs.getInt("id"));
                    componente.setNome(rs.getString("nome"));
                    componente.setMarca(new Marca(rs.getInt("id_marca")));
                    lista.add(componente);
                }
            }
            if (classe.equals(Computador.class)) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT c.* "
                        + "from marca m "
                        + "INNER JOIN comp_comp cc ON m.id = cc.id_marca "
                        + "INNER JOIN comp c ON cc.id_comp = c.id");
                ResultSet rs = st.executeQuery(sql.toString());
                while (rs.next()) {
                    Computador computador = new Computador(rs.getInt("id"));
                    computador.setNome(rs.getString("nome"));
                    lista.add(computador);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    @Override
    public void conectar() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
//            conn = DriverManager.getConnection("jdbc:hsqldb:file:teste", "SA", "");
                        conn = DriverManager.getConnection("jdbc:hsqldb:file:teste", "SA", "");
            conn.setAutoCommit(true);
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE marca ( id integer Identity ,nome varchar(25)); "
                    + "CREATE SEQUence comp_seq;  "
                    + "CREATE TABLE comp ( id integer Identity ,nome varchar(25));  "
                    + "CREATE TABLE comp_comp ( id integer Identity,nome varchar(50), id_marca integer,id_comp integer,  "
                    + "FOREIGN KEY (id_marca) REFERENCES marca(id), "
                    + "FOREIGN KEY (id_comp) REFERENCES comp(id) );");
            try{
            conn.createStatement().executeUpdate(sql.toString());
            }catch(Exception ex){
                System.out.println("Ja tem o banco");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Marca getMarcaAleatoria(int tam) {
        Random r = new Random();
        Marca marca = new Marca(r.nextInt(tam));
        return marca;
    }
}

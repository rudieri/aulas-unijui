/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasservidor.banco;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author manchini
 */
public class Transacao {

    private Session session;
    Transaction t;
    boolean fecharSessao = true;

    public Transacao() {
        this(true);
    }

    public Transacao(boolean fecharSessao) {
        session = HibernateUtil.getSessionFactory().openSession();
        this.fecharSessao = fecharSessao;
        t = session.beginTransaction();
    }

    public void begin() {
        t.begin();
    }

    public void save(Object obj) {
        session.save(obj);
    }

    public void update(Object obj) {
        session.update(obj);
    }

    public void saveOrUpdate(Object obj) {
        session.saveOrUpdate(obj);
    }

    public void delete(Object obj) {
        session.delete(obj);
    }


    public Object load(Serializable serial){
        return session.load(serial.getClass(), serial);
    }

    public List listar(String tabela) {
        Query lista = session.createQuery("from "+tabela);
        return  lista.list();
    }
    

    public void commit() {
        t.commit();
        if (fecharSessao) {
            session.close();
        }
    }

    public void close() {
        session.close();
    }
}

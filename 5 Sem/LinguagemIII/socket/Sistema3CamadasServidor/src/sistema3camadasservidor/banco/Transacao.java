/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasservidor.banco;

import java.io.Serializable;
import java.util.List;
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

    public Object load(Class classe, Serializable id) {
        return session.get(classe, id);
    }

    public List listar(String Objeto, String filtro) {
        List lista = session.createQuery("select t from " + Objeto + " as t " + filtro).list();
        return lista;
    }

    public void commit() {
        t.commit();
        if (fecharSessao) {
            session.close();
        }
    }

    public void rollback() {
        t.rollback();
        if (fecharSessao) {
            session.close();
        }
    }

    public void close() {
        session.close();
    }
}

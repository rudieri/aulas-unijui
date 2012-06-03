/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Predicate;
import com.objetos.Marca;
import com.objetos.ObjetoBase;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author manchini
 */
public class BD4O implements PersistenciaInterface {
    private ExtObjectContainer db;
    private ArrayList<Marca> listamarca = new ArrayList<Marca>();
    @Override
    public void insert(Object obj) {
        ObjectSet set = db.query(obj.getClass());
        ((ObjetoBase)obj).setId(set.size());
        db.store(obj);
        if(obj instanceof Marca){
            listamarca.add((Marca)obj);
        }
    }
    @Override
    public void update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void delete(Object object) {
            db.delete(object);
    }
    @Override
    public void carregar(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public ArrayList listarTudo(Class classe) {
        ObjectSet obj;
        obj = db.query(classe);
        ArrayList<Object> list = new ArrayList<Object>();
        while (obj.hasNext()) {
            list.add(obj.next());
        }
        return list;
    }
    @Override
    public void conectar() {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "db.yap");
        this.db = db.ext();
    }
    @Override
    public void desconectar() {
        if (!this.db.isClosed()) {
            db.close();
        }
    }
    @Override
    public Marca getMarcaAleatoria(int tam) {
        Random r = new Random();
        final int id =r.nextInt(tam);        
        Marca marca = listamarca.get(id);
        return marca;
    }
}
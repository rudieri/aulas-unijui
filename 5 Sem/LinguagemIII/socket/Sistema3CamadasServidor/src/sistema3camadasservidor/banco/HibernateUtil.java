/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasservidor.banco;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import sistema3camadasbase.musica.Musica;
import sistema3camadasbase.musica.album.Album;
import sistema3camadasbase.musica.artista.Artista;
import sistema3camadasbase.musica.capas.Capa;
import sistema3camadasbase.musica.genero.Genero;


/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author manchini
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final AnnotationConfiguration cfg;

    static {
        try {

            cfg = new AnnotationConfiguration();
            getCfg().addAnnotatedClass(Musica.class);
            getCfg().addAnnotatedClass(Artista.class);
            getCfg().addAnnotatedClass(Genero.class);
            getCfg().addAnnotatedClass(Album.class);
            getCfg().addAnnotatedClass(Capa.class);
            sessionFactory = getCfg().buildSessionFactory();




        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @return the cfg
     */
    public static AnnotationConfiguration getCfg() {
        return cfg;
    }
}

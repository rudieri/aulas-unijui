package sistema3camadasservidor;


import org.hibernate.tool.hbm2ddl.SchemaExport;
import sistema3camadasservidor.banco.HibernateUtil;
import sistema3camadasservidor.conexao.Servidor;

public class GeraTabelasnoBanco {

    public static void main(String args[]) {

        gerar();
    }

    public static void gerar() {
        Servidor.startBanco();
        org.apache.log4j.BasicConfigurator.configure();
        SchemaExport se = new SchemaExport(HibernateUtil.getCfg());
        se.create(true, true);
        Servidor.stopBanco();


    }
}

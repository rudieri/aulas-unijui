package jogovelha.banco;


import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GeraTabelasnoBanco {

    public static void main(String args[]) {

        gerar();
    }

    public static void gerar() {
//        org.apache.log4j.BasicConfigurator.configure();
        SchemaExport se = new SchemaExport(HibernateUtil.getCfg());
        se.create(true, true);


    }
}

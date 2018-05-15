package Sistema;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Main {

    public static final String NomeDoUsuario = System.getProperty("user.name");

    public static void main(String[] args) throws JRException, SQLException {

        //Lista dos carros
        List<Carro> lista = new ArrayList<Carro>();

        Carro c1 = new Carro();
        c1.setNumero_chassi(123);
        c1.setNome("Ferrari California");
        c1.setCor("Vermelha");
        c1.setAno(2018);
        c1.setPotencia_cv(580);
        c1.setValor(2500000);

        Carro c2 = new Carro();
        c2.setNumero_chassi(321);
        c2.setNome("Fusca");
        c2.setCor("Azul");
        c2.setAno(1998);
        c2.setPotencia_cv(120);
        c2.setValor(10000);

        lista.add(c1);
        lista.add(c2);

        System.out.println("");

        //Consumindo o modelo do relatorio criado no JasperStudio e salvo no diretorio do projeto
        JasperReport report = JasperCompileManager.compileReport("src/Relatorio/ModeloCarro.jrxml");

        /*
            Parametros
            1 - O relátorio que consumimos acima
            2 - Um Map com parametros que são passados ao relatorio no momento do preenchimento. No nosso caso é null, pois não passamos parametro
            3 - A lista mas não diretamente, é necesseraio transformar em um data source utilizando a classe JRBeanCollectionDataSource
         */
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));

        //Exportando o arquivo no formato PDF.
        JasperExportManager.exportReportToPdfFile(print,
                "C:/Users/" + NomeDoUsuario + "/Desktop/RelatorioCarro.pdf");

        System.out.println("Relatorio Criado!");

        //Abrindo o arquivo PDF no PC (O PDF vai abrir na aplicação padrao que ler PDF do seu computador)
        try {
            Desktop.getDesktop().open(new File("C:/Users/" + NomeDoUsuario + "/Desktop/RelatorioCarro.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

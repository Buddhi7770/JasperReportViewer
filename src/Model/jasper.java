package Model;

import java.util.HashMap;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * Create a Object of this class and call viewReport Method
 * <li>Author Buddhi7770</li>
 * <li>Copyright 2024</li>
 * @author Buddhi7770
 */
public class jasper {
    /**
     * 
     * @param name Name of the jasperReport Created in Reports Folder in your project
     * @param parameters HashMap of Parameters
     * @param table Table of DataSource 
     * @throws java.lang.Exception
     */
    
    public void viewReport(String name, HashMap parameters, JTable table) throws Exception {
        this.rootPath += name;
        JRTableModelDataSource dataSource = new JRTableModelDataSource(table.getModel());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
    }

    /**
     *   
     * A EmptyDatasource will be generated
     * @param name Name of the jasperReport Created in Reports Folder in your project
     * @param parameters HashMap of Parameters
     * @throws java.lang.Exception
     */
    public void viewReport(String name, HashMap parameters) throws Exception {
        this.rootPath += name;
        JREmptyDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
    }

    /**
     * A Empty HashMap and EmptyDataSoucre will be generated
     * @param name Name of the jasperReport Created in Reports Folder in your project
     * @throws java.lang.Exception
     */
    public void viewReport(String name) throws Exception {
        this.rootPath += name;
        HashMap parameters = new HashMap();
        JREmptyDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
    }

    private String rootPath = "/Reports/";
}

package Model;

import java.util.HashMap;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

/**
 *
 * Create a Object of this class and call viewReport Method
 * <li>Author Buddhi7770</li>
 * <li>Copyright 2024</li>
 *
 * @author Buddhi7770
 */
public class Jasper {

    /**
     *
     * @param name Name of the jasperReport Created in Reports Folder in your
     * project
     * @param parameters HashMap of Parameters
     * @param table Table of DataSource
     * @return jasperPrint
     * @throws java.lang.Exception
     */
    public JasperPrint viewReport(String name, HashMap parameters, JTable table) throws Exception {
        this.rootPath += name;
        JRTableModelDataSource dataSource = new JRTableModelDataSource(table.getModel());
        JasperPrint jasperPrint = JasperFillManager.fillReport(Jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    /**
     *
     * @param name Name of the jasperReport Created in Reports Folder in your
     * project
     * @param parameters HashMap of Parameters
     * @param connection MySQL Database connection
     * @return jasperPrint
     * @throws java.lang.Exception
     */
    public JasperPrint viewReport(String name, HashMap parameters, Connection connection) throws Exception {
        this.rootPath += name;
        JasperPrint jasperPrint = JasperFillManager.fillReport(Jasper.class.getResourceAsStream(this.rootPath), parameters, connection);
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    /**
     *
     * A EmptyDatasource will be generated
     *
     * @param name Name of the jasperReport Created in Reports Folder in your
     * project
     * @param parameters HashMap of Parameters
     * @return jasperPrint
     * @throws java.lang.Exception
     */
    public JasperPrint viewReport(String name, HashMap parameters) throws Exception {
        this.rootPath += name;
        JREmptyDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(Jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    /**
     * A Empty HashMap and EmptyDataSoucre will be generated
     *
     * @param name Name of the jasperReport Created in Reports Folder in your
     * project
     * @return jasperPrint
     * @throws java.lang.Exception
     */
    public JasperPrint viewReport(String name) throws Exception {
        this.rootPath += name;
        HashMap parameters = new HashMap();
        JREmptyDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(Jasper.class.getResourceAsStream(this.rootPath), parameters, dataSource);
        JasperViewer.viewReport(jasperPrint, false);
        return jasperPrint;
    }

    /**
     * A JasperPrint returned by viewReport
     *
     * @param jasperPrint JasperPrint to Export as PDF
     * @param fileName FileName of the PDF
     * @throws java.lang.Exception
     */
    public void exportReportPDF(JasperPrint jasperPrint, String fileName) throws Exception {
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
    }

    /**
     * A JasperPrint returned by viewReport
     *
     * @param jasperPrint JasperPrint to Export as HTML
     * @param fileName Filename of the HTML
     * @throws java.lang.Exception
     */
    public void exportReportHTML(JasperPrint jasperPrint, String fileName) throws Exception {
        JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
    }

    /**
     * A Simple jasperPrint
     *
     * @param jasperPrint JasperPrint to Print on Default Printer
     * @param showDialog Show the PrintDialog
     * @return printStatus
     * @throws java.lang.Exception
     */
    public Boolean simplePrintReport(JasperPrint jasperPrint, Boolean showDialog) throws Exception {
        return JasperPrintManager.printReport(jasperPrint, showDialog);
    }

    /**
     * An Advance jasperPrint, Please call setPrinterSettings method to
     * customize and after call this method
     *
     * @param jasperPrint JasperPrint to print on Customized Printer
     * @return printStatus
     * @throws java.lang.Exception
     */
    public Boolean advancePrintReport(JasperPrint jasperPrint) throws Exception {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(mediaSize);
        printRequestAttributeSet.add(new Copies(copies));
        if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
            printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
        } else {
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
        }

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

        JRPrintServiceExporter jRPrintServiceExporter = new JRPrintServiceExporter();

        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(pageDialog);
        configuration.setDisplayPrintDialog(printDialog);

        jRPrintServiceExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        jRPrintServiceExporter.setConfiguration(configuration);

        jRPrintServiceExporter.exportReport();

        return true;

    }

    /**
     * @param selectedPrinter Name of the printer
     * @param mediaSize size of the paper
     * @param copies amount of copies
     */
    public void setPrinterSettings(String selectedPrinter, MediaSizeName mediaSize, int copies) {
        this.mediaSize = mediaSize;
        this.copies = copies;
        this.selectedPrinter = selectedPrinter;
    }

    /**
     * @return true if printer exists
     */
    public Boolean verifyMyPrinter() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

        if (services != null && services.length != 0) {
            for (PrintService service : services) {
                String existingPrinter = service.getName();
                if (existingPrinter.equals(selectedPrinter)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param pageDialog showPageDialog window
     * @param printDialog showPrintDialog window
     */
    public void showDialogs(Boolean pageDialog, Boolean printDialog) {
        this.pageDialog = pageDialog;
        this.printDialog = printDialog;
    }

    private MediaSizeName mediaSize = MediaSizeName.ISO_A4;
    private int copies = 1;
    private String selectedPrinter = "Microsoft XPS Document Writer";
    private Boolean pageDialog = false;
    private Boolean printDialog = false;
    private String rootPath = "/Reports/";
}

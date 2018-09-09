package com.acm.javafx.invoicesystem.report;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.acm.javafx.invoicesystem.model.OthersInvoiceItemFormatter;

import javafx.stage.Window;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import win.zqxu.jrviewer.JRViewerFX;

public class PrintReport {
	public static void print(String path,Map<String, Object> parameters,List<OthersInvoiceItemFormatter> list,Window owner) throws JRException {
			JasperReport jasperReport= (JasperReport)JRLoader.loadObject(new File(path));
			JRDataSource dataSource= new JRBeanCollectionDataSource(list);
			JasperPrint print= JasperFillManager.fillReport(jasperReport, parameters,dataSource);
			JRViewerFX.printWithPrintDialog(owner, print);
	}
	public static void previewAndPrint(String path,Map<String, Object> parameters,List<OthersInvoiceItemFormatter> list,Window owner) throws JRException {
		JasperReport jasperReport= (JasperReport)JRLoader.loadObject(new File(path));
		JRDataSource dataSource= new JRBeanCollectionDataSource(list);
		JasperPrint print= JasperFillManager.fillReport(jasperReport, parameters,dataSource);
		JRViewerFX.preview(owner, print);
}
}

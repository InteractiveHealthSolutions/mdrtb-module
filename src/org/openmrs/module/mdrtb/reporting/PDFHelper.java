package org.openmrs.module.mdrtb.reporting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@SuppressWarnings("deprecation")
public class PDFHelper {

	/*public void createPdf(String html ) throws IOException, DocumentException {
    	System.out.println("\n\n");
    	System.out.println(html);
    	System.out.println("\n\n");
		FileOutputStream file = new FileOutputStream(new File());
        Document document = new Document();
        PdfWriter.getInstance(document, file);
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(html));
        document.close();
        file.close();
    }*/
	
	public void createPdf(String html ) throws IOException, DocumentException {
//    	System.out.println("\n\n");
//    	System.out.println(html);
//    	System.out.println("\n\n");
    	Document document = new Document(PageSize.A4_LANDSCAPE);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Zohaib Masood\\Desktop\\demo1.pdf"));
		document.open();
		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		worker.parseXHtml(pdfWriter, document, new StringReader(html));
		document.close();
	}
	public byte[] createAndSavePdf(String html ) throws IOException, DocumentException {
		/*System.out.println("\n\n");
    	System.out.println(html);
    	System.out.println("\n\n");*/
    	Document document = new Document(PageSize.A4_LANDSCAPE);
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
		document.open();
		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		worker.parseXHtml(pdfWriter, document, new StringReader(html));
		document.close();
		byte[] pdf = baos.toByteArray();
		return pdf;
	}
}

package com.print.pdf;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.*;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;



public class XMlToDocxAndPdf {

	private static Configuration configure = null;
    private static final String docxName = "template.docx";
    private static final String outFileXml = "outTemplate.xml";
    private static final String templateZip = "template.zip";
    /**
     * 加载模板
     * @return
     * @throws IOException
     */
	public static Configuration getConfigurationIntance() throws IOException {
		if (configure == null) {
			configure = new Configuration();
			configure.setClassForTemplateLoading(XMlToDocxAndPdf.class, File.separator + "xml");
			configure.setDefaultEncoding("UTF-8");
		}
		return configure;
	}
    /**
     * 生成docx
     * @throws Exception
     */
    public static  void makeWord(String basePath,String templateName,Map<?, ?>  dataMap) throws Exception{
    	FileOutputStream fos = null;
    	Writer writerOut = null;
    	InputStream zipFile = null;
    	OutputStream docFileOut = null;
    	try {
        /** 初始化配置文件 **/
    	 Configuration configuration=  getConfigurationIntance();
        /** 加载模板 **/
        Template template = configuration.getTemplate(templateName+".xml");
        /** 准备数据 **/
       // Map<String,String> dataMap = new HashMap<String, String>();
        /** 在xml文件中有${textDeal}这个标签**/
        /** 指定输出word文件的路径 **/
        String outFilePath =basePath +outFileXml;
        File docFile = new File(outFilePath);
        if (!docFile.exists()) {
        	docFile.getParentFile().mkdirs();
        	docFile.createNewFile();
        }
        fos = new FileOutputStream(docFile);
        writerOut=new BufferedWriter(new OutputStreamWriter(fos),10240);
        template.process(dataMap,writerOut);
            /**
             * 加载zip文件流
             */
    	zipFile  = XMlToDocxAndPdf.class.getClassLoader().getResourceAsStream("xml"+File.separator+templateZip);
        ZipInputStream zipInputStream = ZipUtils.wrapZipInputStream(zipFile);
        File file = new File(basePath+docxName);
        if (!file.exists()) {
        	file.createNewFile();
        }
        docFileOut = new FileOutputStream(file);
        ZipOutputStream zipOutputStream = ZipUtils.wrapZipOutputStream(docFileOut);
        String itemname = "word/document.xml";
        ZipUtils.replaceItem(zipInputStream, zipOutputStream, itemname, new FileInputStream(docFile));
        } catch (Exception e) {

        }finally {
        	if (writerOut!=null) {
        		writerOut.flush();
        		writerOut.close();
        	}
        	ZipUtils.close(fos);
        	ZipUtils.close(zipFile);
        	ZipUtils.close(docFileOut);
        }
    }

    /**
     * 生成pdf
     */
     public static  String makePdfByXcode(String basePath,String pdfName){
        long startTime=System.currentTimeMillis();
        String filePath = null;
        ByteArrayInputStream swapStream = null;
        ByteArrayOutputStream   baos= null;
        try {
            XWPFDocument document=new XWPFDocument(new FileInputStream(new File(basePath+docxName)));
            baos=new   ByteArrayOutputStream();
           /* File outFile=new File(basePath+"test211.pdf");
            outFile.getParentFile().mkdirs();
            OutputStream out=new FileOutputStream(outFile);*/
            /**
             * 加载字体，不然linux下面中文为空或者乱码
             */
            IFontProvider fontProvider = new ExtITextFontRegistry();
            PdfOptions options= PdfOptions.create();
            options.fontProvider(fontProvider);
            PdfConverter.getInstance().convert(document,baos,options);
            swapStream = new ByteArrayInputStream(baos.toByteArray());
           // filePath= FileUtil.uploadFile(swapStream, pdfName+".pdf", null);
        }catch (Exception e) {

        }finally{
        	try {
	        	if (baos!=null) {
	        		baos.flush();
					baos.close();
	        	}
	        	if(swapStream!=null) {
	        		swapStream.close();
	        	}
	        	/**
	             * 清空文件夹
	             */
	            if (filePath!=null) {

	            }
        	} catch (IOException e) {
			}
        }

        return filePath;
    }
}
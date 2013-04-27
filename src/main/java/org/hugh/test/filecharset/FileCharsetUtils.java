package org.hugh.test.filecharset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class FileCharsetUtils
{

	public static String getFileCharset(String filePath) throws IOException
	{
		return getFileCharset(new File(filePath));
	}

	/** 只使用 org.mozilla.intl.chardet.nsDetector. 可以自定义检查长度.
	 * 
	 * @param file
	 * @return
	 * @throws IOException */
	public static String getFileCharset2(File file) throws IOException
	{
		nsICharsetDetectionObserverImpl observer = new nsICharsetDetectionObserverImpl();
		nsDetector det = new nsDetector(nsDetector.ALL);
		det.Init(observer);

		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));

		byte[] buf = new byte[1024];
		int len;

		while ((len = stream.read(buf, 0, buf.length)) != -1)
		{

			det.DoIt(buf, len, false);
		}

		det.DataEnd();

		return observer.encoding;
	}

	/** http://www.iteye.com/topic/108540
	 * 
	 * @param file
	 * @return
	 * @throws IOException */
	@SuppressWarnings("deprecation")
	public static String getFileCharset(File file) throws IOException
	{
		/*------------------------------------------------------------------------ 
		  detector是探测器，它把探测任务交给具体的探测实现类的实例完成。 
		  cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 
		  加进来，如ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector。   
		  detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的 
		  字符集编码。 
		--------------------------------------------------------------------------*/
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/*------------------------------------------------------------------------- 
		  ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于 
		  指示是否显示探测过程的详细信息，为false不显示。 
		---------------------------------------------------------------------------*/
		detector.add(new ParsingDetector(false));
		/*-------------------------------------------------------------------------- 
		  JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码 
		  测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以 
		  再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。 
		 ---------------------------------------------------------------------------*/
		detector.add(JChardetFacade.getInstance());
		// ASCIIDetector用于ASCII编码测定
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector用于Unicode家族编码的测定
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try
		{
			charset = detector.detectCodepage(file.toURL());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return charset.displayName();
	}

	private static class nsICharsetDetectionObserverImpl implements nsICharsetDetectionObserver
	{
		private String encoding = "";

		public void Notify(String charset)
		{
			this.encoding = charset;
		}
	}
}

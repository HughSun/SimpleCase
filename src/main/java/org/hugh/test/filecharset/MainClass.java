package org.hugh.test.filecharset;

import java.io.IOException;

public class MainClass
{
	public static void main(String[] args) throws IOException
	{
		String gb2312 = FileCharsetUtils.getFileCharset("src/main/resource/org/hugh/test/filecharset/gb2312.txt");
		System.out.println(gb2312);
		
		String utf8 = FileCharsetUtils.getFileCharset("src/main/resource/org/hugh/test/filecharset/utf-8.txt");
		System.out.println(utf8);
		
		String test = FileCharsetUtils.getFileCharset("src/main/resource/org/hugh/test/filecharset/test.txt");
		System.out.println(test);
		
	}
}

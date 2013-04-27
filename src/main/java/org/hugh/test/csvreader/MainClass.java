package org.hugh.test.csvreader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class MainClass
{
	public static void main(String[] args) throws IOException
	{

		File file = new File("src/main/resource/org/hugh/test/csvreader/test.csv");
		CSVReader reader = new CSVReader(new FileReader(file));
		String[] values;

		long time1 = System.currentTimeMillis();

		int lineNum = 1;

		while ((values = reader.readNext()) != null && !isEmpty(values))
		{
			System.out.println(lineNum);
			lineNum += 1;

			for (String value : values)
			{
				System.out.print("[" + value + "]/t");
			}
			System.out.println();
		}

		time1 = System.currentTimeMillis() - time1;

		reader.close();

		System.out.println(System.currentTimeMillis() - time1);
	}

	public static boolean isEmpty(String[] values)
	{
		for (String value : values)
			if (value != null && !value.isEmpty())
				return false;
		return true;
	}
}

package curate;

import java.io.*;
import java.util.*;

public class SplitFiles {

	static String prefix="/host/heteroDatasets/hyperparam-optim/d6/";
	static String infile="file1-mod.csv";
	static String outfile1="file1-id.csv";
	static String outfile2="file2-id.csv";
	
	public static void main(String[] args)throws IOException{
		splitFiles();
	}
	
	//split files will not have headers but will have id columns
	public static void splitFiles()throws IOException{
		Scanner in=new Scanner(new FileReader(prefix+infile));
		PrintWriter out1=new PrintWriter(new File(prefix+outfile1));
		PrintWriter out2=new PrintWriter(new File(prefix+outfile2));
		
		//first pass
		HashSet<String> dupNums=new HashSet<String>(5000);
		if(in.hasNextLine())
			in.nextLine();
		
		while(in.hasNextLine()){
			String line=in.nextLine();
			String[] fields=(new CSVParser()).parseLine(line);
			if(fields[0].contains("dup")){
				out2.println(line);
				dupNums.add(fields[0].split("-")[1]);
			}
		}
		
		in.close();
		
		//second pass
		Random k=new Random(System.currentTimeMillis());
		in=new Scanner(new FileReader(prefix+infile));
		if(in.hasNextLine())
			in.nextLine();
		while(in.hasNextLine()){
			String line=in.nextLine();
			String[] fields=(new CSVParser()).parseLine(line);
			if(fields[0].contains("dup"))
				continue;
			else if(dupNums.contains(fields[0].split("-")[1]))
				out1.println(line);
			else{
				if(k.nextInt()%2==0)
					out1.println(line);
				else
					out2.println(line);
			}
		}
		in.close();
		out1.close();
		out2.close();
	}
}

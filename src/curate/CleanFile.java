package curate;

import java.io.*;
import java.util.*;

public class CleanFile {

	static String prefix="/host/heteroDatasets/hyperparam-optim/d6/";
	//use for first clean-up
	static String origfile="file1-orig.csv";
	static String modfile="file1-mod.csv";
	
	//use for next clean-up (both files)
	static String idfile="file1-id.csv";
	static String colfile="file1.csv";
	
	public static void main(String[] args)throws IOException{
		//removeWhiteSpace();
		removeColumn(0);
	}
	
	public static void removeColumn(int colnum)throws IOException{
		Scanner in=new Scanner(new FileReader(prefix+idfile));
		PrintWriter out=new PrintWriter(new File(prefix+colfile));
		while(in.hasNextLine()){
			String[] fields=(new CSVParser()).parseLine(in.nextLine());
			String[] f=new String[fields.length-1];
			for(int i=0, count=0; i<fields.length; i++)
				if(i==colnum)
					continue;
				else f[count++]=fields[i];
			out.println(fieldsToLine(f));
		}
		out.close();
		in.close();
	}
	
	public static void removeWhiteSpace()throws IOException{
		Scanner in=new Scanner(new FileReader(prefix+origfile));
		PrintWriter out=new PrintWriter(new File(prefix+modfile));
		while(in.hasNextLine()){
			String line=in.nextLine();
			line=line.replace("\"", "'");
			String[] fields=null;
			try{
			fields=(new CSVParser()).parseLine(line);
			}catch(Exception e){
				System.out.println(line);
			}
			for(int i=0; i<fields.length; i++){
				fields[i]=fields[i].trim();
			}
			out.println(fieldsToLine(fields));
		}
		in.close();
		out.close();
	}
	
	public static String fieldsToLine(String[] fields){
		String a="";
		for(String f:fields)
			a+=(f+",");
		a=a.substring(0,a.length()-1);
		return a;
	}
}

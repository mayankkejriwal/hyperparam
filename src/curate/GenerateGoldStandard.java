package curate;

import java.io.*;
import java.util.*;


//we will also use this to generate schema1,2.txt and goldStandard_schema (all hard-coded)
public class GenerateGoldStandard {

	static String prefix="/host/heteroDatasets/hyperparam-optim/d6/";
	static String gs="goldStandard";
	static String infile1="file1-id.csv";
	static String infile2="file2-id.csv";
	
	static String sfile1="archive/schema1.txt";
	static String sfile2="archive/schema2.txt";
	static String sgold="goldStandard_schema";
	
	public static void main(String[] args)throws IOException{
		generateGold();
		generateSchemaFiles();
	}
	
	public static void generateSchemaFiles()throws IOException{
		PrintWriter out1=new PrintWriter(new File(prefix+sfile1));
		PrintWriter out2=new PrintWriter(new File(prefix+sfile2));
		PrintWriter out3=new PrintWriter(new File(prefix+sgold));
		String[] header=(new String("culture,sex,age,date_of_birth,title,given_name,surname,state,suburb,postcode,street_number,address_1,address_2,phone_number,soc_sec_id,blocking_number,family_role")).split(",");
		for(int i=0; i<header.length; i++)
		{
			out1.println(i+"\t"+header[i]);
			out2.println(i+"\t"+header[i]);
			out3.println(i+" "+i);
		}
		out1.close();
		out2.close();
		out3.close();
	}
	
	public static void generateGold()throws IOException{
		Scanner in1=new Scanner(new FileReader(prefix+infile1));
		Scanner in2=new Scanner(new FileReader(prefix+infile2));
		PrintWriter out=new PrintWriter(new File(prefix+gs));
		HashMap<String,HashSet<Integer>> dups=new HashMap<String,HashSet<Integer>>();
		int count=0;
		while(in2.hasNextLine()){
			String id=(new CSVParser()).parseLine(in2.nextLine())[0];
			if(id.contains("dup")){
				String num=id.split("-")[1];
				if(!dups.containsKey(num))
					dups.put(num,new HashSet<Integer>());
				dups.get(num).add(count);
			}
			count++;
		}
		count=0;
		while(in1.hasNextLine()){
			String id=(new CSVParser()).parseLine(in1.nextLine())[0];
			String num=id.split("-")[1];
			if(dups.containsKey(num)){
				for(int j:dups.get(num))
					out.println(count+" "+j);
			}
			count++;
		}
		
		in1.close();
		in2.close();
		out.close();
	}
}

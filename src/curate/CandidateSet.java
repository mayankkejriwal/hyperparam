package curate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import generateSupFiles.GenerateFeaturesFile.Hetero;

public class CandidateSet {

	static String prefix="/host/heteroDatasets/hyperparam-optim/game/";
	static String file1="file1.csv";
	static String file2="file2.csv";
	static String schemaGold="goldStandard_schema";
	static String candidateSet="candidateSet";
	static String gold="goldStandard";
	static String output="candidateSet.csv";
	
	static String oldCandidateSet="candidateSet-notSupp";
	
	
	public static void main(String[] args)throws IOException{
		unionGoldStandard();
		
		Hetero.generateCSVCandidateSet(prefix+file1, prefix+file2, prefix+schemaGold,
				prefix+candidateSet,prefix+gold, prefix+output);
		
		
	}
	
	public static void unionGoldStandard()throws IOException{
		Scanner in=new Scanner(new FileReader(prefix+oldCandidateSet));
		HashSet<String> cs=new HashSet<String>();
		while(in.hasNextLine()){
			String line=in.nextLine();
			cs.add(line);
		}
		in.close();
		in=new Scanner(new FileReader(prefix+gold));
		HashSet<String> gs=new HashSet<String>();
		while(in.hasNextLine()){
			String line=in.nextLine();
			gs.add(line);
		}
		in.close();
		PrintWriter out=new PrintWriter(new File(prefix+candidateSet));
		for(String c:cs)
			out.println(c);
		for(String g:gs)
			if(!cs.contains(g))
				out.println(g);
		out.close();
	}
}

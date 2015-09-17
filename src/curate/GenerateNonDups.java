package curate;

import java.io.*;
import java.util.*;

@Deprecated
public class GenerateNonDups {
	static String prefix="/host/heteroDatasets/hyperparam-optim/d1/";
	static String cs="candidateSet";
	static String ndcs="nondups-candidateSet";
	
	public static void main(String[] args)throws IOException{
		generateNDCS();
	}
	
	public static void generateNDCS()throws IOException{
		HashMap<String, HashSet<String>> cs_pairs=new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> ndcs_pairs=new HashMap<String, HashSet<String>>();
		HashSet<String> elements=new HashSet<String>(30000);
		Scanner in=new Scanner(new FileReader(prefix+cs));
		int count=0;
		while(in.hasNextLine()){
			count++;
			String[] pair=in.nextLine().split(" ");
			if(!cs_pairs.containsKey(pair[0])){
				cs_pairs.put(pair[0], new HashSet<String>());
				ndcs_pairs.put(pair[0], new HashSet<String>());
			}
			cs_pairs.get(pair[0]).add(pair[1]);
			elements.add(pair[1]);
			
		}
		in.close();
		ArrayList<String> array=new ArrayList<String>(elements);
		int l=array.size();
		Random rand=new Random(System.currentTimeMillis());
		int limit=0;
		while(limit<=count){
			for(String k:cs_pairs.keySet()){
				HashSet<String> f=cs_pairs.get(k);
				String index=null;
				while(true){
					index=array.get(rand.nextInt(l));
					if(!f.contains(index))
						break;
				}
				ndcs_pairs.get(k).add(index);
				limit++;
				if(limit>count)
					break;
			}
		}
		
		PrintWriter out=new PrintWriter(new File(prefix+ndcs));
		for(String k:ndcs_pairs.keySet()){
			HashSet<String> f=ndcs_pairs.get(k);
			for(String d:f)
				out.println(k+" "+d);
		}
		out.close();
	}
}

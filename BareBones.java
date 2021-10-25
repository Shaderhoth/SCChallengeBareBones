import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class BareBones {
	int currentLine = 0;
	int totalLines = 0;
	int maxWhile = 0;
	int tempWhile = 0;
	String tempReturn = "";
	String line = "";
	boolean finished = false;
	HashMap<String, String> varMap = new HashMap<String, String>();
	public static void main(String[] args) {
		BareBones bb = new BareBones();
		bb.run();
	}
	public void run(){
		try{
			BufferedReader br = new BufferedReader(new FileReader("BareBones.txt"));
			while (line != null){
				line = br.readLine();
				totalLines++;
				if( line != null){
					if (line.contains("while")){
						tempWhile++;
						if (tempWhile > maxWhile){
							maxWhile = tempWhile;					
						}
					}else if (line.contains("while")){
						tempWhile--;
					}
				}
			}
			String[] lines = new String[totalLines];
			br = new BufferedReader(new FileReader("BareBones.txt"));
			currentLine = 0;
			while (currentLine < totalLines){
				lines[currentLine]=br.readLine();
				currentLine++;
			}
			int[] whiles = new int[maxWhile];
			String variable;
			int lastEnd = 0;
			tempWhile = -1;
			String[] splitLine;
			currentLine = 0;
			String tag;
			while (currentLine < totalLines){
				if( lines[currentLine] != null){
					line = lines[currentLine].trim();
					splitLine = line.split("\\s+");
					if (splitLine[0].equals("clear")){
						variable = splitLine[1].replaceAll(";", "");
						setVar(variable, 0);
					}else if (splitLine[0].equals("incr")){
						variable = splitLine[1].replaceAll(";", "");
						setVar(variable, getVar(variable) + 1);
					}else if (splitLine[0].equals("decr")){
						variable = splitLine[1].replaceAll(";", "");
						setVar(variable, getVar(variable) - 1);
					}else if (splitLine[0].equals("while")){
						tempWhile++;
						whiles[tempWhile] = currentLine;
					}else{
						splitLine = lines[whiles[tempWhile]].trim().split("\\s+");
						variable = splitLine[1];
						if (getVar(variable) > 0){
							currentLine = whiles[tempWhile];
						}else {
							tempWhile--;
						}
					}
				}
				currentLine++;
			}
			for(Map.Entry<String, String> item : varMap.entrySet()) {
				System.out.println(item.getKey() + ": " + item.getValue());
			}
		} catch (Exception e) {
      System.out.println(e);
      System.exit(1);
    }
	}
	public int getVar(String var){
		tempReturn = varMap.get(var);
		if (tempReturn == null){
			varMap.put(var, "0");
			return 0;
		} else {
			return Integer.parseInt(tempReturn);
		}
	}
	public void setVar(String var, int amt){
		varMap.put(var, String.valueOf(amt));
	}
}
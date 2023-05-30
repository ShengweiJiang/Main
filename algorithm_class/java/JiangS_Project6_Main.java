import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class DijktraSSS{
	int numNodes;
	int sourceNode;
	int minNode;
	int currentNode;
	int newCost;
	int[][] costMatrix;
	int[] fatherAry;
	int[] ToDoAry;
	int[] BestAry ;
	
	DijktraSSS(int numNodes){ //pass test
		this.numNodes = numNodes;
		costMatrix = new int[numNodes+1][numNodes+1]; //create an matrix with (N+1)^2
		for(int j=0;j<=numNodes;j++) { // init it with all 9999
			for(int k=0;k<=numNodes;k++) {
				costMatrix[j][k]=9999;
			}
		}
		
		for(int j=0;j<=numNodes;j++) { //init diagnal with all 0
			
				costMatrix[j][j]=0;
			
		}

		for(int i=1;i<=numNodes;i++) { //init first row with 0,1,2...N
			costMatrix[0][i]=i;
			
		}
		
		for(int j=1;j<=numNodes;j++) { //init first col with 0,1,2...N
			costMatrix[j][0]=j;
		}
		

		BestAry = new int[numNodes+1]; //init BestAry 
		
		for(int i=0; i<=numNodes; i++) BestAry[i] = 9999; //set init bestary to 9999
	
		fatherAry = new int[numNodes+1]; //init fatherAry 
		ToDoAry = new int[numNodes+1]; //init ToDoAry 
		for(int i=0; i<=numNodes; i++) ToDoAry[i] = 1; //set init bestary to 1
	
	}
	
	 void loadCostMatrix(Scanner inFile) { //pass test
		 int i;
		 int j;
		 int k;
		 while(inFile.hasNext()) {
			 i = inFile.nextInt();
			 j = inFile.nextInt();
			 k = inFile.nextInt();
			 
			 costMatrix[i][j]=k;
			 costMatrix[j][i]=k;
			 
		 }
		 
		

		
	}
	
	void setBestAry(int sourceNode) { //pass test
		
		for(int i=1;i<=numNodes;i++) {
			BestAry[i]=costMatrix[sourceNode][i];
		}
		
		
	//	for(int i=1;i<=numNodes;i++) {
		//	System.out.print(BestAry[i]+" ");
		//}
		//System.out.println();
		
	}
	
	void setFatherAry(int sourceNode){ //pass test
		
		for(int i=1;i<=numNodes;i++) {
			fatherAry[i] = sourceNode;
		}
		
		//for(int i=0;i<=numNodes;i++) {
		//	System.out.print(fatherAry[i]);
		//}
		//System.out.println();
	}
	
	void setToDoAry(int sourceNode) { //pass test
		for(int i=0; i<=numNodes; i++) ToDoAry[i] = 1; //set init bestary to 1
		ToDoAry[sourceNode]=0;
		//for(int i=0;i<=numNodes;i++) {
		//	System.out.print(ToDoAry[i]);
		//}
		//System.out.println();
	}
	
	int findMinNode() {
		int minCost = 99999;
		int minNode = 0;
		int index = 1;
		
		while(index <= numNodes) {
			
		if(ToDoAry[index]  ==1 && BestAry[index] < minCost) {
			
		
			minCost = BestAry[index];
			minNode = index;
		}
		
		index++;
		
		}
		
		
	
		return minNode;
		
	}
	
	int computeCost(int minNode, int Node) { //sudo pass
		return BestAry[minNode]+costMatrix[minNode][Node];
	}
	
	boolean checkToDoary() { //sudo pass
		for(int i=1;i<=numNodes;i++){
			if(ToDoAry[i] == 0);
			else return false;
		}
		return true;
	}
	
	void debugPrint( BufferedWriter deBugFile, int sourceNode) throws IOException {
		if(sourceNode !=1) return;
		deBugFile.write("the sourceNode is :" + sourceNode + "\n");
		deBugFile.write("the father array is : " );
		for(int i= 1 ; i<=numNodes; i++) {
			deBugFile.write(fatherAry[i]+" ");
			
		}
		
		deBugFile.write("\n");
		
		deBugFile.write("the best cost array is : ");
		for(int i=1; i<=numNodes; i++) {
			deBugFile.write(BestAry[i]+" ");
		}
		deBugFile.write("\n");
		
		deBugFile.write("the Todo array is : ");
		for(int i=1; i<=numNodes; i++) {
			deBugFile.write(ToDoAry[i] + " ") ;
		}
		deBugFile.write("\n");
		deBugFile.write("===============================\n\n");
	}
	
	void printShortestPath(int currentNode, int sourceNode, BufferedWriter SSSfile) throws IOException {
		int father = currentNode;
	
		
		
		SSSfile.write("the path from " + sourceNode +" to "+ currentNode + " :" + currentNode );
				
				
				do {
					
					father = fatherAry[father];
					SSSfile.write("<-" + father );
				}
			
					while(father != sourceNode);
				
				
				
				
				
				SSSfile.write(": cost = "+ BestAry[currentNode]+"\n");
			
			//}
			
	
		
	}
	
}

public class JiangS_Project6_Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner inFile = new Scanner(new File(args[0]));
		BufferedWriter SSSfile = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter deBugFile = new BufferedWriter(new FileWriter(args[2]));
		int numNodes = inFile.nextInt();
		//System.out.println(numNodes);
		DijktraSSS obj = new DijktraSSS(numNodes);
		obj.loadCostMatrix(inFile);
		int sourceNode = 1;
		SSSfile.write("========================================\n" + "There are " + numNodes + " nodes in the input graph. Below are all pairs of shortest paths:\n=========================================\n");
		while(sourceNode <= numNodes) {
		obj.setBestAry(sourceNode);
		obj.setFatherAry(sourceNode);
		obj.setToDoAry(sourceNode);
		
		
		while(obj.checkToDoary() != true) {
		obj.minNode = obj.findMinNode();
		
		obj.ToDoAry[obj.minNode]=0;
		obj.debugPrint(deBugFile,sourceNode);
		
		int childNode = 1;
		
		while(childNode <= numNodes) {
		if(obj.ToDoAry[childNode] == 1) {
			int newCost = obj.computeCost(obj.minNode,childNode);
			if(newCost < obj.BestAry[childNode]) {
				obj.BestAry[childNode] = newCost;
				obj.fatherAry[childNode] = obj.minNode;
				
				
		
				
			
				obj.debugPrint(deBugFile,sourceNode);
				
				
			}
		}
		childNode++;
		}
		
		}
		
		
		int currentNode = 1;
		
		while(currentNode <= numNodes) {
			
		obj.printShortestPath(currentNode, sourceNode, SSSfile);
		
		//for(int i=1;i<=numNodes;i++) {
		//	System.out.print(obj.fatherAry[i] + " ");
		//}
		
		
		
		currentNode++;
		}
		SSSfile.write("==========================================\n\n");
		sourceNode++;
		
	}
		
		inFile.close();
		deBugFile.close();
		SSSfile.close();
		
	}
	


}

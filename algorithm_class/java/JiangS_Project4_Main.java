import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class TreeNode{
	
	int key1;
	int key2;
	int rank;
	TreeNode child1;
	TreeNode child2;
	TreeNode child3;
	TreeNode father;
	
	TreeNode(int key1,int key2,int rank,TreeNode child1,TreeNode child2,TreeNode child3,TreeNode father){
		this.key1 = key1;
		this.key2 = key2;
		this.rank = rank;
		this.child1 = child1;
		this.child2 = child2;
		this.child3 = child3;
		this.father = father;
	}
	
	public void printNode(TreeNode Tnode, BufferedWriter outFile) throws IOException {
		if(this.rank==-1||this.rank==5) {
			if(Trees.isLeaf(Tnode)) outFile.write(this.key1+","+this.key2+","+this.rank+","+"null"+","+"null"+","+"null"+","+"null"+"\n"); //only root, no child
			else if(this.child3==null ) outFile.write(this.key1+","+this.key2+","+this.rank+","+this.child1.key1+","+this.child2.key1+","+"null"+","+"null"+"\n");//root with 2 child
			else  outFile.write(this.key1+","+this.key2+","+this.rank+","+this.child1.key1+","+this.child2.key2+","+this.child3.key1+","+"null"+"\n"); //root with 3 child
			
		}
		
		else {
			if(Trees.isLeaf(Tnode)) outFile.write(this.key1+","+this.key2+","+this.rank+","+"null"+","+"null"+","+"null"+","+this.father.key1+"\n"); //only root, no child
			else if(this.child3==null ) outFile.write(this.key1+","+this.key2+","+this.rank+","+this.child1.key1+","+this.child2.key1+","+"null"+","+this.father.key1+"\n");//root with 2 child
			else  outFile.write(this.key1+","+this.key2+","+this.rank+","+this.child1.key1+","+this.child2.key2+","+this.child3.key1+","+this.father.key1+"\n"); //root with 3 child
			
		}
	}
	
}




class Trees{
	 TreeNode root;
	 int data1=-1;
		int data2=-1;
	public  TreeNode initialTree(Scanner inFile, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("Entering initialTree () method\n");
		root = new TreeNode(-1,-1,-1,null,null,null,null);
		
		if(inFile.hasNext())  data1 = inFile.nextInt();
		else System.out.println("can't read data from inFile");
		
		if(inFile.hasNext())  data2 = inFile.nextInt();
		else System.out.println("can't read data from inFile");
		
		deBugFile.write("before swap data1 and data2 are: " + data1 + "," + data2+"\n");
		if(data2<data1) swap(data1,data2);
		
		deBugFile.write("after swap data1 and data2 are: " + data1 + "," + data2+"\n");
		
		TreeNode newNode1 = new TreeNode(data1,-1,1,null,null,null,root);
		TreeNode newNode2 = new TreeNode(data2,-1,2,null,null,null,root);
		root.child1 = newNode1;
		root.child2 = newNode2;
		root.key1 = data2;
		root.printNode(root, deBugFile);
		deBugFile.write("Exiting initialTree() method\n\n");
		
		return root;
	}
	
	public void build23Tree(Scanner inFile, Trees root, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("Entering build23Tree() method \n");
		int data;
		TreeNode spot;
		while(inFile.hasNext()) {
			data = inFile.nextInt();
			spot = findSpot(root.root,data,deBugFile);
			if(spot==null) continue;
			deBugFile.write("in build23Tree(), printing spot info. \n");
			spot.printNode(spot, deBugFile);
			TreeNode leafNode = new TreeNode(data, -1, 5, null, null, null, null);
			treeInsert(spot,leafNode,deBugFile);
			deBugFile.write("In build23Tree; printing preOrder () after one treeInsert \n");
			perOrder(root.root,deBugFile);
		}
		inFile.close();
		deBugFile.close();
		
	}
	
	public void swap(int data1,int data2){
		this.data1 = data2;
		this.data2 = data1;
	}
	
	TreeNode findSpot(TreeNode spot, int data, BufferedWriter deBugFile) throws IOException {
		deBugFile.write("Entering findSpot() method \n");
		deBugFile.write("Spot's key1 and key2 and data are: "+ spot.key1 + "," + spot.key2 + "," + data+"\n");
		if(spot.child1 == null) {
			deBugFile.write("In findSpot () You are at leaf level, you are too far down the tree!!  \n");
			return null;
		}
		
		if(data == spot.key1 || data == spot.key2) {
			deBugFile.write("In findSpot (): data is already in Spot¡¯s keys, no need to search further!  \n");
			return null;
		}
		
		if(Trees.isLeaf(spot.child1)) {
			if(data == spot.child1.key1 || data == spot.child2.key1) {
				deBugFile.write(" In findSpot (): data is already in a leaf node. " );
				return null;
			}
		
			else return spot;
		}
		
		else {
			if(data<spot.key1) return findSpot(spot.child1,data,deBugFile);
			else if(spot.key2 == -1 || data < spot.key2) return findSpot(spot.child2,data,deBugFile);
			else if(spot.key2!=-1 && data>= spot.key2 ) return findSpot(spot.child3,data,deBugFile);
			else {
				deBugFile.write("In findSpot (), something is wrong about data " + data + "\n");
				return null;
			}
			
			
		}
		
		
	
	}
	
	void treeInsert(TreeNode spot, TreeNode newNode, BufferedWriter deBugFile) throws IOException{
		int count;
		if(spot == null) {
			deBugFile.write("In treeInsert (), Spot is null, something is wrong \n");
			return; 
		}
		
		else {
			deBugFile.write("In treeInsert (). Printing Spot and newNode info \n");
			spot.printNode(spot, deBugFile);
			newNode.printNode(newNode, deBugFile);
		}
		
		if(spot.key2 == -1) count =2;
		else count = 3;
		
		deBugFile.write("In treeInsert () method; Spot kids count is " +count + "\n");
		
		if (count ==2) spotHas2kidsCase(spot,newNode,deBugFile);
		else if (count == 3) spotHas3kidsCase(spot,newNode,deBugFile);
		deBugFile.write("Leaving treeInsert () method \n");
		
	}
	int countKids(TreeNode Tnode) {
		if(Tnode==null) return 2;
		else return 3;
		
	}
	
	void spotHas2kidsCase(TreeNode spot, TreeNode newNode, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("Entering spotHas2kidCase () method \n");
		deBugFile.write("In spotHas2kidCase () method; Spot¡¯s rank is " + spot.rank+"\n");
		if(newNode.key1<spot.child2.key1) {
			spot.child3 = spot.child2;
			spot.child2 = newNode;	
		}
		else spot.child3 = newNode;
		if(spot.child2.key1<spot.child1.key1) {
			TreeNode tmpNode = spot.child1;
			spot.child1 = spot.child2;
			spot.child2 = tmpNode;
		}
		
		spot.child1.father = spot;
		spot.child1.rank = 1;
		spot.child2.father = spot;
		 spot.child2.rank = 2;
		 spot.child3.father = spot;
		 spot.child3.rank = 3;
		 
		 updateKeys(spot,deBugFile);
		
		
		
		
		
		
		
	}
	void spotHas3kidsCase(TreeNode spot, TreeNode newNode, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("Entering spotHas3kidCase () method \n");
		deBugFile.write("\"In spotHas3kidCase () method; Spot¡¯s rank is " + spot.rank+"\n");
		TreeNode sibling = new TreeNode(-1, -1, 5, null, null, null, null);
		if(newNode.key1>spot.child3.key1) {
			sibling.child2 = newNode;
			sibling.child1 = spot.child3;
			spot.child3 = null;
		}
	
		else if(newNode.key1<spot.child3.key1) {
			sibling.child2 = spot.child3;
			spot.child3 = newNode;
		}
		
		if(spot.child3!=null) {
			if(spot.child3.key1>spot.child2.key1) {
				sibling.child1 = spot.child3;
				spot.child3 = null;
			}
			else {
				sibling.child1 = spot.child2;
				spot.child2 = newNode;
			}
		}
		
		else if(spot.child2.key1<spot.child1.key1) {
			TreeNode tmpNode = spot.child1;
			spot.child1 = spot.child2;
			spot.child2 = tmpNode;
		}
		
		spot.child1.father = spot;
		spot.child1.rank = 1;
		spot.child2.father = spot;
		spot.child2.rank = 2;
		spot.child3 = null;
		sibling.child1.father = sibling;
		sibling.child1.rank = 1;
		sibling.child2.father = sibling;
		sibling.child2.rank = 2;
		sibling.child3 = null;
		updateKeys(spot,deBugFile);
		updateKeys(sibling,deBugFile);
		if(spot.rank == -1 && spot.father == null) root = makeNewRoot(spot,sibling,deBugFile);
		else treeInsert(spot.father,sibling,deBugFile);
		
		if(spot.rank>1) updateKeys(spot.father,deBugFile);
		deBugFile.write("Leaving spotHas3kidsCase() method.\n");
		
	}
	
	public static boolean isLeaf(TreeNode Tnode) {
		return Tnode.child1==null;
	}
	
	TreeNode makeNewRoot(TreeNode spot, TreeNode sibling, BufferedWriter deBugFile) throws IOException {
		deBugFile.write("Entering makeNewRoot () method. \n");
		TreeNode newRoot = new TreeNode(-1, -1, -1, null, null, null, null);
		newRoot.child1 = spot;
		newRoot.child2 = sibling;
		newRoot.child3 = null;
		newRoot.key1 = findMinLeaf(sibling);
		newRoot.key2 = -1;
		spot.father = newRoot;
		spot.rank = 1;
		sibling.father = newRoot;
		sibling.rank = 2;
		deBugFile.write("Leaving makeNewRoot () method. \n");
		return newRoot;
		
	}
	
	void updateKeys(TreeNode Tnode, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("Entering updateKeys () method.\n");
		if(Tnode == null) return;
		Tnode.key1 = findMinLeaf(Tnode.child2);
		Tnode.key2 = findMinLeaf(Tnode.child3);
		if(Tnode.rank>1) updateKeys(Tnode.father,deBugFile);
		deBugFile.write("Leaving updateKeys () method.\n");
	}
	
	int findMinLeaf(TreeNode Tnode) {
		if(Tnode == null) return -1;
		if(Tnode.child1 == null) return Tnode.key1;
		else return findMinLeaf(Tnode.child1);
	}
	
	public void perOrder(TreeNode root, BufferedWriter outFile) throws IOException{
		if(Trees.isLeaf(root)) outFile.write("("+root.key1+","+root.key2+")\n");
		else {
			outFile.write("("+root.key1+","+root.key2+")\n");
			if(root.child3==null) {//2 child 
				perOrder(root.child1,outFile);
				perOrder(root.child2,outFile);
			}
			else {
				perOrder(root.child1,outFile);
				perOrder(root.child2,outFile);
				perOrder(root.child3,outFile);
				
			}
			
		}
		
	}
	
	/*int threeDepth(TreeNode root) { // not used
		
	}
	*/
}



public class JiangS_Project4_Main {

	public static void main(String[] args) throws IOException {
		Scanner inFile = new Scanner(new File(args[0]));
		BufferedWriter treeFile = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter deBugFile = new BufferedWriter(new FileWriter(args[2]));
		Trees root = new Trees();
		root.initialTree(inFile, deBugFile);
		root.build23Tree(inFile,root,deBugFile);
		root.perOrder (root.root, treeFile);
		inFile.close();
		treeFile.close();
		deBugFile.close();
		
		
		
		
		// TODO Auto-generated method stub

	}

}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class HNode{
	String chStr,code;
	int prob,numBits,entropy;
	HNode left,right,next;
	
	HNode(String chStr,int prob,String code,int numBits,int entropy,HNode left,HNode right,HNode next){
		this.chStr=chStr;
		this.prob=prob;
		this.code=code;
		this.numBits=numBits;
		this.entropy=entropy;
		this.left=left;
		this.right=right;
		this.next=next;
		
				
	}
	
	public static void printNode(HNode t, BufferedWriter outFile,int i) throws IOException{
		if(i==0) {
			while(t.next!=null) {
				if(t.left!=null&&t.right!=null) outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+t.next.chStr+","+t.left.chStr+","+t.right.chStr+")"+"->");
				else outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+t.next.chStr+","+"null"+","+"null"+")"+"->");
			t=t.next;
			
			}
			if(t.left!=null&&t.right!=null) outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+"null"+","+t.left.chStr+","+t.right.chStr+")\n");
			else outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+"null"+","+"null"+","+"null"+")\n");

	}
		
		else if(t!=null){
			if(t.left!=null&&t.right!=null) outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+"null"+","+t.left.chStr+","+t.right.chStr+")"+"->");
			else outFile.write("("+t.chStr+","+t.prob+","+t.code+","+t.numBits+","+t.entropy+","+t.next.chStr+","+"null"+","+"null"+")"+"->");
		
		
		}
			

		}
	}
	


class Huffman{
	HNode listHead,Root;
	int totalEntropy;
	
	Huffman(){
		listHead = new HNode("dummy", 0, "", 0, 0, null, null, null);
	}
	
	HNode findSpot(HNode listHead, HNode newNode) {
		HNode spot = listHead;
		while(spot.next!=null && newNode.prob>spot.next.prob ) {
			spot=spot.next;
		}
		return spot; 
	}
	
	void listInsert(HNode listHead, HNode newNode, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("In listInsert method \n");
		HNode spot = this.findSpot(listHead,newNode);
		deBugFile.write("Returns from findSpot where Spot.data is ");
		deBugFile.write(spot.prob + "\n");
		newNode.next = spot.next;
		spot.next = newNode;
	}
	
	void constructHuffmanLList(Scanner inFile, BufferedWriter deBugFile, HNode listHead) throws IOException{
		deBugFile.write("Entering constructHuffmanLList method\n");
		while(inFile.hasNext()) {
		String chr=inFile.next();
		int prob = Integer.parseInt(inFile.next());
		
		HNode newNode = new HNode( chr,prob,"",0,0,null,null,null);
		this.listInsert(listHead, newNode, deBugFile);
		this.printList(listHead, deBugFile);
		}
		deBugFile.write("Exit constructHuffmanLList method \n\n");
		
		
	}
	

HNode constructHuffmanBinTree(HNode listHead, BufferedWriter deBugFile) throws IOException {
	deBugFile.write("Entering constructHuffmanBinTree method\n");
	while(listHead.next.next!=null) {
	HNode newNode = new HNode ("", 0, "", 0, 0, null, null, null) ;
	newNode.prob=listHead.next.prob + listHead.next.next.prob;
	newNode.chStr=listHead.next.chStr + listHead.next.next.chStr;
	newNode.left=listHead.next;
	newNode.right=listHead.next.next;
	newNode.next = null;
	this.listInsert(listHead, newNode, deBugFile);
	listHead.next=listHead.next.next.next;
	printList(listHead,deBugFile);
	}
	
	return listHead.next;
	
		
	
	
	}
	


	void printEntropyTable(HNode T, String code, BufferedWriter outFile1,int totalEntropy) throws IOException{
		if(Huffman.isLeaf(T)) {
		T.code = code;
		T.numBits = code.length();
		T.entropy=T.prob*T.numBits;
		totalEntropy +=T.entropy;

		this.totalEntropy += totalEntropy;
		outFile1.write(T.chStr+" "+T.prob+" "+T.code+" "+T.numBits+" "+T.entropy+"\n");
		}
		else {
			printEntropyTable (T.left, code + "0", outFile1, totalEntropy);
			printEntropyTable (T.right, code + "1", outFile1, totalEntropy);
		}
		
	}
	
	static void preOrder(HNode T,BufferedWriter outFile1) throws IOException{
		if(Huffman.isLeaf(T)) HNode.printNode(T, outFile1,1);
		else {
			HNode.printNode(T, outFile1,1);
			Huffman.preOrder(T.left, outFile1);
			Huffman.preOrder(T.right, outFile1);
		}
	}
	
	static void inOrder(HNode T,BufferedWriter outFile1) throws IOException{
		if(Huffman.isLeaf(T)) HNode.printNode(T, outFile1,1);
		else {
			Huffman.inOrder(T.left, outFile1);
			HNode.printNode(T, outFile1,1);
		
			Huffman.inOrder(T.right, outFile1);
		}
	}
	static void postOrder(HNode T,BufferedWriter outFile1) throws IOException{
		if(Huffman.isLeaf(T)) HNode.printNode(T, outFile1,1);
		else {
			
			Huffman.postOrder(T.left, outFile1);
			Huffman.postOrder(T.right, outFile1);
			HNode.printNode(T, outFile1,1);
		}
	}
	
	static boolean isLeaf(HNode node){
		return node.left==null&&node.right==null;
	}
	
	void printList(HNode listHead, BufferedWriter outFile) throws IOException{
		HNode.printNode(listHead,outFile,0);
	}
	
	
}

public class JiangS_Project3_Main {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(new File(args[0]));
		BufferedWriter outFile1 = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter deBugFile = new BufferedWriter(new FileWriter(args[2]));
		Huffman huffman = new Huffman();
		HNode listHead = huffman.listHead;
		huffman.constructHuffmanLList(input, deBugFile, listHead);
		huffman.printList(listHead, outFile1);
		HNode Root = huffman.constructHuffmanBinTree(listHead,deBugFile);
		outFile1.write("printing the entropy Table\n");
		huffman.totalEntropy=0;
		huffman.printEntropyTable(Root, "", outFile1, huffman.totalEntropy);
		
		
		outFile1.write("The Huffman Coding scheme's entropy = " + (double)huffman.totalEntropy/100.00+"\n");
		outFile1.write("print preOrder\n");
		Huffman.preOrder(Root, outFile1);
		outFile1.write("perOrder End\n\n");
		outFile1.write("print inOrder\n");
		Huffman.inOrder(Root, outFile1);
		outFile1.write("inOrder End\n\n");
		outFile1.write("print postOrder\n");
		Huffman.postOrder(Root, outFile1);
		outFile1.write("postOrder End\n\n");
		input.close();
		outFile1.close();
		deBugFile.close();
		
	}
}

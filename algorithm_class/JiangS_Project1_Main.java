import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class JiangS_Project1_Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner  inFile = new Scanner(new File(args[0]));
		BufferedWriter  deBugFile = new BufferedWriter(new FileWriter(args[2]));
		BufferedWriter outFile = new BufferedWriter(new FileWriter(args[1]));

		
		ListNode listHead = new ListNode("dummy");
		 LList orderedList= new LList(listHead,inFile,deBugFile);
		 
		 orderedList.printList(listHead, outFile);
		 
		ListNode middleNode = orderedList.findMiddleNode(listHead,deBugFile);
		
		if(middleNode != null) outFile.write("the world in the middle of list is: " + middleNode.data);
		 
		 
		 inFile.close();
		 outFile.close();
		 deBugFile.close();
		
	} //ending main method

}

class ListNode{
	String data;
	ListNode next;
	ListNode(String data){
		this.data = data;
		next = null;
	}
}

class LList{
	ListNode listHead = null;
	ListNode middleNode = null;
	
	LList(ListNode listHead, Scanner inFile, BufferedWriter deBugFile ) throws IOException{
		this.listHead = listHead;
		deBugFile.write("In constructLL method \n");
		while(inFile.hasNext()) {
			
			String data = inFile.next();
			
			ListNode newNode = new ListNode(data);
			this.listInsert(listHead,newNode,deBugFile);
			this.printList(listHead,deBugFile);
			
		}
	}
	
	void listInsert(ListNode listHead, ListNode newNode, BufferedWriter deBugFile) throws IOException{
		deBugFile.write("In listInsert method \n");
		ListNode spot = this.findSpot(listHead,newNode);
		deBugFile.write("Returns from findSpot where Spot.data is ");
		deBugFile.write(spot.data + "\n");
		newNode.next = spot.next;
		spot.next = newNode;
	}
	
	ListNode findSpot(ListNode listHead, ListNode newNode) {
		ListNode spot = listHead;
		while(spot.next!=null && newNode.data.toLowerCase().compareTo(spot.next.data.toLowerCase())>=0 ) {
			spot=spot.next;
		}
		return spot; 
	}
	

	
	ListNode findMiddleNode(ListNode listHead, BufferedWriter deBugFile) throws IOException {
		deBugFile.write("In findMiddleNode method \n");
		ListNode walker1 = listHead.next;
		ListNode walker2 = listHead.next;
		while(walker2!=null && walker2.next!=null) {
			walker1 = walker1.next;
			walker2=walker2.next.next;
			deBugFile.write("walker1's data is "+ walker1.data + "\n");
		}
		
		
		return middleNode = walker1;
	}
	
	void printList(ListNode listHead, BufferedWriter File) throws IOException {
		int count=0;
		ListNode printer = listHead;
		while(printer.next!=null) {
			File.write("(" + printer.data + ","+printer.next.data +")");
			printer = printer.next;
			count++;
			if(count==5) {
				File.write("\n");
				count=0;
			}
			else File.write(" - >");
		}
		File.write("NULL" + "\n");
	}
} //ending list

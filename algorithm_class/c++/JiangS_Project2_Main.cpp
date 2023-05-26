#include<iostream>
#include<fstream>
#include<cmath>

using namespace std;

class ListNode{ //basic node store which data and point to next node
     public:
    int data;
    ListNode* next;

    ListNode(){ //defalt constructor, prevent error happen
          this->data = -999;
        this->next = nullptr;
    }

    ListNode(int data){ //creating new node and next note pointer is nullptr
        this->data = data;
        this->next = nullptr;

    }
};

class LLQueue{
     public:
   
    ListNode* head;
    ListNode* tail;

    LLQueue(){// constructor, asign a dummy node when initilize
        ListNode* dummy = new ListNode(-999);
        this->head = dummy;
        tail = head;
     

    }

     static void insertQ(LLQueue &queue, ListNode* newNode ){ //insert new node to the queue
        (*queue.tail).next = newNode;
        queue.tail = newNode;
      
    }

    static ListNode* deleteQ(LLQueue &queue){ //delete a node from queue
        ListNode* temp = (*queue.head).next; //create a temp to point the first node
        (*queue.head).next= (*temp).next; //move head to next next node
        if((*queue.head).next==nullptr) queue.tail = queue.head;
        (*temp).next=nullptr; //set temp's next node to null
        return temp; //return the deleted node
    }
    bool isEmpty(){
        if(this->tail==this->head) return true;
        return false;
    }

 

    void printQueue(int table,int index, ofstream &File){
        ListNode* spot= head;
         File<<"Table"<<"["<<table<<"]"<<"["<<index<<"]"<<": ";
        while((*spot).next!=nullptr){
           File<<"("<<(*spot).data<<","<<(*(*spot).next).data<<")"<<"->";
           spot=(*spot).next;
        }
        File<<"("<<(*spot).data<<","<<"NULL"<<")"<<"->"<<"NULL"<<endl;
    }

};


class RadixSort{
    
    public:
   static const int tableSize = 10;
    LLQueue hashTable[2][tableSize];
    int data;
    int currentTable;
    int previousTable;
    int maxDigits;
    int offSet;
    int digitPosition;
    int currentDigit;

    RadixSort(ifstream &inFile,ofstream &outFile){
         for(int i=0;i<2;i++){
            for(int j=0; j<10;j++){
                LLQueue llqueue;
                hashTable[i][j]=llqueue;
            }
            
        }

        preProcessing(inFile,outFile);
    }

    void preProcessing(ifstream &inFile,ofstream &deBugFile){
        deBugFile<<"Peforming first Reading"<<endl;
        int negativeNum=0;
        int positiveNum=0;
        while(inFile>>data){
            if(data<negativeNum) negativeNum = data;
            else if(data>positiveNum) positiveNum = data;
        }

        if(negativeNum<0) offSet=abs(negativeNum);
        else offSet = 0;
        positiveNum = positiveNum + offSet;

        maxDigits = getLength(positiveNum);
        deBugFile<<"negative number: "<<negativeNum<<","<<"offSet: "<<offSet<<","<<"positive number: "<<positiveNum<<","<<"max Digits: "<<maxDigits<<endl;


    }

    void rSort(ifstream &inFile,ofstream &outFile1,ofstream &deBugFile){//performing radix sort
        deBugFile<<"***Peforming RSort"<<endl;
        digitPosition = 0;
        currentTable = 0;

        while(inFile>>data){// read data from file 
             data += offSet;
        ListNode* newNode = new ListNode(data); //create new node for storage
        int hashIndex = getDigit(data,digitPosition); 
    
        LLQueue::insertQ(hashTable[currentTable][hashIndex],newNode); //insert node to corect position
        }


        

        while(digitPosition<maxDigits){
          
           
            digitPosition++;  //move to next digit position
            previousTable=currentTable;  //save current table
            currentTable=fmod(currentTable+1,2); //move to next table
            deBugFile<<"digit position: "<<digitPosition<<","<<"current table: "<<currentTable<<","<<"previous table: "<<previousTable<<endl; //show the current status
          
            outFile1<<"**Printing hashTable["<<previousTable<<"]"<<endl;
            printTable(hashTable[previousTable], previousTable,outFile1); //print table which is not yet be moved
            int tableIndex = 0; //start from index 0
            while(tableIndex<=tableSize-1){ //loop until table index to 9, notice: the papaer said >=
                while(!hashTable[previousTable][tableIndex].isEmpty()){ // if next is poiter to a node
                    
                    ListNode* newNode = LLQueue::deleteQ(hashTable[previousTable][tableIndex]); //call deletion function-------------maybe here has bug
                    int hashIndex = getDigit((*newNode).data,digitPosition); //get the deleted node hash index----------------maybe here has bug

                  
                    LLQueue::insertQ(hashTable[currentTable][hashIndex],newNode); //insert to current table

                }
            tableIndex++; 
          

            }
           outFile1<<endl;
        }    

        printSortedData(currentTable,outFile1);// chagne to currentTable
        
    }

    int getLength(int data){
        string s = to_string(data);
        return s.length();

    }

    int getDigit(int data, int postition){
        string s = to_string(data);

       
        if(s.length()<postition+1) return 0;
        else return s[s.length()-postition-1]-'0';
    }

    void printTable(LLQueue* hashTable, int previousTable,ofstream &File){
  
            for(int j=0;j<10;j++){
                if(hashTable[j].isEmpty()) continue;
                hashTable[j].printQueue(previousTable,j,File);
    
            }

    }

    void printSortedData(int previousTable,ofstream &outFile1){
        outFile1<<"**Printing sotred data"<<endl;
          int count = 0;
          for(int i=0;i<10;i++){
           ListNode* spot= hashTable[previousTable][i].head;
                while((*spot).next!=nullptr){
                
               outFile1<<(*(*spot).next).data-offSet<<" ";
               spot=(*spot).next;
                count++;
                if(count==10) {
                    outFile1<<endl;
                    count=0;
                }

               
            }

         }
    }

};

int main(int argc, const char *argv[]){
    ifstream inFile(argv[1]);
    ofstream outFile1(argv[2]);
    ofstream deBugFile(argv[3]);
    RadixSort radixSort(inFile,deBugFile);
 
    inFile.close();
    inFile.open(argv[1]);
    radixSort.rSort(inFile,outFile1,deBugFile);
    inFile.close();
    outFile1.close();
    deBugFile.close();
//-----------------test LLQueue---------------------------------
   // LLQueue test;
  //  test.printQueue(1,2);


}

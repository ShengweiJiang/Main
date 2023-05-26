#include <iostream>
#include<fstream>
#include<string>
using namespace std;

class node{
    public:
    int ID;
    node* next;

   node(){
        ID=0;
        next = nullptr;
   }
    node(int i){
        ID = i;
        next = nullptr;
    }

     node(int i,node d){
        ID = i;
        next = &d;
    }
};

class color{
    public:
     int numNodes;
    int numUncolor;
    node* hashTable;
    int* colorAry;

    public:
    color(istream& inFile){
        inFile >> numNodes;
        numUncolor = numNodes;
        hashTable = new node[numNodes+1];
        colorAry = new int[numNodes+1]();
        for(int i=1; i< numNodes+1; i++){
            hashTable[i] =  node(i);
        }
       
    }

    void loadGraph(istream& inFile){
         int i;
		 int j;
		 while(inFile>>i && inFile >> j) {
			
			 hashInsert(i,j);
			 hashInsert(j,i);
			 
		 }
    }

    void hashInsert(int id1,int id2){
        node* newNode = new node(id2); 
        newNode->next = hashTable[id1].next;
        hashTable[id1].next = newNode;

    }

    void printHashTable(ostream& outFile2){
        for(int i=1; i<numNodes+1; i++){
            outFile2<<"hashTable "<<i;
            node spot = hashTable[i];

            
            while(spot.next != nullptr){
                outFile2<<"->"<< spot.next->ID;
                spot = *(spot.next);

            }
            outFile2<<endl;
        }
    }

    void Method1(ostream& outFile1, ostream& deBugFile){
        deBugFile<<"entering Method 1"<<endl;
        int newColor = 64;

        while(numUncolor>0){
        newColor++;
        int nodeID=1;

        while(nodeID<=numNodes){
        if(colorAry[nodeID]== 0){
            if( this->checkNeighbors(nodeID, newColor)== true){
                colorAry[nodeID] = newColor;
                numUncolor--;

            }
        }

        nodeID ++;
        }
        this->printAry(deBugFile);
        }

        this->printAry(outFile1);
        deBugFile<<"leaving Method 1"<<endl;


    }

    void Method2(ostream& outFile1, ostream& deBugFile){
        deBugFile<<"entering Method2"<<endl;
        int lastUsedColor=64;
        int nextNodeID=0;
        //step7
        while(nextNodeID < numNodes){
        //step1

       
        nextNodeID++;
        //step2
        int nextUsedColor = 1+64;
        bool coloredFlag = false;
        //step4
        while(coloredFlag == false && nextUsedColor<=lastUsedColor){
             
        //step3
        if(lastUsedColor> 64 && this->checkNeighbors(nextNodeID,nextUsedColor)== true){
            colorAry[nextNodeID]= nextUsedColor;
            coloredFlag= true;
        }
        else{
            nextUsedColor++;
        }

        }

        //step 5
        if(coloredFlag == false){
            lastUsedColor++;
            colorAry[nextNodeID]=lastUsedColor;
            deBugFile<<"last used color is: "<< (char)lastUsedColor<<endl;
        }
        //step6
        printAry(deBugFile);
        }

        //step 8
       
        printAry(outFile1);
        deBugFile<<"Leaving Method 2";





    }

    bool checkNeighbors(int nodeID, int color){
        node* nextNode = &hashTable[nodeID];

        while(nextNode!= nullptr){
        if(nextNode==nullptr){
            return true;
        }
        if(colorAry[nextNode->ID]==color) return false;
        nextNode= nextNode->next;
        }

        return true;
    }

    void printAry(ostream& file){
        file<<numNodes<<endl;
        for(int i=1; i<numNodes+1; i++){
            if(colorAry[i]<64) file<<i<<" uncolored"<<endl;
            else file<<i<<" "<<(char)colorAry[i]<<endl;
        }

        file<<endl;
    }

};

int main(int arc, const char* arv[]){
    ifstream inFile(arv[1]);
  
    ofstream outFile1(arv[3]);
    ofstream deBugFile(arv[4]);
    int numNode;

   color graph(inFile);
    graph.loadGraph(inFile);
    graph.printHashTable(deBugFile);

      string whichMethod = arv[2];
      if(stoi(whichMethod)==1) graph.Method1(outFile1, deBugFile);
      else if(stoi(whichMethod)==2) graph.Method2(outFile1,deBugFile);
      else {
        deBugFile<<"argv [2] only accept 1 or 2";
        return 0;
      }

    inFile.close();
    outFile1.close();
    deBugFile.close();

}

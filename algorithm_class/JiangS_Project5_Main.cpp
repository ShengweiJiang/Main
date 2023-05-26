#include <iostream>
#include<fstream>
using namespace std;

class Edge{
 public:
    int nU;
    int nW;
    int cost;
    Edge* next;


    Edge(int n1,int n2, int cost){
        this->nU = n1;
        this->nW = n2;
        this->cost = cost;
        next = NULL;
    }

    void printEdge(Edge edge, ostream &File){
         if(edge.next!=NULL) {
             File<<"<"<<edge.nU<<","<<edge.nW<<","<<edge.cost<<">"<<" -> ";

         }
         else File<<"<"<<edge.nU<<","<<edge.nW<<","<<edge.cost<<">"<<endl;
         
    }
};


	
	
	

class KruskalMST{

    public:
    int N=0;
    int* whichSet ;
    int numSet;
    Edge edgeList;
    Edge mstList;

    KruskalMST(Edge obj1, Edge obj2,int N) : edgeList(obj1), mstList(obj2){
       this-> N = N;
       this->whichSet = new int[N+1];
       for(int i=1;i<N+1;i++){
        whichSet[i]=i;
       }
 
       

    }

     void insertEdege(Edge &newEdge,Edge &list){

        Edge* spot = findSpot(list,newEdge);

        
        newEdge.next = spot->next;
        spot->next=&newEdge;

   
          
    }

    Edge* findSpot(Edge &listHead, Edge &newEdge){
        
        Edge* spot = &listHead;
        
        
		while(spot->next!=NULL && newEdge.cost>spot->next->cost ) {
			spot=spot->next;
          
		}
		return spot; 
    }

    Edge* removeEdge(){
        if(edgeList.next==nullptr){
            cout<<"no edge to remove"<<endl;
            return nullptr;

        } 
        Edge* temp = this->edgeList.next;
        edgeList.next = temp->next;
        temp->next=nullptr;
        return temp;

    }
    

    void merge2Sets(int Ni,int Nj){
        if(whichSet[Ni]<whichSet[Nj]) updateWhichSet(whichSet[Nj],whichSet[Ni]);
        else updateWhichSet(whichSet[Ni],whichSet[Nj]);
    }

    void updateWhichSet(int a, int b){
        for(int i=1;i<=N;i++){
            if (whichSet[i]==a) whichSet[i]=b;

        }
    }


    void printArray(int* array, ofstream &File){
        for(int i=1;i<N+1;i++){
            File<<array[i]<<" ";
        }
        File<<endl;
    }

    void printList(Edge list,ofstream &outFile){
        Edge *spot = &list;
        while(spot!=NULL){
         
              spot->printEdge(*spot,outFile);
        
              spot=spot->next;
        }

        
      
    }

   


};



int main(int argc, const char* argv[]){
    ifstream inFile(argv[1]);
    ofstream outFile1(argv[2]);
    ofstream deBugFile(argv[3]);
    int u,w,cost;
    int N;
    inFile>>N;
    int numSets = N;
    Edge edge(0,0,0);
    KruskalMST krusk(edge,edge,N);
    int totalMSTCost = 0;
    
    deBugFile<<"***Printing the input graph***"<<endl;
    while( inFile>>u){
    inFile>>w;
    inFile>>cost;
     
    Edge *newEdge = new Edge(u,w,cost); //why i cant use newEdge? if i use it will change edgelist.next. it is not soppose to do that. is this undefine behavior? 
   
    deBugFile<<"newEdge from inFile is"<<endl;
       
    edge.printEdge(*newEdge,deBugFile);

       krusk.insertEdege(*newEdge,krusk.edgeList);

    deBugFile<<"Printing edgeList after insert the new edge:"<<endl;
    krusk.printList(krusk.edgeList,deBugFile);
    deBugFile<<" ***At the end of printing all edges of the input graph"<<endl;

    }
while(numSets>1){
    Edge* nextedge = krusk.removeEdge();
    while(krusk.whichSet[nextedge->nU]==krusk.whichSet[nextedge->nW]){
        nextedge = krusk.removeEdge();
    }

    deBugFile<<"the nextEdge is"<<endl;
    nextedge->printEdge(*nextedge,deBugFile);
     krusk.insertEdege(*nextedge,krusk.mstList);
     totalMSTCost += nextedge->cost;
     krusk.merge2Sets(nextedge->nU,nextedge->nW);
     numSets--;
     deBugFile<<"numSets is "<<numSets<<endl;

     deBugFile<<"Printing whichSet array"<<endl;
     krusk.printArray(krusk.whichSet,deBugFile);
     deBugFile<<"Print the growing MST list"<<endl;
     krusk.printList(krusk.mstList,deBugFile);
}
  
    krusk.printList(krusk.mstList,outFile1);
        outFile1<<"*** A Kruskal’s MST of the input graph is given below: ***"<<endl;
          outFile1<<N<<endl;
          Edge* print = &krusk.mstList;
          while(print!=nullptr&&print->next!=nullptr){
            outFile1<<print->next->nU<<" "<<print->next->nW<<" "<<print->next->cost<<endl;
            print=print->next;

          }
    outFile1<<"*** The total cost of a Kruskal’s MST is: "<<totalMSTCost;
    inFile.close();
    outFile1.close();
    deBugFile.close();
    


}
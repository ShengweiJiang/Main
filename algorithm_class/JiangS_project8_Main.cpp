#include <iostream>
#include<fstream>
#include<string>

using namespace std;


class AstarNode{
    public:
    int config[9]{0,0,0,0,0,0,0,0,0};
    int gStar;
    int hStar;
    int fStar;

    AstarNode* next;
    AstarNode* parent;


    public:

        AstarNode(){
      
        this->gStar = 0;
        this->hStar = 0;
        this->fStar = 0;
        this->next = NULL;
        this->parent = NULL;




    }


    AstarNode(int* config, int gStar , int hStar , int fStar , AstarNode* next , AstarNode* parent){

        for(int i=0; i<9; i++){
            this->config[i] = config[i];
        }
        
        this->gStar = gStar;
        this->hStar = hStar;
        this->fStar = fStar;
        this->next = next;
        this->parent = parent;




    }

    void printNode(AstarNode node, ostream& file){
      
        string s1,s2;

        
        for(int i=0; i<9; i++){

           
            s1 = s1 + to_string(node.config[i]) + " ";
                
             if(node.parent!=NULL)     s2 = s2 + to_string(node.parent->config[i]) + " ";

    
        }

        
         if(node.parent!=NULL)
        file<<"<"<<node.parent->fStar<<"["<<s2<<"]"<<"::"<<node.fStar<<"["<<s1<<"]>"<<endl;

        else file<<s1<<endl;

        

        
            }



};


class AStar {
    public:
    AstarNode* startNode;
    AstarNode* Open;
    AstarNode* childList;
    int table[9][9] = {
    {0,1,2,1,2,3,2,3,4},
    {1,0,1,2,1,2,3,2,3},
    {2,1,0,3,2,1,4,3,2},
    {1,2,3,0,1,2,1,2,3},
    {2,1,2,1,0,1,2,1,2},
    {3,2,1,2,1,0,3,2,1},
    {2,3,4,1,2,3,0,1,2},
    {3,2,3,2,1,2,1,0,1},
    {4,3,2,3,2,1,2,1,0}
    
    };

    

    int* initConfig;
    int* goalConfig;
    int dummyConfig[9] {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    
    public:
    AStar() : startNode(NULL), Open(NULL),childList(NULL),initConfig(NULL), goalConfig(NULL){ 

     

     }

    AStar( AstarNode* starNode, AstarNode* Open, AstarNode* childList,  int* initConfig, int* goalConfig) : startNode(starNode), Open(Open),childList(childList),initConfig(initConfig), goalConfig(goalConfig){  }


    int computeHstar(int* nodeConfig, int* goalConfig, ofstream& deBugFile){

        //step 0
      //  deBugFile<<"Entering computeHstar method";
        int sum = 0;
        int i = 0;


        //step 7
        while (i<9){ 
    
        
        int p1 = nodeConfig[i]; //step 1
        int j = 0; // step 2
        // step5
        while(j<9){
        //step3
        if(goalConfig[j]== p1 ) {
            sum += table[i][j];
            break;

        }
        j++; //step 4
        }
        i++; //step 6
        }

      //  deBugFile<<"\nLeaving computeHstar methid: sum = "<< sum<<endl;
        return sum;

    }

    AstarNode* expandChildList(AstarNode* currentNode, ostream& deBugFile){
        //Step 0

        
       // deBugFile<<"Entering expandChildList method\n";
       // deBugFile<<"Printing currentNode";
      //  currentNode->printNode(*currentNode,deBugFile);

    
        AstarNode* tmpList = new AstarNode(this->dummyConfig,0,0,0, NULL,NULL);

        //step 1
        int i = 0;

        //Step 3
        while(currentNode->config[i]!=0 && i < 9){

        //step 2
        if(currentNode->config[i]!=0) i++;

        }
        //Step 4
        if(i >= 9) {
            deBugFile<<"“Something is wrong, currentNode does not have a zero in it";
            return NULL;
        }

        else {
            int zerpPosition = i;
           // deBugFile << "find the zero position in currentNode at position i = "<<i<<endl;
        //Step 5
        int j=0;
        //Step 8
        while(j<9){

        
        //Steo 6

        if(table[zerpPosition][j] == 1) {
            AstarNode* newNode = new AstarNode(currentNode->config,999,999,999,NULL,currentNode);

            

        
            newNode->config[j] = 0;
            newNode->config[zerpPosition] = currentNode->config[j];
           // newNode->printNode(*newNode,deBugFile);
            if(checkAncestors(newNode) == false){

           
                newNode->next = tmpList->next;
                tmpList->next = newNode;
            }
        }

        j++; //Step 7

        }


        //Step 9
       // deBugFile<<"Leaving expandChildList method and printing tmpList"<<endl;
         //   this->printList(tmpList,deBugFile);
        }

    return tmpList;

    }
    void OpenInsert(AstarNode* newNode){
        AstarNode* spot = findSpot(newNode);
        newNode->next = spot->next;
        
        spot->next = newNode;

    }

    AstarNode* findSpot(AstarNode* newNode){
        AstarNode* spot = Open;
        while (spot->next != NULL && spot->next->fStar < newNode->fStar)
        {
          

            spot = spot->next;
        }
        
        return spot;

    }



    AstarNode* remove(AstarNode* list){  //keep its parent when i delete
        AstarNode* tmp;

       

        if(list->next != NULL) {
            tmp = list->next;
            if(tmp->next == NULL){
                list->next = NULL;
               
                return tmp;
            }
            else {
                list->next = tmp->next;
              
                 tmp->next = NULL;
                 return tmp;
            }

        }

        return NULL;

       


    }
    bool match(int* config1, int* config2){
        for(int i=0; i<9; i++){
      
            if(config1[i]!=config2[i]) {
                
                return false;
            }
        }


       
        return true;

    }
    bool checkAncestors(AstarNode* child){

  
        AstarNode* parentNode = child->parent;
        while(parentNode!=NULL){
            if( match(child->config,parentNode->config)) return true;
            else{
                parentNode = parentNode->parent;
            }
        }
       
        return false;

    }

    void printList(AstarNode* node, ostream& file){
        while(node != NULL){

             node->printNode(*node,file);
             node = node->next;

        }

       
        




    }
    void printSolution(AstarNode* node, ostream& file){

        if(node == NULL) return;


         printSolution(node->parent,file);
        file<<node->config[0]<<" "<<node->config[1]<<" "<<node->config[2]<<" "<<endl;
        file<<node->config[3]<<" "<<node->config[4]<<" "<<node->config[5]<<" "<<endl;
        file<<node->config[6]<<" "<<node->config[7]<<" "<<node->config[8]<<" "<<endl;
        file<<"  |"<<endl;
        file<<"  |"<<endl;
        file<<"\\  /"<<endl;
         file<<" \\/"<<endl;
       

    }

    

};


int main(int argc, const char* argv[]){
       ifstream inFile1(argv[1]);
       ifstream inFile2(argv[2]);
       ofstream outFile1(argv[3]);
       ofstream outFile2(argv[4]);
        ofstream deBugFile(argv[5]);
         AStar* astar = new AStar();

        

        int* intConfig = new int[9];
        int* goalConfig = new int[9];
        int j=0;
        int i=0;
    while(inFile1>>intConfig[i]) i++;
     while(inFile2>>goalConfig[j]) j++;

    

     astar->startNode = new AstarNode(intConfig,0,9999,9999,NULL,NULL);
     astar->Open = new AstarNode(astar->dummyConfig,0,0,0,NULL,NULL);
   //  AstarNode* Close = new AstarNode(astar->dummyConfig,0,0,0,NULL,NULL);
     astar->childList = new AstarNode(astar->dummyConfig,0,0,0,NULL,NULL);

    //step 1
    astar->startNode->gStar = 0;
      astar->startNode->hStar = astar->computeHstar( astar->startNode->config,goalConfig,deBugFile);

   

       astar->startNode->fStar =  astar->startNode->gStar +  astar->startNode->hStar;
      astar->OpenInsert( astar->startNode);
   

     //step 10
    AstarNode* currentNode  ;

     while(!( astar->Open->next == NULL || astar->match(( currentNode = astar->remove(astar->Open))->config,goalConfig) )){

       
     
      //step 2
     
    //  deBugFile<<"this is currentNode"<<endl;
     
    //  currentNode->printNode(*currentNode,deBugFile);

    //step 3

    if(currentNode!= NULL && astar->match(currentNode->config,goalConfig)){
          
        outFile2<<"A solution is found!"<<endl;
        astar->printSolution(currentNode,outFile2);
        outFile2<<"Done !"<<endl;

        exit(0);
    }
   
       
    
    //step 4
    astar->childList = astar->expandChildList(currentNode,deBugFile);
   
    //step 8
    while(astar->childList->next!= NULL){
        
    //step 5    
    AstarNode* child = astar->remove(astar->childList);
   // deBugFile<<"In main(), remove node from childList, and printing"<<endl;
  //  child->printNode(*child,deBugFile);

    //step 6
    child->gStar = currentNode->gStar + 1;
    child->hStar = astar->computeHstar(child->config,goalConfig,deBugFile);

   
    child->fStar = child->gStar + child->hStar;
    child->parent = currentNode;
  
    //step 7
    astar->OpenInsert(child);

      

     //step 9
 //  outFile1<<"Below is Open list:"<<endl;

      //  astar->printList(astar->Open,outFile1);


    }
     
   
     }
       
    //step 11
    if(!astar->match(currentNode->config,goalConfig)) outFile1<<"*** Message: no solution can be found in the search!";
     else if(currentNode!= NULL && astar->match(currentNode->config,goalConfig)){
          
        outFile2<<"A solution is found!"<<endl;
        astar->printSolution(currentNode,outFile2);
        outFile2<<"Done !"<<endl;
    }





    inFile1.close();
        inFile2.close();
        outFile1.close();
        outFile2.close();
         deBugFile.close();

    






    
}
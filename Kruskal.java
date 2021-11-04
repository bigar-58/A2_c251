import java.io.File;
import java.util.*;

//Axel Reumann 260945669

public class Kruskal{

    public static WGraph kruskal(WGraph g){
        //Declare an AL of the sorted edges present in graph g
        ArrayList sortedEdges = g.listOfEdgesSorted();

        //Create a new disjoint set object to develop the Minimum Spanning Tree
        DisjointSets partition = new DisjointSets(sortedEdges.size());

        //Create a new graph object so that we can save this as our minimum spanning tree
        WGraph MST = new WGraph();

        /*
        If number of edges is equal to the number of total vertices-1 then we have the property
        that the input graph is already a minimum spanning tree and we can simply return g
        */

        if(sortedEdges.size() == g.getNbNodes()-1){
            return g;
        }


        /*
        For each edge in the sorted edges AL we want to check if we can add an edge to our minimum spanning tree
        and then add it to both the partition and the MST if true
         */
        for (Object sortedEdge : sortedEdges) {
            if (IsSafe(partition, (Edge) sortedEdge) && MST.listOfEdgesSorted().size() < g.getNbNodes()-1) {
                //Add safe edge to the MST
                MST.addEdge((Edge) sortedEdge);

                //Add the vertices of the edge to the union to be able to check for cycles in the graph later
                partition.union(((Edge) sortedEdge).nodes[0], ((Edge) sortedEdge).nodes[1]);

                //Once the property that |E| = |V|-1 we return the current MST
                if(MST.listOfEdgesSorted().size() == g.getNbNodes()-1){
                    return MST;
                }
            }
        }
        return MST;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
        //If they have the same representatives in their partition then there is a cycle
        //i.e. if the vertices in the edge do not cross the "cut" in the graph than there is a cycle
        if(p.find(e.nodes[0]) == p.find(e.nodes[1])){
            return false;
        }

        return true;
    
    }

    public static void main(String[] args){

        String file = "src/g1.txt";
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}

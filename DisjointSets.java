import java.io.*;
import java.util.*;
//Axel Reumann 260945669

/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find representative of element i */
    public int find(int i) {

        //If the parent of i is itself then return the index
        if(this.par[i] == i){
            return i;
        }else{
            //recurse and declare the parent of i to be the representative of i while compressing the find path
            par[i] = find(par[i]);
            return par[i]; //Return parent of i i.e. the representative of the group;
        }

        
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
        int parentI = find(i); //Return the parent of i with path compression
        int parentJ = find(j); //Return the parent of j with path compression

        if(parentI == parentJ){
            //If the parents of the subtrees are the same this means that they are in the same disjoint set
            //And no operation needs to be performed.
            return parentJ;
        }

        /*
        If the rank of the subtree for i is greater than that of j, make the representative of i-tree
        the parent of the representative of the j-tree
        */
        if(rank[parentI] > rank[parentJ]){
            par[parentJ] = parentI;
            return parentI; //Return the new rep of the merged trees
        }
        else if(rank[parentI] < rank[parentJ]){
            //If the rank of set-I is smaller than set-J we do the opposite and make the rep of J the parent of the rep of I
            par[parentI] = parentJ;
            return parentJ; //Return the new rep of the merged trees
        }
        else if(rank[parentI] == rank[parentJ]){
            //If the ranks are equal we merge set-i into set-j
            par[parentI] = parentJ;
            rank[parentJ] += 1; //Increment the rank of the new root node by one
        }

        return parentJ; //Return the new rep of the merged trees.
    }
    
    public static void main(String[] args) {
        
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
        
    }

}

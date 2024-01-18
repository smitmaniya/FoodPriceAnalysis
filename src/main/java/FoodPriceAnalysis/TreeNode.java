package FoodPriceAnalysis;

// class to create node for binary tree
 public class TreeNode {
    String data;// to store key
    int numberofaccurance; // to store value 
    TreeNode l, r;
//constructore 
    public TreeNode(String data) {
        this.data = data; // to store string
        this.numberofaccurance = 1;// initial it is 1
        this.l = this.r = null;
    }
    
}
 // create binary tree

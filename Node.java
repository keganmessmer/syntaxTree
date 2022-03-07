import java.util.ArrayList;


/**
 * Node object.
 * @author Kegan Messmer
 * @version 0
 */

public class Node {


    public String type;
    public String label;
    String content;

    ArrayList<Node> daughters = new ArrayList<Node>();


    /**
     * 
     */
    
    public Node() {
        this(null, null, null);
    }
    
    
    /**
     * 
     * @param label of node
     */

    public Node(String label) {
        this(null, label, null);
    }


    /**
     * 
     * @param type of node
     * @param label of node
     */

    public Node(String type, String label) {
        this(type, label, null);
    }
    
    
    /**
     * 
     * @param type of the node
     * @param label of the node
     * @param content of the node
     */

    public Node(String type, String label, String content) {
        this.type = type;
        this.label = label;
        this.content = content;
    }


    /**
     * 
     * @return node as a string
     */

    public String toString() {
        String output = "";
        output += "[" + label;
        if (content == null) {
            for (Node daughter: daughters) {
                if (daughter != null) {
                    output += daughter.toString();
                }
            }
        } else {
            output += "[" + content + "]";
        }
        output += "]";
        return output;
    }


    /**
     * 
     * @return content
     */

    public String getContent() {
        return content;
    }


    /**
     * 
     * @return content
     */

    public String gc() {
        return getContent();
    }


    /**
     * 
     * @param content to be assigned to the node
     * @return true if successful
     */

    public boolean setContent(String content) {
        if (daughters.size() == 0) {
            this.content = content;
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @param content to be assigned to the node
     * @return true if successful
     */

    public boolean sc(String content) {
        return setContent(content);
    }


    /**
     * 
     * @return daughter nodes
     */

    public ArrayList<Node> getDaughters() {
        return daughters;
    }


    /**
     * 
     * @return daughter nodes
     */

    public ArrayList<Node> gd() {
        return getDaughters();
    }


    /**
     * 
     * @return recursive daughter nodes
     */

    public ArrayList<Node> getDaughtersRecursively() {
        ArrayList<Node> daughters = getDaughters();
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Node daughter: daughters) {
            ArrayList<Node> recursiveDaughters = daughter.getDaughtersRecursively();
            for (Node recursiveDaughter: recursiveDaughters) {
                nodes.add(recursiveDaughter);
            }
        }
        for (Node daughter: daughters) {
            nodes.add(daughter);
        }
        return nodes;
    }


    /**
     * 
     * @return recursive daughter nodes
     */

    public ArrayList<Node> gdr() {
        return getDaughtersRecursively();
    }


    /**
     * 
     * @param node to be added
     * @return true if successful
     */

    public boolean addDaughter(Node node) {
        return addDaughter(-1, node);
    }


    /**
     * 
     * @param index of the new node
     * @param node to be added
     * @return true if successful
     */

    public boolean addDaughter(int index, Node node) {
        if (content == null) {
            if (index == -1) {
                index = daughters.size() - 1;
                if (index < 0) {
                    index = 0;
                }
            }
            daughters.add(index, node);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @param node to be added
     * @return true if successful
     */

    public boolean ad(Node node) {
        return addDaughter(node);
    }


    /**
     * 
     * @param index of the new node
     * @param node to be added
     * @return true if successful
     */

    public boolean ad(int index, Node node) {
        return addDaughter(index, node);
    }


    /**
     * 
     * @return true if has content
     */

    public boolean hasContent() {
        if (content == null) {
            return false;
        }
        return true;
    }


    /**
     * 
     * @return true if has content
     */

    public boolean hc() {
        return hasContent();
    }


    /**
     * 
     * @return constituent nodes
     */

    public ArrayList<Node> getConstituent() {
        ArrayList<Node> nodes = getDaughtersRecursively();
        ArrayList<Node> terminals = new ArrayList<Node>();
        for (Node node: nodes) {
            if (node.hasContent()) {
                terminals.add(node);
            }
        }
        return terminals;
    }


    /**
     * 
     * @return constituent nodes
     */

    public ArrayList<Node> gCons() {
        return getConstituent();
    }


    /**
     * 
     * @return contents of the constituent
     */

    public ArrayList<String> getContentsOfConstituent() {
        ArrayList<Node> nodes = getConstituent();
        ArrayList<String> contents = new ArrayList<String>();
        for (Node node: nodes) {
            contents.add(node.content);
        }
        return contents;
    }


    /**
     * 
     * @return contents of the constituent
     */

    public ArrayList<String> gcoc() {
        return getContentsOfConstituent();
    }


    /**
     * 
     * @param node to be compared
     * @return true if dominates
     */

    public boolean dominates(Node node) {
        ArrayList<Node> nodes = getDaughtersRecursively();
        if (nodes.contains(node)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @param node to be compared
     * @return true if dominates
     */

    public boolean d(Node node) {
        return dominates(node);
    }


    /**
     * 
     * @param node to be compared
     * @return true if immediately dominates
     */

    public boolean immediatelyDominates(Node node) {
        if (daughters.contains(node)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @param node to be compared
     * @return true if immediately dominates
     */

    public boolean id(Node node) {
        return immediatelyDominates(node);
    }


}
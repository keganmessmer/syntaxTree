import java.util.ArrayList;

/**
 * Syntax Tree.
 * @author Kegan Messmer
 * @version 0
 */

public class SyntaxTree {
    

    public Node root;


    /**
     * 
     * @return syntax tree as string
     */

    public String toString() {
        return root.toString();
    }


    /**
     * 
     * @return syntax tree as string
     */

    public String ts() {
        return toString();
    }


    /**
     * 
     * @return link to view the tree online
     */

    public String toUrl() {
        String tree = toString();
        String url = "http://mshang.ca/syntree/?i=";
        url += tree;
        return url;
    }


    /**
     * 
     * @return link to view the tree online
     */

    public String tu() {
        return toUrl();
    }


    /**
     * 
     * @return all nodes in tree
     */

    public ArrayList<Node> getAllNodes() {
        ArrayList<Node> nodes = root.getDaughtersRecursively();
        nodes.add(root);
        return nodes;
    }


    /**
     * 
     * @return all nodes in tree
     */

    public ArrayList<Node> gan() {
        return getAllNodes();
    }


    /**
     * 
     * @param node to get the mother of
     * @return mother node
     */

    public Node getMother(Node node) {
        ArrayList<Node> allNodes = getAllNodes();
        for (Node mother: allNodes) {
            ArrayList<Node> daughters = mother.getDaughters();
            if (daughters.contains(node)) {
                return mother;
            }
        }
        return null;
    }


    /**
     * 
     * @param node to get the mother of
     * @return mother node
     */

    public Node gm(Node node) {
        return getMother(node);
    }


    /**
     * 
     * @param node to get sisters of
     * @return sisters
     */

    public ArrayList<Node> getSisters(Node node) {
        Node mother = getMother(node);
        ArrayList<Node> daughters = mother.getDaughters();
        ArrayList<Node> sisters = new ArrayList<Node>();
        for (Node daughter: daughters) {
            if (daughter != node) {
                sisters.add(daughter);
            }
        }
        return sisters;
    }


    /**
     * 
     * @param node to get sisters of
     * @return sisters
     */

    public ArrayList<Node> gs(Node node) {
        return getSisters(node);
    }


    /**
     * 
     * @param node to get asymmetric c-command of
     * @return asymetrically c-commanded nodes
     */

    public ArrayList<Node> getAsymmetricCCommand(Node node) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<Node> sisters = getSisters(node);
        for (Node sister: sisters) {
            ArrayList<Node> daughters = sister.getDaughtersRecursively();
            for (Node daughter: daughters) {
                nodes.add(daughter);
            }
        }
        return nodes;
    }


    /**
     * 
     * @param node to get asymmetric c-command of
     * @return asymetrically c-commanded nodes
     */

    public ArrayList<Node> gacc(Node node) {
        return getAsymmetricCCommand(node);
    }


    /**
     * 
     * @param node to get c-command of
     * @return c-commanded nodes
     */

    public ArrayList<Node> getCCommand(Node node) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<Node> sisters = getSisters(node);
        for (Node sister: sisters) {
            nodes.add(sister);
            ArrayList<Node> daughters = sister.getDaughtersRecursively();
            for (Node daughter: daughters) {
                nodes.add(daughter);
            }
        }
        return nodes;
    }


    /**
     * 
     * @param node to get c-command of
     * @return c-commanded nodes
     */

    public ArrayList<Node> gcc(Node node) {
        return getCCommand(node);
    }


    /**
     * 
     * @param node1 c-commands
     * @param node2 is c-commanded
     * @return true if node1 c-commands node2
     */

    public boolean cCommands(Node node1, Node node2) {
        try {
            ArrayList<Node> cCommanded = getCCommand(node1);
            if (cCommanded.contains(node2)) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException exception) {
            return false;
        }
    }


    /**
     * 
     * @param node1 c-commands
     * @param node2 is c-commanded
     * @return true if node1 c-commands node2
     */

    public boolean cc(Node node1, Node node2) {
        return cCommands(node1, node2);
    }


    /**
     * 
     * @param node1 c-commands
     * @param node2 is c-commanded
     * @return true if node1 symmetrically c-commands node2
     */

    public boolean symmetricallyCCommands(Node node1, Node node2) {
        if (cCommands(node1, node2) && cCommands(node2, node1)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 
     * @param node1 c-commands
     * @param node2 is c-commanded
     * @return true if node1 symmetrically c-commands node2
     */

    public boolean scc(Node node1, Node node2) {
        return symmetricallyCCommands(node1, node2);
    }


    // TODO: more methods


    // getMothers()


    // getCommonMother()


    // getPreceding()


    // getAllPreceding()


    // precedes()


    /**
     * 
     * @param text to be imported
     * @return new syntax tree
     */

    public static SyntaxTree importTree(String text) {
        SyntaxTree tree = new SyntaxTree();
        Node root = importNode(text);
        tree.root = root;
        return tree;
    }


    /**
     * 
     * @param text to be imported
     * @return new node
     */

    public static Node importNode(String text) {

        int indexOB1 = 0;
        int indexOB2 = text.indexOf('[', indexOB1 + 1);
        int indexCB = text.indexOf(']');
        int beginIndex = indexOB1 + 1;
        int endIndex;
        if (indexOB2 == -1 || indexCB < indexOB2) {
            endIndex = indexCB;
        } else {
            endIndex = indexOB2;
        }
        String label = text.substring(beginIndex, endIndex);
        
        ArrayList<Node> daughters = new ArrayList<Node>();
        if (indexOB2 != -1) {
            int index = indexOB2;
            while (index < text.length()) {
                beginIndex = index;
                endIndex = index;
                if (text.charAt(index) == '[') {
                    int level = 1;
                    while (level > 0) {
                        index++;
                        if (text.charAt(index) == '[') {
                            level++;
                        } else if (text.charAt(index) == ']') {
                            level--;
                        }
                    }
                    index++;
                    endIndex = index;
                    daughters.add(importNode(text.substring(beginIndex, endIndex)));
                } else if (text.charAt(index) == ']') {
                    break;
                } else {
                    index++;
                }
            }
        }
           
        String content = null;
        for (Node daughter: daughters) {
            if (daughter.getDaughters().size() == 0 && daughter.content == null) {
                content = daughter.label;
                daughters.remove(daughter);
                break;
            }
        }

        Node node = new Node();
        node.label = label;
        node.content = content;
        node.daughters = daughters;

        return node;
    
    }

}

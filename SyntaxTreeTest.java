import java.io.FileNotFoundException;

/**
 * For testing.
 * @author Kegan Messmer
 * @version 0
 */

public class SyntaxTreeTest {
    

    static final String HE_LIKES_DOGS = "[TP[NP[N'[N[he]]]][T'[T[(-PAST)]][VP[V'[V[likes]][NP[N'[N[dogs]]]]]]]]";


    /**
     * 
     * @param args from the commandline
     * @throws FileNotFoundException who cares
     */

    public static void main(String[] args) throws FileNotFoundException {
        pl(SyntaxTree.importNode(HE_LIKES_DOGS).toString());
    }


    /**
     * 
     * @return syntax tree
     */

    public static SyntaxTree heLikesDogs() {

        SyntaxTree tree = new SyntaxTree();

        String[] labels = {"N", "V", "N"};
        String[] contents = {"he", "likes", "dogs"};
        Node[] terminals = new Node[3];
        for (int i = 0; i < contents.length; i++) {
            String type = "head";
            String label = labels[i];
            String content = contents[i];
            terminals[i] = new Node(type, label, content);
        }

        String type;
        String label;

        type = "bar";
        label = "N'";
        Node nBar1 = new Node(type, label);
        nBar1.addDaughter(terminals[2]);

        type = "phrase";
        label = "NP";
        Node np1 = new Node(type, label);
        np1.addDaughter(nBar1);

        type = "bar";
        label = "V'";
        Node vBar1 = new Node(type, label);
        vBar1.addDaughter(0, terminals[1]);
        vBar1.addDaughter(1, np1);

        type = "phrase";
        label = "VP";
        Node vp1 = new Node(type, label);
        vp1.addDaughter(vBar1);

        type = "bar";
        label = "T'";
        Node tBar1 = new Node(type, label);
        tBar1.addDaughter(0, new Node("head", "T", "(-PAST)"));
        tBar1.addDaughter(1, vp1);

        type = "bar";
        label = "N'";
        Node nBar2 = new Node(type, label);
        nBar2.addDaughter(terminals[0]);

        type = "phrase";
        label = "NP";
        Node np2 = new Node(type, label);
        np2.addDaughter(nBar2);

        type = "phrase";
        label = "TP";
        Node tp1 = new Node(type, label);
        tp1.addDaughter(0, np2);
        tp1.addDaughter(1, tBar1);

        tree.root = tp1;

        return tree;

    }


    /**
     * 
     * @param x line
     */

    private static void pl(String x) {
        System.out.println(x);
    }


}

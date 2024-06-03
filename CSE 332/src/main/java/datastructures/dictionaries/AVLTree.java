package datastructures.dictionaries;

// import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
// import cse332.interfaces.misc.SimpleIterator;
// import cse332.interfaces.worklists.WorkList;
// import datastructures.worklists.ArrayStack;

// import java.lang.reflect.Array;
// import java.util.Iterator;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    public class AVLNode extends BSTNode {
        private int height;
        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
    }

    public AVLTree() {
        super();
        root = null;
    }

    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new AVLNode(key, value);
            size++;
        }

        // store, update, & return old value
        V oldValue = find(key);
        AVLNode curr = insert(key, value, (AVLNode) root);
        return oldValue;
    }

    private AVLNode insert(K key, V value, AVLNode curr) {
        if (curr == null) {
            curr = new AVLNode(key, null);
            size++;
        }

        // find what direction to insert node
        int direction = key.compareTo(curr.key);

        // insert left
        if (direction < 0) {
            curr.children[0] = insert(key, value, (AVLNode) curr.children[0]);

        // insert right
        } else if (direction > 0) {
            curr.children[1] = insert(key, value, (AVLNode) curr.children[1]);

        // update value
        } else {
            curr.value = value;
        }

        // update heights & balance as necessary
        return balanceHeight(curr);
    }

    // balance left and right subtrees of current node as necessary
    private AVLNode balanceHeight(AVLNode curr) {
        if (curr == null) {
            return null;
        }

        if ((getHeight((AVLNode) curr.children[0]) - getHeight((AVLNode) curr.children[1])) > 1) {

            // Case 1: LL
            if (getHeight((AVLNode) curr.children[0].children[0]) >=
                    getHeight((AVLNode) curr.children[0].children[1])) {
                curr = rotateR(curr);

            // Case 3: LR
            } else {
                curr.children[0] = rotateL((AVLNode) curr.children[0]);
                return rotateR(curr);
            }
        } else if ((getHeight((AVLNode) curr.children[1]) - getHeight((AVLNode) curr.children[0])) > 1) {

            // Case 2: RR
            if (getHeight((AVLNode)curr.children[1].children[1]) >=
                    getHeight((AVLNode) curr.children[1].children[0])) {
                curr = rotateL(curr);

            // Case 4: RL
            } else {
                curr.children[1] = rotateR((AVLNode) curr.children[1]);
                return rotateL(curr);
            }
        }

        // update the height of the current node
        curr.height = Math.max(getHeight((AVLNode) curr.children[0]), getHeight((AVLNode) curr.children[1])) + 1;
        return curr;
    }

    // rotate current node left
    private AVLNode rotateL(AVLNode curr) {
        // get new "root"
        AVLNode rightChild = (AVLNode)curr.children[1];

        // check if current node is root
        if (curr.equals(root)) {
            root = rightChild;
        }

        // current node's right child is now old right's left child
        curr.children[1] = rightChild.children[0];

        // new "root"'s left child is now current node
        rightChild.children[0] = curr;

        // update heights & return new root
        curr.height = Math.max(getHeight((AVLNode) curr.children[0]), getHeight((AVLNode) curr.children[1])) + 1;
        rightChild.height = Math.max(getHeight((AVLNode) rightChild.children[1]), curr.height) + 1;
        return rightChild;
    }

    // rotate current node right
    private AVLNode rotateR(AVLNode curr) {
        // get new "root"
        AVLNode leftChild = (AVLNode)curr.children[0];

        // check if current node is root
        if (curr.equals(root)) {
            root = leftChild;
        }

        // current node's left child is now old left's right child
        curr.children[0] = leftChild.children[1];

        // new root's right child is now current node
        leftChild.children[1] = curr;

        //update heights & return new root
        curr.height = Math.max(getHeight((AVLNode) curr.children[0]), getHeight((AVLNode) curr.children[1])) + 1;
        leftChild.height = Math.max(getHeight((AVLNode)leftChild.children[0]), curr.height) + 1;
        return leftChild;
    }

    // get height of node
    private int getHeight(AVLNode curr) {
        if (curr == null) {
            return -1;
        }
        return curr.height;
    }
}
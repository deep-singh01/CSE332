package verifyavl;

public class VerifyAVL {
    public static boolean verifyAVL(AVLNode root) {
        if (root == null) {
            return true;
        }
        if (checkBST(root)) {
            if (checkHeight(root)) {
                return checkAVL(root);
            }
        }
        return false;
    }

    // check BST property
    private static boolean checkBST(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return true;
        }
        if (curr.left != null && curr.right != null) {
            if (getMaxOnLeft(curr.left) > curr.key || getMinOnRight(curr.right) < curr.key) {
                return false;
            }
            return checkBST(curr.left) && checkBST(curr.right);
        } else if (curr.left != null) {
            if (getMaxOnLeft(curr.left) > curr.key) {
                return false;
            }
            return checkBST(curr.left);
        } else {
            if (getMinOnRight(curr.right) < curr.key) {
                return false;
            }
            return checkBST(curr.right);
        }
    }

    // gets min key
    private static int getMinOnRight(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return curr.key;
        }
        if (curr.left != null && curr.right != null) {
            return Math.min(curr.key, Math.min(getMinOnRight(curr.left), getMinOnRight(curr.right)));
        } else if (curr.left != null) {
            return Math.min(curr.key, getMinOnRight(curr.left));
        } else {
            return Math.min(curr.key, getMinOnRight(curr.right));
        }
    }

    // gets max key
    private static int getMaxOnLeft(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return curr.key;
        }
        if (curr.left != null && curr.right != null) {
            return Math.max(curr.key, Math.max(getMaxOnLeft(curr.left), getMaxOnLeft(curr.right)));
        } else if (curr.left != null) {
            return Math.max(curr.key, getMaxOnLeft(curr.left));
        } else {
            return Math.max(curr.key, getMaxOnLeft(curr.right));
        }
    }

    // check if height correct of nodes
    private static boolean checkHeight(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return curr.height == 0;
        }
        if (curr.left != null && curr.right != null) {
            return (curr.height == Math.max(getHeight(curr.left), getHeight(curr.right)) + 1)
                    && checkHeight(curr.left) && checkHeight(curr.right);
        } else if (curr.left != null) {
            return (curr.height ==  getHeight(curr.left) + 1) && checkHeight(curr.left);
        } else {
            return (curr.height ==  getHeight(curr.right) + 1) && checkHeight(curr.right);
        }
    }

    // manually gets height of node
    private static int getHeight(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return 0;
        }
        if (curr.left != null && curr.right != null) {
            return Math.max(getHeight(curr.left), getHeight(curr.right)) + 1;
        } else if (curr.left != null) {
            return getHeight(curr.left) + 1;
        } else {
            return getHeight(curr.right) + 1;
        }
    }

    // check if AVL Tree
    private static boolean checkAVL(AVLNode curr) {
        if (curr.left == null && curr.right == null) {
            return true;
        }
        if (curr.left != null && curr.right != null) {
            return (Math.abs(curr.left.height - curr.right.height) <= 1)
                    && checkAVL(curr.left) && checkAVL(curr.right);
        } else if (curr.left != null) {
            return curr.left.height == 0 && checkAVL(curr.left);
        } else {
            return curr.right.height == 0 && checkAVL(curr.right);
        }
    }
}
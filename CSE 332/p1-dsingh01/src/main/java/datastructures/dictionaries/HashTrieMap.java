package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        root = new HashTrieNode();
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        V ret = null;
        if (!key.isEmpty()) {
            HashTrieNode curr = (HashTrieNode) root;

            // go through "letters" of the given key
            // add each "letter" of the key or if it exists, get its node
            for (A letter : key) {

                // checks if current "letter" has been added
                // if not, adds to previous "letter's" map
                if (!curr.pointers.containsKey(letter)) {
                    curr.pointers.put(letter, new HashTrieNode());
                }
                curr = curr.pointers.get(letter);
            }

            // key did or did not exist: set and record the appropriate values
            ret = curr.value;
            curr.value = value;
        } else {

            // key was empty: set and record the appropriate values
            root.value = value;
            ret = value;
        }

        // key did not exist: new node was created, so increment size
        if (ret == null) {
            size++;
        }

        return ret;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        HashTrieNode curr = (HashTrieNode) root;

        // go through "letters" of the given key
        for (A letter: key) {
            curr = curr.pointers.get(letter);

            // checks if we couldn't find the key
            if (curr == null) {
                return null;
            }
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        HashTrieNode curr = (HashTrieNode) root;

        // go through "letters" of the given key
        for (A letter: key) {
            curr = curr.pointers.get(letter);

            // checks if we couldn't find the prefix
            if (curr == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode) root;

        // go through "letters" of the given key
        for (A part: key) {

            // checks if current "letter" did not exist or did not have any children
            // if either is true, exit this iteration of the loop
            if (curr == null || curr.pointers.isEmpty()) {
                return;
            }

            // checks if current "letter" has children
            // if it does, try to get next "letter"
            if (!curr.pointers.isEmpty()) {
                curr = curr.pointers.get(part);
            }
        }

        // checks if letter exists and has not been deleted before
        if (curr != null && curr.value != null) {

            // set current "letter's" node's value to null to indicate it and its children were deleted
            if (!curr.pointers.isEmpty()) {
                curr.value = null;

            // if key is empty (implying the root)
            // set root's value to null to indicate it was deleted
            } else {
                root.value = null;
            }

            // decrement size
            size--;
        }
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
}

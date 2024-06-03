package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private FrontListNode front;
    private class FrontListNode {
        private Item<K, V> data;
        private FrontListNode next;
        public FrontListNode(Item<K, V> item, FrontListNode next) {
            this.data = item;
            this.next = next;
        }
        public FrontListNode(Item<K, V> item) {
            this(item, null);
        }
        public FrontListNode() {
            this(null, null);
        }
    }

    public MoveToFrontList() {
        this.front = null;
        this.size = 0;

    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldVal = find(key);
        if (oldVal == null) {
            front = new FrontListNode(new Item(key, value), front);
            size++;
        } else {
            front.data.value = value;
        }
        return oldVal;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        FrontListNode curr = front;
        V val = null;
        if (curr != null && curr.data != null) {
            if (curr.data.key.equals(key)) {
                return curr.data.value;
            }
            while (curr.next != null && curr.next.data != null &&
                    !curr.next.data.key.equals(key)) {

                curr = curr.next;
            }
            if (curr.next != null && curr.next.data != null) {
                val = curr.next.data.value;
                FrontListNode temp = curr.next;
                curr.next = temp.next;
                temp.next = front;
                front = temp;
            }
        }
        return val;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }

    private class MoveToFrontListIterator extends SimpleIterator<Item<K, V>>{
        private FrontListNode curr;
        public MoveToFrontListIterator(){
            curr = MoveToFrontList.this.front;
        }
        public boolean hasNext(){
            return curr != null;
        }
        public Item<K,V> next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Item val = curr.data;
            curr = curr.next;
            return val;
        }
    }
}

package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
// import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
// import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K,V>[] hashTable;
    private double loadFactor;
    private int primeIndex;


    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        hashTable = new Dictionary[PRIME_SIZES[0]];
        for (int i = 0; i < PRIME_SIZES[0]; i++) {
            hashTable[i] = newChain.get();
        }
        primeIndex = 1;
        loadFactor = 0.0;
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (loadFactor == 2.0) {
            hashTable = resize(hashTable);
        }
        int i = Math.abs(key.hashCode() % hashTable.length);
        if (i >= 0) {

            // if no Dictionary at key index exists, create new Dictionary in place
            if (hashTable[i] == null) {
                hashTable[i] = newChain.get();
            }

            // try to find key
            V val = find(key);

            // if key doesn't exist, increment size
            if (val == null) {
                size++;
            }

            // update/insert key/value pair
            hashTable[i].insert(key, value);

            // update loadFactor
            loadFactor = size/hashTable.length;
            return val;
        }
        return null;
    }

    @Override
    public V find(K key) {
        int i = Math.abs(key.hashCode() % hashTable.length);
        if  (i >= 0) {

            // if no Dictionary at key index exists, create new Dictionary in place
            if (hashTable[i] == null) {
                hashTable[i] = newChain.get();
            } else {
                return hashTable[i].find(key);
            }
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        Iterator<Item<K,V>> hashIterator = new Iterator<Item<K,V>>() {
            Iterator<Item<K,V>> indexIterator = null;
            private int index = -1;

            public boolean hasNext() {
                if (index == -1 || !indexIterator.hasNext()) {
                    index++;

                    // find next available item
                    while (index < hashTable.length &&
                            (hashTable[index] == null || hashTable[index].isEmpty())) {
                        index++;
                    }

                    // if there are no remaining items, set iterator to null
                    if (index >= hashTable.length) {
                       indexIterator = null;

                    // otherwise get next possible iterator
                    } else {
                        indexIterator = hashTable[index].iterator();
                    }
                }

                if (indexIterator != null) {
                    return indexIterator.hasNext();
                }
                return false;
            }

            public Item<K,V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return indexIterator.next();
            }
        };
        return hashIterator;
    }

    // resize & rehash the HashTable
    private Dictionary<K,V>[] resize(Dictionary<K,V>[] oldHashTable) {
        Dictionary<K, V>[] newHashTable;
        if (primeIndex > 15) {

            // length*2+1 -> odd: higher likelihood of being prime -> better hashing
            newHashTable = new Dictionary[oldHashTable.length*2+1];
        } else {
            newHashTable = new Dictionary[PRIME_SIZES[primeIndex]];
        }

        // rehashing
        for (int i = 0; i < oldHashTable.length; i++) {
            if (oldHashTable[i] != null) {
                for(Item<K,V> obj : oldHashTable[i]) {
                    int x = Math.abs(obj.key.hashCode() % newHashTable.length);
                    if (x >= 0) {
                        if (newHashTable[x] == null) {
                            newHashTable[x] = newChain.get();
                        }
                        newHashTable[x].insert(obj.key, obj.value);
                    } else {
                        return new Dictionary[0];
                    }
                }
            }
        }
        primeIndex++;
        return newHashTable;
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}

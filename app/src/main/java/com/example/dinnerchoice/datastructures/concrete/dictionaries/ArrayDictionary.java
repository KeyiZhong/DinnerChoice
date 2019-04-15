package com.example.dinnerchoice.datastructures.concrete.dictionaries;

import com.example.dinnerchoice.datastructures.concrete.KVPair;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.example.dinnerchoice.datastructures.interfaces.IDictionary;
import com.example.dinnerchoice.exceptions.NoSuchKeyException;
// import misc.exceptions.NotYetImplementedException;

/**
 * @see com.example.dinnerchoice.datastructures.interfaces.IDictionary
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field.
    // We will be inspecting it in our private tests.
    private Pair<K, V>[] pairs;

    // You may add extra fields or helper methods though!
    private int size;

    public ArrayDictionary() {
        pairs = makeArrayOfPairs(16);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                return pairs[i].value;
            }
        }
        throw new NoSuchKeyException();
    }

    public V getOrDefault(K key, V defaultValue) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (pairs[i].key == key || (pairs[i].key != null && pairs[i].key.equals(key))) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return defaultValue;
        }else {
            return pairs[index].value;
        }
    }

    @Override
    public void put(K key, V value) {
        if (size == pairs.length) {
            Pair<K, V>[] newPairs = makeArrayOfPairs(pairs.length * 2);
            for (int i = 0; i < size; i++) {
                newPairs[i] = pairs[i];
            }
            pairs = newPairs;
        }
        int index = size;
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                index = i;
                break;
            }
        }
        if (index < size) {
            pairs[index].value = value;
        }else {
            pairs[size] = new Pair<K, V>(key, value);
            size = size + 1;
        }
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        V value = null;
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                value = pairs[i].value;
                pairs[i] = pairs[size - 1];
                pairs[size - 1] = null;
                break;
            }
        }
        size = size - 1;
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && pairs[i].key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<>(pairs);
    }

    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        // Add any fields you need to store state information
        private int index;
        private Pair<K, V> pairs[];
        public ArrayDictionaryIterator(Pair<K, V> pairs[]) {
            // Initialize the iterator
            this.pairs = pairs;
            index = 0;
        }

        public boolean hasNext() {
            // Implement hasNext
            if(index >= pairs.length || pairs[index] == null) {
                return false;
            }
            return true;
        }

        public KVPair<K, V> next() {
            // Return the next KVPair in the dictionary
            if(pairs[index] == null) {
                throw new NoSuchElementException();
            }
            KVPair<K, V> nextPair = new KVPair<>(pairs[index].key, pairs[index].value);
            index = index + 1;
            return nextPair;
        }
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}

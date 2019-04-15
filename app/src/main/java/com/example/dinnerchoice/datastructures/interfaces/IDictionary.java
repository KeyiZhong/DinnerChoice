package com.example.dinnerchoice.datastructures.interfaces;

import com.example.dinnerchoice.datastructures.concrete.KVPair;

import java.util.Iterator;

/**
 * Represents a data structure that contains a bunch of key-value mappings. Each key must be unique.
 */
public interface IDictionary<K, V> extends Iterable<KVPair<K, V>> {
    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    public V get(K key);


    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    public void put(K key, V value);

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    public V remove(K key);

    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    public boolean containsKey(K key);

    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    public int size();

    /**
     * Returns a list of all key-value pairs contained within this dict.
     */
    public Iterator<KVPair<K, V>> iterator();
}

package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiMap<K, V> extends HashMap<K, List<V>> {

	/**
	 * Looks for a list that is mapped to the given key. If there is not one
	 * then a new one is created mapped and has the value added to it.
	 * 
	 * @param key
	 * @param value
	 * @return true if the list has already been created, false if a new list is
	 *         created.
	 */
	public boolean putOne(K key, V value) {
		if (this.containsKey(key)) {
			this.get(key).add(value);
			return true;
		} else {
			List<V> values = new ArrayList<>();
			values.add(value);
			this.put(key, values);
			return false;
		}
	}

	public void removeOne(K key, V value) {
		if (this.containsKey(key)) {
			this.get(key).remove(value);
		}
	}

	public void print() {
		for (Entry<K, List<V>> e : this.entrySet()) {
			System.out.println(e.getKey() + ": ");
			for (V v : e.getValue()) {
				System.out.println("      " + v);
			}
			System.out.println();
		}
	}
}
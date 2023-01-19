package hr.fer.oprpp1.custom.collections;

public class Dictionary<K,V> {
	
	private class Record<K,V> {
		
		K key;
		V value;
		
		public Record(K key,V value) {
			if(key == null) throw new NullPointerException("Key cannot be null!");
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	ArrayIndexedCollection<Record<K,V>> hiddenGoods;
	
	public Dictionary() {
		this.hiddenGoods = new ArrayIndexedCollection<>();
	}
	
	public boolean isEmpty() {
		return hiddenGoods.size() == 0;
	}
	
	public int size() {
		return hiddenGoods.size();
	}
	
	public void clear() {
		hiddenGoods.clear();
	}
	
	public V put(K key,V value) {
		if(key == null) throw new NullPointerException("Key cannot be null");
		boolean alreadyExisted = false;
		ElementsGetter<Record<K,V>> getter = hiddenGoods.createElementsGetter();
		V pastValue = null;
		
		while(getter.hasNextElement()) {
			Record<K,V> temp = getter.getNextElement();
			if(temp.getKey().equals(key)) {
				pastValue = temp.getValue();
				temp.setValue(value);
				alreadyExisted = true;
				break;
			}
		}
		
		if(alreadyExisted) return pastValue;
		hiddenGoods.add(new Record<K, V>(key,value));
		return null;
	}
	
	public V get(Object key) {
		if(key == null) throw new NullPointerException("Key cannot be null!");
		ElementsGetter<Record<K,V>> getter = hiddenGoods.createElementsGetter();
		int index = 0;
		
		while(getter.hasNextElement()) {
			Record<K,V> temp = getter.getNextElement();
			if(temp.getKey().equals(key)) {
				return temp.getValue();
			}
		}
		return null;
	}
	
	public V remove(K key) {
		if(key == null) throw new NullPointerException("Key cannot be null!");
		ElementsGetter<Record<K,V>> getter = hiddenGoods.createElementsGetter();
		V searchValue = null;
		while(getter.hasNextElement()) {
			Record<K,V> temp = getter.getNextElement();
			if(temp.getKey().equals(key)) {
				searchValue =  temp.getValue();
				hiddenGoods.remove(temp);
				break;
			}
		}
		if(searchValue == null) return null;
		return searchValue;
	}
	

}

public class ComparableTuple<K, V extends Comparable<V>> implements Comparable<ComparableTuple<K,V>>{
    private K key;
    private V value;


    public ComparableTuple(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }
    

    @Override
    public String toString() {
        return "( " + getKey() + " , " +getValue() + " )";
    }
    
    @Override
    public int compareTo(ComparableTuple<K, V> o) {
        return this.getValue().compareTo(o.getValue());
    }
}

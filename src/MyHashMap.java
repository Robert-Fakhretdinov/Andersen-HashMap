import java.util.Arrays;

public class MyHashMap<K, V>{
    private MyEntry<K, V>[] table;
    private final int DEFAULT_CAPACITY = 16;
    private float loadFactor;
    private int size;


    public MyHashMap() {
        table = new MyEntry[DEFAULT_CAPACITY];
        this.loadFactor = 0.75f;
    }

    public MyHashMap(int capacity) {
        table = new MyEntry[capacity];
        this.loadFactor = 0.75f;
    }

    public MyHashMap(int capacity, float loadFactor) {
        table = new MyEntry[capacity];
        this.loadFactor = loadFactor;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public void put(K key, V value) {

        int index = index(key.hashCode());
        MyEntry<K, V> entry;

        MyEntry<K, V> newEntry = new MyEntry<>();
        newEntry.setKey(key);
        newEntry.setValue(value);

        for (int i = index; i < table.length; i++) {
            entry = table[i];
            if (entry != null) {

                if (entry.getKey().equals(key)) {
                    table[i] = newEntry;
                }
            } else {
                table[i] = newEntry;
                size++;
                increaseCapacity();
                break;
            }
        }
    }

    public V get(K key) {
        MyEntry<K, V> entry;
        int index = index(key.hashCode());
        entry = table[index];
        if (entry != null && entry.getKey().equals(key)) {
            return entry.getValue();
        }
        return null;
    }


    public void remove(K key) {
        MyEntry<K, V> entry;
        int index = index(key.hashCode());
        entry = table[index];
        if (entry != null && entry.getKey().equals(key)) {
            entry.setValue(null);
            entry.setKey(null);
            size--;
        }
    }


    private int index(int hash) {
        return hash % table.length;
    }


    private void increaseCapacity() {
        if (size >= table.length * loadFactor) {
            int newCapacity = table.length * 2;
            transfer(newCapacity);
        }
    }

    private void transfer(int capacity) {
        MyEntry<K, V>[] tempTable = Arrays.copyOf(table, table.length);
        table = new MyEntry[capacity];
        size = 0;
        for (int i = 0; i < tempTable.length; i++) {
            MyEntry<K, V> entry = tempTable[i];
            if (entry != null) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
}

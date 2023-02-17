import java.util.Optional;
import java.util.Map;

class KeyableMap<V extends Keyable> implements Keyable {
    private final String key;
    private final ImmutableMap<String, V> map;

    KeyableMap(String key) {
        this.key = key;
        this.map = new ImmutableMap<String, V>();
    }

    KeyableMap(String key, ImmutableMap<String, V> map) {
        this.key = key;
        this.map = map;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    ImmutableMap<String, V> getMap() {
        return this.map;
    }

    Optional<V> get(String key) {
        return this.map.get(key);
    }

    KeyableMap<V> put(V item) {
        ImmutableMap<String, V> newMap = 
            this.map.put(item.getKey(), item);
        return new KeyableMap<V>(this.getKey(), newMap);
    }

    @Override
    public String toString() {
        String string = this.getKey() + ": {";
        boolean isFirst = true;
        for (Map.Entry<String, V> e : this.map) {
            if (!isFirst) {
                string += ", ";
            }
            string += e.getValue();
            isFirst = false;
        }
        string += "}";
        return string;
    }
}

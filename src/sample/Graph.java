package sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph<T> {
    private Map<T, List<T>> map = new HashMap<>();

    public Map<T, List<T>> getMap(){
        return this.map;
    }

    public void addVertex(T t) {
        this.map.put(t, new LinkedList<T>());
    }

    public void addEdge(T source, T destination, boolean bidirectional) {
        if (!this.map.containsKey(source))
            addVertex(source);
        if (!this.map.containsKey(destination))
            addVertex(destination);
        this.map.get(source).add(destination);

        if (bidirectional)
            this.map.get(destination).add(source);
    }
}

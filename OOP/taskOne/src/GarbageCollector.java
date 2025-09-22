import java.util.*;

public abstract class GarbageCollector {
    protected final Heap heap;
    protected final List<GCObject> roots;
    
    public GarbageCollector(Heap heap) {
        this.heap = heap;
        this.roots = new ArrayList<>();
    }
    
    public void addRoot(GCObject root) {
        if (root != null && !roots.contains(root)) {
            roots.add(root);
        }
    }
    
    public void removeRoot(GCObject root) {
        roots.remove(root);
    }
    
    public List<GCObject> getRoots() {
        return new ArrayList<>(roots);
    }
    
    public abstract Collection<GCObject> collect();
    
    protected void markReachable() {
        for (GCObject obj : heap.getAllObjects()) {
            obj.unmark();
        }
        
        for (GCObject root : roots) {
            markObject(root);
        }
    }
    
    protected void markObject(GCObject obj) {
        if (obj == null || obj.isMarked()) {
            return;
        }
        
        obj.mark();
        
        for (GCObject ref : obj.getReferences()) {
            markObject(ref);
        }
    }
    
    protected List<GCObject> getUnreachableObjects() {
        List<GCObject> unreachable = new ArrayList<>();
        
        for (GCObject obj : heap.getAllObjects()) {
            if (!obj.isMarked()) {
                unreachable.add(obj);
            }
        }
        
        return unreachable;
    }
    
    protected void logHeapState(String phase) {
        int totalObjects = heap.getAllObjects().size();
        int unreachableObjects = getUnreachableObjects().size();
        int reachableObjects = totalObjects - unreachableObjects;
        
        System.out.printf("📊 %s: %d объектов (✅ %d нужных, ❌ %d мусора)%n", 
                         phase, totalObjects, reachableObjects, unreachableObjects);
    }
}


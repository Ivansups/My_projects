import java.util.*;

public class MarkAndSweepGC implements GarbageCollector {
    private final Heap heap;
    private final List<GCObject> roots;

    public MarkAndSweepGC(Heap heap) {
        this.heap = heap;
        this.roots = new ArrayList<>();
    }

    @Override
    public void addRoot(GCObject root) {
        if (root != null && !roots.contains(root)) {
            roots.add(root);
        }
    }

    @Override
    public void removeRoot(GCObject root) {
        roots.remove(root);
    }

    @Override
    public Collection<GCObject> collect() {
        long startTime = System.currentTimeMillis();
        
        // 1. Mark phase
        for (GCObject obj : heap.getObjectsCopy()) {
            obj.unmark();
        }
        
        for (GCObject root : roots) {
            markObject(root);
        }

        // 2. Sweep phase
        List<GCObject> unreachableObjects = new ArrayList<>();
        Iterator<GCObject> iterator = heap.getAllObjects().iterator();
        
        while (iterator.hasNext()) {
            GCObject obj = iterator.next();
            if (!obj.isMarked()) {
                unreachableObjects.add(obj);
                iterator.remove();
                heap.removeObject(obj);
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Mark & Sweep GC выполнен за " + (endTime - startTime) + " мс");
        
        return unreachableObjects;
    }

    private void markObject(GCObject obj) {
        if (obj == null || obj.isMarked()) {
            return;
        }
        obj.mark();
        for (GCObject ref : obj.getReferences()) {
            markObject(ref);
        }
    }

    @Override
    public Collection<GCObject> getRoots() {
        return new ArrayList<>(roots);
    }

    @Override
    public String getAlgorithmName() {
        return "Mark & Sweep";
    }
}

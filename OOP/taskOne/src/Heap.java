import java.util.*;

public class Heap {
    private final int maxSize;
    private final List<GCObject> objects;
    private int currentSize;
    
    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.objects = new ArrayList<>();
        this.currentSize = 0;
    }
    
    public boolean addObject(GCObject obj) {
        if (currentSize + obj.getSize() > maxSize) {
            return false;
        }
        
        objects.add(obj);
        currentSize += obj.getSize();
        obj.setAddress(objects.size() - 1);
        return true;
    }
    
    public void removeObject(GCObject obj) {
        if (objects.remove(obj)) {
            currentSize -= obj.getSize();
        }
    }
    
    public List<GCObject> getAllObjects() {
        return new ArrayList<>(objects);
    }
    
    public int getCurrentSize() {
        return currentSize;
    }
    
    public int getMaxSize() {
        return maxSize;
    }
    
    public boolean hasSpace(int size) {
        return currentSize + size <= maxSize;
    }
    
    public void clear() {
        objects.clear();
        currentSize = 0;
    }
    
    @Override
    public String toString() {
        return String.format("Heap[%d/%d bytes, %d objects]", 
                           currentSize, maxSize, objects.size());
    }
}


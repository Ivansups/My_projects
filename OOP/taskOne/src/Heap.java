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
        return true;
    }

    public void removeObject(GCObject obj) {
        if (objects.remove(obj)) {
            currentSize -= obj.getSize();
        }
    }

    public List<GCObject> getAllObjects() {
        return objects; // Возвращаем сам список, а не копию
    }

    public List<GCObject> getObjectsCopy() {
        return new ArrayList<>(objects); // Копия для внешнего использования
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

    public double getUsagePercentage() {
        return (double) currentSize / maxSize * 100;
    }

    public void resetSize() {
        this.currentSize = 0;
    }

    public void addToSize(int size) {
        this.currentSize += size;
    }
}

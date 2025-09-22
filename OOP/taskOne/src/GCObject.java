 import java.util.*;

public class GCObject {
    private final String name;
    private final int size;
    private int address;
    private boolean marked;
    private final List<GCObject> references;
    
    public GCObject(String name, int size) {
        this.name = name;
        this.size = size;
        this.address = -1;
        this.marked = false;
        this.references = new ArrayList<>();
    }
    
    public void addReference(GCObject obj) {
        if (obj != null && !references.contains(obj)) {
            references.add(obj);
        }
    }
    
    public void removeReference(GCObject obj) {
        references.remove(obj);
    }
    
    public List<GCObject> getReferences() {
        return new ArrayList<>(references);
    }
    
    public void mark() {
        this.marked = true;
    }
    
    public void unmark() {
        this.marked = false;
    }
    
    public boolean isMarked() {
        return marked;
    }
    
    public void setAddress(int address) {
        this.address = address;
    }
    
    public int getAddress() {
        return address;
    }
    
    public int getSize() {
        return size;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("%s[size=%d, addr=%d, marked=%s, refs=%d]", 
                           name, size, address, marked, references.size());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GCObject gcObject = (GCObject) obj;
        return Objects.equals(name, gcObject.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}


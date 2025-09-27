import java.util.*;

public class GCObject {
    private final String name;
    private final int size;
    private boolean marked;
    private final List<GCObject> references;
    private String generation;
    private int age;

    public GCObject(String name, int size) {
        this.name = name;
        this.size = size;
        this.marked = false;
        this.references = new ArrayList<>();
        this.generation = "Young";
        this.age = 0;
    }

    public void addReference(GCObject obj) {
        if (obj != null && !references.contains(obj)) {
            references.add(obj);
        }
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

    public int getSize() { 
        return size; 
    }

    public String getName() { 
        return name; 
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s (%d байт, %s, возраст: %d)", name, size, generation, age);
    }
}

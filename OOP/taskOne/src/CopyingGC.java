import java.util.*;

/**
 * Алгоритм Copying GC для Young Generation
 * Копирует живые объекты в новое пространство
 */
public class CopyingGC implements GarbageCollector {
    private final Heap heap;
    private final List<GCObject> roots;
    private final List<GCObject> fromSpace;
    private final List<GCObject> toSpace;

    public CopyingGC(Heap heap) {
        this.heap = heap;
        this.roots = new ArrayList<>();
        this.fromSpace = new ArrayList<>();
        this.toSpace = new ArrayList<>();
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
        
        // Инициализируем fromSpace всеми объектами из кучи
        fromSpace.clear();
        fromSpace.addAll(heap.getAllObjects());
        
        // Очищаем toSpace
        toSpace.clear();
        
        // 1. Mark phase - помечаем достижимые объекты
        for (GCObject obj : fromSpace) {
            obj.unmark();
        }
        
        for (GCObject root : roots) {
            markObject(root);
        }

        // 2. Copy phase - копируем живые объекты в toSpace
        List<GCObject> collectedObjects = new ArrayList<>();
        
        for (GCObject obj : fromSpace) {
            if (obj.isMarked()) {
                // Копируем живой объект
                toSpace.add(obj);
            } else {
                // Объект мертв - добавляем в список собранных
                collectedObjects.add(obj);
            }
        }
        
        // 3. Обновляем кучу - заменяем старые объекты новыми
        // Сначала очищаем кучу и обновляем размер
        heap.getAllObjects().clear();
        heap.resetSize(); // Сбрасываем размер кучи
        
        // Добавляем только живые объекты
        for (GCObject obj : toSpace) {
            heap.getAllObjects().add(obj);
            heap.addToSize(obj.getSize());
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Copying GC выполнен за " + (endTime - startTime) + " мс");
        System.out.println("Скопировано объектов: " + toSpace.size());
        System.out.println("Собрано объектов: " + collectedObjects.size());
        
        return collectedObjects;
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
        return "Copying GC";
    }
}

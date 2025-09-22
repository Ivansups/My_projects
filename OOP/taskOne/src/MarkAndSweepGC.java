import java.util.*;

public class MarkAndSweepGC extends GarbageCollector {
    
    public MarkAndSweepGC(Heap heap) {
        super(heap);
    }
    
    @Override
    public Collection<GCObject> collect() {
        System.out.println("\n🧹 ЗАПУСК СБОРЩИКА МУСОРА MARK & SWEEP");
        System.out.println("=====================================");
        
        System.out.println("🔍 Этап 1: ПОИСК ДОСТИЖИМЫХ ОБЪЕКТОВ");
        logHeapState("До поиска");
        markReachable();
        logHeapState("После поиска");
        
        List<GCObject> unreachableObjects = getUnreachableObjects();
        
        System.out.println("\n🗑️ Этап 2: УДАЛЕНИЕ МУСОРА");
        if (unreachableObjects.isEmpty()) {
            System.out.println("✅ Мусора не найдено - все объекты нужны!");
        } else {
            System.out.println("Найдены ненужные объекты:");
            for (GCObject obj : unreachableObjects) {
                System.out.println("  ❌ Удаляем: " + obj.getName());
                heap.removeObject(obj);
            }
        }
        
        logHeapState("После очистки");
        
        int freedBytes = unreachableObjects.stream().mapToInt(GCObject::getSize).sum();
        System.out.printf("\n📊 РЕЗУЛЬТАТ: Собрано %d объектов, освобождено %d байт%n", 
                         unreachableObjects.size(), freedBytes);
        
        return unreachableObjects;
    }
    
    public void demonstrateMarkAndSweep() {
        System.out.println("\n📋 СОСТОЯНИЕ ДО СБОРКИ МУСОРА:");
        System.out.println("================================");
        for (GCObject obj : heap.getAllObjects()) {
            System.out.println("  " + obj.getName() + " (размер: " + obj.getSize() + " байт)");
        }
        
        Collection<GCObject> collected = collect();
        
        System.out.println("\n✅ СОСТОЯНИЕ ПОСЛЕ СБОРКИ МУСОРА:");
        System.out.println("=================================");
        for (GCObject obj : heap.getAllObjects()) {
            System.out.println("  " + obj.getName() + " (размер: " + obj.getSize() + " байт)");
        }
        
        if (!collected.isEmpty()) {
            System.out.println("\n🗑️ УДАЛЕННЫЕ ОБЪЕКТЫ:");
            System.out.println("=====================");
            for (GCObject obj : collected) {
                System.out.println("  " + obj.getName() + " (размер: " + obj.getSize() + " байт)");
            }
        }
    }
}


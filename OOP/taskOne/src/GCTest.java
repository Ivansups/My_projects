import java.util.*;

public class GCTest {
    
    public static void main(String[] args) {
        System.out.println("🧪 ТЕСТЫ СБОРЩИКА МУСОРА (MARK & SWEEP)");
        System.out.println("=======================================\n");
        
        testMarkAndSweepBasic();
        testCircularReferences();
        testMemoryReclamation();
        testRootObjects();
        testMultipleRoots();
        
        System.out.println("\n🎉 ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ УСПЕШНО!");
    }
    
    public static void testMarkAndSweepBasic() {
        System.out.println("🔍 Тест 1: Базовая функциональность");
        
        Heap heap = new Heap(500);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject root = new GCObject("Root", 100);
        GCObject reachable = new GCObject("Reachable", 50);
        GCObject unreachable = new GCObject("Unreachable", 50);
        
        heap.addObject(root);
        heap.addObject(reachable);
        heap.addObject(unreachable);
        
        root.addReference(reachable);
        gc.addRoot(root);
        
        Collection<GCObject> collected = gc.collect();
        
        assertTest(heap.getAllObjects().size() == 2, "Должно остаться 2 объекта");
        assertTest(collected.size() == 1, "Должен быть собран 1 объект");
        assertTest(collected.contains(unreachable), "Должен быть собран unreachable объект");
        assertTest(heap.getAllObjects().contains(root), "Root должен остаться");
        assertTest(heap.getAllObjects().contains(reachable), "Reachable должен остаться");
        
        System.out.println("✅ Тест пройден: Базовая функциональность работает корректно\n");
    }
    
    public static void testMultipleRoots() {
        System.out.println("🌳 Тест 2: Множественные корни");
        
        Heap heap = new Heap(500);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject root1 = new GCObject("Root1", 80);
        GCObject root2 = new GCObject("Root2", 80);
        GCObject child1 = new GCObject("Child1", 60);
        GCObject child2 = new GCObject("Child2", 60);
        GCObject orphan = new GCObject("Orphan", 40);
        
        heap.addObject(root1);
        heap.addObject(root2);
        heap.addObject(child1);
        heap.addObject(child2);
        heap.addObject(orphan);
        
        root1.addReference(child1);
        root2.addReference(child2);
        
        gc.addRoot(root1);
        gc.addRoot(root2);
        
        Collection<GCObject> collected = gc.collect();
        
        assertTest(heap.getAllObjects().size() == 4, "Должно остаться 4 объекта");
        assertTest(collected.size() == 1, "Должен быть собран 1 объект");
        assertTest(collected.contains(orphan), "Должен быть собран orphan объект");
        
        System.out.println("✅ Тест пройден: Множественные корни работают корректно\n");
    }
    
    public static void testCircularReferences() {
        System.out.println("🔄 Тест 3: Циклические ссылки");
        
        Heap heap = new Heap(300);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject node1 = new GCObject("Node1", 50);
        GCObject node2 = new GCObject("Node2", 50);
        GCObject node3 = new GCObject("Node3", 50);
        
        heap.addObject(node1);
        heap.addObject(node2);
        heap.addObject(node3);
        
        node1.addReference(node2);
        node2.addReference(node3);
        node3.addReference(node1);
        
        gc.addRoot(node1);
        
        Collection<GCObject> collected = gc.collect();
        
        assertTest(heap.getAllObjects().size() == 3, "Все 3 объекта должны остаться");
        assertTest(collected.size() == 0, "Никакие объекты не должны быть собраны");
        
        System.out.println("✅ Тест пройден: Циклические ссылки обрабатываются корректно\n");
    }
    
    public static void testMemoryReclamation() {
        System.out.println("💾 Тест 4: Освобождение памяти");
        
        Heap heap = new Heap(1000);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject root = new GCObject("Root", 100);
        heap.addObject(root);
        gc.addRoot(root);
        
        int initialSize = heap.getCurrentSize();
        
        for (int i = 0; i < 10; i++) {
            GCObject obj = new GCObject("Temp" + i, 50);
            heap.addObject(obj);
        }
        
        int sizeBeforeGC = heap.getCurrentSize();
        
        Collection<GCObject> collected = gc.collect();
        
        int sizeAfterGC = heap.getCurrentSize();
        int freedMemory = sizeBeforeGC - sizeAfterGC;
        
        assertTest(freedMemory > 0, "Память должна быть освобождена");
        assertTest(collected.size() == 10, "Должно быть собрано 10 объектов");
        assertTest(sizeAfterGC == initialSize, "Размер кучи должен вернуться к исходному");
        
        System.out.println("✅ Тест пройден: Память освобождается корректно (" + 
                          freedMemory + " байт освобождено)\n");
    }
    
    public static void testRootObjects() {
        System.out.println("🌳 Тест 5: Динамическое управление корнями");
        
        Heap heap = new Heap(400);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject root1 = new GCObject("Root1", 80);
        GCObject root2 = new GCObject("Root2", 80);
        GCObject child1 = new GCObject("Child1", 60);
        GCObject child2 = new GCObject("Child2", 60);
        
        heap.addObject(root1);
        heap.addObject(root2);
        heap.addObject(child1);
        heap.addObject(child2);
        
        root1.addReference(child1);
        root2.addReference(child2);
        
        gc.addRoot(root1);
        gc.addRoot(root2);
        
        Collection<GCObject> collected = gc.collect();
        
        assertTest(heap.getAllObjects().size() == 4, "Все 4 объекта должны остаться");
        assertTest(collected.size() == 0, "Никакие объекты не должны быть собраны");
        
        gc.removeRoot(root2);
        collected = gc.collect();
        
        assertTest(heap.getAllObjects().size() == 2, "Должно остаться 2 объекта");
        assertTest(collected.size() == 2, "Должно быть собрано 2 объекта");
        
        System.out.println("✅ Тест пройден: Корневые объекты работают корректно\n");
    }
    
    private static void assertTest(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Тест не пройден: " + message);
        }
    }
}

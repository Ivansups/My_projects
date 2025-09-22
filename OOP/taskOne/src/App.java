public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Эмулятор сборщика мусора Java (Mark & Sweep) ===\n");
        
        demonstrateMarkAndSweep();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        demonstrateAdvancedScenarios();
    }
    
    private static void demonstrateMarkAndSweep() {
        System.out.println("🎯 ДЕМОНСТРАЦИЯ АЛГОРИТМА MARK & SWEEP");
        System.out.println("=====================================");
        
        Heap heap = new Heap(1000);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject obj1 = new GCObject("👤 Пользователь", 100);
        GCObject obj2 = new GCObject("📋 Профиль", 150);
        GCObject obj3 = new GCObject("⚙️ Настройки", 80);
        GCObject obj4 = new GCObject("💾 Кэш", 200);
        GCObject obj5 = new GCObject("🗑️ Временные данные", 120);
        
        heap.addObject(obj1);
        heap.addObject(obj2);
        heap.addObject(obj3);
        heap.addObject(obj4);
        heap.addObject(obj5);
        
        obj1.addReference(obj2);
        obj2.addReference(obj3);
        obj1.addReference(obj4);
        
        gc.addRoot(obj1);
        
        System.out.println("📊 Создано объектов: " + heap.getAllObjects().size());
        System.out.println("💾 Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");
        System.out.println();
        
        gc.demonstrateMarkAndSweep();
    }
    
    private static void demonstrateAdvancedScenarios() {
        System.out.println("🔍 ДОПОЛНИТЕЛЬНЫЕ СЦЕНАРИИ");
        System.out.println("==========================");
        
        demonstrateCircularReferences();
        demonstrateMultipleRoots();
    }
    
    private static void demonstrateCircularReferences() {
        System.out.println("\n🔄 Циклические ссылки:");
        System.out.println("======================");
        
        Heap heap = new Heap(500);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject node1 = new GCObject("🔗 Узел 1", 50);
        GCObject node2 = new GCObject("🔗 Узел 2", 50);
        GCObject node3 = new GCObject("🔗 Узел 3", 50);
        
        heap.addObject(node1);
        heap.addObject(node2);
        heap.addObject(node3);
        
        node1.addReference(node2);
        node2.addReference(node3);
        node3.addReference(node1);
        
        gc.addRoot(node1);
        
        System.out.println("Создан цикл: Узел1 -> Узел2 -> Узел3 -> Узел1");
        gc.demonstrateMarkAndSweep();
    }
    
    private static void demonstrateMultipleRoots() {
        System.out.println("\n🌳 Множественные корни:");
        System.out.println("=======================");
        
        Heap heap = new Heap(600);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        GCObject root1 = new GCObject("🌳 Корень 1", 80);
        GCObject root2 = new GCObject("🌳 Корень 2", 80);
        GCObject child1 = new GCObject("👶 Дочерний 1", 60);
        GCObject child2 = new GCObject("👶 Дочерний 2", 60);
        GCObject orphan = new GCObject("👻 Сирота ебаная", 40);
        
        heap.addObject(root1);
        heap.addObject(root2);
        heap.addObject(child1);
        heap.addObject(child2);
        heap.addObject(orphan);
        
        root1.addReference(child1);
        root2.addReference(child2);
        
        gc.addRoot(root1);
        gc.addRoot(root2);
        
        System.out.println("Два корня с дочерними объектами");
        gc.demonstrateMarkAndSweep();
    }
}

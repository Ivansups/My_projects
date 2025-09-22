/**
 * Пример работы сборщика мусора с простыми переменными
 */
public class SimpleVariablesExample {
    
    public static void main(String[] args) {
        System.out.println("🎯 ПРИМЕР: СБОРЩИК МУСОРА И ПРОСТЫЕ ПЕРЕМЕННЫЕ");
        System.out.println("=============================================\n");
        
        demonstrateLocalVariables();
        demonstrateMethodParameters();
        demonstrateTemporaryObjects();
    }
    
    /**
     * Демонстрация работы с локальными переменными
     */
    private static void demonstrateLocalVariables() {
        System.out.println("📝 ДЕМОНСТРАЦИЯ: Локальные переменные");
        System.out.println("=====================================");
        
        // Создаем кучу и сборщик
        Heap heap = new Heap(1000);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        // Симулируем локальные переменные
        GCObject userName = new GCObject("👤 Имя пользователя", 50);
        GCObject userAge = new GCObject("🎂 Возраст", 30);
        GCObject userHobbies = new GCObject("🎨 Хобби", 80);
        GCObject tempData = new GCObject("🗑️ Временные данные", 60);
        
        // Добавляем в кучу
        heap.addObject(userName);
        heap.addObject(userAge);
        heap.addObject(userHobbies);
        heap.addObject(tempData);
        
        // Создаем связи (как в реальной программе)
        userName.addReference(userHobbies); // Имя ссылается на хобби
        
        // Добавляем корни (локальные переменные)
        gc.addRoot(userName);  // userName - корень
        gc.addRoot(userAge);   // userAge - корень
        // tempData остается без корня - будет собран
        
        System.out.println("📊 Создано переменных: " + heap.getAllObjects().size());
        System.out.println("💾 Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");
        
        // Демонстрируем сборку
        gc.demonstrateMarkAndSweep();
    }
    
    /**
     * Демонстрация работы с параметрами методов
     */
    private static void demonstrateMethodParameters() {
        System.out.println("\n📝 ДЕМОНСТРАЦИЯ: Параметры методов");
        System.out.println("==================================");
        
        Heap heap = new Heap(800);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        // Симулируем параметры метода
        GCObject methodParam1 = new GCObject("📥 Параметр 1", 40);
        GCObject methodParam2 = new GCObject("📥 Параметр 2", 50);
        GCObject localVar = new GCObject("🏠 Локальная переменная", 60);
        GCObject orphan = new GCObject("👻 Сирота", 30);
        
        heap.addObject(methodParam1);
        heap.addObject(methodParam2);
        heap.addObject(localVar);
        heap.addObject(orphan);
        
        // Создаем связи
        methodParam1.addReference(localVar);
        
        // Параметры методов - корни
        gc.addRoot(methodParam1);
        gc.addRoot(methodParam2);
        
        System.out.println("📊 Создано параметров: " + heap.getAllObjects().size());
        
        gc.demonstrateMarkAndSweep();
    }
    
    /**
     * Демонстрация работы с временными объектами
     */
    private static void demonstrateTemporaryObjects() {
        System.out.println("\n📝 ДЕМОНСТРАЦИЯ: Временные объекты");
        System.out.println("==================================");
        
        Heap heap = new Heap(600);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);
        
        // Симулируем временные объекты
        GCObject mainObject = new GCObject("🎯 Главный объект", 100);
        GCObject temp1 = new GCObject("⏰ Временный 1", 40);
        GCObject temp2 = new GCObject("⏰ Временный 2", 50);
        GCObject temp3 = new GCObject("⏰ Временный 3", 30);
        GCObject result = new GCObject("✅ Результат", 60);
        
        heap.addObject(mainObject);
        heap.addObject(temp1);
        heap.addObject(temp2);
        heap.addObject(temp3);
        heap.addObject(result);
        
        // Создаем связи
        mainObject.addReference(temp1);
        temp1.addReference(temp2);
        temp2.addReference(result);
        // temp3 остается без связей
        
        // Только главный объект - корень
        gc.addRoot(mainObject);
        
        System.out.println("📊 Создано объектов: " + heap.getAllObjects().size());
        
        gc.demonstrateMarkAndSweep();
    }
}

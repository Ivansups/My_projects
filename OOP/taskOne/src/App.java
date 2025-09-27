import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== ЭМУЛЯТОР СБОРЩИКА МУСОРА JAVA ===");
        System.out.println("Демонстрация работы сборщика мусора");
        System.out.println("=====================================\n");

        // Основная демонстрация
        demonstrateMarkAndSweep();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Демонстрация Copying GC
        demonstrateCopyingGC();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Демонстрация генератора объектов
        demonstrateObjectGenerator();
        
        System.out.println("\n🎉 ДЕМОНСТРАЦИИ ЗАВЕРШЕНЫ!");
    }

    private static void demonstrateMarkAndSweep() {
        System.out.println("🧹 ДЕМОНСТРАЦИЯ MARK & SWEEP GC");
        System.out.println("================================");

        // Создаем кучу размером 1000 байт
        Heap heap = new Heap(1000);
        MarkAndSweepGC gc = new MarkAndSweepGC(heap);

        // Создаем объекты
        GCObject user1 = new GCObject("User_1", 100);
        GCObject user2 = new GCObject("User_2", 150);
        GCObject cache = new GCObject("Cache", 200);
        GCObject temp = new GCObject("Temp", 50);

        // Добавляем объекты в кучу
        heap.addObject(user1);
        heap.addObject(user2);
        heap.addObject(cache);
        heap.addObject(temp);

        System.out.println("📦 Созданы объекты:");
        System.out.println("  " + user1);
        System.out.println("  " + user2);
        System.out.println("  " + cache);
        System.out.println("  " + temp);
        System.out.println("Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");

        // Создаем связи между объектами
        user1.addReference(cache);
        user2.addReference(cache);
        cache.addReference(temp);

        System.out.println("\n🔗 Созданы связи:");
        System.out.println("  User_1 -> Cache");
        System.out.println("  User_2 -> Cache");
        System.out.println("  Cache -> Temp");

        // Делаем user1 и user2 корневыми объектами
        gc.addRoot(user1);
        gc.addRoot(user2);

        System.out.println("\n🌱 Корневые объекты: User_1, User_2");

        // Запускаем сборку мусора
        System.out.println("\n🧹 Запуск сборки мусора...");
        Collection<GCObject> collected = gc.collect();

        System.out.println("✅ Сборка завершена!");
        System.out.println("Собрано объектов: " + collected.size());
        System.out.println("Оставшиеся объекты: " + heap.getAllObjects().size());
        System.out.println("Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");

        System.out.println("\n📋 Оставшиеся объекты:");
        for (GCObject obj : heap.getAllObjects()) {
            System.out.println("  " + obj);
        }
    }

    private static void demonstrateCopyingGC() {
        System.out.println("🔄 ДЕМОНСТРАЦИЯ COPYING GC");
        System.out.println("===========================");

        // Создаем кучу размером 800 байт
        Heap heap = new Heap(800);
        CopyingGC gc = new CopyingGC(heap);

        // Создаем объекты
        GCObject session = new GCObject("Session", 120);
        GCObject user = new GCObject("User", 100);
        GCObject profile = new GCObject("Profile", 80);
        GCObject temp1 = new GCObject("Temp1", 60);
        GCObject temp2 = new GCObject("Temp2", 40);

        // Добавляем объекты в кучу
        heap.addObject(session);
        heap.addObject(user);
        heap.addObject(profile);
        heap.addObject(temp1);
        heap.addObject(temp2);

        System.out.println("📦 Созданы объекты:");
        System.out.println("  " + session);
        System.out.println("  " + user);
        System.out.println("  " + profile);
        System.out.println("  " + temp1);
        System.out.println("  " + temp2);
        System.out.println("Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");

        // Создаем связи между объектами
        session.addReference(user);
        user.addReference(profile);
        profile.addReference(temp1);

        System.out.println("\n🔗 Созданы связи:");
        System.out.println("  Session -> User");
        System.out.println("  User -> Profile");
        System.out.println("  Profile -> Temp1");
        System.out.println("  Temp2 - изолированный объект");

        // Делаем session корневым объектом
        gc.addRoot(session);

        System.out.println("\n🌱 Корневой объект: Session");

        // Запускаем Copying GC
        System.out.println("\n🔄 Запуск Copying GC...");
        Collection<GCObject> collected = gc.collect();

        System.out.println("✅ Copying GC завершен!");
        System.out.println("Собрано объектов: " + collected.size());
        System.out.println("Оставшиеся объекты: " + heap.getAllObjects().size());
        System.out.println("Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");

        System.out.println("\n📋 Оставшиеся объекты:");
        for (GCObject obj : heap.getAllObjects()) {
            System.out.println("  " + obj);
        }
    }

    private static void demonstrateObjectGenerator() {
        System.out.println("🏭 ДЕМОНСТРАЦИЯ ГЕНЕРАТОРА ОБЪЕКТОВ");
        System.out.println("====================================");

        // Создаем генератор с кучей размером 2000 байт
        ObjectGenerator generator = new ObjectGenerator(2000);
        
        System.out.println("🚀 Запуск генератора на 10 секунд...");
        
        // Запускаем генератор в отдельном потоке
        Thread generatorThread = new Thread(generator::start);
        generatorThread.start();

        try {
            Thread.sleep(10000); // Работаем 10 секунд
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        generator.stop();
        System.out.println("🛑 Генератор остановлен");
    }
}

// Простой генератор объектов
class ObjectGenerator {
    private final Heap heap;
    private final GarbageCollector gc;
    private final Random random = new Random();
    private final AtomicInteger objectCounter = new AtomicInteger(0);
    private final List<GCObject> rootObjects = Collections.synchronizedList(new ArrayList<>());
    private volatile boolean isRunning = true;

    public ObjectGenerator(int heapSize) {
        this.heap = new Heap(heapSize);
        this.gc = new MarkAndSweepGC(heap);
    }

    public void start() {
        System.out.println("🚀 Генератор запущен (куча: " + heap.getMaxSize() + " байт)");

        // Поток для создания объектов
        new Thread(this::objectCreator).start();
        // Поток для запуска GC
        new Thread(this::gcRunner).start();
    }

    public void stop() {
        isRunning = false;
        printStatistics();
    }

    private void objectCreator() {
        while (isRunning) {
            try {
                createRandomObject();
                Thread.sleep(1000); // Создание объекта каждую секунду
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void gcRunner() {
        while (isRunning) {
            try {
                runGarbageCollection();
                Thread.sleep(2000); // GC каждые 2 секунды
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void createRandomObject() {
        int id = objectCounter.incrementAndGet();
        String name = "Object_" + id;
        int size = random.nextInt(200) + 50; // Объекты от 50 до 249 байт

        GCObject obj = new GCObject(name, size);

        synchronized (heap) {
            if (heap.addObject(obj)) {
                // С вероятностью 60% делаем объект корневым
                if (random.nextDouble() < 0.6) {
                    rootObjects.add(obj);
                    gc.addRoot(obj);
                    System.out.println("🌱 Корень: " + obj);
                } else {
                    System.out.println("📦 Объект: " + obj);
                }

                // С вероятностью 20% удаляем случайный корневой объект
                if (!rootObjects.isEmpty() && random.nextDouble() < 0.2) {
                    removeRandomRootObject();
                }
            } else {
                System.out.println("❌ Не удалось создать " + obj.getName() + " - недостаточно памяти");
            }
        }
    }

    private void removeRandomRootObject() {
        if (rootObjects.isEmpty()) return;

        synchronized (heap) {
            GCObject obj = rootObjects.remove(random.nextInt(rootObjects.size()));
            gc.removeRoot(obj);
            System.out.println("🗑️ Удален корневой объект: " + obj.getName());
        }
    }

    private void runGarbageCollection() {
        synchronized (heap) {
            System.out.println("🧹 Запуск GC...");
            Collection<GCObject> collected = gc.collect();
            System.out.println("✅ GC завершен. Собрано объектов: " + collected.size());
        }
    }

    private void printStatistics() {
        System.out.println("\n📈 СТАТИСТИКА:");
        System.out.println("===============");
        System.out.println("Объектов в куче: " + heap.getAllObjects().size());
        System.out.println("Корневых объектов: " + rootObjects.size());
        System.out.println("Использовано памяти: " + heap.getCurrentSize() + "/" + heap.getMaxSize() + " байт");
        System.out.printf("Заполнение кучи: %.1f%%\n", heap.getUsagePercentage());

        System.out.println("\nОставшиеся объекты:");
        for (GCObject obj : heap.getAllObjects()) {
            System.out.println("  " + obj);
        }
    }
}

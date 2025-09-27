import java.util.*;
import java.util.concurrent.*;

/**
 * Простой генератор объектов с очисткой для демонстрации GC
 */
public class SimpleGC {
    
    private final List<GCObject> heap;
    private final List<GCObject> roots;
    private final int maxSize;
    private int currentSize;
    private final Random random;
    private final ExecutorService executor;
    private volatile boolean running;
    
    public SimpleGC(int maxSize) {
        this.maxSize = maxSize;
        this.heap = Collections.synchronizedList(new ArrayList<>());
        this.roots = Collections.synchronizedList(new ArrayList<>());
        this.currentSize = 0;
        this.random = new Random();
        this.executor = Executors.newFixedThreadPool(2);
        this.running = false;
    }
    
    /**
     * Запускает генератор
     */
    public void start() {
        if (running) return;
        
        running = true;
        System.out.println("🚀 Генератор запущен (куча: " + maxSize + " байт)");
        
        // Поток создания объектов
        executor.submit(() -> {
            while (running) {
                try {
                    createObjects();
                    Thread.sleep(2000); // 2 секунды
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        
        // Поток очистки
        executor.submit(() -> {
            while (running) {
                try {
                    Thread.sleep(3000); // 3 секунды
                    if (running) {
                        cleanup();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }
    
    /**
     * Останавливает генератор
     */
    public void stop() {
        running = false;
        executor.shutdown();
        
        try {
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        
        System.out.println("\n🛑 Генератор остановлен");
        printStats();
    }
    
    /**
     * Создает объекты
     */
    private void createObjects() {
        int count = random.nextInt(2) + 1; // 1-2 объекта
        
        for (int i = 0; i < count; i++) {
            String[] types = {"User", "Doc", "Img", "Data", "Cache"};
            String type = types[random.nextInt(types.length)];
            int size = random.nextInt(100) + 50; // 50-150 байт
            
            GCObject obj = new GCObject(type + "_" + (heap.size() + 1), size);
            
            synchronized (heap) {
                if (currentSize + size <= maxSize) {
                    heap.add(obj);
                    currentSize += size;
                    
                    // 70% объектов становятся корнями
                    if (random.nextDouble() < 0.7) {
                        roots.add(obj);
                        System.out.println("🌱 Корень: " + obj.getName() + " (" + size + " байт)");
                    } else {
                        System.out.println("📦 Объект: " + obj.getName() + " (" + size + " байт)");
                    }
                    
                    // Создаем связи
                    if (!roots.isEmpty() && random.nextDouble() < 0.3) {
                        GCObject parent = roots.get(random.nextInt(roots.size()));
                        parent.addReference(obj);
                        System.out.println("🔗 Связь: " + parent.getName() + " -> " + obj.getName());
                    }
                } else {
                    System.out.println("❌ Нет места для " + obj.getName());
                }
            }
        }
    }
    
    /**
     * Очищает недостижимые объекты
     */
    private void cleanup() {
        synchronized (heap) {
            System.out.println("\n🧹 Очистка...");
            
            // Удаляем некоторые корни
            if (!roots.isEmpty() && random.nextDouble() < 0.4) {
                GCObject root = roots.remove(random.nextInt(roots.size()));
                heap.remove(root);
                currentSize -= root.getSize();
                System.out.println("🗑️ Удален корень: " + root.getName());
            }
            
            // Mark & Sweep GC
            markReachable();
            List<GCObject> toRemove = new ArrayList<>();
            
            for (GCObject obj : heap) {
                if (!obj.isMarked()) {
                    toRemove.add(obj);
                }
            }
            
            for (GCObject obj : toRemove) {
                heap.remove(obj);
                currentSize -= obj.getSize();
            }
            
            System.out.println("✅ Очищено: " + toRemove.size() + " объектов");
            System.out.println("📊 Память: " + currentSize + "/" + maxSize + " байт");
            System.out.println("📊 Объектов: " + heap.size() + " (корней: " + roots.size() + ")");
            System.out.println();
        }
    }
    
    /**
     * Отмечает достижимые объекты
     */
    private void markReachable() {
        // Сбрасываем все метки
        for (GCObject obj : heap) {
            obj.unmark();
        }
        
        // Отмечаем достижимые из корней
        for (GCObject root : roots) {
            markObject(root);
        }
    }
    
    /**
     * Рекурсивно отмечает объект и его ссылки
     */
    private void markObject(GCObject obj) {
        if (obj == null || obj.isMarked()) return;
        
        obj.mark();
        for (GCObject ref : obj.getReferences()) {
            markObject(ref);
        }
    }
    
    /**
     * Выводит статистику
     */
    private void printStats() {
        System.out.println("\n📈 СТАТИСТИКА:");
        System.out.println("===============");
        System.out.println("Объектов в куче: " + heap.size());
        System.out.println("Корневых объектов: " + roots.size());
        System.out.println("Использовано памяти: " + currentSize + "/" + maxSize + " байт");
        
        if (!heap.isEmpty()) {
            System.out.println("\nОставшиеся объекты:");
            for (GCObject obj : heap) {
                System.out.println("  " + obj.getName() + " (" + obj.getSize() + " байт)");
            }
        }
    }
    
    /**
     * Простой объект для демонстрации
     */
    static class GCObject {
        private final String name;
        private final int size;
        private boolean marked;
        private final List<GCObject> references;
        
        public GCObject(String name, int size) {
            this.name = name;
            this.size = size;
            this.marked = false;
            this.references = new ArrayList<>();
        }
        
        public void addReference(GCObject obj) {
            if (obj != null && !references.contains(obj)) {
                references.add(obj);
            }
        }
        
        public List<GCObject> getReferences() {
            return references;
        }
        
        public void mark() { this.marked = true; }
        public void unmark() { this.marked = false; }
        public boolean isMarked() { return marked; }
        public String getName() { return name; }
        public int getSize() { return size; }
    }
    
    /**
     * Демонстрация
     */
    public static void main(String[] args) {
        System.out.println("🏭 ПРОСТОЙ ГЕНЕРАТОР ОБЪЕКТОВ");
        System.out.println("==============================");
        System.out.println("Создание и очистка объектов в рантайме");
        System.out.println();
        
        SimpleGC gc = new SimpleGC(1000); // Куча 1000 байт
        gc.start();
        
        System.out.println("Нажмите Enter для остановки...");
        
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        gc.stop();
    }
}

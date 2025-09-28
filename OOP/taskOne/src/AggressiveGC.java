import java.util.*;
import java.util.concurrent.*;

public class AggressiveGC {
    
    private final List<GCObject> heap;
    private final List<GCObject> roots;
    private final int maxSize;
    private int currentSize;
    private final Random random;
    private final ExecutorService executor;
    private volatile boolean running;
    
    public AggressiveGC(int maxSize) {
        this.maxSize = maxSize;
        this.heap = Collections.synchronizedList(new ArrayList<>());
        this.roots = Collections.synchronizedList(new ArrayList<>());
        this.currentSize = 0;
        this.random = new Random();
        this.executor = Executors.newFixedThreadPool(2);
        this.running = false;
    }
    
    public void start() {
        if (running) return;
        
        running = true;
        System.out.println("🚀 АГРЕССИВНЫЙ ГЕНЕРАТОР запущен (куча: " + maxSize + " байт)");
        
        // Поток создания объектов
        executor.submit(() -> {
            while (running) {
                try {
                    createObjects();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        
        // Поток очистки
        executor.submit(() -> {
            while (running) {
                try {
                    Thread.sleep(3000);
                    if (running) {
                        cleanup();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }
    
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
    
    private void createObjects() {
        int count = 1 + random.nextInt(2); // 1-2 объекта
        
        for (int i = 0; i < count; i++) {
            String[] types = {"User", "Doc", "Img", "Data", "Cache"};
            String type = types[random.nextInt(types.length)];
            int size = random.nextInt(100) + 50;
            
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
    
    private void cleanup() {
        synchronized (heap) {
            System.out.println("\n🧹 АГРЕССИВНАЯ ОЧИСТКА...");
            
            // Очень агрессивное удаление корней (80%)
            if (!roots.isEmpty() && random.nextDouble() < 0.8) {
                GCObject root = roots.remove(random.nextInt(roots.size()));
                heap.remove(root);
                currentSize -= root.getSize();
                System.out.println("🗑️ Удален корень: " + root.getName());
            }
            
            // Принудительное освобождение при заполнении кучи на 80%+
            if (currentSize > maxSize * 0.8 && !roots.isEmpty()) {
                int toRemove = Math.min(5, roots.size()); // Удаляем до 5 корней
                for (int i = 0; i < toRemove; i++) {
                    GCObject root = roots.remove(random.nextInt(roots.size()));
                    heap.remove(root);
                    currentSize -= root.getSize();
                    System.out.println("🔥 ПРИНУДИТЕЛЬНО удален: " + root.getName());
                }
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
    
    private void markReachable() {
        for (GCObject obj : heap) {
            obj.unmark();
        }
        
        for (GCObject root : roots) {
            markObject(root);
        }
    }
    
    private void markObject(GCObject obj) {
        if (obj == null || obj.isMarked()) return;
        
        obj.mark();
        for (GCObject ref : obj.getReferences()) {
            markObject(ref);
        }
    }
    
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
    
    public static void main(String[] args) {
        System.out.println("🔥 АГРЕССИВНЫЙ ГЕНЕРАТОР ОБЪЕКТОВ");
        System.out.println("==================================");
        System.out.println("Создание и АГРЕССИВНАЯ очистка объектов");
        System.out.println();
        
        AggressiveGC gc = new AggressiveGC(1000); // Куча 1000 байт
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

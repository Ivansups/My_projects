import java.util.Collection;

/**
 * Базовый интерфейс для всех алгоритмов сборки мусора
 */
public interface GarbageCollector {
    
    /**
     * Добавляет корневой объект (объект, достижимый извне)
     * @param root корневой объект
     */
    void addRoot(GCObject root);
    
    /**
     * Удаляет корневой объект
     * @param root корневой объект для удаления
     */
    void removeRoot(GCObject root);
    
    /**
     * Запускает сборку мусора
     * @return коллекция собранных (удаленных) объектов
     */
    Collection<GCObject> collect();
    
    /**
     * Возвращает список корневых объектов
     * @return список корневых объектов
     */
    Collection<GCObject> getRoots();
    
    /**
     * Возвращает имя алгоритма сборки мусора
     * @return имя алгоритма
     */
    String getAlgorithmName();
}

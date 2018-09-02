package pool;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomThreadPool implements Executor {
    //Очередь с задачами
    private final Queue<Runnable> workQueue;

    private volatile boolean isRunning = true;

    //Обрабатываемые id
    private static Set<Integer> currentIds = new ConcurrentSkipListSet<>();

    //Lock для ограничения записи в currentIds
    private static Lock lock = new ReentrantLock();

    private String poolName;

    public CustomThreadPool(int poolSize, String poolName) {
        this.poolName = poolName;
        workQueue = new ConcurrentLinkedQueue<>();

        //Заполняем pool
        for (int count = 0; count < poolSize; count++) {
            new Thread(new TaskWorker()).start();
        }
    }

    public Queue<Runnable> getWorkQueue() {
        return workQueue;
    }

    //Добавляем новую задачу в очередь
    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }

    public void shutdown() {
        isRunning = false;
    }

    /**
     * Ограничиваем количество обрабатываемых id
     *
     * @param id
     * @return
     */
    public boolean tryLockByWeight(int id) {
        boolean isSuccess = false;

        //Если такой id уже обрабатывается
        if (currentIds.contains(id)) return false;

        //Захватываем монитор класса
        lock.lock();
        try {
            //Повторная проверка занятости id
            if (!currentIds.contains(id)) {
                currentIds.add(id);
                isSuccess = true;
            }
        } finally {
            lock.unlock();
        }
        return isSuccess;
    }

    /**
     * Исполнитель задач из очереди
     */
    private final class TaskWorker implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                //Получаем задачу
                QueryWorker nextTask = (QueryWorker) workQueue.poll();

                if (nextTask != null) {
                    int id = nextTask.getEvent().getId();

                    //Пытаемся получить право работать с данным id
                    System.out.println(poolName + ": Попытка получить право работы с id " + id + " для задачи " + nextTask.getEvent().getUuid());
                    while (true) {
                        if (tryLockByWeight(id)) break;
                    }
                    System.out.println(poolName + ": Получено право работы с id " + id + " для задачи " + nextTask.getEvent().getUuid());
                    //Если захватили возможность работы с данным id
                    try {
                        nextTask.run();
                    } finally {
                        //Освобождаем возможность повторного использования id
                        currentIds.remove(id);
                    }
                }
            }
        }
    }
}
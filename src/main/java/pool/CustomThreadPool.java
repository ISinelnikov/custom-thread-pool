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

    //Текущие обрабатываемые id
    private static Set<Integer> ids = new ConcurrentSkipListSet<>();
    //Lock для ограничения записи в ids
    private static Lock lock = new ReentrantLock();

    private String poolName;

    public CustomThreadPool(int poolSize, String poolName) {
        this.poolName = poolName;
        workQueue = new ConcurrentLinkedQueue<>();

        //Запускаем pool
        for (int count = 0; count < poolSize; count++) {
            new Thread(new TaskWorker()).start();
        }
    }

    public Queue<Runnable> getWorkQueue() {
        return workQueue;
    }

    //Добавляем новую команду в очередь
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
        if (ids.contains(id)) return false;

        lock.lock();
        try {
            if (!ids.contains(id)) {
                ids.add(id);
                isSuccess = true;
            }
        } finally {
            lock.unlock();
        }
        return isSuccess;
    }

    private final class TaskWorker implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                QueryWorker nextTask = (QueryWorker) workQueue.poll();

                if (nextTask != null) {
                    int id = nextTask.getQuery().getId();

                    //Пытаемся получить право работать с данным id
                    System.out.println(poolName + ": Попытка получить право работы с id " + id + " для задачи " + nextTask.getQuery().getUuid());
                    while (true) {
                        if (tryLockByWeight(id)) break;
                    }
                    System.out.println(poolName + ": Получено право работы с id " + id + " для задачи " + nextTask.getQuery().getUuid());
                    //Если захватили возможность работы с данным id
                    try {
                        nextTask.run();
                    } finally {
                        //Освобождаем возможность повторного использования id
                        ids.remove(id);
                    }
                }
            }
        }
    }
}
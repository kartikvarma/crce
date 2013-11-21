package cz.zcu.kiv.crce.concurrency.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zcu.kiv.crce.concurrency.model.Task;


/**
 * TaskRunner class using FixedThreadPool to control crce's thread resources.
 *
 *
 * Date: 11.11.13
 *
 * @author Jakub Danek
 */
public class TaskRunner {
    /*
            STATIC CODE
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private static TaskRunner instance = null;

    /**
     *
     * TaskRunner.init(threadCount) must be run before the instance can be recovered. If not, TaskRunner initializes
     * to default thread count.
     *
     * @return singleton instance of TaskRunner
     */
    public static TaskRunner get() {
        if(instance == null) {
            instance = new TaskRunner();
        }
        return instance;
    }

    public static void init(int maxThreads) {
        instance = new TaskRunner(maxThreads);
    }

    /*
            MEMBER CODE
     */

    private ExecutorService executor;
    private final int maxThreads;

    /**
     * Initializes TaskRunner to default configuration.
     */
    private TaskRunner() {
        //TODO change this to configuration
        this(5);
    }

    /**
     *
     * @param maxThreads maximum number of threads the pool will be able to spawn
     */
    private TaskRunner(int maxThreads) {
        executor = Executors.newFixedThreadPool(maxThreads);
        this.maxThreads = maxThreads;
    }

    /**
     *
     * @return maximum number of allowed threads for this TaskRunner
     */
    public int getMaxThreads() {
        return maxThreads;
    }

    /**
     * Attempts to stop the TaskRunner.
     *
     * First waits for running and scheduled tasks to finish, while not accepting any other tasks. In case some of the
     * running tasks wont finish in time, attempts to interrupt them.
     */
    public void stop() {
        try {
            logger.info("Shutting down, waiting for scheduled tasks to finish...");
            this.executor.shutdown();
            boolean shutdown = this.executor.awaitTermination(10, TimeUnit.SECONDS);

            if(!shutdown) {
                logger.warn("All tasks did not finish in time. Interrupting...");
                int number = this.executor.shutdownNow().size();
                shutdown = this.executor.awaitTermination(2, TimeUnit.SECONDS);
                if(number > 0) {
                    logger.warn("{} tasks were scheduled but never run.", number);
                }
                if(!shutdown) {
                    logger.warn("Failed to interrupt all tasks before quitting.");
                }
            }

        } catch (InterruptedException e) {
            logger.warn("TaskRunner thread interrupted before it was able to shutdown all running tasks.");
        }

        logger.info("TaskRunner stopped.");
    }

    /**
     * Schedules new task to be run
     * @param task task to be scheduled
     * @return Future reference which can be used to gain results after the Task has finished
     */
    public synchronized Future scheduleTask(Task task) {
        logger.info("Task {} called by {} has been scheduled.", task.getId(), task.getCaller());
        return this.executor.submit(task);
    }
}

package net.spehl.jpa.techtalk;

import static java.lang.Math.max;

import com.google.common.util.concurrent.Uninterruptibles;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import net.spehl.jpa.techtalk.model.Transaction;
import net.spehl.jpa.techtalk.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class WriteRunner implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WriteRunner.class);

    Thread thread;
    Random random = new Random();
    int writeIntervalMs = 250;
    AtomicBoolean stopNow = new AtomicBoolean(false);

    TransactionRepository repository;

    public WriteRunner(TransactionRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void setup() {
        logger.info("running");
        thread = new Thread(this);
        thread.run();
    }


    @PreDestroy
    public void shutdown() throws InterruptedException {
        logger.info("got predestroy");
        stopNow.set(true);
        thread.join();
        logger.info("stopped");
    }

    public void run() {
        long nextStartTime = System.currentTimeMillis() + writeIntervalMs;
        while (!stopNow.get()) {
            long startTime = System.currentTimeMillis();

            try {
                performCreate();
            } catch (Exception e) {
                logger.info("write failed with, {}, {}",
                            e.getClass().getName(),
                            e.getMessage());
            }

            long duration = System.currentTimeMillis() - startTime;
            Uninterruptibles.sleepUninterruptibly(max(0, nextStartTime - System.currentTimeMillis()),
                                                  TimeUnit.MILLISECONDS);
            logger.info("write took {}ms", duration);
            nextStartTime += writeIntervalMs;

        }
        logger.info("stopped");
    }

    @Transactional
    void performCreate() {
        Transaction transaction = Transaction.newBuilder()
                .setDifference(random.nextDouble() * 1000)
                .setName(Helpers.weightedChooseOneOf("name_a", "name_b", "name_c")
                                 .apply(new Integer[]{7,2,1}).get())
                .build();
        repository.save(transaction);
    }

}

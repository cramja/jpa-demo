
package net.spehl.jpa.techtalk;

import static java.lang.Math.max;

import com.google.common.util.concurrent.Uninterruptibles;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import net.spehl.jpa.techtalk.repo.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class ReadRunner implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ReadRunner.class);

    Thread thread;
    Random random = new Random();
    int writeIntervalMs = 250;
    AtomicBoolean stopNow = new AtomicBoolean(false);

    TransactionRepository repository;

    public ReadRunner(TransactionRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void setup() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        logger.info("got predestroy");
        stopNow.set(true);
        thread.join();
        logger.info("joined");
    }

    public void run() {
        logger.info("running");
        long nextStartTime = System.currentTimeMillis() + writeIntervalMs;
        while (!stopNow.get()) {
            long startTime = System.currentTimeMillis();

            try {
                performRead();
            } catch (Exception e) {
                logger.info("read failed with, {}, {}",
                            e.getClass().getName(),
                            e.getMessage());
            }

            long duration = System.currentTimeMillis() - startTime;
            Uninterruptibles.sleepUninterruptibly(max(0, nextStartTime - System.currentTimeMillis()),
                                                  TimeUnit.MILLISECONDS);
            logger.info("read took {}ms", duration);
            nextStartTime += writeIntervalMs;

        }
        logger.info("stopped");
    }

    @Transactional
    void performRead() {
        String name = Helpers.chooseOneOf("name_a", "name_b", "name_c").get();
        logger.info("{} of {}", repository.countByName(name), name);
    }
}

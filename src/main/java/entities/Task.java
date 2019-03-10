package entities;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
public class Task implements Runnable {
    private ConcurrentHashMap.Entry<String, List<String>> result = null;
    int count = 0;

    public Task(ConcurrentHashMap.Entry<String, List<String>> result, int count) {
        this.result = result;
        this.count = count;
    }

    @Override
    public void run() {
        Saver saver = new Saver();
        try {
            saver.save(result, count);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounterWithoutLock {
    private final AtomicInteger counter = new AtomicInteger(0);

    int getValue() {
        return counter.get();
    }

    void increment() {
        counter.incrementAndGet();
    }

    public static void main(String[] args)
    {
        SafeCounterWithoutLock sf = new SafeCounterWithoutLock();
        System.out.println(sf.getValue());
        sf.increment();
        System.out.println(sf.getValue());

    }
}


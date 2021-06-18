//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, Serializable {
    private static final long serialVersionUID = -6903933977591709194L;
    private final int capacity;
    private final AtomicInteger count;
    transient LinkedBlockingQueue.Node<E> head;
    private transient LinkedBlockingQueue.Node<E> last;
    private final ReentrantLock takeLock;
    private final Condition notEmpty;
    private final ReentrantLock putLock;
    private final Condition notFull;

    private void signalNotEmpty() {
        ReentrantLock takeLock = this.takeLock;
        takeLock.lock();

        try {
            this.notEmpty.signal();
        } finally {
            takeLock.unlock();
        }

    }

    private void signalNotFull() {
        ReentrantLock putLock = this.putLock;
        putLock.lock();

        try {
            this.notFull.signal();
        } finally {
            putLock.unlock();
        }

    }

    private void enqueue(LinkedBlockingQueue.Node<E> node) {
        this.last = this.last.next = node;
    }

    private E dequeue() {
        LinkedBlockingQueue.Node<E> h = this.head;
        LinkedBlockingQueue.Node<E> first = h.next;
        h.next = h;
        this.head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    void fullyLock() {
        this.putLock.lock();
        this.takeLock.lock();
    }

    void fullyUnlock() {
        this.takeLock.unlock();
        this.putLock.unlock();
    }

    public LinkedBlockingQueue() {
        this(2147483647);
    }

    public LinkedBlockingQueue(int capacity) {
        this.count = new AtomicInteger();
        this.takeLock = new ReentrantLock();
        this.notEmpty = this.takeLock.newCondition();
        this.putLock = new ReentrantLock();
        this.notFull = this.putLock.newCondition();
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.capacity = capacity;
            this.last = this.head = new LinkedBlockingQueue.Node((Object)null);
        }
    }

    public LinkedBlockingQueue(Collection<? extends E> c) {
        this(2147483647);
        ReentrantLock putLock = this.putLock;
        putLock.lock();

        try {
            int n = 0;

            for(Iterator var4 = c.iterator(); var4.hasNext(); ++n) {
                E e = var4.next();
                if (e == null) {
                    throw new NullPointerException();
                }

                if (n == this.capacity) {
                    throw new IllegalStateException("Queue full");
                }

                this.enqueue(new LinkedBlockingQueue.Node(e));
            }

            this.count.set(n);
        } finally {
            putLock.unlock();
        }
    }

    public int size() {
        return this.count.get();
    }

    public int remainingCapacity() {
        return this.capacity - this.count.get();
    }

    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            LinkedBlockingQueue.Node<E> node = new LinkedBlockingQueue.Node(e);
            ReentrantLock putLock = this.putLock;
            AtomicInteger count = this.count;
            putLock.lockInterruptibly();

            int c;
            try {
                while(count.get() == this.capacity) {
                    this.notFull.await();
                }

                this.enqueue(node);
                c = count.getAndIncrement();
                if (c + 1 < this.capacity) {
                    this.notFull.signal();
                }
            } finally {
                putLock.unlock();
            }

            if (c == 0) {
                this.signalNotEmpty();
            }

        }
    }

    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        } else {
            long nanos = unit.toNanos(timeout);
            ReentrantLock putLock = this.putLock;
            AtomicInteger count = this.count;
            putLock.lockInterruptibly();

            int c;
            try {
                while(count.get() == this.capacity) {
                    if (nanos <= 0L) {
                        boolean var10 = false;
                        return var10;
                    }

                    nanos = this.notFull.awaitNanos(nanos);
                }

                this.enqueue(new LinkedBlockingQueue.Node(e));
                c = count.getAndIncrement();
                if (c + 1 < this.capacity) {
                    this.notFull.signal();
                }
            } finally {
                putLock.unlock();
            }

            if (c == 0) {
                this.signalNotEmpty();
            }

            return true;
        }
    }

    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else {
            AtomicInteger count = this.count;
            if (count.get() == this.capacity) {
                return false;
            } else {
                LinkedBlockingQueue.Node<E> node = new LinkedBlockingQueue.Node(e);
                ReentrantLock putLock = this.putLock;
                putLock.lock();

                int c;
                try {
                    if (count.get() == this.capacity) {
                        boolean var6 = false;
                        return var6;
                    }

                    this.enqueue(node);
                    c = count.getAndIncrement();
                    if (c + 1 < this.capacity) {
                        this.notFull.signal();
                    }
                } finally {
                    putLock.unlock();
                }

                if (c == 0) {
                    this.signalNotEmpty();
                }

                return true;
            }
        }
    }

    public E take() throws InterruptedException {
        AtomicInteger count = this.count;
        ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();

        Object x;
        int c;
        try {
            while(count.get() == 0) {
                this.notEmpty.await();
            }

            x = this.dequeue();
            c = count.getAndDecrement();
            if (c > 1) {
                this.notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }

        if (c == this.capacity) {
            this.signalNotFull();
        }

        return x;
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        AtomicInteger count = this.count;
        ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();

        Object x;
        int c;
        try {
            while(true) {
                if (count.get() != 0) {
                    x = this.dequeue();
                    c = count.getAndDecrement();
                    if (c > 1) {
                        this.notEmpty.signal();
                    }
                    break;
                }

                if (nanos <= 0L) {
                    Object var10 = null;
                    return var10;
                }

                nanos = this.notEmpty.awaitNanos(nanos);
            }
        } finally {
            takeLock.unlock();
        }

        if (c == this.capacity) {
            this.signalNotFull();
        }

        return x;
    }

    public E poll() {
        AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        } else {
            ReentrantLock takeLock = this.takeLock;
            takeLock.lock();

            Object x;
            int c;
            try {
                if (count.get() == 0) {
                    Object var5 = null;
                    return var5;
                }

                x = this.dequeue();
                c = count.getAndDecrement();
                if (c > 1) {
                    this.notEmpty.signal();
                }
            } finally {
                takeLock.unlock();
            }

            if (c == this.capacity) {
                this.signalNotFull();
            }

            return x;
        }
    }

    public E peek() {
        AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        } else {
            ReentrantLock takeLock = this.takeLock;
            takeLock.lock();

            Object var3;
            try {
                var3 = count.get() > 0 ? this.head.next.item : null;
            } finally {
                takeLock.unlock();
            }

            return var3;
        }
    }

    void unlink(LinkedBlockingQueue.Node<E> p, LinkedBlockingQueue.Node<E> pred) {
        p.item = null;
        pred.next = p.next;
        if (this.last == p) {
            this.last = pred;
        }

        if (this.count.getAndDecrement() == this.capacity) {
            this.notFull.signal();
        }

    }

    public boolean remove(Object o) {
        if (o == null) {
            return false;
        } else {
            this.fullyLock();

            boolean var8;
            try {
                LinkedBlockingQueue.Node<E> pred = this.head;

                for(LinkedBlockingQueue.Node p = pred.next; p != null; p = p.next) {
                    if (o.equals(p.item)) {
                        this.unlink(p, pred);
                        boolean var4 = true;
                        return var4;
                    }

                    pred = p;
                }

                var8 = false;
            } finally {
                this.fullyUnlock();
            }

            return var8;
        }
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        } else {
            this.fullyLock();

            try {
                for(LinkedBlockingQueue.Node p = this.head.next; p != null; p = p.next) {
                    if (o.equals(p.item)) {
                        boolean var3 = true;
                        return var3;
                    }
                }

                boolean var7 = false;
                return var7;
            } finally {
                this.fullyUnlock();
            }
        }
    }

    public Object[] toArray() {
        this.fullyLock();

        try {
            int size = this.count.get();
            Object[] a = new Object[size];
            int k = 0;

            for(LinkedBlockingQueue.Node p = this.head.next; p != null; p = p.next) {
                a[k++] = p.item;
            }

            Object[] var8 = a;
            return var8;
        } finally {
            this.fullyUnlock();
        }
    }

    public <T> T[] toArray(T[] a) {
        this.fullyLock();

        Object[] var8;
        try {
            int size = this.count.get();
            if (a.length < size) {
                a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
            }

            int k = 0;

            for(LinkedBlockingQueue.Node p = this.head.next; p != null; p = p.next) {
                a[k++] = p.item;
            }

            if (a.length > k) {
                a[k] = null;
            }

            var8 = a;
        } finally {
            this.fullyUnlock();
        }

        return var8;
    }

    public String toString() {
        return Helpers.collectionToString(this);
    }

    public void clear() {
        this.fullyLock();

        try {
            LinkedBlockingQueue.Node h = this.head;

            while(true) {
                LinkedBlockingQueue.Node p;
                if ((p = h.next) == null) {
                    this.head = this.last;
                    if (this.count.getAndSet(0) == this.capacity) {
                        this.notFull.signal();
                    }
                    break;
                }

                h.next = h;
                p.item = null;
                h = p;
            }
        } finally {
            this.fullyUnlock();
        }

    }

    public int drainTo(Collection<? super E> c) {
        return this.drainTo(c, 2147483647);
    }

    public int drainTo(Collection<? super E> c, int maxElements) {
        Objects.requireNonNull(c);
        if (c == this) {
            throw new IllegalArgumentException();
        } else if (maxElements <= 0) {
            return 0;
        } else {
            boolean signalNotFull = false;
            ReentrantLock takeLock = this.takeLock;
            takeLock.lock();

            try {
                int n = Math.min(maxElements, this.count.get());
                LinkedBlockingQueue.Node<E> h = this.head;
                int i = 0;

                try {
                    while(i < n) {
                        LinkedBlockingQueue.Node<E> p = h.next;
                        c.add(p.item);
                        p.item = null;
                        h.next = h;
                        h = p;
                        ++i;
                    }

                    int var17 = n;
                    return var17;
                } finally {
                    if (i > 0) {
                        this.head = h;
                        signalNotFull = this.count.getAndAdd(-i) == this.capacity;
                    }

                }
            } finally {
                takeLock.unlock();
                if (signalNotFull) {
                    this.signalNotFull();
                }

            }
        }
    }

    LinkedBlockingQueue.Node<E> succ(LinkedBlockingQueue.Node<E> p) {
        if (p == (p = p.next)) {
            p = this.head.next;
        }

        return p;
    }

    public Iterator<E> iterator() {
        return new LinkedBlockingQueue.Itr();
    }

    public Spliterator<E> spliterator() {
        return new LinkedBlockingQueue.LBQSpliterator();
    }

    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        this.forEachFrom(action, (LinkedBlockingQueue.Node)null);
    }

    void forEachFrom(Consumer<? super E> action, LinkedBlockingQueue.Node<E> p) {
        int batchSize = true;
        Object[] es = null;
        int len = 0;

        int n;
        do {
            this.fullyLock();

            try {
                if (es == null) {
                    if (p == null) {
                        p = this.head.next;
                    }

                    for(LinkedBlockingQueue.Node q = p; q != null; q = this.succ(q)) {
                        if (q.item != null) {
                            ++len;
                            if (len == 64) {
                                break;
                            }
                        }
                    }

                    es = new Object[len];
                }

                for(n = 0; p != null && n < len; p = this.succ(p)) {
                    if ((es[n] = p.item) != null) {
                        ++n;
                    }
                }
            } finally {
                this.fullyUnlock();
            }

            for(int i = 0; i < n; ++i) {
                E e = es[i];
                action.accept(e);
            }
        } while(n > 0 && p != null);

    }

    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        return this.bulkRemove(filter);
    }

    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return this.bulkRemove((e) -> {
            return c.contains(e);
        });
    }

    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return this.bulkRemove((e) -> {
            return !c.contains(e);
        });
    }

    LinkedBlockingQueue.Node<E> findPred(LinkedBlockingQueue.Node<E> p, LinkedBlockingQueue.Node<E> ancestor) {
        if (ancestor.item == null) {
            ancestor = this.head;
        }

        LinkedBlockingQueue.Node q;
        while((q = ancestor.next) != p) {
            ancestor = q;
        }

        return ancestor;
    }

    private boolean bulkRemove(Predicate<? super E> filter) {
        boolean removed = false;
        LinkedBlockingQueue.Node<E> p = null;
        LinkedBlockingQueue.Node<E> ancestor = this.head;
        LinkedBlockingQueue.Node<E>[] nodes = null;
        int len = 0;

        int n;
        do {
            this.fullyLock();

            try {
                if (nodes == null) {
                    p = this.head.next;

                    for(LinkedBlockingQueue.Node q = p; q != null; q = this.succ(q)) {
                        if (q.item != null) {
                            ++len;
                            if (len == 64) {
                                break;
                            }
                        }
                    }

                    nodes = new LinkedBlockingQueue.Node[len];
                }

                for(n = 0; p != null && n < len; p = this.succ(p)) {
                    nodes[n++] = p;
                }
            } finally {
                this.fullyUnlock();
            }

            long deathRow = 0L;

            int i;
            for(i = 0; i < n; ++i) {
                Object e;
                if ((e = nodes[i].item) != null && filter.test(e)) {
                    deathRow |= 1L << i;
                }
            }

            if (deathRow != 0L) {
                this.fullyLock();

                try {
                    for(i = 0; i < n; ++i) {
                        LinkedBlockingQueue.Node q;
                        if ((deathRow & 1L << i) != 0L && (q = nodes[i]).item != null) {
                            ancestor = this.findPred(q, ancestor);
                            this.unlink(q, ancestor);
                            removed = true;
                        }

                        nodes[i] = null;
                    }
                } finally {
                    this.fullyUnlock();
                }
            }
        } while(n > 0 && p != null);

        return removed;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        this.fullyLock();

        try {
            s.defaultWriteObject();

            for(LinkedBlockingQueue.Node p = this.head.next; p != null; p = p.next) {
                s.writeObject(p.item);
            }

            s.writeObject((Object)null);
        } finally {
            this.fullyUnlock();
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.count.set(0);
        this.last = this.head = new LinkedBlockingQueue.Node((Object)null);

        while(true) {
            E item = s.readObject();
            if (item == null) {
                return;
            }

            this.add(item);
        }
    }

    private final class LBQSpliterator implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        LinkedBlockingQueue.Node<E> current;
        int batch;
        boolean exhausted;
        long est = (long)LinkedBlockingQueue.this.size();

        LBQSpliterator() {
        }

        public long estimateSize() {
            return this.est;
        }

        public Spliterator<E> trySplit() {
            LinkedBlockingQueue.Node h;
            if (!this.exhausted && ((h = this.current) != null || (h = LinkedBlockingQueue.this.head.next) != null) && h.next != null) {
                int n = this.batch = Math.min(this.batch + 1, 33554432);
                Object[] a = new Object[n];
                int i = 0;
                LinkedBlockingQueue.Node<E> p = this.current;
                LinkedBlockingQueue.this.fullyLock();

                try {
                    if (p != null || (p = LinkedBlockingQueue.this.head.next) != null) {
                        for(; p != null && i < n; p = LinkedBlockingQueue.this.succ(p)) {
                            if ((a[i] = p.item) != null) {
                                ++i;
                            }
                        }
                    }
                } finally {
                    LinkedBlockingQueue.this.fullyUnlock();
                }

                if ((this.current = p) == null) {
                    this.est = 0L;
                    this.exhausted = true;
                } else if ((this.est -= (long)i) < 0L) {
                    this.est = 0L;
                }

                if (i > 0) {
                    return Spliterators.spliterator(a, 0, i, 4368);
                }
            }

            return null;
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            if (!this.exhausted) {
                E e = null;
                LinkedBlockingQueue.this.fullyLock();

                try {
                    LinkedBlockingQueue.Node p;
                    if ((p = this.current) != null || (p = LinkedBlockingQueue.this.head.next) != null) {
                        do {
                            e = p.item;
                            p = LinkedBlockingQueue.this.succ(p);
                        } while(e == null && p != null);
                    }

                    if ((this.current = p) == null) {
                        this.exhausted = true;
                    }
                } finally {
                    LinkedBlockingQueue.this.fullyUnlock();
                }

                if (e != null) {
                    action.accept(e);
                    return true;
                }
            }

            return false;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            if (!this.exhausted) {
                this.exhausted = true;
                LinkedBlockingQueue.Node<E> p = this.current;
                this.current = null;
                LinkedBlockingQueue.this.forEachFrom(action, p);
            }

        }

        public int characteristics() {
            return 4368;
        }
    }

    private class Itr implements Iterator<E> {
        private LinkedBlockingQueue.Node<E> next;
        private E nextItem;
        private LinkedBlockingQueue.Node<E> lastRet;
        private LinkedBlockingQueue.Node<E> ancestor;

        Itr() {
            LinkedBlockingQueue.this.fullyLock();

            try {
                if ((this.next = LinkedBlockingQueue.this.head.next) != null) {
                    this.nextItem = this.next.item;
                }
            } finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }

        }

        public boolean hasNext() {
            return this.next != null;
        }

        public E next() {
            LinkedBlockingQueue.Node p;
            if ((p = this.next) == null) {
                throw new NoSuchElementException();
            } else {
                this.lastRet = p;
                E x = this.nextItem;
                LinkedBlockingQueue.this.fullyLock();

                try {
                    E e = null;

                    for(p = p.next; p != null && (e = p.item) == null; p = LinkedBlockingQueue.this.succ(p)) {
                    }

                    this.next = p;
                    this.nextItem = e;
                    return x;
                } finally {
                    LinkedBlockingQueue.this.fullyUnlock();
                }
            }
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            LinkedBlockingQueue.Node p;
            if ((p = this.next) != null) {
                this.lastRet = p;
                this.next = null;
                int batchSize = true;
                Object[] es = null;
                int len = 1;

                int n;
                do {
                    LinkedBlockingQueue.this.fullyLock();

                    try {
                        if (es == null) {
                            p = p.next;

                            for(LinkedBlockingQueue.Node q = p; q != null; q = LinkedBlockingQueue.this.succ(q)) {
                                if (q.item != null) {
                                    ++len;
                                    if (len == 64) {
                                        break;
                                    }
                                }
                            }

                            es = new Object[len];
                            es[0] = this.nextItem;
                            this.nextItem = null;
                            n = 1;
                        } else {
                            n = 0;
                        }

                        for(; p != null && n < len; p = LinkedBlockingQueue.this.succ(p)) {
                            if ((es[n] = p.item) != null) {
                                this.lastRet = p;
                                ++n;
                            }
                        }
                    } finally {
                        LinkedBlockingQueue.this.fullyUnlock();
                    }

                    for(int i = 0; i < n; ++i) {
                        E e = es[i];
                        action.accept(e);
                    }
                } while(n > 0 && p != null);

            }
        }

        public void remove() {
            LinkedBlockingQueue.Node<E> p = this.lastRet;
            if (p == null) {
                throw new IllegalStateException();
            } else {
                this.lastRet = null;
                LinkedBlockingQueue.this.fullyLock();

                try {
                    if (p.item != null) {
                        if (this.ancestor == null) {
                            this.ancestor = LinkedBlockingQueue.this.head;
                        }

                        this.ancestor = LinkedBlockingQueue.this.findPred(p, this.ancestor);
                        LinkedBlockingQueue.this.unlink(p, this.ancestor);
                    }
                } finally {
                    LinkedBlockingQueue.this.fullyUnlock();
                }

            }
        }
    }

    static class Node<E> {
        E item;
        LinkedBlockingQueue.Node<E> next;

        Node(E x) {
            this.item = x;
        }
    }
}

package 无锁状态和死锁状态;

import java.util.AbstractList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * It is a thread safe and lock-free vector.
 * This class implement algorithm from:<br>
 *
 * Lock-free Dynamically Resizable Arrays <br>
 *
 * Damian Dechev, Peter Pirkelbauer, and Bjarne Stroustrup<br>
 * Texas A&M University College Station, TX 77843-3112<br>
 * {dechev, peter.pirkelbauer}@tamu.edu, bs@cs.tamu.edu
 *
 *
 * @author Zhi Gan
 *
 * @param <E> type of element in the vector
 *
 */
public class LockFreeVector<E> extends AbstractList<E> {
    private static final boolean debug = false;
    /**
     * Size of the first bucket. sizeof(bucket[i+1])=2*sizeof(bucket[i])
     */
    private static final int FIRST_BUCKET_SIZE = 8;

    /**
     * number of buckets. 30 will allow 8*(2^30-1) elements
     */
    private static final int N_BUCKET = 30;

    /**
     * We will have at most N_BUCKET number of buckets. And we have
     * sizeof(buckets.get(i))=FIRST_BUCKET_SIZE**(i+1)
     */
    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;

    /**
     * @author ganzhi
     *
     * @param <E>
     */
    static class WriteDescriptor<E> {
        public E oldV;
        public E newV;
        public AtomicReferenceArray<E> addr;
        public int addr_ind;

        /**
         * Creating a new descriptor.
         *
         * @param addr Operation address
         * @param addr_ind	Index of address
         * @param oldV old operand
         * @param newV new operand
         */
        public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind,
                               E oldV, E newV) {
            this.addr = addr;
            this.addr_ind = addr_ind;
            this.oldV = oldV;
            this.newV = newV;
        }

        /**
         * set newV.
         */
        public void doIt() {
            addr.compareAndSet(addr_ind, oldV, newV);
        }
    }

    /**
     * @author ganzhi
     *
     * @param <E>
     */
    static class Descriptor<E> {
        public int size;
        volatile WriteDescriptor<E> writeop;

        /**
         * Create a new descriptor.
         *
         * @param size Size of the vector
         * @param writeop Executor write operation
         */
        public Descriptor(int size, WriteDescriptor<E> writeop) {
            this.size = size;
            this.writeop = writeop;
        }

        /**
         *
         */
        public void completeWrite() {
            WriteDescriptor<E> tmpOp = writeop;
            if (tmpOp != null) {
                tmpOp.doIt();
                writeop = null; // this is safe since all write to writeop use
                // null as r_value.
            }
        }
    }

    private AtomicReference<Descriptor<E>> descriptor;
    private static final int zeroNumFirst = Integer
            .numberOfLeadingZeros(FIRST_BUCKET_SIZE);;

    /**
     * Constructor.
     */
    public LockFreeVector() {
        buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
        buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
        descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,
                null));
    }

    /**
     * add e at the end of vector.
     *
     * @param e
     *            element added
     */
    public void push_back(E e) {
        Descriptor<E> desc;
        Descriptor<E> newd;
        do {
            desc = descriptor.get();
            desc.completeWrite();
            //desc.size   Vector 本身的大小
            //FIRST_BUCKET_SIZE  第一个一位数组的大小
            int pos = desc.size + FIRST_BUCKET_SIZE;
            int zeroNumPos = Integer.numberOfLeadingZeros(pos);  // 取出pos 的前导领
            //zeroNumFirst  为FIRST_BUCKET_SIZE 的前导领
            int bucketInd = zeroNumFirst - zeroNumPos;  //哪个一位数组
            //判断这个一维数组是否已经启用
            if (buckets.get(bucketInd) == null) {
                //newLen  一维数组的长度
                int newLen = 2 * buckets.get(bucketInd - 1).length();
                if (debug) {
                    System.out.println("New Length is:" + newLen);
                }
                buckets.compareAndSet(bucketInd, null,
                        new AtomicReferenceArray<E>(newLen));
            }

            int idx = (0x80000000>>>zeroNumPos) ^ pos;   //在这个一位数组中，我在哪个位置
            newd = new Descriptor<E>(desc.size + 1, new WriteDescriptor<E>(
                    buckets.get(bucketInd), idx, null, e));
        } while (!descriptor.compareAndSet(desc, newd));
        descriptor.get().completeWrite();
    }

    /**
     * Remove the last element in the vector.
     *
     * @return element removed
     */
    public E pop_back() {
        Descriptor<E> desc;
        Descriptor<E> newd;
        E elem;
        do {
            desc = descriptor.get();
            desc.completeWrite();

            int pos = desc.size + FIRST_BUCKET_SIZE - 1;
            int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                    - Integer.numberOfLeadingZeros(pos);
            int idx = Integer.highestOneBit(pos) ^ pos;
            elem = buckets.get(bucketInd).get(idx);
            newd = new Descriptor<E>(desc.size - 1, null);
        } while (!descriptor.compareAndSet(desc, newd));

        return elem;
    }

    /**
     * Get element with the index.
     *
     * @param index
     *            index
     * @return element with the index
     */
    @Override
    public E get(int index) {
        int pos = index + FIRST_BUCKET_SIZE;
        int zeroNumPos = Integer.numberOfLeadingZeros(pos);
        int bucketInd = zeroNumFirst - zeroNumPos;
        int idx = (0x80000000>>>zeroNumPos) ^ pos;
        return buckets.get(bucketInd).get(idx);
    }

    /**
     * Set the element with index to e.
     *
     * @param index
     *            index of element to be reset
     * @param e
     *            element to set
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E e) {
        int pos = index + FIRST_BUCKET_SIZE;
        int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                - Integer.numberOfLeadingZeros(pos);
        int idx = Integer.highestOneBit(pos) ^ pos;
        AtomicReferenceArray<E> bucket = buckets.get(bucketInd);
        while (true) {
            E oldV = bucket.get(idx);
            if (bucket.compareAndSet(idx, oldV, e)) {
                return oldV;
            }
        }
    }

    /**
     * reserve more space.
     *
     * @param newSize
     *            new size be reserved
     */
    public void reserve(int newSize) {
        int size = descriptor.get().size;
        int pos = size + FIRST_BUCKET_SIZE - 1;
        int i = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                - Integer.numberOfLeadingZeros(pos);
        if (i < 1) {
            i = 1;
        }

        int initialSize = buckets.get(i - 1).length();
        while (i < Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
                - Integer.numberOfLeadingZeros(newSize + FIRST_BUCKET_SIZE - 1)) {
            i++;
            initialSize *= FIRST_BUCKET_SIZE;
            buckets.compareAndSet(i, null, new AtomicReferenceArray<E>(
                    initialSize));
        }
    }

    /**
     * size of vector.
     *
     * @return size of vector
     */
    @Override
    public int size() {
        return descriptor.get().size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E object) {
        push_back(object);
        return true;
    }
}
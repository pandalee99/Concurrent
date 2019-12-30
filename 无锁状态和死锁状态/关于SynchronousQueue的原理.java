package 无锁状态和死锁状态;

import java.util.concurrent.SynchronousQueue;

public class 关于SynchronousQueue的原理 {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue s=new SynchronousQueue();
        s.put(1);
        s.take();

    }
}
//for (;;) {
//                SNode h = head;
//                if (h == null || h.mode == mode) {  // empty or same-mode
//                    if (timed && nanos <= 0) {      // can't wait
//                        if (h != null && h.isCancelled())
//                            casHead(h, h.next);     // pop cancelled node
//                        else
//                            return null;
//                    } else if (casHead(h, s = snode(s, e, h, mode))) {
//                        SNode m = awaitFulfill(s, timed, nanos);
//                        if (m == s) {               // wait was cancelled
//                            clean(s);
//                            return null;
//                        }
//                        if ((h = head) != null && h.next == s)
//                            casHead(h, s.next);     // help s's fulfiller
//                        return (mode == REQUEST) ? m.item : s.item;
//                    }
//                } else if (!isFulfilling(h.mode)) { // try to fulfill
//                    if (h.isCancelled())            // already cancelled
//                        casHead(h, h.next);         // pop and retry
//                    else if (casHead(h, s=snode(s, e, h, FULFILLING|mode))) {
//                        for (;;) { // loop until matched or waiters disappear
//                            SNode m = s.next;       // m is s's match
//                            if (m == null) {        // all waiters are gone
//                                casHead(s, null);   // pop fulfill node
//                                s = null;           // use new node next time
//                                break;              // restart main loop
//                            }
//                            SNode mn = m.next;
//                            if (m.tryMatch(s)) {
//                                casHead(s, mn);     // pop both s and m
//                                return (mode == REQUEST) ? m.item : s.item;
//                            } else                  // lost match
//                                s.casNext(m, mn);   // help unlink
//                        }
//                    }
//                } else {                            // help a fulfiller
//                    SNode m = h.next;               // m is h's match
//                    if (m == null)                  // waiter is gone
//                        casHead(h, null);           // pop fulfilling node
//                    else {
//                        SNode mn = m.next;
//                        if (m.tryMatch(h))          // help match
//                            casHead(h, mn);         // pop both h and m
//                        else                        // lost match
//                            h.casNext(m, mn);       // help unlink
//                    }
//                }
//            }
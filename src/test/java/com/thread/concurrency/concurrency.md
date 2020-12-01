
## AbstractQueuedSynchronizer 同步器

### 关键词介绍

#### 1 CLH队列 -- Craig, Landin, and Hagersten lock queue

    CLH队列是AQS中“等待锁”的线程队列。在多线程中，为了保护竞争资源不被多个线程同时操作而起来错误，我们常常需要通过锁来保护这些资源。
    在独占锁中，竞争资源在一个时间点只能被一个线程锁访问；而其它线程则需要等待。CLH就是管理这些“等待锁”的线程的队列。    
    CLH是一个非阻塞的 FIFO 队列。也就是说往里面插入或移除一个节点的时候，在并发条件下不会阻塞，而是通过自旋锁和 CAS 保证节点插入和移除的原子性。

#### 2 CAS函数 -- Compare And Swap 

    CAS函数，是比较并交换函数，它是原子操作函数；即，通过CAS操作的数据都是以原子方式进行的。
    例如，compareAndSetHead(), compareAndSetTail(), compareAndSetNext()等函数。
    它们共同的特点是，这些函数所执行的动作是以原子的方式进行的。

```java
    /* <p>To enqueue into a CLH lock, you atomically splice it in as new
     * tail. To dequeue, you just set the head field.
     * <pre>
     *      +------+  prev +-----+       +-----+
     * head |      | <---- |     | <---- |     |  tail
     *      +------+       +-----+       +-----+
     * </pre>
     */
```

### 线程获取锁过程
下列步骤中线程A和B进行竞争。

1. 线程A执行CAS执行成功，state值被修改并返回true，线程A继续执行。
2. 线程A执行CAS指令失败，说明线程B也在执行CAS指令且成功，这种情况下线程A会执行步骤3。
3. 生成新Node节点node，并通过CAS指令插入到等待队列的队尾（同一时刻可能会有多个Node节点插入到等待队列中），如果tail节点为空，则将head节点指向一个空节点（代表线程B）
4. node插入到队尾后，该线程不会立马挂起，会进行自旋操作。因为在node的插入过程，线程B（即之前没有阻塞的线程）可能已经执行完成，所以要判断该node的前一个节点pred是否为head节点（代表线程B），如果pred == head，表明当前节点是队列中第一个“有效的”节点，因此再次尝试tryAcquire获取锁，
4.1 如果成功获取到锁，表明线程B已经执行完成，线程A不需要挂起。
4.2 如果获取失败，表示线程B还未完成，至少还未修改state值。进行步骤5。
5. 前面我们已经说过只有前一个节点pred的线程状态为SIGNAL时，当前节点的线程才能被挂起。
5.1 如果pred的waitStatus == 0，则通过CAS指令修改waitStatus为Node.SIGNAL。
5.2 如果pred的waitStatus > 0，表明pred的线程状态CANCELLED，需从队列中删除。
5.3 如果pred的waitStatus为Node.SIGNAL，则通过LockSupport.park()方法把线程A挂起，并等待被唤醒，被唤醒后进入步骤6。
6. 线程每次被唤醒时，都要进行中断检测，如果发现当前线程被中断，那么抛出InterruptedException并退出循环。
从无限循环的代码可以看出，并不是被唤醒的线程一定能获得锁，必须调用tryAccquire重新竞争，因为锁是非公平的，有可能被新加入的线程获得，从而导致刚被唤醒的线程再次被阻塞，这个细节充分体现了“非公平”的精髓。

### 线程释放锁过程

1. 如果头结点head的waitStatus值为-1，则用CAS指令重置为0；
2. 找到waitStatus值小于0的节点s，通过LockSupport.unpark(s.thread)唤醒线程。



## ReentrantLock 


下文先根据3种不同情形看ReentrantLock获取锁的详细流程，最后再说释放锁的流程:
1、单独一个线程获取锁lock()
2、同一个线程线程执行两次lock()
3、两个不同线程各执行一次lock()
4、释放锁
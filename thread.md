@[toc]

# 1 Java线程基础
*案例中涉及代码 --> [点击跳转](https://github.com/Weision/java-thread)*
## 1.1 并行、串行与并发
场景：餐厅后厨
串行：大厨做完干煸豆角后，又做了一盘椒盐蘑菇；
并行：大厨同时开了两个灶，同时做干煸豆角和椒盐蘑菇；
并发：大厨和他的３个徒弟同时开了８个灶，做了４份干煸豆角和椒盐蘑菇；
## 1.2 线程生命周期
每个线程都有自己的局部变量表、程序计数器和生命周期：
 - ***NEW*** 新建
 - ***RUNNABLE*** 可执行
 - ***RUNNING*** 运行中
 - ***BLOCKED*** 阻塞
 - ***TERMINATED*** 结束
 
 除了new和terminated，其他各个状态之间会在不同的条件下会进行转换：
![这里写图片描述](https://img-blog.csdn.net/20180908140742685)
### 1.2.1 NEW
new一个Thread实例时，进入NEW状态，这时的thread实例只是一个简单的对象：
```java
Thread thread = new Thread();
```
### 1.2.2 RUNNABLE
start()方法让一个thread实例进入RUNNABEL状态，这时才真正在JVM中创建了一个线程，等待CPU的调度获得执行权：
```java
Thread thread = new Thread();
thread.start();
```
### 1.2.3 RUNNING
一旦CPU通过轮询或其他方式从执行队列中选中该线程，此时才会真正执行线程中的逻辑代码，进入RUNNING状态；

### 1.2.4 BLCOKED
如下方法可以让一个线程进入BLCOKED状态：
```
Object的wait方法
Thread的sleep方法
Thread的join方法
InterruptibleChannel的io操作
Selector的wakeup方法
```
### 1.2.5 TERMINATED
ERMINATED状态是一个现成的最终状态，处于该状态后线程生命周期也就结束了，不会在切换至其它状态了；


## 1.3 线程执行单元
### 1.3.1 执行单元
*每个线程的业务逻辑部分被称为**执行单元**，而逻辑部分都是写在run()方法中的，也就是说线程的执行单元就是run()方法；*

***创建线程***有一种方式：构造Thread类；
***实现执行单元***有两种方式：
1)重写Thread类的run()方法 ；
```
public class ThreadChild extends Thread {
    @Override
    public void run() {
        Thread.currentThread().setName("Thread's child");
        for (int i = 0; i < 10; i++)
            System.out.println(Thread.currentThread().getName() + "------------");
    }
}
```
2)实现Runnable接口的runnable方法；
```
public class RunnableImpl implements Runnable {
    public void run() {
        Thread.currentThread().setName("Runnable's implemention");
        for (int i = 0; i < 10; i++)
            System.out.println(Thread.currentThread().getName() + "------------");
    }
}
```

### 1.3.2 Thread中的run()方法--模板方法的应用
```
        //new一个Thread对象
        Thread thread = new Thread();
```
在直接new一个Thread对象的时候，run()方法中是一个空实现，看下Thread中的run()方法：
![这里写图片描述](https://img-blog.csdn.net/20180908141723242)
看13.1中的第一个例子，执行逻辑run()方法是在子类ThreadChild中实现了逻辑细节，这是一个典型的模板方法；
## 1.4 Thread&Runnabel的关系
### 1.4.1 类图上的关系
![这里写图片描述](https://img-blog.csdn.net/20180903223719465)
Runnable现为一个[函数式接口](https://blog.csdn.net/weixx3/article/details/81194499)，Thread类是Runnable的一个实现类，实现了Runnable的run()方法，Thread不仅有run()方法，还有很多其他重要的方法来管理自己实例化线程的生命周期；

### 1.4.2 Runnable较Thread易于共享资源
案例为营业大厅取号机，从资源共享角度看通过实现Runnable接口更易于资源共享：
1)用Thread实现执行单元：
```
public class TicketWindow extends Thread {
    //柜台名称
    private final String name;
    //最多受理的业务数
    private static final int max = 10;

    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;

    }

    @Override
    public void run() {
        while (index < max)
            System.out.println("柜台:" + name + "当前号码为:" + (index++));
    }
}
```
调用方：
```
public static void main(String[] args) {
        TicketWindow ticketWindow1 = new TicketWindow("一号取号机");
        TicketWindow ticketWindow2 = new TicketWindow("二号取号机");
        TicketWindow ticketWindow3 = new TicketWindow("三号取号机");
        //三个TicketWindow线程公用静态MAX[10]
        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();
    }
```

执行结果：
```
柜台:一号取号机当前号码为:1
柜台:一号取号机当前号码为:2
柜台:一号取号机当前号码为:3
柜台:一号取号机当前号码为:4
柜台:一号取号机当前号码为:5
柜台:一号取号机当前号码为:6
柜台:一号取号机当前号码为:7
柜台:一号取号机当前号码为:8
柜台:一号取号机当前号码为:9
```
1)用Runnable实现执行单元：
```
public class TicketRunnable implements Runnable {

    private int index = 1;//不用static修改
    private final static int MAX = 10;

    public void run() {
        while (index <= MAX) {
            System.out.println(Thread.currentThread().getName() + "号码是：" + (index++));

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```
调用方：
```
public static void main(String[] args) {
        TicketRunnable ticketRunnable = new TicketRunnable();
        Thread ticketWindow4 = new Thread(ticketRunnable,"四号取号机");
        Thread ticketWindow5 = new Thread(ticketRunnable,"五号取号机");
        Thread ticketWindow6 = new Thread(ticketRunnable,"六号取号机");
        //三个线程公用一个runnable的私有属性MAX[10]
        ticketWindow4.start();
        ticketWindow5.start();
        ticketWindow6.start();
    }
```
执行结果：
```
四号取号机号码是：1
五号取号机号码是：2
六号取号机号码是：3
四号取号机号码是：4
五号取号机号码是：5
六号取号机号码是：6
四号取号机号码是：7
六号取号机号码是：8
五号取号机号码是：7
四号取号机号码是：9
六号取号机号码是：10
```
### 1.4.3 Thread&Runnabel的关系--策略模式的应用
#### 1.4.3.1 策略模式
要了解Thread与Runnable的关系需要先了解策略模式：
*策略模式*：业务和行为解耦，职责分明，不同上下环境下执行不同的策略；
*参见1*-->[菜鸟教程](http://www.runoob.com/design-pattern/strategy-pattern.html)
*参见2*-->[我的案例](https://blog.csdn.net/weixx3/article/details/80288244)
#### 1.4.3.2 Java线程策略模式的应用
*上下文角色*：Thread -->控制线程生命周期
*策略角色*：Runnable实现类 --> 不同的实现类是不同的策略，实现不同的逻辑
*线程策略模式的应用* --> 不同的业务场景下生成不同业务逻辑的线程，业务逻辑和线程生命周期控制解构，Thread和Runnable职责分明：
```
public static void main(String[] args) {
        //策略角色：不同的Runnable实现类
        ThreeYearsOldRunnable threeYearsOldRunnable = new ThreeYearsOldRunnable();
        TenYearsOldRunnable tenYearsOldRunnable = new TenYearsOldRunnable();
        EighteenYearsOldRunnable eighteenYearsOldRunnable = new EighteenYearsOldRunnable();
        TwentyFiveYearsOldRunnable twentyFiveYearsOldRunnable = new TwentyFiveYearsOldRunnable();
        ThirtyYearsOldRunnable thirtyYearsOldRunnable = new ThirtyYearsOldRunnable();

        //上下文角色：Thread实例
        Thread threeYearsOld = new Thread(threeYearsOldRunnable, "三岁");
        Thread tenYearsOld = new Thread(tenYearsOldRunnable, "十岁");
        Thread eighteenYearsOld = new Thread(eighteenYearsOldRunnable, "十八岁");
        Thread wentyFiveYearsOld = new Thread(twentyFiveYearsOldRunnable, "二十五岁");
        Thread thirtyYearsOld = new Thread(thirtyYearsOldRunnable, "三十岁");

        //不同的上下文下
        for (int age = 0; age <= 100; age++) {
            if (3 == age)
                threeYearsOld.start();
            if (10 == age)
                tenYearsOld.start();
            if (18 == age)
                eighteenYearsOld.start();
            if (25 == age)
                wentyFiveYearsOld.start();
            if (30 == age)
                thirtyYearsOld.start();
        }
    }
```
执行结果：

```
三岁:叔叔好~我还有很多玩具要玩~
十岁:我在三年级学习,数学好难~
十八岁:告别难忘的高中时代，期待我的大学生活~
二十五岁:告别大学，初入职场处处碰壁~
三十岁: 我有了孩子，才知道我的爸妈多么不容易~
```

### 1.4.4 Runnable&Thread关系总结

 - Thread是Runnable的实现类，实现了Runnable的run()方法，但是个空方法，应用了模板模式，具体的实现交给子类；
 - Thread和Runnable应用了策略模式，Thread管理线程的生命周期，Runnable实现类实现业务逻辑，两者职责分明；
 - 创建线程时Runnable实现类较Thread子类能更好更容易地在线程间共享资源；


# 2 Thread构造函数
从Thread构造方法来看Thread的名字、与ThreadGroup和Jvm Stack的关系；
## 2.1 线程命名
在常见线程的时候可以给线程指定一个名字，便于在多线程程序中查找问题；
### 2.1.1 线程的默认命名

 - Thread()
 - Thread(Runnable Target)
 - Thread(ThreadGroup group, Runnable Target)

这三个构造方法没有提供线程命名的参数，线程会进行如下命名：***以"Thread-"作为前缀与一个自增数进行组合，自增数会随着jvm进程中线程的数量不断自增***
```
public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }
public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }
public Thread(ThreadGroup group, Runnable target) {
        init(group, target, "Thread-" + nextThreadNum(), 0);
    }        
```
启了一个线程，被默认命名为"Thread-0"：
![这里写图片描述](https://img-blog.csdn.net/20180908160801981)
可以在线程启动前为其命名：
```
new Thread(() ->
        {
            //为其命名
            Thread.currentThread().setName("weixx");
            try {
                TimeUnit.SECONDS.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
```
![这里写图片描述](https://img-blog.csdn.net/20180908161129618)
### 2.1.2 用带有命名参数的构造方法为线程命名

- public Thread(String name)
- public Thread(ThreadGroup group, String name)
- public Thread(Runnable target, String name) 
- public Thread(ThreadGroup group, Runnable target, String name)
- public Thread(ThreadGroup group, Runnable target, String name, long stackSize) 

## 2.2 ThreadGroup
- public Thread(ThreadGroup group, String name)
- public Thread(ThreadGroup group, Runnable target)
- public Thread(ThreadGroup group, Runnable target, String name)
- public Thread(ThreadGroup group, Runnable target, String name, long stackSize) 

在Thread构造方法中：
- 可以显式地为线程指定group
- 如果没有显式地指定一个ThreadGroup，则会将其加入其父线程所在的线程组
![这里写图片描述](https://img-blog.csdn.net/20180908162844692)
创建两个线程，一个构造时候指定ThreadGroup，一个不指定：
![这里写图片描述](https://img-blog.csdn.net/20180908165406683)
*注：main方法的ThreadGroup为"main"*

## 2.3 JVM Stack 虚拟机栈
需要先了解JVM内存结构-->[\[importnew\]](http://www.importnew.com/27454.html)
### 2.3.1 带有stackSize参数的构造方法
- public Thread(ThreadGroup group, Runnable target, String name,  long stackSize)

线程创建时，除了这个构造方法显式的指定了stackSize，其它构造方法都使用了默认值"0"：
![这里写图片描述](https://img-blog.csdn.net/20180908171108529)
官方文档对stackSize的解释：
- **stackSize越大**代表着**当前线程内方法**调用递归**深度越深**
- **stackSize越小**代表着**创建线程**的数量**越多**
当程序进行无限制深度递归时，Java栈中会不断地进行压栈弹栈操作，JVM的内存大小是有限的，终有被压爆的时候，最后会抛出StackOverFlowError异常，stackSize数量级大小与递归深度成正比，该参数一般不会主动设置，采用系统默认值"0"就好；

### 2.3.2 Java栈深度
栈帧结构图：
![这里写图片描述](https://img-blog.csdn.net/20180908174854453)
每个线程创建的时候，JVM会为其创建Java栈，Java栈的大小可以通过-xss参数调整，方法调用就是栈帧被压入和弹出的过程，由结构图可以看出Java虚拟机栈大小相同的情况下，局部变量表等占用的内存越小，可以被压入的栈帧就越多，反之越少，栈帧的大小被称为宽度，栈帧的数量被成为Java栈的深度；
### 2.3.3 Thread与Java栈
***进程的大小 ≈ 堆内存 + 线程数量 \* 栈内存***
***线程数量 = (最大地址空间(MaxProcessMemory) - JVM堆内存 - ReservedOsMemory)/ThreadStackSize(XSS)***

**结论：线程数量与Java栈内存大小成反比，与堆内存成反比**

*注：ReservedOsMemory为系统保留内存；*

## 2.4 守护线程
守护线程是特殊的线程，一般用于处理后台工作，如JDK垃圾回收；
### 2.4.1 JVM程序什么情况下会退出
官方文档：若JVM中没有一个非守护线程，则JVM的进程会退出；

```
public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(
                () -> {
                    int i = 1;
                    try {
                        while (true) {
                            Thread.currentThread().setName("weixx");
                            //1当前线程sleep 10s
                            TimeUnit.SECONDS.sleep(10l);
                            System.out.println("thread 第[" + i + "]sleep结束~");
                            i++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("thread 挂了~");
                    }
                }
        );
        //2 设置为守护线程
        thread.setDaemon(true);
        //3 启动线程
        thread.start();
        //4 main方法线程sleep 5s
        TimeUnit.SECONDS.sleep(5l);
        System.out.println("main方法生命周期结束~");
    }
```

![这里写图片描述](https://img-blog.csdn.net/20180908184226653)
发现main方法线程结束后，JVM也没有退出，因为JVM进程中还存在一个非守护线程在运行；
现将2初的注释打开，将thread设置为守护线程，再次执行程序：
![这里写图片描述](https://img-blog.csdn.net/20180908184715394)
thread第一次sleep结束后，JVM直接退出了，连日志都没来得及打印，因为没有一个非守护线程存在了，所以退出了；
### 2.4.2 守护线程总结
守护线程特性：
- 守护线程具备自动结束生命周期的特性
- 主要用于一些后台任务

> 参考文献：
>  [ 1 ] Java高并发编程详解 汪文君著。--北京:机械工业出版社，2018年6月第1版  

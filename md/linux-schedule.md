# Linux Schedule
进程是资源分配的基本单位，线程是调度的基本单位。 

## 1 查看进程的线程 
线程是现代操作系统上进行并行执行的一个流行的编程方面的抽象概念。
当一个程序内有多个线程被叉分出用以执行多个流时，这些线程就会在它们之间共享特定的资源（如，内存地址空间、打开的文件），以使叉分开销最小化，并避免大量高成本的IPC（进程间通信）通道。
这些功能让线程在并发执行时成为一个高效的机制。

在Linux中，程序中创建的线程（也称为轻量级进程，LWP）会具有和程序的PID相同的“线程组ID”。然后，各个线程会获得其自身的线程ID（TID）。
对于Linux内核调度器而言，线程不过是恰好共享特定资源的标准的进程而已。经典的命令行工具，如ps或top，都可以用来显示线程级别的信息，只是默认情况下它们显示进程级别的信息。

`ps -T -p <pid>`
`top -H -p <pid>`

[Linux进程与线程的区别](https://my.oschina.net/cnyinlinux/blog/422207)

## 2 内核角度看fork(),clone(),vfork() 的异同
Linux系统将进程的创建与目标进程的执行分成两步:
第一步是从已经存在的进程那里像细胞分裂一样复制出一个子进程。
第二步是目标程序的执行：子进程调用execve()函数执行可执行文件的影像开始新的进程，涉及程序的加载过程。

    fork()，这些标志全为0，表示要全部拷。
    vfork()，则是父、子进程共用虚存空间。
    clone()，由调用者设定并作为参数传递，一般是用来创建线程

[内核角度看fork(),clone(),vfork() ](https://blog.csdn.net/RUN32875094/article/details/79364920)
[Linux-fork()，vfork()和clone的区别](https://blog.csdn.net/caoyan_12727/article/details/52489908)
[Linux中fork、vfork、clone](https://zhuanlan.zhihu.com/p/59065065)

## 3 JVM线程与Linux内核线程的映射(关系)



[JVM线程与Linux内核线程的映射(关系)](https://www.jianshu.com/p/03493f021043)
[Linux与JVM的内存关系分析](https://www.cnblogs.com/heavenhome/articles/6364713.html)
[Java线程与Linux内核线程的映射关系](http://itindex.net/detail/50129-java-%E7%BA%BF%E7%A8%8B-linux)



## 4 JVM线程

[深入理解JVM（线程部分）](https://cloud.tencent.com/developer/article/1605797)
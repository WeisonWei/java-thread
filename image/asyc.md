> https://www.zhihu.com/question/318472639/answer/652254785


其中supplyAsync用于有返回值的任务，runAsync则用于没有返回值的任务。Executor参数可以手动指定线程池，否则默认ForkJoinPool.commonPool()系统级公共线程池，注意：这些线程都是Daemon线程，主线程结束Daemon线程不结束，只有JVM关闭时，生命周期终止。
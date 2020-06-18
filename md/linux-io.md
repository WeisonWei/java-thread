# Linux IO

## 同步和异步
同步和异步与结果的`获取机制`有关。
同步：调用发出后 -->  没有得到结果之前,就不返回;
异步：调用发出后 -->  立即返回,不会立刻得到结果,在完成后，通过状态、通知和回调来通知调用者;

## 阻塞与非阻塞
阻塞与非阻塞与等待结果通知时的`线程状态`有关。
同步：调用发出后 -->  线程挂起,得到结果之后,线程结束;
异步：调用发出后 -->  指在不能立刻得到结果之前，该函数不会阻塞当前线程;

> 阻塞和同步是完全不同的概念。
> [阻塞和同步](https://blog.csdn.net/z_ryan/article/details/80873449)
> [阻塞和同步](https://www.jianshu.com/p/486b0965c296)
> [阻塞和同步](https://www.cnblogs.com/cyyz-le/p/10962818.html)
> [阻塞和同步](https://www.cnblogs.com/bakari/p/10966303.html)
> [操作系统IO](https://juejin.im/post/5eeadd81e51d4573c91b90b0?utm_source=gold_browser_extension)

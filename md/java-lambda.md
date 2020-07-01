# Lambda
导致foreach测试时数据不正常的罪魁祸首是：
Lambda表达式Lambda表达式 在应用程序中首次使用时，需要额外加载ASM框架，因此需要更多的编译，
加载的时间Lambda表达式的底层实现并非匿名内部类的语法糖，而是其优化版foreach的底层实现其实和增强for循环没有本质区别，一个是外部迭代器，一个是内部迭代器而已通过； 
foreach + Lambda 的写法，效率并不低，只不过需要提前进行预热(加载框架)

> [Lambda初次使用很慢](https://juejin.im/post/5efa93e2e51d4534634c6421)
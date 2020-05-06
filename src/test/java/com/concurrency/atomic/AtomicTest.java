package com.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class AtomicTest {
    public static AtomicLong num = new AtomicLong();

    /**
     * https://blog.csdn.net/lhn1234321/article/details/82193289
     *
     * @throws InterruptedException
     */
    @Test
    public void atomic() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        log.info("=====start=====" + num);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 1; i < 101; i++) {
            int finalI = i;
            executorService.submit(() -> {
                num.incrementAndGet();
                log.info("=====" + finalI + "=====" + num);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("=====end=====" + num);
    }

    /**
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/bin/java -ea "-javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=52561:/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit5-rt.jar:/Applications/IntelliJ IDEA.app/Contents/plugins/junit/lib/junit-rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/lib/tools.jar:/Users/admin/github/thread-concurrency-java/target/test-classes:/Users/admin/github/thread-concurrency-java/target/classes:/Users/admin/.m2/repository/org/projectlombok/lombok/1.16.18/lombok-1.16.18.jar:/Users/admin/.m2/repository/cn/hutool/hutool-all/5.3.3/hutool-all-5.3.3.jar:/Users/admin/.m2/repository/org/junit/platform/junit-platform-launcher/1.5.2/junit-platform-launcher-1.5.2.jar:/Users/admin/.m2/repository/org/apiguardian/apiguardian-api/1.1.0/apiguardian-api-1.1.0.jar:/Users/admin/.m2/repository/org/junit/platform/junit-platform-engine/1.5.2/junit-platform-engine-1.5.2.jar:/Users/admin/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/Users/admin/.m2/repository/org/junit/platform/junit-platform-commons/1.5.2/junit-platform-commons-1.5.2.jar:/Users/admin/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.5.2/junit-jupiter-engine-5.5.2.jar:/Users/admin/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.5.2/junit-jupiter-api-5.5.2.jar:/Users/admin/.m2/repository/org/junit/vintage/junit-vintage-engine/5.5.2/junit-vintage-engine-5.5.2.jar:/Users/admin/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/admin/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/admin/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/admin/.m2/repository/org/slf4j/slf4j-log4j12/1.7.25/slf4j-log4j12-1.7.25.jar:/Users/admin/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 com.concurrency.atomic.AtomicTest,atomic
     *
     *  INFO - =====start=====0
     *  INFO - =====1=====1
     *  INFO - =====2=====2
     *  INFO - =====3=====3
     *  INFO - =====4=====4
     *  INFO - =====5=====5
     *  INFO - =====6=====6
     *  INFO - =====7=====7
     *  INFO - =====8=====8
     *  INFO - =====9=====9
     *  INFO - =====10=====10
     *  INFO - =====11=====11
     *  INFO - =====12=====12
     *  INFO - =====13=====13
     *  INFO - =====14=====14
     *  INFO - =====15=====15
     *  INFO - =====16=====16
     *  INFO - =====17=====17
     *  INFO - =====18=====18
     *  INFO - =====19=====19
     *  INFO - =====20=====20
     *  INFO - =====21=====21
     *  INFO - =====23=====23
     *  INFO - =====22=====22
     *  INFO - =====26=====26
     *  INFO - =====25=====25
     *  INFO - =====24=====24
     *  INFO - =====34=====34
     *  INFO - =====33=====33
     *  INFO - =====38=====38
     *  INFO - =====31=====32
     *  INFO - =====41=====41
     *  INFO - =====32=====31
     *  INFO - =====50=====50
     *  INFO - =====30=====30
     *  INFO - =====29=====29
     *  INFO - =====28=====28
     *  INFO - =====27=====27
     *  INFO - =====56=====56
     *  INFO - =====55=====55
     *  INFO - =====54=====54
     *  INFO - =====53=====53
     *  INFO - =====52=====52
     *  INFO - =====51=====51
     *  INFO - =====49=====49
     *  INFO - =====47=====47
     *  INFO - =====46=====46
     *  INFO - =====48=====48
     *  INFO - =====45=====45
     *  INFO - =====44=====44
     *  INFO - =====43=====43
     *  INFO - =====42=====42
     *  INFO - =====40=====40
     *  INFO - =====39=====39
     *  INFO - =====37=====37
     *  INFO - =====35=====35
     *  INFO - =====36=====36
     *  INFO - =====75=====75
     *  INFO - =====74=====74
     *  INFO - =====73=====73
     *  INFO - =====72=====72
     *  INFO - =====71=====71
     *  INFO - =====70=====70
     *  INFO - =====69=====69
     *  INFO - =====68=====68
     *  INFO - =====67=====67
     *  INFO - =====66=====66
     *  INFO - =====65=====65
     *  INFO - =====64=====64
     *  INFO - =====63=====63
     *  INFO - =====62=====62
     *  INFO - =====61=====61
     *  INFO - =====60=====60
     *  INFO - =====59=====59
     *  INFO - =====58=====58
     *  INFO - =====57=====57
     *  INFO - =====94=====94
     *  INFO - =====93=====93
     *  INFO - =====92=====92
     *  INFO - =====91=====91
     *  INFO - =====90=====90
     *  INFO - =====89=====89
     *  INFO - =====88=====88
     *  INFO - =====87=====87
     *  INFO - =====86=====86
     *  INFO - =====85=====85
     *  INFO - =====84=====84
     *  INFO - =====83=====83
     *  INFO - =====82=====82
     *  INFO - =====81=====81
     *  INFO - =====80=====80
     *  INFO - =====79=====79
     *  INFO - =====78=====78
     *  INFO - =====77=====77
     *  INFO - =====76=====76
     *  INFO - =====100=====100
     *  INFO - =====99=====99
     *  INFO - =====98=====98
     *  INFO - =====97=====97
     *  INFO - =====96=====96
     *  INFO - =====95=====95
     *  INFO - =====end=====100
     *
     *
     * Process finished with exit code 0
     */
}

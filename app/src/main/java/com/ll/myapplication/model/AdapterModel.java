package com.ll.myapplication.model;

import java.util.Objects;

/**
 * @Author: ll
 * @CreateTime: 2021/10/10 18:23
 */
public class AdapterModel {
    public void recordLog() {
        LogFactory logFactory = new LogAdapter(new NbLoggerImp());
        logFactory.debug("Test", "我将使用牛逼logger打印log");
    }
}

//系统原来的日志接口如下
interface LogFactory {
    void debug(String tag, String message);
}

//下面是第三方库提供的日志功能，但是其接口与二狗他们系统目前使用的不兼容。
interface NbLogger {
    void d(int priority, String message, Object... obj);
}

//具体提供日志功能的实现类
class NbLoggerImp implements NbLogger {
    @Override
    public void d(int priority, String message, Object... obj) {
        System.out.println(String.format("牛逼logger记录:%s", message));
    }
}

//这个类是适配器模式的核心，通过此类就可以将三方库提供的接口转换为系统的目标接口
class LogAdapter implements LogFactory {
    private NbLogger nbLogger;

    public LogAdapter(NbLogger nbLogger) {
        this.nbLogger = nbLogger;
    }

    @Override
    public void debug(String tag, String message) {
        Objects.requireNonNull(nbLogger);
        nbLogger.d(1, message);
    }
}

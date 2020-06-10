package com.hrt.biz.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HrtApp
 * PushTaskThreadPools
 * 推送商户认证成功数据到综合
 * @author xuegangliu  2019/5/14
 * @since JDK1.8+
 **/
public class PushTaskThreadPools {

    public final static ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2 + 1
    );

    public synchronized static void addWorkerThread(Runnable runnable){
        executorService.execute(runnable);
    }
}
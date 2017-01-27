/**
 * 
 */
package com.kvvssut.ac.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author srimantas 19-Jan-2017
 */
public class JobExecutor {

	private final ThreadPoolExecutor threadPoolExecutor;

	public JobExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTimeInSeconds) {
		BlockingQueue<Runnable> jobQueue = new LinkedBlockingQueue<Runnable>();
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeInSeconds,
				TimeUnit.SECONDS, jobQueue);
		threadPoolExecutor.allowCoreThreadTimeOut(true);
	}

	public void execute(Runnable runnable) {
		threadPoolExecutor.execute(runnable);
	}

	public boolean awaitTerminationInSeconds(int time) throws InterruptedException {
		return threadPoolExecutor.awaitTermination(time, TimeUnit.SECONDS);
	}

	public void shutdown() {
		threadPoolExecutor.shutdown();
	}

}

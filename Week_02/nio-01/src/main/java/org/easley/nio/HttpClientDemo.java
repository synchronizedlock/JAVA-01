package org.easley.nio;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HttpClientDemo
 * --------------
 * 原本写了不少抽象实现一个ShutdownHook去结束run()方法，
 * 以避免整个停掉线程池。最终虽然是实现了，flag还是不可避免的
 * 和业务代码耦合在了一起，原是想通过持续检查flag来决定何时
 * 停止业务代码的执行。为什么没有类似ShutdownHookSupportedRunnable
 * 这种概念的接口呢，控制线程的停止总是如此的不方便。
 *
 * @author Easley
 * @date 2021/1/22
 * @since 1.0
 */
public class HttpClientDemo {
    private static final long RUN_SECONDS = 10;
    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    private static final String URL = "http://localhost:8088/api/hello";
    private static final int SOCKET_TIME_OUT = 10_000;
    private static final int CONNECT_TIME_OUT = 10_000;
    private static final RequestConfig DEFAULT_REQUEST_CONFIG = buildDefaultRequestConfig();
    private static final HttpGet DEFAULT_GET_REQUEST = buildDefaultGetRequest();

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long endMillis = startMillis + TimeUnit.SECONDS.toMillis(RUN_SECONDS);

        AtomicInteger requestCount = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) {
            executorService.execute(() -> keepSendingRequests(endMillis, requestCount));
        }
        shutdownAndAwaitTermination(executorService);
        System.out.printf("Totally %d times requests.", requestCount.get());
    }

    private static void shutdownAndAwaitTermination(ExecutorService executorService) {
        try {
            executorService.shutdown();
            if (executorService.awaitTermination(RUN_SECONDS, TimeUnit.SECONDS)) {
                System.out.println("All threads executed completely.");
            } else {
                System.out.println("Time is up but the task is not finished.");
                // Force shutdown thread pool, and discard the remaining tasks.
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void keepSendingRequests(long endMillis, AtomicInteger requestCount) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            while (System.currentTimeMillis() < endMillis && !Thread.interrupted()) {
                sendDefaultGetRequest(httpClient);
                requestCount.incrementAndGet();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendDefaultGetRequest(CloseableHttpClient httpClient) {
        try (CloseableHttpResponse resp = httpClient.execute(DEFAULT_GET_REQUEST)) {
            System.out.printf("Http status: %s%nResponse content:%s%n",
                    resp.getStatusLine(),
                    EntityUtils.toString(resp.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RequestConfig buildDefaultRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIME_OUT)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .build();
    }

    private static HttpGet buildDefaultGetRequest() {
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setConfig(DEFAULT_REQUEST_CONFIG);
        return httpGet;
    }
}

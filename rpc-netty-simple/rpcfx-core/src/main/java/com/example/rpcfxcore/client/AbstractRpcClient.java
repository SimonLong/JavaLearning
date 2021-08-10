/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rpcfxcore.client;

import com.example.rpcfxcore.api.LoadBalancer;
import com.example.rpcfxcore.api.Router;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRpcClient {

    /**
     * create proxy
     * @param serviceClass service class
     * @param url server url
     * @param <T> T
     * @return proxy class
     */
    public abstract  <T> T create(final Class<T> serviceClass, final String url);

    protected ConcurrentHashMap<String, Object> proxyCache = new ConcurrentHashMap<>();

    public Object getProxy(String className) {
        return proxyCache.get(className);
    }

    public Boolean isExit(String className) {
        return proxyCache.containsKey(className);
    }

    public void add(String className, Object proxy) {
        proxyCache.put(className, proxy);
    }

    public <T, filters> T createFromRegistry(final Class<T> serviceClass, final String zkUrl, Router router, LoadBalancer loadBalance) {

        // curator Provider list from zk
        List<String> invokers = new ArrayList<>();
        // 从zk拿到服务提供的列表
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl).namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();
        try {
            List<String> uris = client.getChildren().forPath("/" + serviceClass.getName());
            if (!CollectionUtils.isEmpty(uris)) {
                uris.forEach(s -> {
                    invokers.add("http://" + s.replace("_", ":"));
                });
            }
//            byte[] bytes  = client.getData().storingStatIn(stat).forPath("/" + serviceClass.getName());
//            System.out.println(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // todo:监听zk的临时节点，根据事件更新这个list（注意，需要做个全局map保持每个服务的提供者List）

        List<String> urls = router.route(invokers);

        String url = loadBalance.select(urls); // router, loadbalance

        return (T) create(serviceClass, url);

    }
}

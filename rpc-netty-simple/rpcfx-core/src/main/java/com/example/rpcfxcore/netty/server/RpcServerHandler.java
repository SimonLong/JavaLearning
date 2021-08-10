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

package com.example.rpcfxcore.netty.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.rpcfxcore.api.RpcfxRequest;
import com.example.rpcfxcore.api.RpcfxResponse;
import com.example.rpcfxcore.netty.common.RpcProtocol;
import com.example.rpcfxcore.serialize.IRpcSerializer;
import com.example.rpcfxcore.serialize.ProtostuffSerializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    private ApplicationContext applicationContext;

    RpcServerHandler(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) throws Exception {
        log.info("Netty server receive message:");
        log.info("Message length: " + msg.getLen());
//        log.info("Message content: " + new String(msg.getContent(), CharsetUtil.UTF_8));

        // 获取 RpcProtocol中的 RpcRequest内容，反序列化成 RpcRequest 对象
        IRpcSerializer rpcSerializer = ProtostuffSerializer.getInstance();
        RpcfxRequest rpcRequest = rpcSerializer.deserialize(msg.getContent(), RpcfxRequest.class);
//        RpcfxRequest rpcRequest = JSON.parseObject(new String(msg.getContent(), CharsetUtil.UTF_8),
//                RpcfxRequest.class);
        log.info("Netty server serializer : " + rpcRequest.toString());

        // 获取相应的bean，反射调用方法，获取结果
        RpcfxResponse response = invoke(rpcRequest);

        // 返回结果给netty 客户端
        RpcProtocol message = new RpcProtocol();
//        String requestJson = JSON.toJSONString(response);
        byte[] serialize = rpcSerializer.serialize(response);
        message.setLen(serialize.length);
        message.setContent(serialize);

        channelHandlerContext.writeAndFlush(message).sync();
        log.info("return response to client end");
    }

    /**
     * 获取接口实现对应的bean，反射调用方法，返回结果
     * @param request rpc request
     * @return result
     */
    private RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        Object service = applicationContext.getBean(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            log.info("Server method invoke result: " + result.toString());
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            log.info("Server Response serialize to string return");
            return response;
        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}

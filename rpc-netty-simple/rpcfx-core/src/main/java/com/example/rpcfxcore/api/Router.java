package com.example.rpcfxcore.api;

import java.util.List;

public interface Router {

    List<String> route(List<String> urls);
}

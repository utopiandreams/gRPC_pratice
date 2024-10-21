package com.kihong.chatserver.config;

import io.grpc.Context;

public class ContextKeys {
    public static final Context.Key<String> USERNAME_KEY = Context.key("username");
    public static final Context.Key<String> ID_KEY = Context.key("id");
}

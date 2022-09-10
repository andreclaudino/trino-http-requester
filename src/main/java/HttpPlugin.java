package com.d2x.trinoplugins;

import com.d2x.trinoplugins.functions.HttpGetFunction;
import com.google.common.collect.ImmutableSet;
import io.trino.spi.Plugin;

import java.util.Set;

public class HttpPlugin implements Plugin {
    @Override
    public Set<Class<?>> getFunctions()
    {
        return ImmutableSet.<Class<?>>builder()
                .add(HttpGetFunction.class)
                .build();
    }
}

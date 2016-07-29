package com.thoughtworks.ketsu.util;

import com.google.inject.Injector;

import javax.inject.Inject;

public class SafeInjector<T> {
    @Inject
    static Injector injector;
    public  static <T> T injectMembers(T t) {
        if( t == null ) return null;
        injector.injectMembers(t);
        return t;
    }
}

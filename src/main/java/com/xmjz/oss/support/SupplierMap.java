package com.xmjz.oss.support;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * 支持value为Supplier的map 由于类型约束,这里的V只能为Object类型
 *
 * @author chengz
 */
public class SupplierMap<K, V> extends HashMap<K, V> {
    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        V v = super.get(key);
        if (v instanceof Supplier) {
            return ((Supplier<V>) v).get();
        }
        return v;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getOrDefault(Object key, V defaultValue) {
        V v = super.getOrDefault(key, defaultValue);
        if (v instanceof Supplier) {
            return ((Supplier<V>) v).get();
        }
        return v;
    }
}

package com.common;

import java.util.HashMap;

public class MyHashMap extends HashMap<Integer,Integer> {
    @Override
    public Integer put(Integer key, Integer value) {
        Integer newValue=value;
        if(containsKey(key)){
            Integer oldValue=get(key);
            newValue=oldValue+newValue;
        }
        return super.put(key,newValue);
    }
}

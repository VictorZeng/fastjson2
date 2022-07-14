package com.alibaba.fastjson2.reader;

import com.alibaba.fastjson2.JSONReader;

import java.lang.reflect.Type;

class ObjectReaderImplInt64
        extends ObjectReaderBaseModule.PrimitiveImpl<Long> {
    static final ObjectReaderImplInt64 INSTANCE = new ObjectReaderImplInt64();

    @Override
    public Class getObjectClass() {
        return Long.class;
    }

    @Override
    public Long readJSONBObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        return jsonReader.readInt64();
    }

    @Override
    public Long readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        return jsonReader.readInt64();
    }
}

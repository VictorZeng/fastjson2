package com.alibaba.fastjson2.writer;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONWriter;

import java.lang.reflect.Field;

final class FieldWriterCharValField<T>
        extends FieldWriterImpl<T> {
    final Field field;

    FieldWriterCharValField(String name, int ordinal, String format, String label, Field field) {
        super(name, ordinal, 0, format, label, char.class, char.class);
        this.field = field;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public Object getFieldValue(Object object) {
        try {
            return field.getChar(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new JSONException("field.get error, " + name, e);
        }
    }

    @Override
    public boolean write(JSONWriter jsonWriter, T object) {
        char value = (char) getFieldValue(object);

        writeFieldName(jsonWriter);
        jsonWriter.writeChar(value);
        return true;
    }

    @Override
    public void writeValue(JSONWriter jsonWriter, Object object) {
        char value = (char) getFieldValue(object);
        jsonWriter.writeChar(value);
    }
}

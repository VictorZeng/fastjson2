package com.alibaba.fastjson2.reader;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONSchemaValidException;
import com.alibaba.fastjson2.TestUtils;
import com.alibaba.fastjson2.annotation.JSONField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldReaderInt8ValueFuncTest {
    @Test
    public void test() {
        Bean bean = new Bean();
        ObjectReader<Bean> objectReader = TestUtils.READER_CREATOR_LAMBDA.createObjectReader(Bean.class);
        FieldReader fieldReader = objectReader.getFieldReader("value");
        fieldReader.accept(bean, "123");
        assertEquals(123, bean.value);
        assertNotNull(fieldReader.getMethod());

        fieldReader.accept(bean, (short) 101);
        assertEquals(101, bean.value);

        fieldReader.accept(bean, 102);
        assertEquals(102, bean.value);

        assertThrows(JSONException.class, () -> fieldReader.accept(bean, new Object()));

        assertEquals(
                101,
                objectReader.readObject(
                        JSONReader.of("{\"value\":101}"),
                        0
                ).value
        );
    }

    public static class Bean {
        private byte value;

        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }
    }

    @Test
    public void test1() {
        Bean1 bean = new Bean1();
        ObjectReader<Bean1> objectReader = ObjectReaderCreatorLambda.INSTANCE.createObjectReader(Bean1.class);
        FieldReader fieldReader = objectReader.getFieldReader("value");
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, "95"));
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, (short) 95));
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, 95));
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, 95L));
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, 95F));
        assertThrows(JSONSchemaValidException.class, () -> fieldReader.accept(bean, 95D));

        assertEquals(
                (byte) 101,
                objectReader.readObject(
                        JSONReader.of("{\"value\":101}"),
                        0
                ).value
        );
    }

    public static class Bean1 {
        @JSONField(schema = "{'minimum':100}")
        private byte value;

        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }
    }

    @Test
    public void test2() {
        Bean2 bean = new Bean2();
        ObjectReader objectReader = ObjectReaderCreatorLambda.INSTANCE.createObjectReader(Bean2.class);
        FieldReader fieldReader = objectReader.getFieldReader("value");
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, "123"));
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, 123));
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, (short) 123));
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, 123L));
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, 123F));
        assertThrows(UnsupportedOperationException.class, () -> fieldReader.accept(bean, 123D));
    }

    public static class Bean2 {
        public void setValue(byte value) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    public void test3() {
        ObjectReader<Bean3> objectReader = TestUtils.READER_CREATOR_LAMBDA.createObjectReader(Bean3.class);
        assertEquals(
                (byte) 123,
                objectReader.readObject(
                        JSONReader.of("{\"id\":101, \"value\":123}")
                ).value
        );
    }

    public static class Bean3 {
        private byte value;
        public final int id;

        public Bean3(@JSONField(name = "id") int id) {
            this.id = id;
        }

        public byte getValue() {
            return value;
        }

        public void setValue(byte value) {
            this.value = value;
        }
    }
}

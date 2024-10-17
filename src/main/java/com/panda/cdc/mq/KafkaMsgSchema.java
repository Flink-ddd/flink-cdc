package com.panda.cdc.mq;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Preconditions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

public class KafkaMsgSchema implements DeserializationSchema<String>, SerializationSchema<String> {
    private static final long serialVersionUID = 1L;
    private transient Charset charset;

    public KafkaMsgSchema() {
        // 默认UTF-8编码
        this(Charset.forName("UTF-8"));
    }

    public KafkaMsgSchema(Charset charset) {
        this.charset = Preconditions.checkNotNull(charset);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String deserialize(byte[] message) {
        // 将Kafka的消息反序列化为java对象
        return new String(message, charset);
    }

    public boolean isEndOfStream(String nextElement) {
        // 流永远不结束
        return false;
    }

    public byte[] serialize(String element) {
        // 将java对象序列化为Kafka的消息
        return element.getBytes(this.charset);
    }

    public TypeInformation<String> getProducedType() {
        // 定义产生的数据Typeinfo
        return BasicTypeInfo.STRING_TYPE_INFO;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.charset.name());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String charsetName = in.readUTF();
        this.charset = Charset.forName(charsetName);
    }

}

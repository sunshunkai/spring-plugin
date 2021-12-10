package com.ssk.core.json.jackson;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;

import java.io.IOException;
import java.io.Writer;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
public class SecurityWriterBasedJsonGenerator extends WriterBasedJsonGenerator {

    /** 零时存储一下字段名 */
    private String fieldName;

    public SecurityWriterBasedJsonGenerator(IOContext ctxt, int features, ObjectCodec codec, Writer w, char quoteChar) {
        super(ctxt, features, codec, w, quoteChar);
    }

    @Override
    public void writeFieldName(SerializableString name) throws IOException
    {
        // Object is a value, need to verify it's allowed
        int status = _writeContext.writeFieldName(name.getValue());
        if (status == JsonWriteContext.STATUS_EXPECT_VALUE) {
            _reportError("Can not write a field name, expecting a value");
        }
        _writeFieldName(name, (status == JsonWriteContext.STATUS_OK_AFTER_COMMA));

        setFieldName(name.getValue());
    }

    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return fieldName;
    }
}

package com.ssk.service.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
public class SecurityMappingJsonFactory extends MappingJsonFactory {

    @Override
    protected JsonGenerator _createGenerator(Writer out, IOContext ctxt) throws IOException
    {
        SecurityWriterBasedJsonGenerator gen = new SecurityWriterBasedJsonGenerator(ctxt,
                _generatorFeatures, _objectCodec, out, _quoteChar);
        if (_maximumNonEscapedChar > 0) {
            gen.setHighestNonEscapedChar(_maximumNonEscapedChar);
        }
        if (_characterEscapes != null) {
            gen.setCharacterEscapes(_characterEscapes);
        }
        SerializableString rootSep = _rootValueSeparator;
        if (rootSep != DEFAULT_ROOT_VALUE_SEPARATOR) {
            gen.setRootValueSeparator(rootSep);
        }
        return gen;
    }


}

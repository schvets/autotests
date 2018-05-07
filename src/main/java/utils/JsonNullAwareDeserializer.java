package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.NullNode;

public final class JsonNullAwareDeserializer
        extends JsonNodeDeserializer
{
    @Override
    public JsonNode getNullValue()
    {
        return NullNode.getInstance();
    }
}
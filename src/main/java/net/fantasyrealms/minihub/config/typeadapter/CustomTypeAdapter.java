package net.fantasyrealms.minihub.config.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomTypeAdapter<T> extends TypeAdapter<T> {
    
    @NotNull
    public abstract Map<Object, Object> serialize(@NotNull T object);
    
    @NotNull
    public abstract T deserialize(@NotNull Map<Object, Object> map);
    
    @Override
    public void write(JsonWriter writer, T value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        
        Map<Object, Object> map = serialize(value);
        writeValue(writer, map);
    }
    
    @Override
    public T read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        
        Object value = readValue(reader);
        if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) value;
            return deserialize(map);
        }
        
        throw new IllegalStateException("Expected JSON object but got: " + value.getClass().getSimpleName());
    }
    
    private void writeValue(JsonWriter writer, Object value) throws IOException {
        switch (value) {
            case null -> writer.nullValue();
            case String s -> writer.value(s);
            case Number number -> writer.value(number);
            case Boolean b -> writer.value(b);
            case Map<?, ?> ignored -> {
                @SuppressWarnings("unchecked")
                Map<Object, Object> map = (Map<Object, Object>) value;
                writer.beginObject();
                for (Map.Entry<Object, Object> entry : map.entrySet()) {
                    writer.name(entry.getKey().toString());
                    writeValue(writer, entry.getValue());
                }
                writer.endObject();
            }
            case List<?> ignored -> {
                @SuppressWarnings("unchecked")
                List<Object> list = (List<Object>) value;
                writer.beginArray();
                for (Object item : list) {
                    writeValue(writer, item);
                }
                writer.endArray();
            }
            default ->
                // Fallback for other types
                    writer.value(value.toString());
        }
    }
    
    private Object readValue(JsonReader reader) throws IOException {
        JsonToken token = reader.peek();
        
        switch (token) {
            case BEGIN_OBJECT:
                return readObject(reader);
            case BEGIN_ARRAY:
                return readArray(reader);
            case STRING:
                return reader.nextString();
            case NUMBER:
                // Handle both integer and floating-point numbers
                String numberStr = reader.nextString();
                try {
                    if (numberStr.contains(".") || numberStr.contains("e") || numberStr.contains("E")) {
                        return Double.parseDouble(numberStr);
                    } else {
                        long longValue = Long.parseLong(numberStr);
                        if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                            return (int) longValue;
                        }
                        return longValue;
                    }
                } catch (NumberFormatException e) {
                    return Double.parseDouble(numberStr);
                }
            case BOOLEAN:
                return reader.nextBoolean();
            case NULL:
                reader.nextNull();
                return null;
            default:
                throw new IllegalStateException("Unexpected token: " + token);
        }
    }
    
    private Map<Object, Object> readObject(JsonReader reader) throws IOException {
        Map<Object, Object> map = new HashMap<>();
        reader.beginObject();
        
        while (reader.hasNext()) {
            String key = reader.nextName();
            Object value = readValue(reader);
            map.put(key, value);
        }
        
        reader.endObject();
        return map;
    }
    
    private List<Object> readArray(JsonReader reader) throws IOException {
        List<Object> list = new ArrayList<>();
        reader.beginArray();
        
        while (reader.hasNext()) {
            Object value = readValue(reader);
            list.add(value);
        }
        
        reader.endArray();
        return list;
    }
}

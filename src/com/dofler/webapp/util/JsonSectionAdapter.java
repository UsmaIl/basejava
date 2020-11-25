package com.dofler.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASS_NAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public JsonElement serialize(T section, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASS_NAME, section.getClass().getName());
        JsonElement element = context.serialize(section);
        retValue.add(INSTANCE, element);
        return retValue;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = jsonObject.getAsJsonPrimitive(CLASS_NAME);

        try {
            Class clazz = Class.forName(prim.getAsString());
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

}

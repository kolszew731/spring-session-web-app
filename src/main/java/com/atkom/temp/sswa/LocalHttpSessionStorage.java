package com.atkom.temp.sswa;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalHttpSessionStorage {

    private static final Map<String, Map<String, Object>> sessions = new ConcurrentHashMap<>();
    private static final Map<String, Object> EMPTY = new ConcurrentHashMap<>();

    public void setAttribute(String sessionId, String attributeId, Object value) {
        sessions.computeIfAbsent(sessionId, s -> new ConcurrentHashMap<>());
        Map<String, Object> attributes = sessions.get(sessionId);
        attributes.put(attributeId, value);
    }

    public Object getAttribute(String sessionId, String attributeId) {
        Map<String, Object> attributes = sessions.getOrDefault(sessionId, EMPTY);
        return attributes.get(attributeId);
    }

    //TODO : nedd to listen on sesion destroy and remove item from map

}

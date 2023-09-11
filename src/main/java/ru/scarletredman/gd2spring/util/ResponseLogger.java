package ru.scarletredman.gd2spring.util;

import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

public interface ResponseLogger {

    <T> T result(T obj);

    @RequiredArgsConstructor
    @Log4j2(topic = "GeometryDash API (Output)")
    class DebugResponseLogger implements ResponseLogger {

        private final ObjectWriter ow;

        @Override
        public <T> T result(T obj) {
            try {
                if (obj instanceof String) log.info(obj);
                else log.info(ow.writeValueAsString(obj));
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
            return obj;
        }
    }

    class NoneResponseLogger implements ResponseLogger {

        @Override
        public <T> T result(T obj) {
            return obj;
        }
    }
}

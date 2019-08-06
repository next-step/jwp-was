package controller;

import webserver.Request;

@FunctionalInterface
public interface Condition {

    boolean isMapping(Request request);
}

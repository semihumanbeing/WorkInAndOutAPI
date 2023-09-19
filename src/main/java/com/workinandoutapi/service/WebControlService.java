package com.workinandoutapi.service;

import java.util.Map;

public interface WebControlService {
    boolean workIn(String user) throws Exception;
    boolean workOut(String user) throws Exception;
    Map<String, Boolean> getStatus(String userId) throws Exception;
}

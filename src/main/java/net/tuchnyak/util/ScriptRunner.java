package net.tuchnyak.util;

import java.util.Arrays;

import rife.database.DbQueryManager;

public class ScriptRunner {

    private final DbQueryManager qManager;

    public ScriptRunner(DbQueryManager qManager) {
        this.qManager = qManager;
    }

    public void executeUpdate(String script) {
        Arrays.stream(script.split(";"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .forEach(qManager::executeUpdate);
    }

}

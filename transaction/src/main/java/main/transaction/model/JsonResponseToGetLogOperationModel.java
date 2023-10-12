package main.transaction.model;

import java.util.List;

public class JsonResponseToGetLogOperationModel {

    private List<Log> logs;

    public JsonResponseToGetLogOperationModel(List<Log> logs) {
        this.logs = logs;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

}

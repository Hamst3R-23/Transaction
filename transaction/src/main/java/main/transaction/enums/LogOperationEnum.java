package main.transaction.enums;


public enum LogOperationEnum {
    deleteAccount("deleteAccount"),
    setAccount("setAccount"),
    addMoney("addMoney"),
    subtractMoney("subtractMoney"),
    transferMoney("transferMoney"),
    convertMoney("convertMoney");


    private final String operation;

    LogOperationEnum(String operation) {
        this.operation = operation;
    }

    public final String getOperation() {
        return operation;
    }
}

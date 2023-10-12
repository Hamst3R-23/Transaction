package main.transaction.enums;


public enum LogOperationEnum {
    DELETE_ACCOUNT("deleteAccount"),
    SET_ACCOUNT("setAccount"),
    ADD_MONEY("addMoney"),
    SUBTRACT_MONEY("subtractMoney"),
    TRANSFER_MONEY("transferMoney"),
    CONVERT_MONEY("convertMoney");

    private final String operation;

    LogOperationEnum(String operation) {
        this.operation = operation;
    }

    public final String getOperation() {
        return operation;
    }
}

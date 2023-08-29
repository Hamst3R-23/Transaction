package main.transaction.model;

public class SortingPaginationSettings {

    private int pageNum = 0;
    private int pageSize = 3;
    private String sortBy = "id";
    private String orderQue = "desc";

    private Long accountid;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderQue() {
        return orderQue;
    }

    public void setOrderQue(String orderQue) {
        this.orderQue = orderQue;
    }

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
}

package group4.organicapplication.dto;

public class SearchOrderObject {
    private String statusOrder;

    public SearchOrderObject(){
        statusOrder = "";
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    @Override
    public String toString() {
        return "SearchOrderObject{" +
                "statusOrder='" + statusOrder + '\'' +
                '}';
    }
}

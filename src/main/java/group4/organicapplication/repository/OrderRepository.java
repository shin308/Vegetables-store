package group4.organicapplication.repository;

import group4.organicapplication.model.Orders;
import group4.organicapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Orders> {
    public List<Orders> findByOrderStatusAndShipper(String orderStatus, User shipper);

    public int countByOrderStatus(String orderStatus);

    @Query("select o from Orders o where o.user.id =:userId order by o.id desc")
    List<Orders> findByUserId(Long userId);

    @Query(value = "SELECT DATE_FORMAT(order.receiveDay, '%d') as day, "
            + " DATE_FORMAT(order.receiveDay, '%m') as week, SUM(purOrder.totalAmount) as total "
            + " FROM Orders order, PurchaseOrder as purOrder"
            + " WHERE order.id = purOrder.order.id and order.orderStatus = 'Hoàn thành'"
            + " GROUP BY DATE_FORMAT(order.receiveDay, '%d'), DATE_FORMAT(order.receiveDay, '%m')"
            + " ORDER BY DATE_FORMAT(order.receiveDay, '%m') asc")
    public List<Object> getOrderByDayAndWeek();

    @Query(value = "SELECT DATE_FORMAT(order.receiveDay, '%U') as week, "
            + " DATE_FORMAT(order.receiveDay, '%Y') as month, SUM(purOrder.totalAmount) as total "
            + " FROM Orders order, PurchaseOrder as purOrder"
            + " WHERE order.id = purOrder.order.id and order.orderStatus = 'Hoàn thành'"
            + " GROUP BY DATE_FORMAT(order.receiveDay, '%U'),DATE_FORMAT(order.receiveDay, '%Y')"
            + " ORDER BY DATE_FORMAT(order.receiveDay, '%U') asc"

    )
    public List<Object> getOrderByWeekAndMonth();

    @Query(value = "SELECT DATE_FORMAT(order.receiveDay, '%m') as month, "
            + " DATE_FORMAT(order.receiveDay, '%Y') as year, SUM(purOrder.totalAmount) as total "
            + " FROM Orders order, PurchaseOrder as purOrder"
            + " WHERE order.id = purOrder.order.id and order.orderStatus = 'Hoàn thành'"
            + " GROUP BY DATE_FORMAT(order.receiveDay, '%m'),DATE_FORMAT(order.receiveDay, '%Y')"
            + " ORDER BY DATE_FORMAT(order.receiveDay, '%m') asc"

    )
    public List<Object> getOrderByMonthAndYear();

//    @Query(value = "SELECT DATE_FORMAT(order.receiveDay, '%Y') as year, "
//            + " SUM(purOrder.totalAmount) as total "
//            + " FROM Orders order, PurchaseOrder as purOrder"
//            + " WHERE order.id = purOrder.order.id and order.orderStatus = 'Hoàn thành'"
//            + " GROUP BY DATE_FORMAT(order.receiveDay, '%Y')"
//            + " ORDER BY year asc"
//
//    )
//    public List<Object> getOrderByYear();
}

package group4.organicapplication.service.impl;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import group4.organicapplication.dto.SearchOrderObject;
import group4.organicapplication.model.Orders;
import group4.organicapplication.model.User;
import group4.organicapplication.repository.OrderRepository;
import group4.organicapplication.service.OrderService;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.querydsl.core.BooleanBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

//    @Override
//    public Page<Orders> getAllOrderByFilter(SearchOrderObject object, int page) throws ParseException {
//        BooleanBuilder builder = new BooleanBuilder();
//
//        String trangThaiDon = object.getStatusOrder();
//        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
//
//        if (!trangThaiDon.equals("")) {
//            builder.and(QDonHang.donHang.trangThaiDonHang.eq(trangThaiDon));
//        }
//
//        if (!tuNgay.equals("") && tuNgay != null) {
//            if (trangThaiDon.equals("") || trangThaiDon.equals("Đang chờ giao") || trangThaiDon.equals("Đã hủy")) {
//                builder.and(QDonHang.donHang.ngayDatHang.goe(formatDate.parse(tuNgay)));
//            } else if (trangThaiDon.equals("Đang giao")) {
//                builder.and(QDonHang.donHang.ngayGiaoHang.goe(formatDate.parse(tuNgay)));
//            } else { // hoàn thành
//                builder.and(QDonHang.donHang.ngayNhanHang.goe(formatDate.parse(tuNgay)));
//            }
//        }
//
//        if (!denNgay.equals("") && denNgay != null) {
//            if (trangThaiDon.equals("") || trangThaiDon.equals("Đang chờ giao") || trangThaiDon.equals("Đã hủy")) {
//                builder.and(QDonHang.donHang.ngayDatHang.loe(formatDate.parse(denNgay)));
//            } else if (trangThaiDon.equals("Đang giao")) {
//                builder.and(QDonHang.donHang.ngayGiaoHang.loe(formatDate.parse(denNgay)));
//            } else { // hoàn thành
//                builder.and(QDonHang.donHang.ngayNhanHang.loe(formatDate.parse(denNgay)));
//            }
//        }
//
//        return donHangRepo.findAll(builder, PageRequest.of(page - 1, 6));
//    }

    @Override
    public Orders updateOrder(Orders order){
        return orderRepository.save(order);
    }

    @Override
    public Orders save(Orders order){
        return orderRepository.save(order);
    }

    @Override
    public Orders findById(long id){
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Orders> findByOrderStatus(String orderStatus){
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public int countByOrderStatus(String orderStatus){
        return orderRepository.countByOrderStatus(orderStatus);
    }

}

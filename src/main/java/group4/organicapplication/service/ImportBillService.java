package group4.organicapplication.service;

import group4.organicapplication.model.ImportBill;
import group4.organicapplication.model.ImportProduct;
import group4.organicapplication.model.Product;
import group4.organicapplication.util.fileUploadUtil;
import group4.organicapplication.repository.ImportBillRepository;
import group4.organicapplication.repository.ImportProductRepository;
import group4.organicapplication.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImportBillService {

    @Autowired
    private ImportBillRepository imBillRepository;

    @Autowired private ProductRepository productRepository;

    @Autowired private ImportProductRepository imProductRepository;

    public List<ImportBill> getAll(){
        return  imBillRepository.findAll();
    }

    public List<ImportBill> getImportBillBySupplierId(int supplierId) {
        return imBillRepository.findBySupplierID(supplierId);
    }

    public List<ImportBill> findImportBillByProductName(String searchName) {
        return imBillRepository.findImportBillByProductName(searchName);
    }


    @Transactional
    public ImportBill createImportBill(ImportBill importBill)
    {
        if(importBill != null) {
            return imBillRepository.save(importBill);
        }
        else {
            return null;
        }
    }

    public ImportBill get(int importID) {
        ImportBill ib = imBillRepository.findImportBillsByImportID(importID);
        return ib;
    }
    public ImportBill getImportBillByProductId(Integer productID) {
        ImportBill ib = imBillRepository.findByProductID(productID);
        return ib;
    }
}
package group4.organicapplication.service;

import group4.organicapplication.model.ImportProduct;
import group4.organicapplication.repository.ImportProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportProductService {

    @Autowired
    private ImportProductRepository reProductRepository;

    public List<ImportProduct> getAll() {
        return reProductRepository.findAll();
    }

    public ImportProduct save(ImportProduct importProduct) {
        return reProductRepository.save(importProduct);
    }


}
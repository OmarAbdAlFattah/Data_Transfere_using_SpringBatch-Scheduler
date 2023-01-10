package com.javadevjournal;

import com.javadevjournal.customer.data.CustomerModel;
import com.javadevjournal.customer.repo.CustomerRepository;
import com.javadevjournal.product.data.ProductModel;
import com.javadevjournal.product.repo.ProductRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TheWriter implements ItemWriter<ProductModel> {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public TheWriter(CustomerRepository customerRepository,
                     ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void write(Chunk<? extends ProductModel> chunk) throws Exception {
        for (ProductModel pm :
                chunk) {
            System.out.println(
                    "MyWriter  : Writing data  :"
                    + pm.getUser_id() + " : "
                    + pm.getItem_id() + " : "
                    + pm.getRating()  + " : "
                    + pm.getTime_stamp()
            );
            productRepository.save(pm);
        }
    }
}

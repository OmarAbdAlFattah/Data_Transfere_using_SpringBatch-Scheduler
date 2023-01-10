package com.javadevjournal;

import com.javadevjournal.customer.data.CustomerModel;
import com.javadevjournal.product.data.ProductModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TheProcessor implements ItemProcessor<CustomerModel, ProductModel> {

    @Override
    public ProductModel process(CustomerModel customerModel) throws Exception {

        System.out.println("MyBatchProcessor : Processing data : "+ customerModel);
        return new ProductModel(customerModel.getUser_id(), customerModel.getItem_id(), customerModel.getRating(), customerModel.getTime_stamp());
    }
}

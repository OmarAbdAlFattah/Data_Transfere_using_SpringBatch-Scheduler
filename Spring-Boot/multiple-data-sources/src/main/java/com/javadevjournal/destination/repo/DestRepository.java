package com.javadevjournal.destination.repo;

//import com.javadevjournal.product.data.ProductModel;
import com.javadevjournal.destination.data.DestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestRepository extends JpaRepository<DestModel,Integer> {
}

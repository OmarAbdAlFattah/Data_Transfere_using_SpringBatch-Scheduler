package com.javadevjournal.mainSource.repo;

import com.javadevjournal.mainSource.data.MainSourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainSourceRepository extends JpaRepository<MainSourceModel,Integer> {
}

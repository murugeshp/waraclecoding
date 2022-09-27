package com.mrugesh1996.waraclecoding.repositry;

import com.mrugesh1996.waraclecoding.entities.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeDAO extends JpaRepository<Cake, Integer> {
    public Cake findByTitle(String title);
}

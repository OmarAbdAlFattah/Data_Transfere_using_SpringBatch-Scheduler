package com.javadevjournal.customer.data;

import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.*;
@Data
@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
//    @Column(unique = false,nullable = false)
    private int user_id;
    private int item_id;
    private int rating;
    private int time_stamp;

    public CustomerModel() {}

    public CustomerModel(int user_id, int item_id, int rating, int time_stamp) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.rating = rating;
        this.time_stamp = time_stamp;
    }

    @Override
    public String toString() {
        return String.format(
                "Movie[id=%d, user_id='%s', item_id='%s',rating='%s', time_stamp='%s']",
                id, user_id, item_id, rating, time_stamp);
    }
}

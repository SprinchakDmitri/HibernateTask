package com.company.hibernateproj.entity;

import com.company.hibernateproj.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;



@Data
@Entity
@Table(name = "t_task")
@NoArgsConstructor

public class Task {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment",strategy = "increment")
    private long task_id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Task(String name, String description, Date startDate, Date endDate, Status status) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}

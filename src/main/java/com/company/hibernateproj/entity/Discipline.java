package com.company.hibernateproj.entity;


import com.company.hibernateproj.enums.DisciplinesEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "t_discipline")
@NoArgsConstructor
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long disciplineId;

    @Enumerated(EnumType.STRING)
    private DisciplinesEnum disciplineName;

    @Column
    @ToString.Exclude
    @OneToMany(mappedBy = "discipline",
                cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Collection<User> members;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name="headOfDiscipline",referencedColumnName = "user_id")
    private User headOfDiscipline;

    public Discipline(DisciplinesEnum disciplineName) {
        this.disciplineName = disciplineName;
    }

}

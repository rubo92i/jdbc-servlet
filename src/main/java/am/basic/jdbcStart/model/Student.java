package am.basic.jdbcStart.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private int balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fakultet_id", referencedColumnName = "id")
    private Fakultet fakultet;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private List<Book> books;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "students_teaches",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
    private List<Teacher> teachers;

    @Enumerated(value = EnumType.STRING)
    private StudentType studentType;

}

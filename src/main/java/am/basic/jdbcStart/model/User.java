package am.basic.jdbcStart.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String code;

    @NotBlank
    @Size(min = 5)
    @Column(name = "username", nullable = false, unique = true)
    private String username;


    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(name = "status", nullable = false)
    private int status;





}

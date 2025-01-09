package ru.maxima.spring_security_demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_security")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 2, max = 20, message = "Поле должно быть от 2 до 20 символов")
    @Column(name = "username")
    private String userName;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1900,  message = "Год должен быть от 1900")
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Column(name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public String getPassword() {
        return password;
    }
}

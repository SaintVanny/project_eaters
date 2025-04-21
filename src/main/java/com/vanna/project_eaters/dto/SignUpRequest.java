package com.vanna.project_eaters.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @Email(message = "Не email")
    private String email;

    @Min(value = 18, message = "Возраст должен быть больше 18")
    private Integer age;
}

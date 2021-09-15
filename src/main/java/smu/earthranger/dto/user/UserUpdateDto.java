package smu.earthranger.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class UserUpdateDto {
    @Size(min = 1, max = 30, message= "이름은 1 ~ 30자여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다")
    private String name;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자가 적어도 1개 이상씩 포함된 8자 이상의 비밀번호여야 합니다.")
    private String password;
}

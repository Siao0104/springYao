package com.example.spring_yao.model.usercalendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class UserCalendarCrForm {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 使用者名稱 */
    @NotBlank(message = "使用者名稱不能為空")
    @Length(message = "使用者名稱不能超過{max}個字",max = 10)
    private String userName;

    /** 代辦事項 */
    @NotBlank(message = "代辦事項不能為空")
    @Length(message = "代辦事項不能超過{max}個字",max = 200)
    private String remark;

    /** 生效否 */
    private boolean enabled;

    /** 日期 */
    @NotNull(message = "日期不能為空")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date executeDay;
}

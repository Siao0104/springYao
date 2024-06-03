package com.example.spring_yao.model.usercalendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserCalendarListVO {

    /** 主鍵 */
    private int id;

    /** 版本 */
    private Integer version;

    /** 使用者名稱 */
    private String userName;

    /** 代辦事項 */
    private String remark;

    /** 生效否 */
    private boolean enabled;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date executeDay;
}

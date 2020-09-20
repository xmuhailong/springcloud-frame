package com.zzl.db.user.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaozhonglong
 * @since 2020-09-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键ID
     */
        private Long id;

      /**
     * 姓名
     */
      private String username;

      /**
     * 姓名
     */
      private String password;

      /**
     * 年龄
     */
      private Integer age;

      /**
     * 邮箱
     */
      private String email;


}

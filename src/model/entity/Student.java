package model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Student {
    public static List<Student> students = null;
    private String sid;
    private String name;
    private String sex;
    private String birthday;
    private String province;
    private String hobby;
    private String phone;

    public String toString() {
        return "学号:" + sid + "\t"
                + "姓名:" + name + "\t"
                + "性别:" + sex + "\t"
                + "生日:" + birthday + "\t"
                + "省份:" + province + "\t"
                + "爱好:" + hobby + "\t"
                + "电话:" + phone;
    }
}

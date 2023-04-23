package com.fanqie.manage.param;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class excelDemo {
    @ExcelProperty(value = "开课学期")
    private String term;  //学期

    @ExcelProperty(value = "课程名称")
    private String teachName;//课程名称

    @ExcelProperty(value = "理论课时")
    private String theoreticalHours;//理论课时

    @ExcelProperty(value = "实践课时")
    private String practicalHours;//实践学时

    @ExcelProperty(value = "教学班名称")
    private String className;//教学班名称

    @ExcelProperty(value = "上课人数")
    private String classNumber;//班级人数

    @ExcelProperty(value = "录成绩教师")
    private String userName;//教职工名称

    @ExcelProperty(value = "开课院系")
    private String faculty;//教职工院系

}

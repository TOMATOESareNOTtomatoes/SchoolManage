package com.fanqie.test.EasyExcel;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class DemoData {

//    @ExcelProperty(index = 0)
    private String string;
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;
//    @ExcelProperty(index = 2)
    private Double doubleData;

}
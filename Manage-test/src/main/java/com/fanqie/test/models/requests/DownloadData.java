package com.fanqie.test.models.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class DownloadData {
    private String string;
    private Date date;
    private Double aDouble;
}

package com.example.ulnamsong.linemappractice;

/**
 * Created by Ulnamsong on 2015-12-05.
 */
public class Station {
    private String name;
    private String line_num;
    private String origin_stnnum;
    private String fr_code;

    public void Station(){
        this.name="AAA";
        this.line_num="BBB";
        this.fr_code="CCC";
        this.origin_stnnum="DDD";

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLine_num(String line_num) {
        this.line_num = line_num;
    }

    public void setFr_code(String fr_code) {
        this.fr_code = fr_code;
    }

    public void setStnOriginNum(String name11) {
        this.origin_stnnum = name11;
    }

    public String getName() {
        return name;
    }

    public String getLine_num() {
        return line_num;
    }

    public String getFr_code() {
        return fr_code;
    }

    public String getOrigin_stnnum() {
        return origin_stnnum;
    }


}


package demo.covid19.dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author butterboy
 * @version \: Covid19Country.java,v 0.1 2022-12-13 00:08
 */
@Setter
@Getter
public class Covid19Country {

    /**
     * 国家id，自增
     */
    private long id;

    /**
     * 国家代码
     */
    private String code;

    /**
     * 国家名称
     */
    private String name;

    /**
     * 国家中国名称
     */
    private String nameCn;

    /**
     * -1删除；0待执行；1已执行
     */
    private int state;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 人口（截至2020）
     */
    private Long population;

}
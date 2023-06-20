package demo.covid19.dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author butterboy
 * @version \: Covid19Data.java,v 0.1 2022-12-13 00:07
 */
@Setter
@Getter
public class Covid19Data {

    /**
     * id，自增
     */
    private long id;

    /**
     * 国家id
     */
    private long countryId;

    /**
     * 国家名
     */
    private String countryName;

    /**
     * 国家中文名
     */
    private String countryCnName;

    /**
     * 新增病例
     */
    private int newCases;

    /**
     * 累计病例
     */
    private long cumulativeCases;

    /**
     * 累计感染率
     */
    private double cumulativeRate;

    /**
     * 新增死亡
     */
    private int newDeaths;

    /**
     * 累计死亡
     */
    private int cumulativeDeaths;

    /**
     * 日期
     */
    private String date;

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

}
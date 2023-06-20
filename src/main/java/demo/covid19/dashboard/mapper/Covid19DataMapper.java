package demo.covid19.dashboard.mapper;

import demo.covid19.dashboard.model.Covid19Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author butterboy
 * @version \: Covid19DataMapper.java,v 0.1 2022-12-13 00:07
 */
@Mapper
public interface Covid19DataMapper {

    List<Covid19Data> getDataByDate(@Param("date") String date);

    List<Covid19Data> getNewCaseLine(@Param("time") String time);

    List<Long> getCumulativeCases(@Param("date") String date);

    List<Long> getCumulativeDeaths(@Param("date") String date);

    Long getNewCaseTotal(@Param("date") String date);

    Long getNewDeathTotal(@Param("date") String date);

}
package demo.covid19.dashboard.mapper;

import demo.covid19.dashboard.model.Covid19Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author butterboy
 * @version \: Covid19CountryMapper.java,v 0.1 2022-12-13 00:08
 */
@Mapper
public interface Covid19CountryMapper {
    List<String> getAllNames(@Param("language") int language);

    List<String> getAllNamesForDeath(@Param("language") int language);

    List<String> getAllNamesForLine(@Param("language") int language);

}
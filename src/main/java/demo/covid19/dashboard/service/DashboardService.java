package demo.covid19.dashboard.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import demo.covid19.dashboard.mapper.Covid19CountryMapper;
import demo.covid19.dashboard.mapper.Covid19DataMapper;
import demo.covid19.dashboard.model.Covid19Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Resource
    private Covid19CountryMapper covid19CountryMapper;

    @Resource
    private Covid19DataMapper covid19DataMapper;

    /**
     * 获取柱形、折线图的条目（即国家）
     *
     * @param type     柱形图枚举：0累计感染top10，1累计死亡top10，2单日新增折线图
     * @param language 语言：0中文，1英文
     */
    public List<String> getTop10Countries(@RequestParam int type, @RequestParam int language) {
        if (type == 0) {
            return covid19CountryMapper.getAllNames(language); // 这里的数据没必要全量拉取，将哪些从头到尾都没进过top10的国家去掉
        } else if (type == 1) {
            return covid19CountryMapper.getAllNamesForDeath(language); // 同上
        } else {
            return covid19CountryMapper.getAllNamesForLine(language); // 仅展现一些比较主流的国家曲线（全量展示曲线会很难看...）
        }
    }

    /**
     * 按日期聚合各个图表数据，返回一个集合，按约定的下标顺序存放数据；
     * 每个下标所代表的数据模块：0累计感染top10  1累计死亡top10  2地图  3单日新增折线图  4感染/死亡数据大观
     *
     * @return refresh data...
     */
    public Object[] getRefreshData(String date) throws Exception {
        CompletableFuture<List<Long>> top10ForNewCases = CompletableFuture.supplyAsync(() -> this.getTop10Data(0, date));
        CompletableFuture<List<Long>> top10ForDeath = CompletableFuture.supplyAsync(() -> this.getTop10Data(1, date));
        CompletableFuture<List<MapData>> worldMap = CompletableFuture.supplyAsync(() -> this.getWorldMapData(date));
        CompletableFuture<List<Object[]>> lineChart = CompletableFuture.supplyAsync(() -> this.getLineData(date));
        CompletableFuture<CasesData> casesData = CompletableFuture.supplyAsync(() -> this.getCasesData(date));
        return new Object[]{top10ForNewCases.get(), top10ForDeath.get(), worldMap.get(), lineChart.get(), casesData.get()};
    }

    /**
     * 按日期获取柱状图数据
     *
     * @param type 柱形图类型：0累计感染top10，1累计死亡top10
     * @param date 日期
     * @return top10 data
     */
    private List<Long> getTop10Data(int type, String date) {
        if (type == 0) {
            return covid19DataMapper.getCumulativeCases(date);
        } else {
            return covid19DataMapper.getCumulativeDeaths(date);
        }
    }

    /**
     * 获取地图感染数据
     *
     * @param date 日期
     * @return world map data
     */
    public List<MapData> getWorldMapData(@RequestParam String date) {
        List<Covid19Data> dataList = covid19DataMapper.getDataByDate(date);
        if (dataList != null && dataList.size() > 0) {
            return dataList.stream().map(d -> {
                MapData data = new MapData();
                data.name = d.getCountryName();
                data.value = (double) d.getCumulativeCases();
                return data;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private List<Object[]> getLineData(String date) {
        List<Covid19Data> dataList = covid19DataMapper.getNewCaseLine(date + "%");
        Set<Long> countryIdSet = Sets.newHashSet(228L, 74L, 213L, 80L, 44L,
                107L, 173L, 98L, 30L, 177L, 105L, 208L, 234L, 202L, 100L);
        List<Object[]> result = Lists.newArrayList();

        for (Covid19Data covid19Data : dataList) {
            countryIdSet.remove(covid19Data.getCountryId());
        }

        if (countryIdSet.size() > 0) {
            countryIdSet.forEach(countryId -> {
                Covid19Data data = new Covid19Data();
                data.setCountryId(countryId);
                data.setNewCases(0);
                dataList.add(data);
            });
        }
        dataList.stream().sorted((d1, d2) -> (int) (d1.getCountryId() - d2.getCountryId()))
                .forEach(dl -> result.add(new Object[]{date.replace("-", "/"), dl.getNewCases()}));
        return result;
    }

    private CasesData getCasesData(String date) {
        String prevDate = LocalDate.parse(date).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        CasesData result = new CasesData();
        result.setCurrentNewCases(covid19DataMapper.getNewCaseTotal(date));
        result.setCurrentNewDeaths(covid19DataMapper.getNewDeathTotal(date));
        result.setPrevNewCases(covid19DataMapper.getNewCaseTotal(prevDate));
        result.setPrevNewDeaths(covid19DataMapper.getNewDeathTotal(prevDate));
        return result;
    }

    @Setter
    @Getter
    private static class MapData {
        private String name;
        private Double value;
    }

    @Setter
    @Getter
    private static class CasesData {
        private Long prevNewCases;
        private Long currentNewCases;
        private Long prevNewDeaths;
        private Long currentNewDeaths;
    }
}

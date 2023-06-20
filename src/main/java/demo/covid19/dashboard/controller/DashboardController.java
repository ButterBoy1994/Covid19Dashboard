package demo.covid19.dashboard.controller;

import com.google.common.collect.Lists;
import demo.covid19.dashboard.service.DashboardService;
import demo.covid19.dashboard.utils.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    final static DateTimeFormatter Y_M_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/covid19/dashboard/cn")
    public ModelAndView dashboardCN() {
        ModelAndView cnView = new ModelAndView("dashboard");
        cnView.addObject("language", 0);
        cnView.addObject("head", "新冠历史数据变迁");
        cnView.addObject("title", "新冠2019-2023数据盘");
        cnView.addObject("border", "/imgs/border.png");
        return cnView;
    }

    @RequestMapping("/covid19/dashboard/en")
    public ModelAndView dashboardEN() {
        ModelAndView enView = new ModelAndView("dashboard");
        enView.addObject("language", 1);
        enView.addObject("head", "Evolution of COVID-19 Historical Data");
        enView.addObject("title", "COVID-19 2019-2023 Data Dashboard");
        enView.addObject("border", "/imgs/border-en.png");
        return enView;
    }

    /**
     * 获取柱形、折线图的条目（即国家）
     *
     * @param type     柱形图类型：0累计感染top10，1累计死亡top10，2单日新增折线图
     * @param language 语言：0中文，1英文
     */
    @GetMapping("/get_countries")
    public BaseResponse<List<String>> getTop10Countries(@RequestParam int type,
                                                        @RequestParam(required = false, defaultValue = "0") int language) {
        return BaseResponse.success(dashboardService.getTop10Countries(type, language));
    }

    /**
     * 将涉及日期全部传到前端（前端日期处理太痛苦了...所以干脆让后端直接返回）
     *
     * @return dates...
     */
    @RequestMapping("/dates")
    public BaseResponse<List<String>> getChartDates() {
        LocalDate start = LocalDate.parse("2019-12-30", Y_M_D);
        LocalDate end = LocalDate.parse("2022-12-10", Y_M_D);
        List<String> result = Lists.newArrayList();
        do {
            result.add(Y_M_D.format(start));
            start = start.plusDays(1);
        } while (!start.isAfter(end));
        return BaseResponse.success(result);
    }

    /**
     * 获取柱形图的条目（即国家）
     *
     * @param date 日期
     */
    @GetMapping("/refresh_data")
    public BaseResponse<Object[]> getRefreshData(@RequestParam String date) throws Exception {
        return BaseResponse.success(dashboardService.getRefreshData(date));
    }

}

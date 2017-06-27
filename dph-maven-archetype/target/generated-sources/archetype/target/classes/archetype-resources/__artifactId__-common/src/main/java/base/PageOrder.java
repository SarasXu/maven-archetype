#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.base;

import com.dph.common.utils.service.BaseOrder;
import com.dph.common.utils.utils.DateUtils;
import com.github.pagehelper.Page;

import java.text.ParseException;
import java.util.Date;

/**
 * description:
 * saras_xu@163.com 2017-04-30 11:10 创建
 */
public class PageOrder extends BaseOrder {
    private static final long serialVersionUID = -8983086315906540122L;
    /**
     * 分页信息
     */
    private Page page;
    /**
     * 查询开始时间
     */
    private Date startTime;
    /**
     * 查询结束时间
     */
    private Date endTime;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 初始化startTime为当天00:00:00
     */
    public void initStartTime(String startTime) {
        try {
            this.startTime = DateUtils.string2DateTimeByAutoZero(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化endTime为当天23:59:59
     */
    public void initEndTime(String endTime) {
        try {
            this.endTime = DateUtils.string2DateTimeBy23(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

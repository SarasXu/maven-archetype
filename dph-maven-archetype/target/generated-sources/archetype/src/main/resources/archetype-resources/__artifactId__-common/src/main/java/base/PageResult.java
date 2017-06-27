#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.base;

import com.dph.common.utils.service.BaseResult;
import com.github.pagehelper.Page;

/**
 * description:
 * saras_xu@163.com 2017-05-02 14:56 创建
 */
public class PageResult extends BaseResult {
    private static final long serialVersionUID = -2014220613536338739L;
    /**
     * 分页信息，但实际返回的page不包含分页信息，日志打印时pageInfo类的实体列表为分页信息，page为实体列表，此为插件问题
     */
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}

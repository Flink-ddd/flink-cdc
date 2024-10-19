package com.panda.cdc.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author vensen
 */
@Data
@ApiModel(description = "统一分页列表信息返回对象")
public class BaseRespDTO<T> implements Serializable {

    @ApiModelProperty(value = "数据列表数据")
    private List<T> data;

    @ApiModelProperty(value = "页码")
    private long pageNum;

    @ApiModelProperty(value = "每页数量")
    private long pageSize;

    @ApiModelProperty(value = "总条数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private long pages;

    /**
     * 获取分页对象
     *
     * @param pageObject 原始分页对象
     * @param data   数据列表
     * @param <T>        泛型类型
     * @return 返回分页对象
     */
    public static <T> BaseRespDTO<T> of(Page<T> pageObject, List<T> data) {
        BaseRespDTO<T> baseRespDTO = new BaseRespDTO<T>();
        baseRespDTO.setData(data);
        baseRespDTO.setPageNum(pageObject.getCurrent());
        baseRespDTO.setPageSize(pageObject.getSize());
        baseRespDTO.setPages(pageObject.getPages());
        baseRespDTO.setTotal(pageObject.getTotal());
        return baseRespDTO;
    }


}

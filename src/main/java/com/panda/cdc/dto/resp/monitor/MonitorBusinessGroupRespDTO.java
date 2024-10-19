package com.panda.cdc.dto.resp.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("业务组的返回参数")
public class MonitorBusinessGroupRespDTO {
    @ApiModelProperty(value = "业务组ID")
    private String businessGroupID;

    @ApiModelProperty(value = "业务组名称")
    private String businessGroupName;
}

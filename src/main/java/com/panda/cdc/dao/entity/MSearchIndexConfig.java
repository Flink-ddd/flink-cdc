package com.panda.cdc.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 搜索-初始化数据同步任务管理表
 * </p>
 *
 * @author system
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MSearchIndexConfig对象", description="搜索-初始化数据同步任务管理表")
public class MSearchIndexConfig implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "索引编码")
    @TableField("index_code")
    private String indexCode;

    @ApiModelProperty(value = "索引type")
    @TableField("index_type")
    private String indexType;

    @ApiModelProperty(value = "当前索引下的数据模版Redis-key")
    @TableField("redis_key")
    private String redisKey;

    @ApiModelProperty(value = "执行同步模式 1：执行接口数据JSON文件（默认）")
    @TableField("execute_mode")
    private Integer executeMode;

    @ApiModelProperty(value = "当前初始化数据中的唯一字段标识")
    @TableField("execute_ident")
    private String executeIdent;

    @ApiModelProperty(value = "当前初始化数据中的唯一字段标识（适用于定位cdc索引模版）")
    @TableField("execute_ident_mapper")
    private String executeIdentMapper;

    @ApiModelProperty(value = "执行描述")
    @TableField("execute_remark")
    private String executeRemark;

    @ApiModelProperty(value = "初始化数据同步涉及到的数据库，多个以逗号隔开")
    @TableField("involve_database")
    private String involveDatabase;

    @ApiModelProperty(value = "初始化数同步涉及到的数据表，多个以逗号隔开")
    @TableField("involve_table")
    private String involveTable;

    @ApiModelProperty(value = "注册来源类型（1.PC；2.小程序；3.H5；4.APP）")
    @TableField("sd_scrtp")
    private String sdScrtp;

    @ApiModelProperty(value = "注册伙伴产品(业务项目)")
    @TableField("id_scrvar")
    private String idScrvar;

    @ApiModelProperty(value = "注册上传机构（公司名）")
    @TableField("id_scrorg")
    private String idScrorg;

    @ApiModelProperty(value = "启用禁用（是否失效）0启用，1禁用")
    @TableField("fg_dis")
    private Integer fgDis;

    @ApiModelProperty(value = "是否删除，0未删除，1已删除")
    @TableField("fg_del")
    private Integer fgDel;

    @ApiModelProperty(value = "创建用户")
    @TableField("id_usr_crt")
    private Long idUsrCrt;

    @ApiModelProperty(value = "创建时间")
    @TableField("dtm_crt")
    private LocalDateTime dtmCrt;

    @ApiModelProperty(value = "最后更新用户")
    @TableField("id_usr_edt")
    private Long idUsrEdt;

    @ApiModelProperty(value = "最后更新时间")
    @TableField("dtm_edt")
    private LocalDateTime dtmEdt;

    @ApiModelProperty(value = "记录版本号")
    @TableField("ver_no")
    private String verNo;


}

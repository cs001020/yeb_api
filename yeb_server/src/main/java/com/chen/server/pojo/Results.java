package com.chen.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应
 *
 * @author CHEN
 * @since  2022/09/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value ="统一响应结果",description = "")
public class Results {
    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "信息")
    private String message;
    @ApiModelProperty(value = "数据")
    private Object data;

    /**
     * 成功响应
     *
     * @param message 消息
     * @return {@link Results}
     */
    public static Results success(String message){
        return new Results(200,message,null);
    }

    /**
     * 成功响应
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Results}
     */
    public static Results success(String message,Object data){
        return new Results(200,message,data);
    }

    /**
     * 失败响应
     *
     * @param message 消息
     * @return {@link Results}
     */
    public static Results error(String message){
        return new Results(500,message,null);
    }

    /**
     * 失败响应
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Results}
     */
    public static Results error(String message,Object data){
        return new Results(500,message,data);
    }


}

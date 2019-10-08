package com.wdzg.benc.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义响应结构
 */
@Data
public class ResultUtil implements Serializable{

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     *  响应业务状态
     */
    private Integer code;

    /**
     *  响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    /**
     * 数量
     */
    private Integer count;



    public static ResultUtil build(Integer code, String msg,Integer count, Object data) {
        return new ResultUtil(code, msg,count, data);
    }

    public static ResultUtil ok(Object data) {
        return new ResultUtil(data);
    }

    public static ResultUtil ok() {
        return new ResultUtil(null);
    }

    public ResultUtil() {
    }

    public static ResultUtil build(Integer code, String msg) {
        return new ResultUtil(code, msg, 0,null);
    }

    private ResultUtil(Integer code, String msg, Integer count ,Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    private ResultUtil(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }

    public ResultUtil(Integer code ,Integer count ,Object data) {
        this.code = code;
        this.msg = "";
        this.count = count;
        this.data = data;
    }


    /**
     *
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static ResultUtil formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResultUtil.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(),jsonNode.get("count").intValue(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static ResultUtil format(String json) {
        try {
            return MAPPER.readValue(json, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static ResultUtil formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(),jsonNode.get("count").intValue(), obj);
        } catch (Exception e) {
            return null;
        }
    }


}

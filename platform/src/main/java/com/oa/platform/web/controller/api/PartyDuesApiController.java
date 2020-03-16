package com.oa.platform.web.controller.api;

import com.oa.platform.biz.PartyDuesBiz;
import com.oa.platform.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 党费API接口
 * @author Feng
 * @date 2020/03/16
 */
@RestController
@RequestMapping("/api/dues")
public class PartyDuesApiController extends BaseController {

    @Autowired
    private PartyDuesBiz partyDuesBiz;

    /**
     * 保存或更新
     * @param recordId 唯一标识
     * @param payTime 缴纳时间
     * @param payAmount 缴纳金额
     * @param remark 备注
     * @param payPeriod 缴纳期(如：上缴哪季度党费)
     * @param recordFlag 信息标识
     * @return
     */
    @PostMapping("save")
    public Map<String, Object> save(
            @RequestParam(defaultValue = "", required = false) String recordId,
            @RequestParam String payTime,
            @RequestParam(defaultValue = "0.00") String payAmount,
            @RequestParam(defaultValue = "", required = false) String remark,
            @RequestParam String payPeriod,
            Integer recordFlag) {
        return partyDuesBiz.save(recordId, payTime, payAmount, remark, payPeriod, recordFlag);
    }

    /**
     * 根据id获得党员缴费信息
     * @param id 唯一标识
     * @return
     */
    @GetMapping("get")
    public Map<String, Object> get(@RequestParam String id) {
        return partyDuesBiz.get(id);
    }

    /**
     * 根据id删除缴费信息
     * @param id 唯一标识
     * @return
     */
    @PostMapping("deleteById")
    public Map<String, Object> deleteById(@RequestParam String id) {
        return partyDuesBiz.deleteById(id);
    }

    /**
     * 检索
     * @param userId 用户ID
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("search")
    public Map<String, Object> search(
            @RequestParam(defaultValue = "", required = false) String userId,
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false)  int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        return partyDuesBiz.search(userId, key, pageNum, pageSize);
    }

    /**
     * 获取当前用户缴费记录
     * @param key 关键字
     * @param pageNum 页码
     * @param pageSize 每页记录数
     * @return
     */
    @GetMapping("getCurrUserDues")
    public Map<String, Object> getCurrUserDues(
            @RequestParam(defaultValue = "", required = false) String key,
            @RequestParam(defaultValue = PAGE_NUM_STR, required = false)  int pageNum,
            @RequestParam(defaultValue = PAGE_SIZE_STR, required = false) int pageSize) {
        String userId = this.getUserIdOfSecurity();
        if ("".equals(userId)) {
            return partyDuesBiz.getParamErrorVo();
        }
        else {
            return partyDuesBiz.search(userId, key, pageNum, pageSize);
        }
    }
}

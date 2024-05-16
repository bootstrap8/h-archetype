package it.pkg.code.demo.control;

import it.pkg.code.demo.feign.DemoService;
import com.github.hbq969.code.common.cache.Expire;
import com.github.hbq969.code.common.restful.ReturnMessage;
import it.pkg.code.demo.service.ExampleService;
import it.pkg.code.demo.view.request.ExampleRequest;
import it.pkg.code.demo.view.response.ExampleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : hbq969@gmail.com
 * @description : 示例Control类
 * @createTime : 2023/8/11 09:49
 */
@RestController
@Api(tags = "示例接口")
@Slf4j
@RequestMapping(path = "/example")
public class ExampleControl {

  @Autowired
  private ExampleService service;

  @Autowired
  private DemoService demoService;

  @ApiOperation("查询列表")
  @RequestMapping(path = "/dict/list", method = RequestMethod.GET)
  @ResponseBody
  public ReturnMessage<?> queryList(
      @RequestParam(name = "key", defaultValue = "default") String key) {
    log.info("查询列表");
    return ReturnMessage.success(service.queryList(key));
  }

  @Cacheable(keyGenerator = "apiKeyGenerator", value = "default",unless = "#result.state.value!='OK'")
  @Expire(methodKey = "testCache", time = 5, unit = TimeUnit.SECONDS)
  @ApiOperation("测试接口缓存")
  @RequestMapping(path = "/cache/test", method = RequestMethod.POST)
  @ResponseBody
  public ReturnMessage<ExampleResponse> testCache(@RequestBody ExampleRequest request) {
    log.info("测试接口缓存: {}", request);
    return ReturnMessage.success(new ExampleResponse());
  }

    @ApiOperation("feign测试百度查询")
    @RequestMapping(path = "/queryBaidu", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage<?> queryBaidu() {
      log.info("百度查询");
    return ReturnMessage.success(demoService.query());
    }
}

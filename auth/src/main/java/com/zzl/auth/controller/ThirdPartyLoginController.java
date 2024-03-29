package com.zzl.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzl.auth.config.ThirdPartyLoginHelper;
import com.zzl.auth.config.ThirdResources;
import com.zzl.core.base.constants.Constant;
import com.zzl.core.base.domain.ResultHelper;
import com.zzl.core.base.enums.ResultEnum;
import com.zzl.core.base.exception.AppException;
import com.zzl.core.base.utils.HttpUtil;
import com.zzl.auth.persistence.service.IAppUserService;
import com.zzl.core.base.domain.user.LoginModel;
import com.zzl.core.base.domain.user.ThirdPartyUser;
import com.zzl.core.base.domain.user.AppUser;
import com.zzl.core.base.domain.user.UserThirdparty;
import com.zzl.auth.persistence.service.IUserThirdpartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zhaozhonglong on 2018/7/24.
 */
@Slf4j
@Controller
@ApiIgnore
@RequestMapping("/thirdPartyLogin")
public class ThirdPartyLoginController {

    @Autowired
    private IUserThirdpartyService userThirdpartyService;
    @Autowired
    private IAppUserService IAppUserService;
    @Autowired
    private OAuth2Controller oAuth2Controller;

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @param type     微博:sina  微信:wx  QQ:qq
     */
    @GetMapping("/sns")
    public void thirdLogin(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("type") String type, @RequestParam("returnUrl") String returnUrl) {
        //拼接第三方登录授权地址
        String url = ThirdPartyLoginHelper.getRedirectUrl(request.getHeader("host"), type);

        //存储登录前的位置
        String ip = HttpUtil.getIp(request);
        Constant.RETURN_URL.put(ip, returnUrl.replaceAll("＃", "#"));

        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            log.error("调用第三方出错：", e);

            throw new AppException(ResultEnum.UNKNOW_ERROR);
        }
    }

    /**
     * QQ登录回调
     *
     * @param request
     * @return
     */
    @GetMapping("/callback/qq")
    public void qqCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String host = request.getHeader("host");
        String code = request.getParameter("code");
        if (!StringUtils.isEmpty(code)) {// 如果不为空
            // 获取token和openid
            Map<String, String> map = ThirdPartyLoginHelper.getQQTokenAndOpenid(code, host);
            String openId = map.get("openId");
            if (!StringUtils.isEmpty(openId)) {// 如果openID存在
                // 获取第三方用户信息存放到session中
                ThirdPartyUser thirdUser = ThirdPartyLoginHelper.getQQUserinfo(map.get("access_token"), openId);
                thirdUser.setProvider("QQ");
                writeHrefHtml(request, response, thirdUser);
            } else {// 如果未获取到OpenID
                response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
            }
        } else {// 如果没有返回令牌，则直接返回到登录页面
            response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
        }
    }

    /**
     * 微信登录回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/callback/wx")
    public void wxCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String host = request.getHeader("host");
        String code = request.getParameter("code");
        if (!StringUtils.isEmpty(code)) {// 如果不为空
            // 获取token和openid
            Map<String, String> map = ThirdPartyLoginHelper.getWxTokenAndOpenid(code, host);
            String openId = map.get("openId");
            if (!StringUtils.isEmpty(openId)) {// 如果openID存在
                // 获取第三方用户信息存放到session中
                ThirdPartyUser thirdUser = ThirdPartyLoginHelper.getWxUserinfo(map.get("access_token"), openId);
                thirdUser.setProvider("WX");
                writeHrefHtml(request, response, thirdUser);
            } else {// 如果未获取到OpenID
                response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
            }
        } else {// 如果没有返回令牌，则直接返回到登录页面
            response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
        }
    }

    /**
     * 微博登录回调
     * 第三方登录流程,自己实现,不用第三方SDK
     * 1.访问登录接口,根据类型拼接第三方授权url,重定向到该url上 url参数包括client_id,response_type,scope,redirect_uri信息等待用户登录
     * 2.用户确认登录后重定向到第一步带的redirect_uri并且给code赋值
     * 3.利用client_id、client_secret和code换取access_token和openid
     * 4.利用access_token和openid获取用户信息 如果获取到,则用HttpServletResponse写html页面window.location.href跳转到首页并带上自己生成的token
     * 5.使用获得的Access Token调用API  微博的access_token是有过期时间的，通常过期时间为30天
     */
    @GetMapping("callback/sina")
    public void sinaCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String host = request.getHeader("host");
        String code = request.getParameter("code");
        if (!StringUtils.isEmpty(code)) {
            //利用client_id、client_secret和code换取access_token和openid
            JSONObject json = ThirdPartyLoginHelper.getSinaTokenAndUid(code, host);
            String uid = json.getString("uid");//openid
            if (!StringUtils.isEmpty(uid)) {
                //利用access_token获取第三方用户信息
                ThirdPartyUser thirdUser = ThirdPartyLoginHelper.getSinaUserinfo(json.getString("access_token"), uid);
                thirdUser.setOpenid("ceshiopenid");
                thirdUser.setProvider("SINA");
                // 返回token
                writeHrefHtml(request, response, thirdUser);
            } else {// 如果未获取到OpenID
                response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
            }
        } else {// 如果没有返回令牌，则直接返回到登录页面
            response.sendRedirect(ThirdResources.THIRDPARTY.getString("my_login"));
        }
    }

    @GetMapping("/cancel/callback/sina")
    public String sinaCancelCallback(HttpServletRequest request, ModelMap modelMap) {
        return ThirdResources.THIRDPARTY.getString("my_login");
    }

    private void writeHrefHtml(HttpServletRequest request, HttpServletResponse response, ThirdPartyUser thirdUser) throws Exception {
        ResultHelper resultHelper = thirdPartyLogin(request, thirdUser);
        String ip = HttpUtil.getIp(request);

        if ("10000".equals(resultHelper.getRespCode())) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Document</title>\n" +
                    "    <script>window.location.href='" + Constant.RETURN_URL.get(ip) + "?token=" + resultHelper.getData() + "'</script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>");
        }

    }

    private ResultHelper thirdPartyLogin(HttpServletRequest request, ThirdPartyUser param) throws Exception {
        AppUser sysUser;
        // 查询是否已经绑定过
        UserThirdparty userThirdparty = userThirdpartyService.getOne(new LambdaQueryWrapper<UserThirdparty>()
                .eq(UserThirdparty::getProviderType, param.getProvider())
                .eq(UserThirdparty::getOpenId, param.getOpenid()));
        if (StringUtils.isEmpty(userThirdparty)) {
            sysUser = userThirdpartyService.insertThirdPartyUser(param);
        } else {
            sysUser = IAppUserService.getById(userThirdparty.getUserNo());
        }

        LoginModel loginModel = new LoginModel();
        loginModel.setUsername(sysUser.getUsername() + "|THIRD");
        loginModel.setPassword(sysUser.getUsername());
        loginModel.setClientId("system");
        loginModel.setClientSecret("system");
        loginModel.setScope("app");
        loginModel.setGrantType("password");
        return oAuth2Controller.getUserTokenInfo(loginModel);
    }
}

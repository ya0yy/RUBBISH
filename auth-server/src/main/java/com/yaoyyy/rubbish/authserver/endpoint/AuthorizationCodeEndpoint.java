package com.yaoyyy.rubbish.authserver.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Map;
/**
 * 　　　　　　　 ┏┓　 ┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　┃
 * 　　　　　　　┃　　　━　　 ┃ ++ + + +
 * 　　　　　　 ████━████  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃ + + + +
 * 　　　　　　　　 ┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　 ┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃　　+
 * 　　　　　　　　 ┃　 　 ┗━━━┓ + +
 * 　　　　　　　　 ┃ 　　　　   ┣┓
 * 　　　　　　　　 ┃ 　　　　　 ┏┛
 * 　　　　　　　　 ┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　  ┃┫┫ ┃┫┫
 * 　　　　　　　　  ┗┻┛ ┗┻┛+ + + +
 * <p>
 * rubbish-parent
 * 2019-05-16 17:33
 *
 * 重写spring oauth自带的/oauth/authorize 流程，获取授权码
 * @author yaoyy
 */

@RestController
@SessionAttributes("authorizationRequest")
public class AuthorizationCodeEndpoint {

    @Autowired
    AuthorizationEndpoint authorizationEndpoint;

    @RequestMapping(value = "/oauth/authorize", method = RequestMethod.GET)
    public String authorize(Map<String, Object> model, @RequestParam Map<String, String> parameters,
                                  SessionStatus sessionStatus, Principal principal) {

        // 调用自带的authorize，获取视图
        ModelAndView authorize = authorizationEndpoint.authorize(model, parameters, sessionStatus, principal);
        // 从视图中拿出我们需要修改的数据
        Map<String, Object> model1 = authorize.getModel();

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model1.get("authorizationRequest");
        // 模拟用户同意授权
        authorizationRequest.setApproved(true);
        parameters.put(OAuth2Utils.USER_OAUTH_APPROVAL, String.valueOf(Boolean.TRUE));

        // 这里相当于在页面中用户点击同意的按钮操作
        View view = authorizationEndpoint.approveOrDeny(parameters, model1, sessionStatus, principal);
        // 从视图中拿到授权码
        RedirectView redirectView = (RedirectView) view;
        String url = redirectView.getUrl() == null ? "_code=_" : redirectView.getUrl();
        return url.split("code=")[1];
    }

}

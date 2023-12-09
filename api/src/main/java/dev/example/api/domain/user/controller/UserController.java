package dev.example.api.domain.user.controller;

import dev.example.api.common.api.Api;
import dev.example.api.common.error.UserErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.api.domain.alarm.model.Alarm;
import dev.example.api.domain.user.business.UserBusiness;
import dev.example.api.domain.user.controller.model.User;
import dev.example.api.domain.user.controller.model.request.UserJoinRequest;
import dev.example.api.domain.user.controller.model.request.UserLoginRequest;
import dev.example.api.domain.user.controller.model.response.UserJoinResponse;
import dev.example.api.domain.user.controller.model.response.UserLoginResponse;
import dev.example.api.domain.user.service.UserService;
import dev.example.api.utils.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserBusiness userBusiness;

    @PostMapping("/join")
    public Api<UserJoinResponse> join(
            @RequestBody Api<UserJoinRequest> request
    ) {
        UserJoinResponse response = userBusiness.join(request.getBody());
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<UserLoginResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        UserLoginResponse response = userBusiness.login(request);
        return Api.OK(response);
    }

    @GetMapping("/alarm")
    public Api<Page<Alarm>> alarm(
            Authentication authentication,
            Pageable pageable
    ) {
        User valiedUser = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new ApiException(UserErrorCode.INTERNAL_SERVER_ERROR));
        Page<Alarm> alarmList = userBusiness.getAlarmListCustom(valiedUser.getId(), pageable);


//        Page<Alarm> alarmList = userBusiness.getAlarmList(authentication.getName(), pageable);
        return Api.OK(alarmList);
    }



}

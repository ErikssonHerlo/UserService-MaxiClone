package com.erikssonherlo.user.application.usecase;

import com.erikssonherlo.common.application.anotation.UseCase;
import com.erikssonherlo.common.application.exception.BadRequestException;
import com.erikssonherlo.user.domain.model.User;
import com.erikssonherlo.user.infrastructure.port.input.GetUserInfo;
import com.erikssonherlo.user.infrastructure.port.output.UserJpaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class GetUserInfoUseCase implements GetUserInfo {

    private final UserJpaRepositoryPort userJpaRepositoryPort;
    @Override
    public Optional<User> getUserInfo(Authentication authentication) {
        System.out.println(authentication.toString());
        try{
            Optional<User> user = userJpaRepositoryPort.findByEmail(authentication.getName());
            return user;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}

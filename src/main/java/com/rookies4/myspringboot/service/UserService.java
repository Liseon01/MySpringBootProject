package com.rookies4.myspringboot.service;

import com.rookies4.myspringboot.controller.dto.UserDTO;
import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    //User 등록
    @Transactional
    public UserDTO.UserResponse createUser(UserDTO.UserCreateRequest request) {
        // Email 중복 검사
        userRepository.findByEmail(request.getEmail())
                .ifPresent(entity -> {
                    throw new BusinessException("User with this Email already Exist", HttpStatus.CONFLICT);
                });
        UserEntity entity = request.toEntity();
        UserEntity saveEntity = userRepository.save(entity);
        // Entity => DTO로 변환
        return  new UserDTO.UserResponse(saveEntity);

    }

    public UserDTO.UserResponse getUserById(Long id) {
        UserEntity userEntity = getUserExist(id);
        return new UserDTO.UserResponse(userEntity);
    }
    private UserEntity getUserExist(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }
    public List<UserDTO.UserResponse> getAllUSers() {
        return userRepository.findAll()
                .stream().map(entity -> new UserDTO.UserResponse(entity)).toList();
    }
    @Transactional
    public UserDTO.UserResponse updateUser(String email, UserDTO.UserUpdateRequest request) {
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        existUser.setName(request.getName());
        return new UserDTO.UserResponse(existUser);
    }
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = getUserExist(id);
        userRepository.delete(userEntity);
    }
}

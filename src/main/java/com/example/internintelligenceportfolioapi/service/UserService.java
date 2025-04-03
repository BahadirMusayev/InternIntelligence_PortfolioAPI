package com.example.internintelligenceportfolioapi.service;

import com.example.internintelligenceportfolioapi.dao.entity.UserEntity;
import com.example.internintelligenceportfolioapi.dao.exception.PasswordException;
import com.example.internintelligenceportfolioapi.dao.repository.UserRepository;
import com.example.internintelligenceportfolioapi.mapper.UserMapper;
import com.example.internintelligenceportfolioapi.model.output.UserDtoOutput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDtoOutput get() {
        log.info("Get Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        return userMapper.
                mapEntityToDtoOutput(userEntity);
    }

    @Transactional
    public void updateName(String newName) {
        log.info("Update Name Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userEntity.setName(newName);
        userRepository.save(userEntity);

        log.info("Update Name Ended ");
    }

    @Transactional
    public void updateSurname(String newSurname) {
        log.info("Update Surname Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userEntity.setSurname(newSurname);
        userRepository.save(userEntity);

        log.info("Update Surname Ended ");
    }

    @Transactional
    public void updateBirthDate(LocalDate newBirthDate) {
        log.info("Update Birth Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userEntity.setBirthDate(newBirthDate);
        userRepository.save(userEntity);

        log.info("Update Birth Date Ended ");
    }

    @Transactional
    public void deleteBirthDate() {
        log.info("Delete Birth Date Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userEntity.setBirthDate(null);
        userRepository.save(userEntity);

        log.info("Delete Birth Date Ended ");
    }

    @Transactional
    public void updateEmail(String newEmail) {
        log.info("Update Email Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userEntity.setEmail(newEmail);
        userRepository.save(userEntity);

        log.info("Update Email Ended ");
    }

    @Transactional
    public void updatePassword(String currentPassword, String newPassword) {
        log.info("Update Password Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        if (!passwordEncoder.matches(currentPassword, userEntity.getPassword())) {
            throw new PasswordException("Current password is incorrect !");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);

        log.info("Update Password Ended ");
    }

    @Transactional
    public void addSkill(String skill) {
        log.info("Add Skill Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        Set<String> skills = userEntity.getSkills();
        skills.add(skill);
        userRepository.save(userEntity);

        log.info("Add Skill Ended ");
    }

    @Transactional
    public void deleteSkill(String skill) {
        log.info("Delete Skill Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        Set<String> skills = userEntity.getSkills();
        Iterator<String> iterator = skills.iterator();
        while (iterator.hasNext()) {
            if (skill.equals(iterator.next())) {
                iterator.remove();
            }
        }
        userRepository.save(userEntity);

        log.info("Delete Skill Ended ");
    }

    @Transactional
    public void delete() {
        log.info("Delete Started... ");

        UserEntity userEntity = UserAuthService.getUser();
        userRepository.deleteById(userEntity.getId());

        log.info("Delete Ended ");
    }
}

package com.game.web.rest;


import com.game.biz.service.dto.PasswordChangeDTO;
import com.game.biz.service.dto.UserDTO;
import com.game.domain.Authority;
import com.game.domain.User;
import com.game.repository.UserRepository;
import com.game.security.AuthoritiesConstants;
import com.game.security.SecurityUtils;
import com.game.service.MailService;
import com.game.service.UserService;
import com.game.service.mapper.UserMapper;
import com.game.web.rest.errors.EmailAlreadyUsedException;
import com.game.web.rest.errors.EmailNotFoundException;
import com.game.web.rest.errors.InvalidPasswordException;
import com.game.web.rest.errors.LoginAlreadyUsedException;
import com.game.web.rest.vm.KeyAndPasswordVM;
import com.game.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {



    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
        public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }


    /**
     * {@code POST  /setadmin} : set admin.
     * @param userId the managed user View Model.
     */
    @PostMapping("/setadmin")
    public void setAdmin(@Valid @RequestBody Long userId) {
        UserMapper userMapper = new UserMapper();
        Optional<User> user = userService.getUserWithAuthorities(userId);
        User us = user.get();
        UserDTO usDto = userMapper.userToUserDTO(us);

        Set<Authority> authorities = us.getAuthorities();
        Authority adminAuth = new Authority();
        adminAuth.setName(AuthoritiesConstants.ADMIN);

        Authority userRole = new Authority();
        userRole.setName(AuthoritiesConstants.USER);

        if(us.getAuthorities().contains(adminAuth)){
            authorities.remove(adminAuth);
            authorities.add(userRole);
        }
        else{
            authorities.add(adminAuth);
        }
        us.setAuthorities(authorities);
        usDto.setAuthorities(
            authorities.stream().map(e->e.getName()).collect(Collectors.toSet())
        );

        userService.save(us);
        userService.updateUser(usDto);
    }


    /**
     * {@code POST  /setadmin} : set admin.
     * @param userId the managed user View Model.
     */
    @PostMapping("/removeUserRole")
    public void removeUserRole(@Valid @RequestBody Long userId) {

        Optional<User> user = userService.getUserWithAuthorities(userId);
        User us = user.get();
        Set<Authority> authorities = us.getAuthorities();
        Authority userRole = new Authority();
        userRole.setName(AuthoritiesConstants.USER);
        authorities.remove(userRole);
        us.setAuthorities(authorities);
        if(us.getAuthorities().isEmpty()){
            userService.deactivateUser(us);
        }
        userService.save(us);
    }

    /**
     * {@code POST  /setadmin} : set admin.
     * @param userId the managed user View Model.
     */
    @PostMapping("/addUserRole")
    public void addUserRole(@Valid @RequestBody Long userId) {

        Optional<User> user = userService.getUserWithAuthorities(userId);
        User us = user.get();

        if(us.getActivationKey() != null){
            userService.activateRegistration(us.getActivationKey());
        }
        else {
            Set<Authority> authorities = us.getAuthorities();
            Authority userRole = new Authority();
            userRole.setName(AuthoritiesConstants.USER);
            authorities.add(userRole);
            us.setAuthorities(authorities);
            userService.reactivateUser(us);
            userService.save(us);
        }
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount( @RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}

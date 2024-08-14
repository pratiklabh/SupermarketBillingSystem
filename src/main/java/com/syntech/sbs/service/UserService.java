package com.syntech.sbs.service;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }
    
    public User authenticate(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
    
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User findUserByPhone(String phone){
        return userRepository.findByPhone(phone);
    }
}
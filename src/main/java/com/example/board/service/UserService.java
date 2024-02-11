package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.domain.Role;
import com.example.board.domain.User;
import com.example.board.repository.RoleRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor // lombok이 final필드를 초기화하는 생성자를 자동으로 생성한다.
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User addUser(String name, String email, String password){
        Optional<User> optionalUser= userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new RuntimeException("이미가입된 이메일입니다.");
        }
        Role role = roleRepository.findByName("ROLE_USER").get();
        User user = new User();
        user.setPassword(password);
        user.setRoles(Set.of(role));
        user.setEmail(email);
        user.setName(name);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User getUser(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<String> getRoles(int userId) {
        Set<Role> roles =  userRepository.findById(userId).orElseThrow().getRoles();
        List<String> list = new ArrayList<>();
        for(Role role:roles){
            list.add(role.getName());
        }
        return list;
    }
}

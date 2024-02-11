package com.example.board.controller;

import com.example.board.domain.User;
import com.example.board.dto.LoginInfo;
import com.example.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/userRegForm")
    public String userRegForm(){
        return "userRegForm";
    }

    /**
     * 회원 정보를 등록
     * @param name
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/userReg")
    public String userReg(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password
    ){
        System.out.println("name : "+name);
        System.out.println("email : "+email);
        System.out.println("password : "+password);

        userService.addUser(name,email,password);

        return "redirect:/welcome";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "loginform";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession httpSession //Spring이 자동으로 session을 처리하는 HttpSession객체를 넣어줌
    ){
        //email 에 해당하는 회원정보를 읽어온후 아이디 암호가 맞다면 세션에 회원정보를 저장.
        System.out.println("email : "+ email);
        System.out.println("password : "+ password);
        try{
            User user = userService.getUser(email);
            if(user.getPassword().equals(password)){
                System.out.println("비밀번호가 같습니다.");
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getEmail(), user.getName());

                List<String> roles = userService.getRoles(user.getUserId());
                loginInfo.setRoles(roles);

                httpSession.setAttribute("loginInfo", loginInfo);
                System.out.println("세션에 로그인 정보가 저장됨");
            }else{
                throw new RuntimeException("이메일이나 암호가 일치하지 않습니다.");
            }
            System.out.println(user);
        }catch(Exception ex){
            return "redirect:/loginform?error=true";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        //세션에서 회원정보를 삭제
        session.removeAttribute("loginInfo");
        return "redirect:/";
    }
}

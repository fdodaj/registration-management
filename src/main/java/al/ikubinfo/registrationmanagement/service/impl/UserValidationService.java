package al.ikubinfo.registrationmanagement.service.impl;


import al.ikubinfo.registrationmanagement.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {
    public String validatePhoneNumber(UserDto userDto){
        String message = "";
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (userDto.getPhoneNumber().matches(pattern)) {
            System.out.println("Valid");
        }
        else {
            message = "* Please enter a correct phone number";
        }
        return message;
    }

}

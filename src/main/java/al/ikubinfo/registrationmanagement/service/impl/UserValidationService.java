package al.ikubinfo.registrationmanagement.service.impl;


import al.ikubinfo.registrationmanagement.dto.UserDto;
import al.ikubinfo.registrationmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository repository;

    public String validatePhoneNumber(UserDto userDto) {
        StringBuilder message = new StringBuilder();
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (!userDto.getPhoneNumber().matches(pattern)) {
            message.append("* Please enter a correct phone number");
        }
        return message.toString();
    }

    public String validateUniqueUser(UserDto userDto) {
        String message = "";
        if (repository.getByEmail(userDto.getEmail()) != null) {
            message = "User already exists ";
        }
        return message;
    }

}

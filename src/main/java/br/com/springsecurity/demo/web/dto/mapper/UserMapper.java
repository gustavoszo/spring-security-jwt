package br.com.springsecurity.demo.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.springsecurity.demo.entity.User;
import br.com.springsecurity.demo.web.dto.UserCreateDto;
import br.com.springsecurity.demo.web.dto.UserResponseDto;

public class UserMapper {
    
    public static User toUser(UserCreateDto dto) {
        return new ModelMapper().map(dto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        String role = user.getRole().getRole();
        // Classe anônima da ModelMapper, a partir do objeto PropertyMap
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                // A partir do método map, é feito o acesso aos campos presentes em UsuarioResponseDto
                map().setRole(role);
            }  
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<User> users) {
        return users.stream()
            .map(user -> UserMapper.toDto(user))
            .collect(Collectors.toList());
    }

}

package com.gen.eChannel.verification.services.impl;

import com.gen.eChannel.verification.dto.AddressDto;
import com.gen.eChannel.verification.dto.LoginDto;
import com.gen.eChannel.verification.dto.UserDto;
import com.gen.eChannel.verification.entities.Address;
import com.gen.eChannel.verification.entities.Status;
import com.gen.eChannel.verification.entities.User;
import com.gen.eChannel.verification.exceptions.ApiException;
import com.gen.eChannel.verification.exceptions.ResourceNotFoundException;
import com.gen.eChannel.verification.repositories.AddressRepo;
import com.gen.eChannel.verification.repositories.StatusRepo;
import com.gen.eChannel.verification.repositories.UserRepo;
import com.gen.eChannel.verification.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private StatusRepo statusRepo;

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, AddressRepo addressRepo) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.addressRepo = addressRepo;
    }


    @Override
    public UserDto createUser(UserDto userDto) {

        Optional<User> user = userRepo.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new ResourceNotFoundException("User already exists", "User Id", user.get().getId());
        }

        AddressDto addressDto = userDto.getAddress();
        Address address = modelMapper.map(addressDto, Address.class);
        Address addressCreated = addressRepo.save(address);

        User user1 =modelMapper.map(userDto, User.class);
        user1.setAddress(addressCreated);

        User savedUsers = userRepo.save(user1);
        return  modelMapper.map(savedUsers, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepo.findAll();
        List<UserDto> userDtos =users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public LoginDto login(LoginDto loginDto) {

        String tempEmail = loginDto.getEmail();
        String tempPass = loginDto.getPassword();

        User user = new User();
        if (tempEmail != null && tempPass != null) {
            user = userRepo.findByEmailAndPassword(tempEmail, tempPass)
                    .orElseThrow(() -> new ApiException("Invalid username or password !!"));
        }
        return modelMapper.map(user, LoginDto.class);
    }


}

package lk.ijse.crop_monitoring_backend.controller;

import lk.ijse.crop_monitoring_backend.dto.AuthDTO;
import lk.ijse.crop_monitoring_backend.dto.ResponseDTO;
import lk.ijse.crop_monitoring_backend.dto.StaffDTO;
import lk.ijse.crop_monitoring_backend.dto.UserDTO;
import lk.ijse.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_backend.service.StaffService;
import lk.ijse.crop_monitoring_backend.service.UserService;
import lk.ijse.crop_monitoring_backend.util.Enums.Designation;
import lk.ijse.crop_monitoring_backend.util.Enums.FieldName;
import lk.ijse.crop_monitoring_backend.util.Enums.Gender;
import lk.ijse.crop_monitoring_backend.util.Enums.Role;
import lk.ijse.crop_monitoring_backend.util.JwtUtil;
import lk.ijse.crop_monitoring_backend.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staff")
@CrossOrigin
@RequiredArgsConstructor
public class StaffManageController {
    @Autowired
    private final StaffService staffService;

    @Autowired
    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> addStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffService.addStaff(staffDTO);

            UserDTO userDTO = new UserDTO();

            userDTO.setEmail(staffDTO.getEmail());
            userDTO.setRole(staffDTO.getRole());
            userDTO.setPassword(userDTO.getPassword());

            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    System.out.println("save control-switch");
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        // Check user role
        if ("MANAGER".equals(loadedUser.getRole())) {
            // Handle admin login
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole("MANAGER");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Manager Login Success", authDTO));

        } else if ("ADMINISTRATIVE".equals(loadedUser.getRole())) {
            // Handle user login
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole("ADMINISTRATIVE");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Administrative Login Success", authDTO));

        } else if ("SCIENTIST".equals(loadedUser.getRole())) {
            // Handle user login
            String token = jwtUtil.generateToken(loadedUser);
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
            }

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);
            authDTO.setRole("SCIENTIST");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Scientist Login Success", authDTO));

        } else if ("OTHER".equals(loadedUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, "Others Cannot Log Into This System", null));

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, "Invalid Role", null));
        }
    }

    @GetMapping(value = "/designations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDesignations() {
        List<String> designations = Arrays.stream(Designation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(designations);
    }

    @GetMapping(value = "/genders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getGenders() {
        List<String> genders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(genders);
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getRoles() {
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}

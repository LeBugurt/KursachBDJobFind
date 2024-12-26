package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.UserService;
import bd.hh.kursach.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class HrController {

    private final UserService userService;

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(userService.deleteUserById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }
}

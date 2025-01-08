package com.company.coursemanager.file.controller;

import com.company.coursemanager.file.service.FileService;
import com.company.coursemanager.file.service.FileServiceImpl;
import com.company.coursemanager.users.service.UserService;
import com.company.coursemanager.users.service.UserServiceImpl;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "File Management")
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    /**
     * Uploads a profile image for the user
     *
     * @param file The image file to be uploaded
     * @return Response containing the updated user information
     */
    @PostMapping("/profile-image")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userService.updateProfileImage(file));
        return RestHelper.responseSuccess(response);
    }

    /**
     * Retrieves a profile image
     *
     * @param fileName The name of the file to retrieve
     * @return The image resource
     */
    @GetMapping("/profile-image/{fileName:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Deletes a profile image
     *
     * @param fileName The name of the file to delete
     * @return Response indicating success of the operation
     */
    @DeleteMapping("/profile-image/{fileName:.+}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> deleteProfileImage(@PathVariable String fileName) {
        String message = fileService.deleteFile(fileName);
        return RestHelper.responseMessage(message);
    }
}

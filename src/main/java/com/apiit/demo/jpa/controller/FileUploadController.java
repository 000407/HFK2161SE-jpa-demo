package com.apiit.demo.jpa.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apiit.demo.jpa.service.StorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/tests")
@Log4j2
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(@Qualifier("databaseStorageService") StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/upload")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }

    @GetMapping("/files/view/{id}")
    public String viewFile(@PathVariable Long id, Model model) {
        model.addAttribute("fileId", id);
        return "viewImage";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/tests/file/upload";
    }

    @GetMapping("/files/get/{id}")
    public @ResponseBody byte[] serveFile(@PathVariable long id, HttpServletResponse response) {
        response.setContentType("image/jpeg"); // TODO: Make the content type dynamic
        return storageService.loadFileBytes(id);
    }
}

package com.rfid.tcc.controller;

import com.rfid.tcc.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/libras")
public class AcaoController {

    @Autowired
    private AcaoService acaoService;

    @PostMapping
    public void upload(@RequestParam MultipartFile file, @RequestParam Integer codigorfid) {
        acaoService.salvarArquivo(file, codigorfid);
    }

    @GetMapping("/{diretorio}")
    public String listarArquivos(@PathVariable String diretorio){

        return acaoService.analisar(diretorio);
    }
}

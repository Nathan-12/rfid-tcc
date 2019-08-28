package com.rfid.tcc.service.impl;

import com.rfid.tcc.model.Acao;
import com.rfid.tcc.repository.AcaoRepository;
import com.rfid.tcc.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class AcaoServiceImpl implements AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;

    @Value("${documents.folder}")
    private String diretorio;

    @Override
    public Acao salvarAcao(MultipartFile file){
        Acao acao = new Acao();

        String renomearName = Integer.toString(acao.getCodigorfid());
        acao.setNome(renomearName);
        acaoRepository.save(acao);
        acao.setCaminho(diretorio + acao.getId());
        acaoRepository.save(acao);

        try {
            File arquivoFile = new File(diretorio, String.valueOf(acao.getId()));
            FileOutputStream fop = new FileOutputStream(arquivoFile);
            arquivoFile.createNewFile();
            fop.write(file.getBytes());
            fop.flush();
            fop.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return acao;
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}

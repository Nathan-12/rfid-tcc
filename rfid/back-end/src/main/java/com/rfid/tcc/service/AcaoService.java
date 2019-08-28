package com.rfid.tcc.service;

import com.rfid.tcc.model.Acao;
import org.springframework.web.multipart.MultipartFile;

public interface AcaoService {


    Acao salvarAcao(MultipartFile file);
}

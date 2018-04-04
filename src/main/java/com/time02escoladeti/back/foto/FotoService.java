package com.time02escoladeti.back.foto;

import com.time02escoladeti.back.Recursos.BackEndApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class FotoService {
    private final FotoRepository repository;
    private final BackEndApplicationProperties applicationProperties;

    @Autowired
    public FotoService(FotoRepository repository, BackEndApplicationProperties applicationProperties) {
        this.repository = repository;
        this.applicationProperties = applicationProperties;
    }

    public FotoId cadastrar(MultipartFile foto) throws IOException {
        String[] split = foto.getOriginalFilename().split(Pattern.quote("."));
        String extensao = split[split.length - 1];
        String nomeGerado = gerarNome(extensao);

        foto.transferTo(new File(applicationProperties.getPathPhoto(), nomeGerado));

        if (!new File(applicationProperties.getPathPhoto(), nomeGerado).exists()) {
            throw new RuntimeException("Não foi possível salvar o arquivo no disco");
        }

        Foto fotoEntidade = new Foto(nomeGerado);
        repository.save(fotoEntidade);
        return fotoEntidade.getId();
    }

    private static String gerarNome(String extensao) {
        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssSSS", Locale.getDefault());

        return String.format("%s.%s", format.format(time), extensao);
    }

    public FotoDto get(FotoId id) {
        Foto foto = repository.findOne(id);

        String url = String.format("%s%s", applicationProperties.getFilesUrl(), foto.getNomeFoto());
        return new FotoDto(foto.getId(), foto.getNomeFoto(), url);
    }
}

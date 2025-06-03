package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.DocumentosTramiteRequest;
import com.agendalc.agendalc.dto.DocumentosTramiteResponse;

public interface DocumentosTramiteService {

    DocumentosTramiteResponse createDocumentosTramite(DocumentosTramiteRequest request);

}

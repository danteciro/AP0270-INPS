package gob.osinergmin.inpsweb.service.dao.impl;



import javax.inject.Inject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gob.osinergmin.inpsweb.domain.MdiDocumentoAdjunto;
import gob.osinergmin.inpsweb.domain.builder.MdiDocumentoAdjuntoBuilder;
import gob.osinergmin.inpsweb.service.dao.CrudDAO;
import gob.osinergmin.inpsweb.service.dao.MdiDocumentoAdjuntoDAO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;


@Service("mdiDocumentoAdjuntoDAO")
public class MdiDocumentoAdjuntoDAOImpl implements MdiDocumentoAdjuntoDAO{
    private static final Logger LOG = LoggerFactory.getLogger(MdiDocumentoAdjuntoDAOImpl.class);

   
    @Inject
    private CrudDAO crud;

    @Override
    public DocumentoAdjuntoDTO get(Long idDocumentoAdjunto){
        MdiDocumentoAdjunto documentoAdjunto = crud.find(idDocumentoAdjunto, MdiDocumentoAdjunto.class);
        DocumentoAdjuntoDTO documentoAdjuntoDTO = MdiDocumentoAdjuntoBuilder.toDocumentoAdjuntoDto(documentoAdjunto);
        return documentoAdjuntoDTO;
    }

	@Override
	public DocumentoAdjuntoDTO findDocumentoAdjunto(Long idDocumentoAdjunto) {
		DocumentoAdjuntoDTO retorno = null;
		try{
	        MdiDocumentoAdjunto registroDTO = crud.find(idDocumentoAdjunto, MdiDocumentoAdjunto.class);
	        retorno = MdiDocumentoAdjuntoBuilder.toDocumentoAdjuntoDto(registroDTO);
	    }catch(Exception ex){
	        LOG.error("",ex);
	    }
	    return retorno;
	}
	
    
}

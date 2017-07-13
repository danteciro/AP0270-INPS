/**
* Resumen		
* Objeto		: PghDocumentoAdjuntoBuilder.java
* Descripción		: PghDocumentoAdjuntoBuilder
* Fecha de Creación	: 
* PR de Creación	:
* Autor			:   
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                        Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE791-RSIS42   18/10/2016      Alexander Vilca Narvaez       Adecuar la funcionalidad "CERRAR ORDEN" cuando se atiende una orden de levantamiento DSR-CRITICIDAD
* 
*/ 


package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiMaestroColumna;
import gob.osinergmin.inpsweb.domain.PghDetalleLevantamiento;
import gob.osinergmin.inpsweb.domain.PghDetalleSupervision;
import gob.osinergmin.inpsweb.domain.PghDocumentoAdjunto;
import gob.osinergmin.inpsweb.domain.PghOrdenServicio;
import gob.osinergmin.inpsweb.domain.PghSupervision;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import java.util.ArrayList;
import java.util.List;

public class PghDocumentoAdjuntoBuilder {
    
	public static PghDocumentoAdjunto toDocumentoAdjuntoDomain(DocumentoAdjuntoDTO registro) {
		PghDocumentoAdjunto registroDomain = new PghDocumentoAdjunto();
		if(registro!=null){
			if(registro.getIdDocumentoAdjunto()!=null){
				registroDomain.setIdDocumentoAdjunto(registro.getIdDocumentoAdjunto());
			}
			registroDomain.setDescripcionDocumento(registro.getDescripcionDocumento());
			registroDomain.setNombreDocumento(registro.getNombreArchivo());
			registroDomain.setArchivoAdjunto(registro.getArchivoAdjunto());
			if(registro.getDetalleSupervision()!=null && registro.getDetalleSupervision().getIdDetalleSupervision()!=null){
				PghDetalleSupervision detalleSupervision = new PghDetalleSupervision();
				detalleSupervision.setIdDetalleSupervision(registro.getDetalleSupervision().getIdDetalleSupervision());
				registroDomain.setIdDetalleSupervision(detalleSupervision);
			}
			if(registro.getSupervision()!=null && registro.getSupervision().getIdSupervision()!=null){
				PghSupervision supervision = new PghSupervision();
				supervision.setIdSupervision(registro.getSupervision().getIdSupervision());
				registroDomain.setIdSupervision(supervision);
			}
                        if(registro.getIdTipoDocumento()!=null){
                            MdiMaestroColumna idTipoDocumento= new MdiMaestroColumna(registro.getIdTipoDocumento().getIdMaestroColumna());
                            registroDomain.setIdTipoDocumento(idTipoDocumento);
                        }
                        if(registro.getOrdenServicio()!=null){
                            PghOrdenServicio ordenServicio=new PghOrdenServicio(registro.getOrdenServicio().getIdOrdenServicio());
                            registroDomain.setIdOrdenServicio(ordenServicio);
                        }
                         if(registro.getIdDetalleLevantamiento()!=null){
                             PghDetalleLevantamiento idDetalleLevantamiento = new PghDetalleLevantamiento(registro.getIdDetalleLevantamiento());
                            registroDomain.setIdDetalleLevantamiento(idDetalleLevantamiento);
                        }
                        
		}
		
		return registroDomain;
	}
	
	public static DocumentoAdjuntoDTO toDocumentoAdjuntoDTO(PghDocumentoAdjunto registro) {
            DocumentoAdjuntoDTO registroDTO = new DocumentoAdjuntoDTO();
            if(registro!=null){
                registroDTO.setIdDocumentoAdjunto(registro.getIdDocumentoAdjunto());
                registroDTO.setDescripcionDocumento(registro.getDescripcionDocumento());
                registroDTO.setNombreArchivo(registro.getNombreDocumento());
                if (registro.getArchivoAdjunto()!=null) {
                        registroDTO.setArchivoAdjunto(registro.getArchivoAdjunto());
                }
                if(registro.getIdTipoDocumento()!=null){
                    MaestroColumnaDTO tipoDoc=new MaestroColumnaDTO();
                    tipoDoc.setIdMaestroColumna(registro.getIdTipoDocumento().getIdMaestroColumna());
                    tipoDoc.setDescripcion(registro.getIdTipoDocumento().getDescripcion());
                    /* OSINE791 RSIS42 - Inicio */
                    tipoDoc.setCodigo(registro.getIdTipoDocumento().getCodigo());
                    /* OSINE791 RSIS42 - Fin */
                    registroDTO.setIdTipoDocumento(tipoDoc);                    
                }
                registroDTO.setFechaCarga(registro.getFechaCreacion());
                registroDTO.setFlagEnviadoSiged(registro.getFlagEnviadoSiged());
                DetalleSupervisionDTO detalleSupervision = new DetalleSupervisionDTO();
                detalleSupervision.setIdDetalleSupervision(registro.getIdDetalleSupervision() == null ? null : registro.getIdDetalleSupervision().getIdDetalleSupervision());
                registroDTO.setDetalleSupervision(detalleSupervision);
            }
            return registroDTO;
	}
	
	public static List<DocumentoAdjuntoDTO> toListDocumentoAdjuntoDTO(List<PghDocumentoAdjunto> lista) {
		DocumentoAdjuntoDTO registroDTO;
        List<DocumentoAdjuntoDTO> retorno = new ArrayList<DocumentoAdjuntoDTO>();
        if (lista != null) {
            for (PghDocumentoAdjunto maestro : lista) {
                registroDTO = toDocumentoAdjuntoDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
}

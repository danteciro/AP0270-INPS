/**
* Resumen
* Objeto			: DetaSupeCampRechCargaBuilder.java
* Descripción		: Objeto que transmite datos del domain al DTO y viceversa.
* PR de Creación	: OSINE_SFS-1063.
* Fecha de Creación	: 23/09/2016.
* Autor				: Hernan Torres Saenz.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* 
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.dse_common.domain.dto.ReleDTO;
import gob.osinergmin.inpsweb.domain.NpsDetaSupeCampRechCarga;
import gob.osinergmin.inpsweb.domain.NpsSupeCampRechCarga;
import gob.osinergmin.inpsweb.util.Constantes;
import gob.osinergmin.inpsweb.util.Utiles;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaDTO;
import gob.osinergmin.mdicommon.domain.dto.DetaSupeCampRechCargaRepDTO;
import gob.osinergmin.mdicommon.domain.dto.ReleServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.SupeCampRechCargaDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DetaSupeCampRechCargaBuilder {
	
	public static List<DetaSupeCampRechCargaDTO> toListDetaSupeCampRechCargaDto(List<NpsDetaSupeCampRechCarga> lista){
		DetaSupeCampRechCargaDTO detaSupeCampRechCargaDTO;
		List<DetaSupeCampRechCargaDTO> retorno = new ArrayList<DetaSupeCampRechCargaDTO>();
	    if(lista != null && lista.size()>0){
	    	for (NpsDetaSupeCampRechCarga maestro : lista) {
	    		detaSupeCampRechCargaDTO = toDetaSupeCampRechCargaDto(maestro);
                retorno.add(detaSupeCampRechCargaDTO);
            }
	    }
	    return retorno;
	}
	
	public static DetaSupeCampRechCargaDTO toDetaSupeCampRechCargaDto(NpsDetaSupeCampRechCarga registro) {
		DetaSupeCampRechCargaDTO registroDTO = new DetaSupeCampRechCargaDTO();
        
		registroDTO.setIdDetaSupeCampRechCarga(registro.getIdDetaSupeCampRechCarga());
		
		if(registro.getIdSupeCampRechCarga()!=null && registro.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null){
			SupeCampRechCargaDTO supeCamp = new SupeCampRechCargaDTO();
			supeCamp.setIdSupeCampRechCarga(registro.getIdSupeCampRechCarga().getIdSupeCampRechCarga());
			registroDTO.setIdSupeCampRechCarga(supeCamp);
		}
		
		registroDTO.setIdRele(registro.getIdRele().intValue());
		registroDTO.setReleUmbralHz(registro.getReleUmbralHz());
		registroDTO.setReleUmbralS(registro.getReleUmbralS());
		registroDTO.setReleDerivadaHz(registro.getReleDerivadaHz());
		registroDTO.setReleDerivadaHzs(registro.getReleDerivadaHzs());
		registroDTO.setReleDerivadaS(registro.getReleDerivadaS());
		registroDTO.setDemandaMaxima(registro.getDemandaMaxima());
		registroDTO.setDemandaMedia(registro.getDemandaMedia());
		registroDTO.setDemandaMinima(registro.getDemandaMinima());
		registroDTO.setDemandaMw(registro.getDemandaMw());
		registroDTO.setObservaciones(registro.getObservaciones());
		
		if(registro.getHora()!=null){
			registroDTO.setHora((String.valueOf(registro.getHora())).substring(11, 16));
		}
		
		registroDTO.setFlgFiscalizado(registro.getFlgFiscalizado());
		registroDTO.setFlgExisteReleUmbral(registro.getFlgExisteReleUmbral());
		registroDTO.setFlgExisteReleDerivada(registro.getFlgExisteReleDerivada());
		registroDTO.setFlgAjusteReleUmbral(registro.getFlgAjusteReleUmbral());
		registroDTO.setFlgAjusteReleDerivada(registro.getFlgAjusteReleDerivada());
		registroDTO.setFlgOtroAjusteUmbral(registro.getFlgOtroAjusteUmbral());
		registroDTO.setFlgOtroAjusteDerivada(registro.getFlgOtroAjusteDerivada());
		registroDTO.setFlgProtocoloUmbral(registro.getFlgProtocoloUmbral());
		registroDTO.setFlgProtocoloDerivada(registro.getFlgProtocoloDerivada());
		registroDTO.setPotr(registro.getPotr());
		registroDTO.setEstado(registro.getEstado());
		
        return registroDTO;
    }
	
	public static NpsDetaSupeCampRechCarga getNpsDetaSupeCampRechCarga(DetaSupeCampRechCargaDTO registroDTO) {
		NpsDetaSupeCampRechCarga registro = new NpsDetaSupeCampRechCarga();
        
		registro.setIdDetaSupeCampRechCarga(registroDTO.getIdDetaSupeCampRechCarga());
		
		if(registroDTO.getIdSupeCampRechCarga()!=null && registroDTO.getIdSupeCampRechCarga().getIdSupeCampRechCarga()!=null){
			NpsSupeCampRechCarga supeCamp = new NpsSupeCampRechCarga();
			supeCamp.setIdSupeCampRechCarga(registroDTO.getIdSupeCampRechCarga().getIdSupeCampRechCarga());
			registro.setIdSupeCampRechCarga(supeCamp);
		}
		
		registro.setIdRele(BigInteger.valueOf(registroDTO.getIdRele()));
		registro.setReleUmbralHz(registroDTO.getReleUmbralHz());
		registro.setReleUmbralS(registroDTO.getReleUmbralS());
		registro.setReleDerivadaHz(registroDTO.getReleDerivadaHz());
		registro.setReleDerivadaHzs(registroDTO.getReleDerivadaHzs());
		registro.setReleDerivadaS(registroDTO.getReleDerivadaS());
		registro.setDemandaMaxima(registroDTO.getDemandaMaxima());
		registro.setDemandaMedia(registroDTO.getDemandaMedia());
		registro.setDemandaMinima(registroDTO.getDemandaMinima());
		registro.setDemandaMw(registroDTO.getDemandaMw());
		registro.setObservaciones(registroDTO.getObservaciones());
		
		if(registroDTO.getHora()!=null && !registroDTO.getHora().equals("")){
			registro.setHora(Utiles.stringToDate(registroDTO.getHora(),Constantes.FORMATO_FECHA_CORTA));
		}
		
		registro.setFlgFiscalizado(registroDTO.getFlgFiscalizado());
		registro.setFlgExisteReleUmbral(registroDTO.getFlgExisteReleUmbral());
		registro.setFlgExisteReleDerivada(registroDTO.getFlgExisteReleDerivada());
		registro.setFlgAjusteReleUmbral(registroDTO.getFlgAjusteReleUmbral());
		registro.setFlgAjusteReleDerivada(registroDTO.getFlgAjusteReleDerivada());
		registro.setFlgOtroAjusteUmbral(registroDTO.getFlgOtroAjusteUmbral());
		registro.setFlgOtroAjusteDerivada(registroDTO.getFlgOtroAjusteDerivada());
		registro.setFlgProtocoloUmbral(registroDTO.getFlgProtocoloUmbral());
		registro.setFlgProtocoloDerivada(registroDTO.getFlgProtocoloDerivada());
		registro.setPotr(registroDTO.getPotr());
		registro.setEstado(registroDTO.getEstado());
		
        return registro;
    }

	public static ReleServicioDTO reletorele(ReleDTO rele) {
		ReleServicioDTO releServicio=new ReleServicioDTO();
		if(rele!=null){
			releServicio.setAlimentador(rele.getAlimentador());
			releServicio.setCodigoRele(rele.getCodigoRele());
			releServicio.setCodInterrupcion(rele.getCodInterrupcion());
			releServicio.setDemandaMax(rele.getDemandaMax());
			releServicio.setDemandaMed(rele.getDemandaMed());
			releServicio.setDemandaMin(rele.getDemandaMin());
			releServicio.setEtapa(rele.getEtapa());
			releServicio.setFechaImplementacion(rele.getFechaImplementacion());
			releServicio.setIdRele(rele.getIdRele());
			releServicio.setkV(rele.getkV());
			releServicio.setMarca(rele.getMarca());
			releServicio.setModelo(rele.getModelo());
			releServicio.setPotR(rele.getPotR());
			releServicio.setReleDerivadaHZ(rele.getReleDerivadaHZ());
			releServicio.setReleDerivadaHZS(rele.getReleDerivadaHZS());
			releServicio.setReleDerivadaS(rele.getReleDerivadaS());
			releServicio.setReleUmbralHz(rele.getReleUmbralHz());
			releServicio.setReleUmbralS(rele.getReleUmbralS());
			releServicio.setSerie(rele.getSerie());
			releServicio.setSubEstacion(rele.getSubEstacion());
			releServicio.setDemandaMwFecha(rele.getDemandaMwFecha());
			releServicio.setDemandaMaximaHora(rele.getDemandaMaximaHora());
			releServicio.setDemandaMediaHora(rele.getDemandaMediaHora());
			releServicio.setDemandaMinimaHora(rele.getDemandaMinimaHora());
		}		
		return releServicio;
	}

	public static DetaSupeCampRechCargaRepDTO reletoreporte(DetaSupeCampRechCargaDTO deta) {
		DetaSupeCampRechCargaRepDTO reporte = new DetaSupeCampRechCargaRepDTO();
		if(deta!=null){
			
			reporte.setIdDetaSupeCampRechCarga(deta.getIdDetaSupeCampRechCarga());
			reporte.setIdRele(deta.getIdRele());
			
			reporte.setReleUmbralHz(deta.getReleUmbralHz());
			reporte.setReleUmbralS(deta.getReleUmbralS());
			reporte.setReleDerivadaHz(deta.getReleDerivadaHz());
			reporte.setReleDerivadaHzs(deta.getReleDerivadaHzs());
			reporte.setReleDerivadaS(deta.getReleDerivadaS());
			reporte.setDemandaMaxima(deta.getDemandaMaxima());
			reporte.setDemandaMedia(deta.getDemandaMedia());
			reporte.setDemandaMinima(deta.getDemandaMinima());
			reporte.setDemandaMw(deta.getDemandaMw());
			reporte.setObservaciones(deta.getObservaciones());
			reporte.setHora(deta.getHora());
			reporte.setFlgFiscalizado(deta.getFlgFiscalizado());
			reporte.setFlgExisteReleUmbral(deta.getFlgExisteReleUmbral());
			reporte.setFlgExisteReleDerivada(deta.getFlgExisteReleDerivada());
			reporte.setFlgAjusteReleUmbral(deta.getFlgAjusteReleUmbral());
			reporte.setFlgAjusteReleDerivada(deta.getFlgAjusteReleDerivada());
			reporte.setFlgOtroAjusteUmbral(deta.getFlgOtroAjusteUmbral());
			reporte.setFlgOtroAjusteDerivada(deta.getFlgOtroAjusteDerivada());
			reporte.setFlgProtocoloUmbral(deta.getFlgProtocoloUmbral());
			reporte.setFlgProtocoloDerivada(deta.getFlgProtocoloDerivada());
			reporte.setPotr(deta.getPotr());
			reporte.setEstado(deta.getEstado());
			reporte.setDemandaMw(deta.getDemandaMw());
			reporte.setDemandaMaximaHora(deta.getDemandaMaximaHora());
			reporte.setDemandaMediaHora(deta.getDemandaMinimaHora());
			reporte.setDemandaMinimaHora(deta.getDemandaMinimaHora());
			
			/*EMPIEZA R's*/
			if(deta.getReleServicio()!=null){
				reporte.setIdReleR(deta.getReleServicio().getIdRele());
				reporte.setCodigoReleR(deta.getReleServicio().getCodigoRele());
				reporte.setMarcaR(deta.getReleServicio().getMarca());
				reporte.setModeloR(deta.getReleServicio().getModelo());
				reporte.setSerieR(deta.getReleServicio().getSerie());
				reporte.setSubEstacionR(deta.getReleServicio().getSubEstacion());
				reporte.setkVR(deta.getReleServicio().getkV());
				reporte.setAlimentadorR(deta.getReleServicio().getAlimentador());
				reporte.setCodInterrupcionR(deta.getReleServicio().getCodInterrupcion());
				reporte.setFechaImplementacionR(deta.getReleServicio().getFechaImplementacion());
				reporte.setEtapaR(deta.getReleServicio().getEtapa());
				reporte.setReleUmbralHzR(deta.getReleServicio().getReleUmbralS());
				reporte.setReleUmbralSR(deta.getReleServicio().getReleUmbralS());
				reporte.setReleDerivadaHZR(deta.getReleServicio().getReleDerivadaHZ());
				reporte.setReleDerivadaHZSR(deta.getReleServicio().getReleDerivadaHZS());
				reporte.setReleDerivadaSR(deta.getReleServicio().getReleDerivadaS());
				reporte.setDemandaMaxR(deta.getReleServicio().getDemandaMax());		
				reporte.setDemandaMedR(deta.getReleServicio().getDemandaMed());
				reporte.setDemandaMinR(deta.getReleServicio().getDemandaMin());
				reporte.setPotRR(deta.getReleServicio().getPotR());
				reporte.setDemandaMwFechaR(deta.getReleServicio().getDemandaMwFecha());
				reporte.setDemandaMaximaHoraR(deta.getReleServicio().getDemandaMaximaHora());
				reporte.setDemandaMediaHoraR(deta.getReleServicio().getDemandaMediaHora());
				reporte.setDemandaMinimaHoraR(deta.getReleServicio().getDemandaMinimaHora());
			}
			
					
		}
		return reporte;
	}
}

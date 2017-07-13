/**
* Resumen
* Objeto		: UnidadSupervisadaBuilder.java
* Descripción		: Constructor de UnidadSupervisada
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     10/05/2016      Mario Dioses Fernandez              Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
*
*/

package gob.osinergmin.inpsweb.domain.builder;

import gob.osinergmin.inpsweb.domain.MdiActividad;
import gob.osinergmin.inpsweb.domain.MdiEmpresaSupervisada;
import gob.osinergmin.inpsweb.domain.MdiUnidadSupervisada;
import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.PlantaEnvasadoraDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.busqueda.BusquedaUnidadSupervisadaDTO;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.CollectionUtils;

public class UnidadSupervisadaBuilder {
    
    public static List<BusquedaUnidadSupervisadaDTO> getListUnidadSupervisada(List<Object[]> listaUnidadSupervisada) {    	
    	DireccionUnidadSupervisadaDTO direccion=null;
		List<BusquedaUnidadSupervisadaDTO> listaUnidadSupervisadaDTO = new ArrayList<BusquedaUnidadSupervisadaDTO>();
		UbigeoDTO ubigeo;
		if (!CollectionUtils.isEmpty(listaUnidadSupervisada)) {
		    // listaUnidadSupervisadaDTO= new ArrayList<UnidadSupervisadaDTO>();
		    // BusquedaUnidadSupervisadaDTO unidadSupervisadaDTO= null;
		    for (Object[] unidadSupervisada : listaUnidadSupervisada) {
			// unidadSupervisadaDTO= getUnidadSupervisadaDTO(unidaSupervisada);
				BusquedaUnidadSupervisadaDTO unidadSupervisadaDTO = new BusquedaUnidadSupervisadaDTO();
				direccion = new DireccionUnidadSupervisadaDTO();
				if(unidadSupervisada[0]!=null){
					unidadSupervisadaDTO.setIdUnidadSupervisada(new Long(unidadSupervisada[0].toString()));	
				}
				if(unidadSupervisada[1]!=null){
					unidadSupervisadaDTO.setCodigoOsinergmin((String)unidadSupervisada[1]);	
				}
                                if(unidadSupervisada[2]!=null){
					unidadSupervisadaDTO.setNroRegistroHidrocarburo((String)unidadSupervisada[2]);
				}
//				EmpresaSupDTO emprSup = new EmpresaSupDTO();
//				if(unidadSupervisada[3]!=null){
//					emprSup.setRazonSocial((String)unidadSupervisada[3]);
//				}
				if(unidadSupervisada[4]!=null){
					unidadSupervisadaDTO.setActividad((String)unidadSupervisada[4]);
				}
				if(unidadSupervisada[5]!=null){
					unidadSupervisadaDTO.setDireccion((String)unidadSupervisada[5]);	
                                        direccion.setDireccionCompleta((String)unidadSupervisada[5]);
				}
				if(unidadSupervisada[6]!=null){
					unidadSupervisadaDTO.setNombreUnidad((String)unidadSupervisada[6]);	
				}
	            if(unidadSupervisada[7]!=null){
	            	unidadSupervisadaDTO.setRuc((String)unidadSupervisada[7]);
	            }
	            if(unidadSupervisada[8]!=null){
	            	unidadSupervisadaDTO.setIdActividad(new Long(unidadSupervisada[8].toString()));	
	            }	            
//	            if(unidadSupervisada[9]!=null){
//	            	emprSup.setNroIdentificacion((String)unidadSupervisada[9]);
//	            }
//	            if(unidadSupervisada[10]!=null){
//	            	emprSup.setIdEmpresaSupervisada(new Long(unidadSupervisada[10].toString()));
//	            }            
//	            if(unidadSupervisada[11]!=null){
//	            	emprSup.setTipoDocumentoIdentidad(new MaestroColumnaDTO(null, (String)unidadSupervisada[11]));
//	            }
//	            unidadSupervisadaDTO.setEmpresaSup(emprSup);
	            
	            if(unidadSupervisada[12]!=null){
	            	unidadSupervisadaDTO.setNombreActividad(unidadSupervisada[12].toString());
	            }
	            
	            if(unidadSupervisada[13]!=null){
	            	unidadSupervisadaDTO.setObservacion(unidadSupervisada[13].toString());
	            }
	            if(unidadSupervisada[14]!=null){
	            	unidadSupervisadaDTO.setIdRegistroHidrocarburo(new Long(unidadSupervisada[14].toString()));
	            }	 
                    /* OSINE_SFS-480 - RSIS 11 - Inicio */
	            if(unidadSupervisada[15]!=null && Integer.parseInt(unidadSupervisada[15].toString())!=0){ 
	            	direccion.setIdDirccionUnidadSuprvisada(new Long(unidadSupervisada[15].toString()));
	            }
	            if(unidadSupervisada[16]!=null && !unidadSupervisada[16].equals("")){
	            	ubigeo=new UbigeoDTO();
	            	ubigeo.setIdDepartamento(unidadSupervisada[16].toString());	
	            	direccion.setDepartamento(ubigeo);
	            }
	            if(unidadSupervisada[17]!=null && !unidadSupervisada[17].equals("") ){
	            	ubigeo=new UbigeoDTO();
	            	ubigeo.setIdProvincia(unidadSupervisada[17].toString());	
	            	direccion.setProvincia(ubigeo);
	            }
	            if(unidadSupervisada[18]!=null && !unidadSupervisada[18].equals("")){
	            	ubigeo=new UbigeoDTO();
	            	ubigeo.setIdDistrito(unidadSupervisada[18].toString());	
	            	direccion.setDistrito(ubigeo);
	            }	  
	            unidadSupervisadaDTO.setDireccionUnidadsupervisada(direccion); 
                    /* OSINE_SFS-480 - RSIS 11 - Fin */
	            /* OSINE_SFS-480 - RSIS 26 - Inicio */
                    
//	            if(emprSup!=null && emprSup.getTipoDocumentoIdentidad()!=null && emprSup.getTipoDocumentoIdentidad().getDescripcion()!=null && emprSup.getNroIdentificacion()!=null){
//	            	String nroDocumentoCompleto = emprSup.getTipoDocumentoIdentidad().getDescripcion()+" - "+emprSup.getNroIdentificacion();
//	            	unidadSupervisadaDTO.setNroDocumentoCompleto(nroDocumentoCompleto);
//	            }
	            if(unidadSupervisadaDTO.getRuc()!=null){
	            	String nroDocumentoCompleto = "RUC - "+unidadSupervisadaDTO.getRuc();
	            	unidadSupervisadaDTO.setNroDocumentoCompleto(nroDocumentoCompleto);
	            }
	            if(unidadSupervisada[19]!=null){
	            	unidadSupervisadaDTO.setTipoSeleccion(unidadSupervisada[19].toString());
	            }
                    if(unidadSupervisada[20]!=null){
	            	unidadSupervisadaDTO.setFlagMuestral(unidadSupervisada[20].toString());
	            }
	            /* OSINE_SFS-480 - RSIS 26 - Fin */
	            listaUnidadSupervisadaDTO.add(unidadSupervisadaDTO);
		    }
		}
		return listaUnidadSupervisadaDTO;
    }

    public static MdiUnidadSupervisada getUnidadSupervisada(UnidadSupervisadaDTO unidadSupervisadaDTO) {
		MdiUnidadSupervisada mdiUnidadSupervisada = new MdiUnidadSupervisada();
		mdiUnidadSupervisada.setIdUnidadSupervisada(unidadSupervisadaDTO.getIdUnidadSupervisada());
		mdiUnidadSupervisada.setCodigoOsinergmin(unidadSupervisadaDTO.getCodigoOsinergmin());
		mdiUnidadSupervisada.setDireccion(unidadSupervisadaDTO.getDireccion());
		mdiUnidadSupervisada.setEstado(unidadSupervisadaDTO.getEstado());
		mdiUnidadSupervisada.setIdTipoActividad(unidadSupervisadaDTO.getIdTipoActividad());
		mdiUnidadSupervisada.setNombreUnidad(unidadSupervisadaDTO.getNombreUnidad());
		mdiUnidadSupervisada.setResolucionDirectoral(unidadSupervisadaDTO.getResolucionDirectoral());		
		mdiUnidadSupervisada.setIdTipoUnidad(unidadSupervisadaDTO.getIdTipoUnidad());		
		mdiUnidadSupervisada.setUtm(unidadSupervisadaDTO.getUtm());
		mdiUnidadSupervisada.setZona(unidadSupervisadaDTO.getZona());
		mdiUnidadSupervisada.setObservacion(unidadSupervisadaDTO.getObservacion());
		
		if(unidadSupervisadaDTO.getActividad()!=null && unidadSupervisadaDTO.getActividad().getIdActividad()!=null) {
		    MdiActividad mdiActividad = new MdiActividad();
		    mdiActividad.setIdActividad(unidadSupervisadaDTO.getActividad().getIdActividad());
		    mdiUnidadSupervisada.setIdActividad(mdiActividad);
		}
		
//		if(unidadSupervisadaDTO.getEtapa()!=null && unidadSupervisadaDTO.getEtapa().getIdEtapa()!=null) {
//		    MdiEtapa mdiEtapa = new MdiEtapa();
//		    mdiEtapa.setIdEtapa(unidadSupervisadaDTO.getEtapa().getIdEtapa());
//		    mdiUnidadSupervisada.setIdEtapa(mdiEtapa);
//		}
	
		if(unidadSupervisadaDTO.getEmpresaSupervisada()!=null && unidadSupervisadaDTO.getEmpresaSupervisada().getIdEmpresaSupervisada()!=null) {
		    MdiEmpresaSupervisada mdiEmpresaSupervisada = new MdiEmpresaSupervisada();
		    mdiEmpresaSupervisada.setIdEmpresaSupervisada(unidadSupervisadaDTO.getEmpresaSupervisada().getIdEmpresaSupervisada());
		    mdiUnidadSupervisada.setIdEmpresaSupervisada(mdiEmpresaSupervisada);
		}
		return mdiUnidadSupervisada;
    }       
    
    public static UnidadSupervisadaDTO getUnidadSupervisadaDTO(MdiUnidadSupervisada unidadSupervisada) {
               
                UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();	
                if(unidadSupervisada.getIdUnidadSupervisada() != null){
                   unidadSupervisadaDTO.setIdUnidadSupervisada(unidadSupervisada.getIdUnidadSupervisada());
                }
                if(unidadSupervisada.getCodigoOsinergmin() != null){
                    unidadSupervisadaDTO.setCodigoOsinergmin(unidadSupervisada.getCodigoOsinergmin());
                }
                if(unidadSupervisada.getDireccion() != null){
                   unidadSupervisadaDTO.setDireccion(unidadSupervisada.getDireccion());
                }
		if(unidadSupervisada.getEstado() != null){
                   unidadSupervisadaDTO.setEstado(unidadSupervisada.getEstado());
                }
                if(unidadSupervisada.getIdTipoActividad() != null){
                   unidadSupervisadaDTO.setIdTipoActividad(unidadSupervisada.getIdTipoActividad());
                }
		if(unidadSupervisada.getNombreUnidad() != null){
                   unidadSupervisadaDTO.setNombreUnidad(unidadSupervisada.getNombreUnidad());
                }
		if(unidadSupervisada.getResolucionDirectoral() != null){
                   unidadSupervisadaDTO.setResolucionDirectoral(unidadSupervisada.getResolucionDirectoral());
                }
		if(unidadSupervisada.getIdTipoUnidad() != null){
                   unidadSupervisadaDTO.setIdTipoUnidad(unidadSupervisada.getIdTipoUnidad());
                }
		if(unidadSupervisada.getUtm() != null){
                  unidadSupervisadaDTO.setUtm(unidadSupervisada.getUtm());
                }
		if(unidadSupervisada.getZona()!= null){
                  unidadSupervisadaDTO.setZona(unidadSupervisada.getZona());
                }
		if(unidadSupervisada.getObservacion() != null){
                  unidadSupervisadaDTO.setObservacion(unidadSupervisada.getObservacion());
                }
	        if (unidadSupervisada.getIdActividad() != null && unidadSupervisada.getIdActividad().getIdActividad() != null) {
                  ActividadDTO actividadDTO = new ActividadDTO();
                  actividadDTO.setIdActividad(unidadSupervisada.getIdActividad().getIdActividad());
                  if (unidadSupervisada.getIdActividad().getNombre()!= null) {
                      actividadDTO.setNombre(unidadSupervisada.getIdActividad().getNombre());                  
                  }
                  if (unidadSupervisada.getIdActividad().getCodigo()!= null) {
                      actividadDTO.setCodigo(unidadSupervisada.getIdActividad().getCodigo());                  
                  }
                  unidadSupervisadaDTO.setActividad(actividadDTO);
                }
                if(unidadSupervisada.getNumeroRegistroHidrocarburo()!= null){
                  unidadSupervisadaDTO.setNroRegistroHidrocarburo(unidadSupervisada.getNumeroRegistroHidrocarburo());
                }
                
                unidadSupervisadaDTO.setRuc(unidadSupervisada.getRuc());
                unidadSupervisadaDTO.setNombreComercial(unidadSupervisada.getNombreComercial());
                
                if (unidadSupervisada.getIdEmpresaSupervisada()!= null && unidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada()!= null) {
                  EmpresaSupDTO empresaSupDTO = new EmpresaSupDTO();
                  empresaSupDTO.setIdEmpresaSupervisada(unidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada());
                  if (unidadSupervisada.getIdEmpresaSupervisada().getRazonSocial()!= null) {
                      empresaSupDTO.setRazonSocial(unidadSupervisada.getIdEmpresaSupervisada().getRazonSocial());                  
                  }
                  if (unidadSupervisada.getIdEmpresaSupervisada().getNumeroIdentificacion()!= null) {
                      empresaSupDTO.setNroIdentificacion(unidadSupervisada.getIdEmpresaSupervisada().getNumeroIdentificacion());                  
                  }
                  if (unidadSupervisada.getIdEmpresaSupervisada().getNombreComercial()!= null) {
                      empresaSupDTO.setNombreComercial(unidadSupervisada.getIdEmpresaSupervisada().getNombreComercial());                  
                  }
                  if (unidadSupervisada.getIdEmpresaSupervisada().getTelefono()!= null) {
                      empresaSupDTO.setTelefono(unidadSupervisada.getIdEmpresaSupervisada().getTelefono());                  
                  }
                  if (unidadSupervisada.getIdEmpresaSupervisada().getRuc()!= null) {
                      empresaSupDTO.setRuc(unidadSupervisada.getIdEmpresaSupervisada().getRuc());                  
                  }
                  if (unidadSupervisada.getIdEmpresaSupervisada().getCorreoElectronico()!= null) {
                      empresaSupDTO.setCorreoElectronico(unidadSupervisada.getIdEmpresaSupervisada().getCorreoElectronico());                  
                  }
                    if (unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad() != null && unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getIdMaestroColumna() != null) {
                        MaestroColumnaDTO maestroDTO = new MaestroColumnaDTO();
                        maestroDTO.setIdMaestroColumna(unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getIdMaestroColumna());
                        if (unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getDescripcion() != null) {
                            maestroDTO.setDescripcion(unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getDescripcion());
                        }
                        if (unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getCodigo()!= null) {
                            maestroDTO.setCodigo(unidadSupervisada.getIdEmpresaSupervisada().getTipoDocumentoIdentidad().getCodigo());
                        }
                        empresaSupDTO.setTipoDocumentoIdentidad(maestroDTO);
                    }
                  unidadSupervisadaDTO.setEmpresaSupervisada(empresaSupDTO);
                }
                
//	if(unidadSupervisada.getIdEtapa()!= null && unidadSupervisada.getIdEtapa().getIdEtapa()!=null){
//	    EtapaDTO etapaDTO = new EtapaDTO();
//	    etapaDTO.setIdEtapa(unidadSupervisada.getIdEtapa().getIdEtapa());
//	    unidadSupervisadaDTO.setEtapa(etapaDTO);
//	}
//	
//	if(unidadSupervisada.getIdEmpresaSupervisada()!= null && unidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada()!=null){
//	    EmpresaSupDTO empresaSupDTO = new EmpresaSupDTO();
//	    empresaSupDTO.setIdEmpresaSupervisada(unidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada());
//	    unidadSupervisadaDTO.setEmpresaSupervisada(empresaSupDTO);
//	}
	return unidadSupervisadaDTO;
    }
    
    public static List<UnidadSupervisadaDTO> toListUnidadSupervisadaDTO(List<MdiUnidadSupervisada> lista) {
        UnidadSupervisadaDTO registroDTO;
        List<UnidadSupervisadaDTO> retorno = new ArrayList<UnidadSupervisadaDTO>();
        if (lista != null) {
            for (MdiUnidadSupervisada maestro : lista) {
                registroDTO = getUnidadSupervisadaDTO(maestro);
                retorno.add(registroDTO);
            }
        }
        return retorno;
    }
    
    private static BusquedaUnidadSupervisadaDTO getUnidadSupervisada(BusquedaUnidadSupervisadaDTO unidaSupervisada) {
		BusquedaUnidadSupervisadaDTO unidaSupervisadaDTO = new BusquedaUnidadSupervisadaDTO();
		if (unidaSupervisada != null) {
		    unidaSupervisadaDTO = new BusquedaUnidadSupervisadaDTO();
		    unidaSupervisadaDTO.setCodigoOsinergmin(unidaSupervisada.getCodigoOsinergmin());
		    unidaSupervisadaDTO.setRazonSocial(unidaSupervisadaDTO.getRazonSocial());
		    unidaSupervisadaDTO.setDireccion(unidaSupervisadaDTO.getDireccion());
		}
		return unidaSupervisadaDTO;
    }

    public static List<PlantaEnvasadoraDTO> getListPlantaEnvasadora(List<MdiUnidadSupervisada> listaUnidadSupervisada) {
		List<PlantaEnvasadoraDTO> listaPlantaEnvasadoraDTO = new ArrayList<PlantaEnvasadoraDTO>();
		if (!CollectionUtils.isEmpty(listaUnidadSupervisada)) {
		    for (MdiUnidadSupervisada unidadSupervisada : listaUnidadSupervisada) {
			PlantaEnvasadoraDTO plantaEnvasadora = new PlantaEnvasadoraDTO();
			plantaEnvasadora.setIdPlantaEnvasadora(unidadSupervisada.getIdUnidadSupervisada());
			plantaEnvasadora.setRazonSocial(unidadSupervisada.getNombreUnidad());
			listaPlantaEnvasadoraDTO.add(plantaEnvasadora);
		    }
		}
		return listaPlantaEnvasadoraDTO;
    }
    
    /**
     * Convierte MdiUnidadSupervisada a UnidadSupervisadaDTO incluye su actividad e id de empresa
     * @param unidadSupervisada 
     * @return
     */
    public static UnidadSupervisadaDTO getUnidadSupervisadaDTO2(MdiUnidadSupervisada unidadSupervisada) {
    	UnidadSupervisadaDTO unidadDto = null;
    	if(unidadSupervisada!=null){
    		unidadDto = new UnidadSupervisadaDTO();
    		unidadDto.setIdUnidadSupervisada(unidadSupervisada.getIdUnidadSupervisada());
    		unidadDto.setCodigoOsinergmin(unidadSupervisada.getCodigoOsinergmin());
    		unidadDto.setNombreUnidad(unidadSupervisada.getNombreUnidad());
    		unidadDto.setIdTipoUnidad(unidadSupervisada.getIdTipoUnidad());
    		unidadDto.setEstado(unidadSupervisada.getEstado());
    		unidadDto.setDireccion(unidadSupervisada.getDireccion());
    		unidadDto.setZona(unidadSupervisada.getZona());
    		unidadDto.setUtm(unidadSupervisada.getUtm());
    		unidadDto.setResolucionDirectoral(unidadSupervisada.getResolucionDirectoral());
    		unidadDto.setIdTipoActividad(unidadSupervisada.getIdTipoActividad());
    		unidadDto.setObservacion(unidadSupervisada.getObservacion());
    		//unidadDto.setEtapa(etapa)
    		//trayendo la actividad
    		if(unidadSupervisada.getIdActividad()!=null && unidadSupervisada.getIdActividad().getIdActividad()!=null){
    			unidadDto.setActividad(ActividadBuilder.toActividadDto(unidadSupervisada.getIdActividad()));
    		}
    		//trayendo el id de empresa
    		if(unidadSupervisada.getIdEmpresaSupervisada()!=null && unidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada()!=null){
    			unidadDto.setEmpresaSupervisada(EmpresaSupBuilder.getEmpresaSupDTOSimple(unidadSupervisada.getIdEmpresaSupervisada()));
    		}
    	}
    	return unidadDto;
    }
    
    public static List<UnidadSupervisadaDTO> getListUnidadSupervisadaEmpresa(List<Object[]> listaUnidadSupervisada) {
	List<UnidadSupervisadaDTO> listaUnidadSupervisadaDTO = new ArrayList<UnidadSupervisadaDTO>();
	if (!CollectionUtils.isEmpty(listaUnidadSupervisada)) {
	    for (Object[] data : listaUnidadSupervisada) {
		MdiUnidadSupervisada mdiUnidadSupervisada = (MdiUnidadSupervisada)data[0];
//		MdiActividad mdiActividad = (MdiActividad)data[1];
//		MdiRegistroHidrocarburo mdiRegistroHidrocarburo = (MdiRegistroHidrocarburo)data[2];
		
		UnidadSupervisadaDTO unidadSupervisadaDTO = new UnidadSupervisadaDTO();
		unidadSupervisadaDTO.setIdUnidadSupervisada(mdiUnidadSupervisada.getIdUnidadSupervisada());
		unidadSupervisadaDTO.setCodigoOsinergmin(mdiUnidadSupervisada.getCodigoOsinergmin());
        unidadSupervisadaDTO.setNombreUnidad(mdiUnidadSupervisada.getNombreUnidad());
        if(mdiUnidadSupervisada.getIdEmpresaSupervisada()!=null){
        	if(mdiUnidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada()!=null){
        		EmpresaSupDTO empresaSupervisada = new EmpresaSupDTO();
        		empresaSupervisada.setIdEmpresaSupervisada(mdiUnidadSupervisada.getIdEmpresaSupervisada().getIdEmpresaSupervisada());
        		unidadSupervisadaDTO.setEmpresaSupervisada(empresaSupervisada);
        	}
        }
		
		ActividadDTO actividadDTO = new ActividadDTO();
        if(mdiUnidadSupervisada.getIdActividad()!=null){
                    actividadDTO.setIdActividad(mdiUnidadSupervisada.getIdActividad().getIdActividad());
                    actividadDTO.setNombre(mdiUnidadSupervisada.getIdActividad().getNombre());
                    actividadDTO.setEstado(mdiUnidadSupervisada.getIdActividad().getEstado());
        }
		unidadSupervisadaDTO.setActividad(actividadDTO);
		unidadSupervisadaDTO.setFechaCreacion(mdiUnidadSupervisada.getFechaCreacion());
		unidadSupervisadaDTO.setUsuarioCreacion(mdiUnidadSupervisada.getUsuarioCreacion());
		unidadSupervisadaDTO.setObservacion(mdiUnidadSupervisada.getObservacion());
//		if(mdiRegistroHidrocarburo !=null){
//			unidadSupervisadaDTO.setNroRegistroHidrocarburo(mdiRegistroHidrocarburo.getNumeroRegistroHidrocarburo());
//		}	
		
		listaUnidadSupervisadaDTO.add(unidadSupervisadaDTO);
	    }
	}
	return listaUnidadSupervisadaDTO;
}
    
}

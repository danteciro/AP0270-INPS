package gob.osinergmin.inpsweb.domain.builder;
import gob.osinergmin.inpsweb.domain.MdiDirccionUnidadSuprvisada;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.MaestroColumnaDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import java.util.ArrayList;
import java.util.List;
/**
*
* @author mdiosesf
*/
public class DireccionUnidadSupervisadaBuilder {
    
    public static DireccionUnidadSupervisadaDTO toDireccionUnidadDTO(MdiDirccionUnidadSuprvisada registro){
        DireccionUnidadSupervisadaDTO retorno=new DireccionUnidadSupervisadaDTO();
        if(registro!=null){
            retorno.setIdDirccionUnidadSuprvisada(registro.getIdDirccionUnidadSuprvisada());
            retorno.setDireccionCompleta(registro.getDireccionCompleta());
            if(registro.getMdiUbigeo()!=null){
                UbigeoDTO dep = new UbigeoDTO();
                dep.setIdDepartamento(registro.getMdiUbigeo().getMdiUbigeoPK().getIdDepartamento());
                retorno.setDepartamento(dep);
                
                UbigeoDTO prov = new UbigeoDTO();
                prov.setIdProvincia(registro.getMdiUbigeo().getMdiUbigeoPK().getIdProvincia());
                retorno.setProvincia(prov);
                
                UbigeoDTO dist = new UbigeoDTO();
                dist.setIdDistrito(registro.getMdiUbigeo().getMdiUbigeoPK().getIdDistrito());
                retorno.setDistrito(dist);
            }
            retorno.setTelefono1(registro.getTelefono1());
            
        }
        return retorno;
    }
   
    public static List<DireccionUnidadSupervisadaDTO> getDireccionUnidadDTO(List<Object[]> listaDireccion){
	    List<DireccionUnidadSupervisadaDTO> listaDireccionUnidad = new ArrayList<DireccionUnidadSupervisadaDTO>();
	    try {
	    	DireccionUnidadSupervisadaDTO direccionUnidad = null;
			if(listaDireccion!=null && listaDireccion.size()!=0) {
				for (Object[] registro : listaDireccion) {
					direccionUnidad=new DireccionUnidadSupervisadaDTO();
					UbigeoDTO departamento=new UbigeoDTO();
					UbigeoDTO provincia=new UbigeoDTO();
					UbigeoDTO distrito=new UbigeoDTO();
					MaestroColumnaDTO tipoDireccion = new MaestroColumnaDTO();
					MaestroColumnaDTO tipoVia = new MaestroColumnaDTO();
					direccionUnidad.setIdDirccionUnidadSuprvisada(new Long(registro[0].toString()));					
					if(registro[2]!=null){
						direccionUnidad.setNumeroVia(registro[2].toString());
					}
					if(registro[3]!=null){
						direccionUnidad.setDescripcionVia(registro[3].toString());
					}
					if(registro[4]!=null){
						direccionUnidad.setInterior(registro[4].toString());
					}
					if(registro[5]!=null){
						direccionUnidad.setManzana(registro[5].toString());
					}
					if(registro[6]!=null){
						direccionUnidad.setLote(registro[6].toString());
					}
					if(registro[7]!=null){
						direccionUnidad.setKilometro(registro[7].toString());
					}
					if(registro[8]!=null){
						direccionUnidad.setBlockChallet(registro[8].toString());	
					}
					if(registro[9]!=null){
						direccionUnidad.setEtapa(registro[9].toString());
					}
					if(registro[10]!=null){
						direccionUnidad.setNumeroDepartamento(registro[10].toString());
					}
					if(registro[11]!=null){
						direccionUnidad.setUrbanizacion(registro[11].toString());		
					}
					if(registro[12]!=null){
						direccionUnidad.setDireccionCompleta(registro[12].toString());	
					}
					if(registro[13]!=null){
						direccionUnidad.setReferencia(registro[13].toString());
					}
					if(registro[14]!=null){
						direccionUnidad.setEstado(registro[14].toString());
					}			
					if(registro[15]!=null){						
						departamento.setIdDepartamento(registro[15].toString());						
					}
					if(registro[16]!=null){
						departamento.setNombre(registro[16].toString());
					}					
					if(registro[17]!=null){						
						provincia.setIdProvincia(registro[17].toString());
					}
					if(registro[18]!=null){						
						provincia.setNombre(registro[18].toString());						
					}
					if(registro[19]!=null){
						distrito.setIdDistrito(registro[19].toString());
					}
					if(registro[20]!=null){
						distrito.setNombre(registro[20].toString());
					}
					if(registro[21]!=null){
						tipoDireccion.setIdMaestroColumna(new Long(registro[21].toString()));
					}
					if(registro[22]!=null){
						tipoDireccion.setDescripcion(registro[22].toString());
					}
					if(registro[23]!=null){
						tipoDireccion.setCodigo(registro[23].toString());
					}
					if(registro[24]!=null){
						tipoVia.setIdMaestroColumna(new Long(registro[24].toString()));
					}
					if(registro[25]!=null){
						tipoVia.setDescripcion(registro[25].toString());
					}
					if(registro[26]!=null){
						tipoVia.setCodigo(registro[26].toString());
					}
					direccionUnidad.setDepartamento(departamento);
					direccionUnidad.setProvincia(provincia);
					direccionUnidad.setDistrito(distrito);
					direccionUnidad.setIdTipoDireccion(tipoDireccion);
					direccionUnidad.setIdTipoVia(tipoVia);
					listaDireccionUnidad.add(direccionUnidad);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return listaDireccionUnidad;
	}
}

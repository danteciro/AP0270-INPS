/**
 * Resumen		
 * Objeto				: registrarActaInspeccion.js
 * Descripción			: JavaScript donde se maneja las acciones del registro de Actas.
 * Fecha de Creación	: 13/10/216.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Hernan Torres Saenz.
 * ===============================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * ===============================================================================================
 *
 */

var releVacio='';
releVacio += '<table border="1" >'
releVacio += '    <tr>'
releVacio += '<td><b>Cod. Relé</b></td>'
releVacio += '<td colspan="2" class="auto-style1" align="center"><b>Marca</b></td>'
releVacio += '<td colspan="2" class="auto-style1" align="center"><b>Modelo</b></td>'
releVacio += '<td colspan="2" class="auto-style1" align="center"><b>Serie</b></td>'
releVacio += '<td align="center"><b> S.E.</b></td>'
releVacio += '<td align="center"><b>kV</b></td>'
releVacio += '<td colspan="2" align="center"><b>Alimentador</b></td>'
releVacio += '<td align="center"><b>Cod. Inter.</b></td>'
releVacio += '<td align="center"><b>Fecha Impl.</b></td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td></td>'
releVacio += '<td colspan="2">'
releVacio += '&nbsp;</td>'
releVacio += '<td colspan="2"></td>'
releVacio += '<td colspan="2"></td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '<td colspan="2"></td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td rowspan="2" align="center"><b>Etapa</b></td>'
releVacio += '<td colspan="2" align="center"><b>Relé por Umbral</b></td>'
releVacio += '<td colspan="3" align="center"><b>Relé por Derivada</b></td>'
releVacio += '<td align="center"><b>Pot. R</b></td>'
releVacio += '<td colspan="6" align="center"><b>Demanada 14/07/2015(MW)</b></td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td align="center"><b>(Hz)</b></td>'
releVacio += '<td align="center"><b>(s)</b></td>'
releVacio += '<td align="center"><b>(Hz)</b></td>'
releVacio += '<td align="center"><b>(Hz/s)</b></td>'
releVacio += '<td align="center"><b>(s)</b></td>'
releVacio += '<td align="center"><b>(MW)</b></td>'
releVacio += '<td align="center" colspan="2"><b>Máxima (19:00 h)</b></td>'
releVacio += '<td align="center" colspan="2"><b>Media (11:45 h)</b></td>'
releVacio += '<td align="center" colspan="2"><b>Mínima (3:15 h)</b></td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td rowspan="2"></td>'
releVacio += '<td>&nbsp;</td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '<td></td>'
releVacio += '<td colspan="2"></td>'
releVacio += '<td colspan="2"></td>'
releVacio += '<td colspan="2"></td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td> '
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px;" disabled="disabled"></input>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;">'
releVacio += '   	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '   </div>'
releVacio += '</td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2">'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2">'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2">'
releVacio += '	<div style="text-align: center;">'
releVacio += '    	<input type="text" style="width: 55px" disabled="disabled"/>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td colspan="2" align="center">Código Osinergmin</td>'
releVacio += '<td></td>'
releVacio += '<td colspan="2"><b>Umbral</b></td>'
releVacio += '<td colspan="2"><b>Derivada</b></td>'
releVacio += '<td colspan="2"><b>Observaciones</b></td>'
releVacio += '<td><b>Hora (hh:mm)</b></td>'
releVacio += '<td>'
releVacio += '	<div style="text-align: center;width: 55px;">'
releVacio += '		<input type="text" style="width: 25px" value="" disabled="disabled"/>'
releVacio += '	</div>'
releVacio += '</td>'
releVacio += '<td><b>Demanda (MW)</b></td>'
releVacio += '<td>'
releVacio += '	<input type="text" style="width: 25px" value="" disabled="disabled"/>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td><b>Fiscalizado</b></td>'
releVacio += '<td colspan="2"><b>Existencia de Relé</b></td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '    	<input id="idUmbralExistencia" name="idUmbralExistencia" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '   	<label for="idUmbralExistencia" class="checkbox"></label>'
releVacio += '	</div>'
releVacio += '</td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idUmbralDerivada" name="idUmbralDerivada" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idUmbralDerivada" class="checkbox"></label>'
releVacio += '     </div>'
releVacio += '</td>'
releVacio += '<td colspan="6" rowspan="4">'
releVacio += '    <textarea style="height: 116px; width: 468px" disabled="disabled"></textarea>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td rowspan="3" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idFiscalizado" name="idFiscalizado" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idFiscalizado" class="checkbox"></label>'
releVacio += '     </div>'
releVacio += '</td>'
releVacio += '<td colspan="2"><b>Ajuste en Relé según Esquema</b></td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idAjusteUmbral" name="idAjusteUmbral" type="checkbox" value="1" disabled="disabled" />'
releVacio += '        <label for="idAjusteUmbral" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idAjusteDerivada" name="idAjusteDerivada" type="checkbox"  value="1" disabled="disabled"/>'
releVacio += '        <label for="idAjusteDerivada" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td colspan="2"><b>Otro Ajuste</b></td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idOtroAjusteUmbral" name="idOtroAjusteUmbral" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idOtroAjusteUmbral" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idOtroAjusteDerivada" name="idOtroAjusteDerivada" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idOtroAjusteDerivada" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '<tr>'
releVacio += '<td colspan="2"><b>Protocolo de Pruebas</b></td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idProtocoloUmbral" name="idProtocoloUmbral" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idProtocoloUmbral" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '<td colspan="2" style="text-align: center">'
releVacio += '	<div style="display: inline-block;">'
releVacio += '        <input id="idProtocoloDerivada" name="idProtocoloDerivada" type="checkbox" value="1" disabled="disabled"/>'
releVacio += '        <label for="idProtocoloDerivada" class="checkbox"></label>'
releVacio += '    </div>'
releVacio += '</td>'
releVacio += '</tr>'
releVacio += '</table>';

var paginacion = [];
var a,c,s;
var tabSeleccionado;
var modalRegistro={
		cargarDepartamento:function(idSlctDest){
			$.ajax({
	            url:baseURL + "pages/bandeja/cargarDepartamento",
	            type:'get',
	            dataType:'json',
	            async:false,
	            data:{},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.comboValorId(data,"idDepartamento","nombre",idSlctDest,"-1");
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarProvincia:function(combo, idDepartamento){
			$.ajax({
	            url:baseURL + "pages/ubigeo/buscarProvincias",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{idDepartamento: idDepartamento},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.combo(data,"idProvincia","nombre",combo);
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarDistrito:function(combo, idDepartamento, idProvincia){
			$.ajax({
	            url:baseURL + "pages/ubigeo/buscarDistritos",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{idDepartamento: idDepartamento, idProvincia: idProvincia},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.combo(data,"idDistrito","nombre",combo);
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarSubEstaciones:function(idSlctDest, idEmpresa, idAnio, idZona){
			$.ajax({
	            url:baseURL + "pages/actaInspeccion/cargarSubEstaciones",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{
	            	idEmpresa: idEmpresa, 
	            	anio: idAnio,
	            	idZona: idZona
	            },
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                if(data.resultado==0){
	                	fill.combo(data.listaSubEstaciones,"idSubEstacion","descripcion",idSlctDest);
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                }
	                
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarRelesXSubEstacion:function(idSubEstacion, idEmpresa, idAnio,idSupeCamp){
			$.ajax({
	            url:baseURL + "pages/actaInspeccion/listarReles",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{
	            	subEstacion: idSubEstacion,
	            	idEmpresa: idEmpresa, 
	            	anio: idAnio,
	            	idSupeCamp: idSupeCamp,
	            	ajax: 'true'
	            },
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                if(data.resultado == 0){
		                // Acción de listar DINAMICAMENTE RELES
		                var html = '';
		                paginacion = [];
		                
		                for(var x = 0; x < data.listaDetaSupe.length;x++){
		                	paginacion[x] = "idRele"+data.listaDetaSupe[x].idRele;
		                	if(x==0){
		                		html += '<input type="hidden" id="idReleActual" name="idReleActual" value="'+data.listaDetaSupe[x].idRele+'"/>';
		                		html += '<input type="hidden" id="idIndiceReleActual" name="idIndiceReleActual" value="'+x+'"/>';
		                		
		                		html += '<div id="idRele'+data.listaDetaSupe[x].idRele+'">'
		                	}else{
		                		html += '<div id="idRele'+data.listaDetaSupe[x].idRele+'" style="display:none;" >'
		                	}
		                	html += '<div>'
		                	html += '<input type="hidden" id="idDetaSupeActual'+data.listaDetaSupe[x].idRele+'" value="'+data.listaDetaSupe[x].idDetaSupeCampRechCarga+'"/>';
		                	html += '<table border="1" >'
		                	html += '<tr>'
		                	html += '<td><b>Cod. Relé</b></td>'
		                	html += '<td colspan="2" class="auto-style1" align="center"><b>Marca</b></td>'
		                	html += '<td colspan="2" class="auto-style1" align="center"><b>Modelo</b></td>'
		                	html += '<td colspan="2" class="auto-style1" align="center"><b>Serie</b></td>'
		                	html += '<td align="center"><b>S.E.</b></td>'
		                	html += '<td align="center"><b>kV</b></td>'
		                	html += '<td colspan="2" align="center"><b>Alimentador</b></td>'
		                	html += '<td align="center"><b>Cod. Inter.</b></td>'
		                	html += '<td align="center"><b>Fecha Impl.</b></td>'
		                	html += '</tr>'
		                	html += '<tr>'
				            html += '<td align="center">'+data.listaDetaSupe[x].releServicio.codigoRele+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.marca+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.modelo+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.serie+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.subEstacion+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.kV+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.alimentador+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.codInterrupcion+'</td>'
			                html += '<td align="center">'+fecha(data.listaDetaSupe[x].releServicio.fechaImplementacion)+'</td>'
				            html += '</tr>'
			                html += '<tr>'
			                html += '<td rowspan="2" align="center"><b>Etapa</b></td>'
			                html += '<td colspan="2" align="center"><b>Relé por Umbral</b></td>'
			                html += '<td colspan="3" align="center"><b>Relé por Derivada</b></td>'
			                html += '<td align="center"><b>Pot. R</b></td>'
			                html += '<td colspan="6" align="center"><b>Demanada '+data.listaDetaSupe[x].demandaMwFecha+' (MW)</b></td>'
				            html += '</tr>'
				            html += '<tr>'
			                html += '<td align="center"><b>(Hz)</b></td>'
			                html += '<td align="center"><b>(s)</b></td>'
			                html += '<td align="center"><b>(Hz)</b></td>'
			                html += '<td align="center"><b>(Hz/s)</b></td>'
			                html += '<td align="center"><b>(s)</b></td>'
			                html += '<td align="center"><b>(MW)</b></td>'
			                html += '<td colspan="2" align="center"><b>M&aacute;xima '+data.listaDetaSupe[x].demandaMaximaHora+'</b></td>'
			                html += '<td colspan="2" align="center"><b>Media '+data.listaDetaSupe[x].demandaMediaHora+'</b></td>'
			                html += '<td colspan="2" align="center"><b>M&iacute;nima '+data.listaDetaSupe[x].demandaMinimaHora+'</b></td>'
				            html += '</tr>'
				            html += '<tr>'
			                html += '<td rowspan="2" align="center">'+data.listaDetaSupe[x].releServicio.etapa+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.releUmbralHz+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.releUmbralS+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.releDerivadaHZ+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.releDerivadaHZS+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.releDerivadaS+'</td>'
			                html += '<td align="center">'+data.listaDetaSupe[x].releServicio.potR+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.demandaMax+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.demandaMed+'</td>'
			                html += '<td colspan="2" align="center">'+data.listaDetaSupe[x].releServicio.demandaMin+'</td>'
				            html += '</tr>'
				            html += '<tr>'
			                html += '<td>'
			                html += '<div style="text-align: center;">'
			                if(data.listaDetaSupe[x].releUmbralHz!=null)
			                	html += '<input id="idUmbralHz'+data.listaDetaSupe[x].idRele+'" name="idUmbralHz'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px"  value="'+data.listaDetaSupe[x].releUmbralHz+'"/>'
			                else
			                	html += '<input id="idUmbralHz'+data.listaDetaSupe[x].idRele+'" name="idUmbralHz'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px"  value=""/>'
			                html += '</div>'
			                html += '</td>'
			                html += '<td>'
			                html += '<div style="text-align: center;">'
			                if(data.listaDetaSupe[x].releUmbralS!=null)
			                	html += '<input id="idUmbralS'+data.listaDetaSupe[x].idRele+'" name="idUmbralS'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].releUmbralS+'"/>'
		                    else
		                    	html += '<input id="idUmbralS'+data.listaDetaSupe[x].idRele+'" name="idUmbralS'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
		                    html += '</div>'		
			                html += '</td>'
			                html += '<td>'
			                	html += '<div style="text-align: center;">'	
			                if(data.listaDetaSupe[x].releDerivadaHz!=null)
			                	html += '<input id="idReleDerivadaHz'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaHz'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].releDerivadaHz+'"/>'
			                else
			                	html += '<input id="idReleDerivadaHz'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaHz'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
			                html += '</div>'		
			                html += '</td>'
			                html += '<td>'
			                html += '<div style="text-align: center;">'	
			                if(data.listaDetaSupe[x].releDerivadaHzs!=null)
			                	html += '<input id="idReleDerivadaHzs'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaHzs'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].releDerivadaHzs+'"/>'
			                else
			                	html += '<input id="idReleDerivadaHzs'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaHzs'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
			                html+= '</div>'		
			                html += '</td>'
			                html += '<td>'
				            html += '<div style="text-align: center;">'	
			                if(data.listaDetaSupe[x].releDerivadaS!=null)
			                	html += '<input id="idReleDerivadaS'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaS'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].releDerivadaS+'"/>'
			                else
			                	html += '<input id="idReleDerivadaS'+data.listaDetaSupe[x].idRele+'" name="idReleDerivadaS'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
			                html += '</div>'	
			                html += '</td>'
			                html += '<td>'
					        html += '<div style="text-align: center;">'	
			                if(data.listaDetaSupe[x].potr!=null)
			                	html += '<input id="idPotr'+data.listaDetaSupe[x].idRele+'" name="idPotr'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].potr+'"/>'
			                else
			                	html += '<input id="idPotr'+data.listaDetaSupe[x].idRele+'" name="idPotr'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
			                html += '</div>'	
				            html += '</td>'
				            html += '<td colspan="2">'
				            html += '<div style="text-align: center;">'
				            if(data.listaDetaSupe[x].demandaMaxima!=null)
				            	html += '<input id="idDemandaMaxima'+data.listaDetaSupe[x].idRele+'" name="idDemandaMaxima'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].demandaMaxima+'"/>'
				            else
				            	html += '<input id="idDemandaMaxima'+data.listaDetaSupe[x].idRele+'" name="idDemandaMaxima'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
				            html += '</div>'	
				            html += '</td>'
				            html += '<td colspan="2">'
				            html += '<div style="text-align: center;">'	
				            if(data.listaDetaSupe[x].demandaMedia!=null)
				            	html += '<input id="idDemandaMedia'+data.listaDetaSupe[x].idRele+'" name="idDemandaMedia'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].demandaMedia+'"/>'
				            else
				            	html += '<input id="idDemandaMedia'+data.listaDetaSupe[x].idRele+'" name="idDemandaMedia'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
				            html += '</div>'		
				            html += '</td>'
				            html += '<td colspan="2">'
				            html += '<div style="text-align: center;">'	
				            if(data.listaDetaSupe[x].demandaMinima!=null)
				            	html += '<input id="idDemandaMinima'+data.listaDetaSupe[x].idRele+'" name="idDemandaMinima'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].demandaMinima+'"/>'
				            else
				            	html += '<input id="idDemandaMinima'+data.listaDetaSupe[x].idRele+'" name="idDemandaMinima'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/>'
				            html += '</div>'	
			                html += '</td>'
				            html += '</tr>'
				            html += '<tr>'
			                html += '<td colspan="2"  align="center">Código Osinergmin</td>'
			                html += '<td></td>'
			                html += '<td colspan="2"><b>Umbral</b></td>'
			                html += '<td colspan="2"><b>Derivada</b></td>'
			                html += '<td colspan="2"><b>Observaciones</b></td>'
			                html += '<td ><b>Hora (hh:mm)</b></td>'
			                html += '<td>'
			                html += '<div style="text-align: center;width: 55px;">'	
			                if(data.listaDetaSupe[x].hora!=null)
			                	html += '<input id="idHora'+data.listaDetaSupe[x].idRele+'" name="idHora'+data.listaDetaSupe[x].idRele+'" type="text" maxlength="5" style="width: 30px" value="'+data.listaDetaSupe[x].hora+'"/>'
			                else
			                	html += '<input id="idHora'+data.listaDetaSupe[x].idRele+'" name="idHora'+data.listaDetaSupe[x].idRele+'" type="text" maxlength="5" style="width: 30px" value=""/>'
			                html += '</div>'
			                html += '</td>'
			                html += '<td><b>Demanda (MW)</b></td>'
			                	
			                if(data.listaDetaSupe[x].demandaMw!=null)
			                	html += '<td align="center"><input id="idDemandaMw'+data.listaDetaSupe[x].idRele+'" name="idDemandaMw'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value="'+data.listaDetaSupe[x].demandaMw+'"/></td>'
			                else
			                	html += '<td align="center"><input id="idDemandaMw'+data.listaDetaSupe[x].idRele+'" name="idDemandaMw'+data.listaDetaSupe[x].idRele+'" type="text" style="width: 65px" value=""/></td>'
			                	
				            html += '</tr>'
					        html += '<tr>'
			                html += '<td><b>Fiscalizado</b></td>'
			                html += '<td colspan="2"><b>Existencia de Relé</b></td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'
		                	if(data.listaDetaSupe[x].flgExisteReleUmbral=='1'){
					        	html += '<input id="idExisteUmbral'+data.listaDetaSupe[x].idRele+'" name="idExisteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idExisteUmbral'+data.listaDetaSupe[x].idRele+'" name="idExisteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                	html += '<label for="idExisteUmbral'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                	html += '</div>'
			                html += '</td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'
		                	if(data.listaDetaSupe[x].flgExisteReleDerivada=='1'){
					        	html += '<input id="idExisteDerivada'+data.listaDetaSupe[x].idRele+'" name="idExisteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idExisteDerivada'+data.listaDetaSupe[x].idRele+'" name="idExisteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idExisteDerivada'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
			                html += '<td colspan="6" rowspan="4">'
			                if(data.listaDetaSupe[x].observaciones!=null)
			                	html += '<textarea id="idObservacion'+data.listaDetaSupe[x].idRele+'" name="idObservacion'+data.listaDetaSupe[x].idRele+'" style="height: 116px; width: 468px">'+data.listaDetaSupe[x].observaciones+'</textarea>'
			                else
			                	html += '<textarea id="idObservacion'+data.listaDetaSupe[x].idRele+'" name="idObservacion'+data.listaDetaSupe[x].idRele+'" style="height: 116px; width: 468px"></textarea>'
			                html += '</td>'
				            html += '</tr>'
					        html += '<tr>'
			                html += '<td rowspan="3" style="text-align: center">'
			                html += '<div style="display: inline-block;">'
		                	if(data.listaDetaSupe[x].flgFiscalizado=='1'){
					        	html += '<input id="idFiscalizado'+data.listaDetaSupe[x].idRele+'" name="idFiscalizado'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idFiscalizado'+data.listaDetaSupe[x].idRele+'" name="idFiscalizado'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idFiscalizado'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
			                html += '<td colspan="2"><b>Ajuste en Relé según Esquema</b></td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'
		                	if(data.listaDetaSupe[x].flgAjusteReleUmbral=='1'){
					        	html += '<input id="idAjusteUmbral'+data.listaDetaSupe[x].idRele+'" name="idAjusteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idAjusteUmbral'+data.listaDetaSupe[x].idRele+'" name="idAjusteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
		                    html += '<label for="idAjusteUmbral'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'	
		                	if(data.listaDetaSupe[x].flgAjusteReleDerivada=='1'){
					        	html += '<input id="idAjusteDerivada'+data.listaDetaSupe[x].idRele+'" name="idAjusteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idAjusteDerivada'+data.listaDetaSupe[x].idRele+'" name="idAjusteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idAjusteDerivada'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
				            html += '</tr>'
					        html += '<tr>'
			                html += '<td colspan="2"><b>Otro Ajuste</b></td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'		
		                	if(data.listaDetaSupe[x].flgOtroAjusteUmbral=='1'){
					        	html += '<input id="idOtroAjusteUmbral'+data.listaDetaSupe[x].idRele+'" name="idOtroAjusteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idOtroAjusteUmbral'+data.listaDetaSupe[x].idRele+'" name="idOtroAjusteUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idOtroAjusteUmbral'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'	
		                	if(data.listaDetaSupe[x].flgOtroAjusteDerivada=='1'){
					        	html += '<input id="idOtroAjusteDerivada'+data.listaDetaSupe[x].idRele+'" name="idOtroAjusteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idOtroAjusteDerivada'+data.listaDetaSupe[x].idRele+'" name="idOtroAjusteDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idOtroAjusteDerivada'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
				            html += '</tr>'
					        html += '<tr>'
			                html += '<td colspan="2"><b>Protocolo de Pruebas</b></td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'	
					        if(data.listaDetaSupe[x].flgProtocoloUmbral=='1'){
					        	html += '<input id="idProtocoloUmbral'+data.listaDetaSupe[x].idRele+'" name="idProtocoloUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
					        }else{
					        	html += '<input id="idProtocoloUmbral'+data.listaDetaSupe[x].idRele+'" name="idProtocoloUmbral'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
					        }
			                
		                    html += '<label for="idProtocoloUmbral'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
			                html += '<td colspan="2" style="text-align: center">'
			                html += '<div style="display: inline-block;">'	
		                    if(data.listaDetaSupe[x].flgProtocoloDerivada=='1'){
		                    	html += '<input id="idProtocoloDerivada'+data.listaDetaSupe[x].idRele+'" name="idProtocoloDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" checked/>'
		                    }else{
		                    	html += '<input id="idProtocoloDerivada'+data.listaDetaSupe[x].idRele+'" name="idProtocoloDerivada'+data.listaDetaSupe[x].idRele+'" type="checkbox" value="1" />'
		                    }
		                    
		                    html += '<label for="idProtocoloDerivada'+data.listaDetaSupe[x].idRele+'" class="checkbox"></label>'
		                    html += '</div>'
			                html += '</td>'
				            html += '</tr>'
				        	html += '</table>'
				        	html += '</div>'
				        	html += '</div>'
				        		
				        	html += '<div style="margin-top: 20px;">'
				        	html += '</div>'
				        	
		                }
		                
		                $('#idListaReles').html(html);
		                
		                var html2 = '';
	                	html2 += '<div style="margin-top: 20px;text-align: center;">'
	                	
	                	html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: gray; "> << </a>';
	                	html2 += '<div style="display:inline-block;width:10px;"></div>'
	                	html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: gray; "> < </a>';
	                	
	                	html2 += '<div style="display:inline-block; margin: 15px;">'
	                	html2 +=	'<a  title="1">1</a>';
	                	html2 += '</div>'
	                	html2 += '<div style="display:inline-block;">de</div>'
	                	html2 += '<div style="display:inline-block; margin: 15px;">'
	                	html2 += paginacion.length;
			            html2 += '</div>'
			            
			            if(paginacion.length==1){
			            	html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: gray; "> > </a>';
			            	html2 += '<div style="display:inline-block;width:10px;"></div>'
			            	html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: gray; "> >> </a>';
			            }else{
			            	html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
				            ' onClick="modalRegistro.validarSiguienteRele('+1+')"> > </a>';
			            	html2 += '<div style="display:inline-block;width:10px;"></div>'
			            	html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
			            	' onClick="modalRegistro.validarUltimoRele('+(paginacion.length-1)+')"> >> </a>';
			            }
			            
		                html2 += '</div>'
		                	
		                $('#idPaginacionReles').html(html2);
		                
		                for(var x = 0; x < data.listaDetaSupe.length;x++){
		                	$('#idUmbralHz'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idUmbralS'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idReleDerivadaHz'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idReleDerivadaHzs'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idReleDerivadaS'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idPotr'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idDemandaMaxima'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idDemandaMedia'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idDemandaMinima'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	$('#idDemandaMw'+data.listaDetaSupe[x].idRele).numeric(constant.valida.alphanum.decimalReles);
		                	
		                	$('#idHora'+data.listaDetaSupe[x].idRele).alphanum(constant.valida.alphanum.horasMinutos);
		                }
	                }else{
	                	$('#idListaReles').html(releVacio);
	            		$('#idPaginacionReles').html('');
	                    mensajeGrowl("warn", data.mensaje, "");
	                }
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		validarSiguienteRele:function(indiceSiguiente){
			if(modalRegistro.validarCambioRele()){
				confirmer.open("¿Est&aacute; seguro que desea cambiar de Rel&eacute;?","modalRegistro.siguienteRele('"+indiceSiguiente+"')");
			}else{
				modalRegistro.siguienteRele(indiceSiguiente);
			}
		},
		siguienteRele:function(indiceSiguiente){
			indiceSiguiente=parseInt(indiceSiguiente);
			$('#'+paginacion[indiceSiguiente-1]).attr('style', 'display:none;');
			$('#'+paginacion[indiceSiguiente]).attr('style', 'display:inline;');
			
			$('#idReleActual').val(paginacion[indiceSiguiente]);
			$('#idIndiceReleActual').val(indiceSiguiente);
			
			var html2 = '';
        	html2 += '<div style="margin-top: 20px;text-align: center;">'
        	a = indiceSiguiente-1;
        	html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarPrimerRele()"> << </a>';
        	html2 += '<div style="display:inline-block;width:10px;"></div>'
        	html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarAnteriorRele('+(a)+')"> < </a>';
        	var c = indiceSiguiente+1;
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += '<a  title="1">'+(c)+'</a>';
        	html2 += '</div>'
        	html2 += '<div style="display:inline-block;">de</div>'
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += paginacion.length;
            html2 += '</div>'
            s = indiceSiguiente+1;
            if((indiceSiguiente+1)<paginacion.length){
            	html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            		' onClick="modalRegistro.validarSiguienteRele('+(s)+')"> > </a>';   
            	html2 += '<div style="display:inline-block;width:10px;"></div>'
            	html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
        		' onClick="modalRegistro.validarUltimoRele('+(paginacion.length-1)+')"> >> </a>';
            }else{
            	html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: gray; " > > </a>';
            	html2 += '<div style="display:inline-block;width:10px;"></div>'
            	html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: gray; "> >> </a>';
            }
            
            html2 += '</div>'
            	
            $('#idPaginacionReles').html(html2);
		},
		validarAnteriorRele:function(indiceAnterior){
			if(modalRegistro.validarCambioRele()){
				confirmer.open("¿Est&aacute; seguro que desea cambiar de Rel&eacute;?","modalRegistro.anteriorRele('"+indiceAnterior+"')");
			}else{
				modalRegistro.anteriorRele(indiceAnterior);
			}
		},
		anteriorRele:function(indiceAnterior){
			indiceAnterior=parseInt(indiceAnterior);
			$('#'+paginacion[indiceAnterior]).attr('style', 'display:inline;');
			$('#'+paginacion[indiceAnterior+1]).attr('style', 'display:none;');
			$('#idReleActual').val(paginacion[indiceAnterior]);
			$('#idIndiceReleActual').val(indiceAnterior);
			
			var html2 = '';
        	html2 += '<div style="margin-top: 20px;text-align: center;">'
        	a = indiceAnterior-1;
        	if(indiceAnterior!=0){
        		html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
                ' onClick="modalRegistro.validarPrimerRele()"> << </a>';
        		html2 += '<div style="display:inline-block;width:10px;"></div>'
        		html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            	' onClick="modalRegistro.validarAnteriorRele('+(a)+')"> < </a>';  
        	}else{
        		html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: gray; "> << </a>';
        		html2 += '<div style="display:inline-block;width:10px;"></div>'
        		html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: gray; " > < </a>';  
        	}
        	c = indiceAnterior+1;
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += '<a  title="1">'+(c)+'</a>';
        	html2 += '</div>'
        	html2 += '<div style="display:inline-block;">de</div>'
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += paginacion.length;
            html2 += '</div>'
            s = indiceAnterior+1;
            html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarSiguienteRele('+s+')"> > </a>';
            html2 += '<div style="display:inline-block;width:10px;"></div>'
            html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
        	' onClick="modalRegistro.validarUltimoRele('+(paginacion.length-1)+')"> >> </a>';
            
            html2 += '</div>'
            
            $('#idPaginacionReles').html(html2);
		},
		validarPrimerRele:function(){
			if(modalRegistro.validarCambioRele()){
				confirmer.open("¿Est&aacute; seguro que desea cambiar de Rel&eacute;?","modalRegistro.primerRele()");
			}else{
				modalRegistro.primerRele();
			}
		},
		primerRele:function(){
			$('#'+paginacion[0]).attr('style', 'display:inline;');
			$('#'+paginacion[$('#idIndiceReleActual').val()]).attr('style', 'display:none;');
			$('#idReleActual').val(paginacion[0]);
			$('#idIndiceReleActual').val(0);
			
			var html2 = '';
        	html2 += '<div style="margin-top: 20px;text-align: center;">'

        	html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: gray; "> << </a>';
        	html2 += '<div style="display:inline-block;width:10px;"></div>'
        	html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: gray; " > < </a>';  
        	
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += '<a  title="1">'+(1)+'</a>';
        	html2 += '</div>'
        	html2 += '<div style="display:inline-block;">de</div>'
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += paginacion.length;
            html2 += '</div>'
            
            html2 += '<a id="linkSiguiente" title="Siguiente" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarSiguienteRele('+1+')"> > </a>';
            html2 += '<div style="display:inline-block;width:10px;"></div>'
            html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarUltimoRele('+(paginacion.length-1)+')"> >> </a>';
            
            html2 += '</div>'
            
            $('#idPaginacionReles').html(html2);
		},
		validarUltimoRele:function(ultimoIndice){
			if(modalRegistro.validarCambioRele()){
				confirmer.open("¿Est&aacute; seguro que desea cambiar de Rel&eacute;?","modalRegistro.ultimoRele('"+ultimoIndice+"')");
			}else{
				modalRegistro.ultimoRele(ultimoIndice);
			}
		},
		ultimoRele:function(ultimoIndice){
			ultimoIndice=parseInt(ultimoIndice);
			$('#'+paginacion[ultimoIndice]).attr('style', 'display:inline;');
			$('#'+paginacion[$('#idIndiceReleActual').val()]).attr('style', 'display:none;');
			$('#idReleActual').val(paginacion[ultimoIndice]);
			$('#idIndiceReleActual').val(ultimoIndice);
			
			var html2 = '';
        	html2 += '<div style="margin-top: 20px;text-align: center;">'

    		html2 += '<a id="linkPrimero" title="Primero" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
            ' onClick="modalRegistro.validarPrimerRele()"> << </a>';
        	html2 += '<div style="display:inline-block;width:10px;"></div>'
    		html2 += '<a id="linkAnterior" title="Anterior" style="font-size:25px; font-weight: bold; color: black; cursor: pointer;"'+
        	' onClick="modalRegistro.validarAnteriorRele('+(ultimoIndice-1)+')"> < </a>';  
        	
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += '<a  title="1">'+paginacion.length+'</a>';
        	html2 += '</div>'
        	html2 += '<div style="display:inline-block;">de</div>'
        	html2 += '<div style="display:inline-block; margin: 15px;">'
        	html2 += paginacion.length;
            html2 += '</div>'
            
            html2 += '<a id="linkSiguiente"  title="Siguiente" style="font-size:25px; font-weight: bold; color: gray; " > > </a>';
            html2 += '<div style="display:inline-block;width:10px;"></div>'
        	html2 += '<a id="linkUltimo" title="Ultimo" style="font-size:25px; font-weight: bold; color: gray; "> >> </a>';
            
            html2 += '</div>'
            
            $('#idPaginacionReles').html(html2);
		},
		validarCambioRele:function(){
			var validacion;
			var idReleActual= ($('#idReleActual').val()).replace("idRele", "");
			if($('#idUmbralHz'+idReleActual).val()=='' && $('#idUmbralS'+idReleActual).val()=='' &&
			   $('#idReleDerivadaHz'+idReleActual).val()=='' && $('#idReleDerivadaHzs'+idReleActual).val()=='' &&
			   $('#idReleDerivadaS'+idReleActual).val()=='' && $('#idPotr'+idReleActual).val()=='' &&
			   $('#idDemandaMaxima'+idReleActual).val()=='' && $('#idDemandaMedia'+idReleActual).val()=='' &&
			   $('#idDemandaMinima'+idReleActual).val()=='' && $('#idHora'+idReleActual).val()=='' &&
			   $('#idDemandaMw'+idReleActual).val()=='' && $.trim($('#idObservacion'+idReleActual).val())==''){
				validacion=true;
			}else{
				validacion=false;
			}
    		
    		return validacion;
        },
		validarTerminarRegistroActa:function(){
    		confirmer.open("¿Ud. Está seguro de Terminar el registro del Acta?","modalRegistro.terminarRegistroActa('"+false+"')");
        },
		terminarRegistroActa:function(terminar) {
	    	$.ajax({
	            url:baseURL + "pages/actaInspeccion/actualizarFlagRegistroActa",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{
	            	idSupeCampRechCarga: $('#idSupeCampRechCargaRA').val(),
	            	idEmpresa: $('#idEmpresaRA').val(),
	            	anio: $('#idAnioRA').val(),
	            	estadoFlagRegistro: 'terminarRegistroActa',
	            	terminarRegistroReles: terminar
	            },
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                if(data.resultado=="SUCCESS" || data.resultado==0){
	                	$("#dialogRegistrarActa").dialog('close');
	                	mensajeGrowl("success", data.mensaje, "");
	                	console.info('ROL : : : ['+$('#rolSesion').val()+']');
	                	if($('#rolSesion').val()=='ESPECIALISTA'){
	                		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
	                	}else if($('#rolSesion').val()=='SUPERVISOR'){
	                		supervisorActaInspeccion.procesarGridEmpresasZona('EmpresasZonaSupervisor','');
	                	}
	                }else if(data.resultado=="RESTRICT_INF_ADICIONAL"){
	                	mensajeGrowl("warn", data.mensaje, "");
	                }else if(data.resultado=="RESTRICT"){
		            	confirmer.open(data.mensaje,"modalRegistro.terminarRegistroActa('"+true+"')");
	                }else{
	                	mensajeGrowl("error", data.mensaje, "");
	                }
	                
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
	    },
	    validarGuardarActaRegistro:function(idSupe){
	    	
	    	var campos;
	    	if(tabSeleccionado=='tabObservaciones'){
	    		$('#idAjusteReles').val($.trim($('#idAjusteReles').val()));
	    		$('#idDisparosReles').val($.trim($('#idDisparosReles').val()));
	    		$('#idPruebasReles').val($.trim($('#idPruebasReles').val()));
	    		$('#idEventosReles').val($.trim($('#idEventosReles').val()));
	    		$('#idObservacionesReles').val($.trim($('#idObservacionesReles').val()));
	    		$('#idNotasEmpresa').val($.trim($('#idNotasEmpresa').val()));
	    		
	    		if($('#idAjusteReles').val()!='' || $('#idDisparosReles').val()!='' || 
        		   $('#idPruebasReles').val()!='' || $('#idEventosReles').val()!='' ||
        		   $('#idObservacionesReles').val()!='' || $('#idNotasEmpresa').val()!=''){
        			confirmer.open("¿Ud. est&aacute; seguro de registrar las Observaciones?","modalRegistro.guardarActaObservaciones()");
        		}else{
        			mensajeGrowl("warn", "Si desea Guardar informaci&oacute;n en Observaciones, ingrese como m&iacute;nimo un dato.", "");
        		}
	    	}
	    	
        	if(tabSeleccionado=='tabInformacionInicio'){
        		$('#idSupervisorEmpresa').val($.trim($('#idSupervisorEmpresa').val()));
        		$('#idCargoSupervisor').val($.trim($('#idCargoSupervisor').val()));
        		$('#idSupervisorOsinergmin').val($.trim($('#idSupervisorOsinergmin').val()));
        		$('#idCargoSupervisorOsinergmin').val($.trim($('#idCargoSupervisorOsinergmin').val()));
        		
        		var validar = $('#formMantRegistroActa').validateAllForm("#divMensajeValidaFormRegistrarActa");
        		if(validar){
        			var mensaje="";
        			var validarFechas=false;
        			var resultadoFechaCreaIni =comparaFecha(($('#idFechaInicioRA').val()),($('#idFechaFinRA').val()));
        			if(resultadoFechaCreaIni==1){
        				mensaje="La Fecha de Fin debe ser mayor que la Fecha de Inicio, corregir.";
        				$('#idFechaInicioRA').addClass("error");
        				$('#idFechaFinRA').addClass("error");
        				
        			}else if(($('#idFechaInicioRA').val()==$('#idFechaFinRA').val()) && 
        					($('#slctHoraInicio').val()>$('#slctHoraFin').val())){
        				mensaje="La Hora de Fin debe ser mayor que la Hora de Inicio, corregir.";
        				$('#slctHoraInicio').addClass("error");
        				$('#slctHoraFin').addClass("error");
        				
        			}else if(($('#idFechaInicioRA').val()==$('#idFechaFinRA').val()) && 
        					($('#slctHoraInicio').val()==$('#slctHoraFin').val()) && 
        					($('#slctMinutoInicio').val()>$('#slctMinutoFin').val())){
        				mensaje="El Minuto de Fin debe ser mayor que el Minuto de Inicio, corregir.";
        				$('#slctMinutoInicio').addClass("error");
        				$('#slctMinutoFin').addClass("error");
        				
        			}else{
        				validarFechas=true;
        			}
        			
        			if(validarFechas){
        				$('#idFechaInicioRA').removeClass("error");
        				$('#idFechaFinRA').removeClass("error");
        				
        				$('#slctHoraInicio').removeClass("error");
        				$('#slctHoraInicio').removeClass("error");
        				
        				$('#slctMinutoInicio').removeClass("error");
        				$('#slctMinutoFin').removeClass("error");
        				confirmer.open("¿Ud. est&aacute; seguro de registrar la Informaci&oacute;n Inicial?","modalRegistro.guardarActaInformacionInicial()");
        			}else{
        				mensajeGrowl("warn", mensaje, "");
        			}
        			
        		}
        	}
        	
        	if(tabSeleccionado=='tabReles'){
        		
        		if($('#slctSubEstacionRA').val()!='' && $('#idReleActual').length>0){
        			var idRele= ($('#idReleActual').val()).replace("idRele", "");
        			$('#idObservacion'+idRele).val($.trim($('#idObservacion'+idRele).val()));
        			if($('#idUmbralHz'+idRele).val()!='' || $('#idUmbralS'+idRele).val()!='' || $('#idReleDerivadaHz'+idRele).val()!='' ||
        				$('#idReleDerivadaHzs'+idRele).val()!='' || $('#idDemandaMw'+idRele).val()!='' || $('#idReleDerivadaS'+idRele).val()!='' ||
        				$('#idPotr'+idRele).val()!='' || $('#idDemandaMaxima'+idRele).val()!='' || $('#idDemandaMedia'+idRele).val()!='' ||
        				$('#idDemandaMinima'+idRele).val()!='' || $('#idHora'+idRele).val()!='' || $('#idDemandaMw'+idRele).val()!='' ||
        				$('#idFiscalizado'+idRele).is(':checked') || $('#idExisteUmbral'+idRele).is(':checked') || 
        				$('#idExisteDerivada'+idRele).is(':checked') || $('#idAjusteUmbral'+idRele).is(':checked') || 
        				$('#idAjusteDerivada'+idRele).is(':checked') || $('#idOtroAjusteUmbral'+idRele).is(':checked') ||
        				$('#idOtroAjusteDerivada'+idRele).is(':checked') || $('#idProtocoloUmbral'+idRele).is(':checked') || 
        				$('#idProtocoloDerivada'+idRele).is(':checked') || $('#idObservacion'+idRele).val()!=''){
	        			var validarHoras=false;
	        			if($('#idHora'+idRele).val()!=''){
	        				var horas="";
	            			var minutos="";
	            			var signo="";
	            			if(($('#idHora'+idRele).val()).length==5){
	            				horas=$('#idHora'+idRele).val().substring(0,2);
	            	    		minutos=$('#idHora'+idRele).val().substring(3);
	            	    		signo=$('#idHora'+idRele).val().substring(2,3);
	            	    		if(horas.length==2 && minutos.length==2 && signo==':'){
	            	    			if(parseInt(horas)<=23 && parseInt(minutos)<=59){
	            	    				validarHoras=true;
	            	    			}
	            	    		}
	            			}
	        			}else{
	        				validarHoras=true;
	        			}
	        			
	        			if(validarHoras){
			        		$('#idObservacion'+idRele).val($.trim($('#idObservacion'+idRele).val()));
			        		$('#idHora'+idRele).removeClass("error");
			        		confirmer.open("¿Ud. est&aacute; seguro de registrar el R&eacute;le?","modalRegistro.guardarRele()");
	        			}else{
	        				$('#idHora'+idRele).addClass("error");
	        				mensajeGrowl("warn", "Horas(hh:mm) debe tener el formato correcto [01-23] : [01-59]", "");
	        			}
        			}else{
            			mensajeGrowl("warn", "Si desea Guardar un Rel&eacute;, debe ingresar al menos un dato.", "");
            		}
        		}else{
        			mensajeGrowl("warn", "Si desea Guardar un Rel&eacute;, elija una Sub Estaci&oacute;n &oacute; debe haber cargado un Rel&eacute; previamente.", "");
        		}
	        		
        	}
	    },
	    guardarActaObservaciones:function(){
	    	$.ajax({
	            url:baseURL + "pages/actaInspeccion/actualizarRegistroActa",
	            type:'post',
	            async:false,
	            data:{
	            	tabSeleccionado: 'tabObservaciones',
	            	idSupeCampRechCarga:  $('#idSupeCampRechCargaRA').val(),
	            	ajusteRele: $('#idAjusteReles').val(),
	            	habilitacionRele: $('#idDisparosReles').val(),
	            	protocoloRele: $('#idPruebasReles').val(),
	            	reporteRele: $('#idEventosReles').val(),
	            	otrasObservaciones: $('#idObservacionesReles').val(),
	            	notasEmpresa: $('#idNotasEmpresa').val()
	            },
	            beforeSend:loading.open,
	            success:function(data){
	            	loading.close();
	                if(data.resultado=="SUCCESS" || data.resultado==0){
	                	mensajeGrowl("success", data.mensaje, "");
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                }
	            },
	            error:errorAjax
	        });
	    },
	    guardarActaInformacionInicial:function(){
	    	$.ajax({
	            url:baseURL + "pages/actaInspeccion/actualizarRegistroActa",
	            type:'post',
	            async:false,
	            data:{
	            	tabSeleccionado: 'tabInformacionInicio',
	            	idSupeCampRechCarga:  $('#idSupeCampRechCargaRA').val(),
	            	fechaInicio: $('#idFechaInicioRA').val(),
	            	horaInicio: $('#slctHoraInicio').val(),
	            	minutoInicio: $('#slctMinutoInicio').val(),
	            	fechaFin: $('#idFechaFinRA').val(),
	            	horaFin: $('#slctHoraFin').val(),
	            	minutoFin: $('#slctMinutoFin').val(),
	            	
	            	nombreSupervisorEmpresa: $('#idSupervisorEmpresa').val(),
	            	cargoSupervisorEmpresa: $('#idCargoSupervisor').val(),
	            	nombreSupervisorOsinergmin: $('#idSupervisorOsinergmin').val(),
	            	cargoSupervisorOsinergmin: $('#idCargoSupervisorOsinergmin').val(),
	            	idUbigeo: $('#slctDepartamentoRA').val()+$('#slctProvinciaRA').val()+$('#slctDistritoRA').val(),
	            	idDepartamento: $('#slctDepartamentoRA').val(),
	            	idProvincia: $('#slctProvinciaRA').val(),
	            	idDistrito: $('#slctDistritoRA').val(),
	            },
	            beforeSend:loading.open,
	            success:function(data){
	            	loading.close();
	                if(data.resultado=="SUCCESS" || data.resultado==0){
	                	mensajeGrowl("success", data.mensaje, "");
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                }
	            },
	            error:errorAjax
	        });
	    },
	    guardarRele:function(){
	    	var idReleActual= ($('#idReleActual').val()).replace("idRele", "");
	    	var horas="";
	    	var minutos="";
	    	var fechaHora="";
	    	if($('#idHora'+idReleActual).val()!=''){
	    		horas=$('#idHora'+idReleActual).val().substring(0,2);
	    		minutos=$('#idHora'+idReleActual).val().substring(3);
	    		
	    		if(parseInt(horas)>12){
	    			horas=parseInt(horas)-12;
	    			if(horas<10)
	    				horas="0"+horas;
	    			fechaHora= $('#idFechaActual').val()+ " "+horas+":"+minutos+" PM";
	    		}else{
	    			fechaHora= $('#idFechaActual').val()+ " "+horas+":"+minutos+" AM";
	    		}
	    	}
	    	
	    	$.ajax({
	            url:baseURL + "pages/actaInspeccion/actualizarRele",
	            type:'post',
	            async:false,
	            data:{
	            	idRele: idReleActual,
	            	idDetaSupeCampRechCarga: $('#idDetaSupeActual'+idReleActual).val(),
	            	releUmbralHz: $('#idUmbralHz'+idReleActual).val(),
	            	releUmbralS: $('#idUmbralS'+idReleActual).val(),
	            	releDerivadaHz: $('#idReleDerivadaHz'+idReleActual).val(),
	            	releDerivadaHzs: $('#idReleDerivadaHzs'+idReleActual).val(),
	            	releDerivadaS: $('#idReleDerivadaS'+idReleActual).val(),
	            	potr: $('#idPotr'+idReleActual).val(),
	            	demandaMaxima: $('#idDemandaMaxima'+idReleActual).val(),
	            	demandaMedia: $('#idDemandaMedia'+idReleActual).val(),
	            	demandaMinima: $('#idDemandaMinima'+idReleActual).val(),
	            	hora: fechaHora,
	            	demandaMw: $('#idDemandaMw'+idReleActual).val(),
	            	
	            	flgFiscalizado: $('#idFiscalizado'+idReleActual).is(':checked')? "1" : "0",
	            	flgExisteReleUmbral: $('#idExisteUmbral'+idReleActual).is(':checked') ? "1" : "0",
	            	flgExisteReleDerivada: $('#idExisteDerivada'+idReleActual).is(':checked') ? "1" : "0",
	            	flgAjusteReleUmbral: $('#idAjusteUmbral'+idReleActual).is(':checked') ? "1" : "0",
	            	flgAjusteReleDerivada: $('#idAjusteDerivada'+idReleActual).is(':checked') ? "1" : "0",
	            	flgOtroAjusteUmbral: $('#idOtroAjusteUmbral'+idReleActual).is(':checked') ? "1" : "0",
	            	flgOtroAjusteDerivada: $('#idOtroAjusteDerivada'+idReleActual).is(':checked') ? "1" : "0",
	            	flgProtocoloUmbral: $('#idProtocoloUmbral'+idReleActual).is(':checked') ? "1" : "0",
	            	flgProtocoloDerivada: $('#idProtocoloDerivada'+idReleActual).is(':checked') ? "1" : "0",
	            	
	            	observaciones: $('#idObservacion'+idReleActual).val(),
	            },
	            beforeSend:loading.open,
	            success:function(data){
	            	loading.close();
	                if(data.resultado=="SUCCESS" || data.resultado==0){
	                	mensajeGrowl("success", data.mensaje, "");
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                }
	            },
	            error:errorAjax
	        });
	    },
};
$(function() {
	$("#idFechaInicioRA").datepicker({
        onClose: function (selectedDate) {
            $("#idFechaFinRA").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#idFechaFinRA").datepicker({
        onClose: function (selectedDate) {
            $("#idFechaInicioRA").datepicker("option", "maxDate", selectedDate);
        }
    });
    $('#slctDepartamentoRA').change(function() {
    	fill.clean("#slctProvinciaRA");
    	fill.clean("#slctDistritoRA");
    	if($('#slctDepartamentoRA').val()!=''){
    		modalRegistro.cargarProvincia("#slctProvinciaRA",$('#slctDepartamentoRA').val());
    		modalRegistro.cargarDistrito("#slctDistritoRA",$('#slctDepartamentoRA').val(), $('#slctProvinciaRA').val());
    	}
	});
	$('#slctProvinciaRA').change(function() {
		fill.clean("#slctDistritoRA");
		if($('#slctProvinciaRA').val()!=''){
			modalRegistro.cargarDistrito("#slctDistritoRA",$('#slctDepartamentoRA').val(), $('#slctProvinciaRA').val());
		}	
	});
	
	$('#slctSubEstacionRA').change(function() {
    	if($('#slctSubEstacionRA').val()!=''){
    		modalRegistro.cargarRelesXSubEstacion($('#slctSubEstacionRA').val(),$('#idEmpresaRA').val(),$('#idAnioRA').val(),$('#idSupeCampRechCargaRA').val());
    	}else{
    		$('#idListaReles').html(releVacio);
    		$('#idPaginacionReles').html('');
    	}
	});
});


$(function() {
	
	$('#tabsActaInspeccion').tabs({
	    activate: function (event, ui) {
	    	tabSeleccionado=ui.newPanel[0].id;
	    	$("#dialogRegistrarActa").dialog('option', 'position', 'top');
	    	if(tabSeleccionado=='tabInformacionInicio'){
	    		$('#tabInformacionInicio input,#tabInformacionInicio select').attr('validate', '[O]');
	    		
	    	} else if(tabSeleccionado=='tabObservaciones'){
	    		$('#tabInformacionInicio').find('input').removeAttr('validate');
	    		$('#tabInformacionInicio').find('select').removeAttr('validate');
	    	}
	    }
	});
	
	$('#btnGuardarActaRegistro').click(function() {
		if(tabSeleccionado==''){
			tabSeleccionado='tabInformacionInicio';
		}
		
		modalRegistro.validarGuardarActaRegistro();
	});
	
	$('#btnTerminarActaRegistro').click(function() {
		modalRegistro.validarTerminarRegistroActa();
		
	});
	
	$('#btnCerrarActaRegistro').click(function() {
        $('#dialogRegistrarActa').dialog('close');
    });
	
	$('#tabsActaInspeccion').tabs();
    confirmer.start();
    boton.closeDialog();
});

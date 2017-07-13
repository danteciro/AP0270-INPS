/**
* Resumen		
* Objeto		: busquedaExpediente.jsp
* Descripción		: busquedaExpediente
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  10/05/2016   |   Mario Dioses Fernandez     |     Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica)
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
*                |               |                              |
*                |               |                              |
*  
*/

//common/Expediente/busquedaExpediente.js
var coExpeBuEx={
		/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
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
		
		cargarPeticion:function(idSlctDest){
			$.ajax({
	            url:baseURL + "pages/bandeja/cargarPeticion",
	            type:'get',
	            dataType:'json',
	            async:false,
	            data:{},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.comboValorId(data,"idMaestroColumna","descripcion",idSlctDest,"-1");
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarEstadoOS:function(idSlctDest){
			$.ajax({
	            url:baseURL + "pages/bandeja/cargarEstadoProcesoOS",
	            type:'get',
	            dataType:'json',
	            async:false,
	            data:{},
	            beforeSend:loading.open,
	            success:function(data){
                    loading.close();     
                    for (i=0; i<data.length;i++) { 
                    	if(data[i].identificadorEstado==constant.identificadorEstado.osRegistro || data[i].identificadorEstado==constant.identificadorEstado.osOficiado || data[i].identificadorEstado==constant.identificadorEstado.osConcluido) {                    	
                    		data.splice(i,1);  
                    		i--;
                    	}
                    }   
                    fill.comboValorId(data,"identificadorEstado","nombreEstado",idSlctDest,"-1");
                },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
};
$(function() {
	$('#txtFechaInicioOS').datepicker({
	      showOn: "button",
	      buttonImage: ""+ baseURL+"images/cal.gif",
	      buttonImageOnly: true,
	      buttonText: "Select date"
	});
	
	$('#txtFechaFinalOS').datepicker({
	      showOn: "button",
	      buttonImage: ""+ baseURL+"images/cal.gif",
	      buttonImageOnly: true,
	      buttonText: "Select date"
	});
	
	$('#slctTipoEmprSupeBusqExpe').change(function(){
        fill.clean("#slctEmprSupeOSBusqExpe");
        if($('#slctTipoEmprSupeBusqExpe').find(':checked').attr('codigo')==constant.empresaSupervisora.personaNatural){
            /* OSINE_SFS-480 - RSIS 11 - Inicio */
            common.empresaSupervisora.cargarLocador("#slctEmprSupeOSBusqExpe", -1, -1, -1, -1, -1, -1); 
            /* OSINE_SFS-480 - RSIS 11 - Fin */
        }else if($('#slctTipoEmprSupeBusqExpe').find(':checked').attr('codigo')==constant.empresaSupervisora.personaJuridica){
            /* OSINE_SFS-480 - RSIS 11 - Inicio */
            common.empresaSupervisora.cargarSupervisoraEmpresa("#slctEmprSupeOSBusqExpe",-1, -1, -1, -1, -1, -1); 
            /* OSINE_SFS-480 - RSIS 11 - Fin */
        }
    });
	
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */    
    $('#slctDepartamento').change(function() {
    	fill.clean("#slctProvincia");
    	fill.clean("#slctDistrito");
    	if($('#slctDepartamento').val()!=''){
    		coExpeBuEx.cargarProvincia("#slctProvincia",$('#slctDepartamento').val());
        	coExpeBuEx.cargarDistrito("#slctDistrito",$('#slctDepartamento').val(), $('#slctProvincia').val());
    	}
	});
	$('#slctProvincia').change(function() {
		fill.clean("#slctDistrito");
		if($('#slctProvincia').val()!=''){
			coExpeBuEx.cargarDistrito("#slctDistrito",$('#slctDepartamento').val(), $('#slctProvincia').val());
		}	
	});
	$('#txtCadCodigoOsiBusqUnidad').alphanum(constant.valida.alphanum.codigoOsinergmin);
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
    permiteCopyPasteInput('#txtCodigoOsiBusqExpe,#txtNumeExpeBusqExpe,#txtNumeOSBusqExpe,#txtRazoSociBusqExpe,#txtNumeroRH');
});
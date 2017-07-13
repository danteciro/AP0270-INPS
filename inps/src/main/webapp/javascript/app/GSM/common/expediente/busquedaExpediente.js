/**
* Resumen		
* Objeto			: busquedaExpediente.jsp
* Descripción		: JavaScript para busqueda de Expedientes.
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-1344.
* Autor				: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*  
*/

//common/Expediente/busquedaExpediente.js
var coExpeBuExGSM={
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
		/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
};
$(function() {
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
    		coExpeBuExGSM.cargarProvincia("#slctProvincia",$('#slctDepartamento').val());
        	coExpeBuExGSM.cargarDistrito("#slctDistrito",$('#slctDepartamento').val(), $('#slctProvincia').val());
    	}
	});
	$('#slctProvincia').change(function() {
		fill.clean("#slctDistrito");
		if($('#slctProvincia').val()!=''){
			coExpeBuExGSM.cargarDistrito("#slctDistrito",$('#slctDepartamento').val(), $('#slctProvincia').val());
		}	
	});
	$('#txtCadCodigoOsiBusqUnidad').alphanum(constant.valida.alphanum.codigoOsinergmin);
    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
    permiteCopyPasteInput('#txtCodigoOsiBusqExpe,#txtNumeExpeBusqExpe,#txtNumeOSBusqExpe,#txtRazoSociBusqExpe,#txtNumeroRH');
});
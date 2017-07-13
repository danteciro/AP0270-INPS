/**
* Resumen		
* Objeto			: especialista.js
* Descripción		: JavaScript donde se centraliza las acciones para los formularios del Especialista, gerencia GSM.
* Fecha de Creación	: 24/10/2016.
* PR de Creación	: OSINE_SFS-1344
* Autor				: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
*/

$(function() {
    $('#tabsEspecialistaGSM').tabs();
    confirmer.start();
    especialista.cargarUnidadDivision();
});

var especialista={
    //Busqueda_Unidad_Division_lgarcia
    cargarUnidadDivision : function (){
	//Captura_IdPersonal
	$.ajax({
            url:baseURL + "pages/bandeja/findUnidadDivisionPersonal",
            type:'get',
            async:false,
            data:{  
                idPersonal:$('#idPersonalSesion').val()  
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $('#divDivision').html(data.division);
                $('#divUnidad').html(data.unidad);
            },
            error:errorAjax
	});
}
};
/**
* Resumen		
* Objeto		: comentarioIncumplimiento.js
* Descripción		: comentarioIncumplimiento
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: JPIRO
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/supervision/dsr/comentarioIncumplimiento/comentarioComplemento.js
var comComp={
    getDataComentarioComplemento:function(){
        $.ajax({
            url: baseURL + "pages/comentarioComplemento/obtenerDataComentarioComplemento",
            type: 'post',
            async: false,
            data: {
                idComentarioIncumplimiento:$('#idComentarioIncumplimientoCoCo').val(),
                flagBuscaComplDetSup:constant.estado.activo,
                idComentarioDetSupervision:$('#idComentarioDetSupervisionCoCo').val(),
                codigoOsinergmin:$('#codigoOsinergminCoCo').val()
            },
            beforeSend:loading.open,
            success: function(data) {
                loading.close();
                if (data.resultado == '0') {
                    if(data.complementos!=undefined && data.complementos!=null){
                        comComp.complemento.form(data.complementos);
                    }
                } else {
                    mensajeGrowl("error", data.mensaje);
                }
            },
            error: errorAjax
        });
    },
    complemento:{
        codTipo:{'numero':'NUM','parrafo':'PARR','seleccion':'SELEC','check':'CHK'},
        form:function(lista){
            if(lista.length>0){
                var contObligatorios=0;
                $.each(lista,function(key,val){
                    var html='<div>';
                        html+=comComp.complemento.lbl.armar(val);  
                        html+=comComp.complemento.ipt.armar(val);  
                    html+='</div>';
                    $('#frmComInc').append(html);
                    contObligatorios=(val.complemento.validacion!=null && val.complemento.validacion.indexOf("[O]")!=-1)?contObligatorios+1:contObligatorios;
                })
                if(contObligatorios>0){$('#txtObligComInc').css('display','');}
                comComp.complemento.init();
            }else{
                $('#frmComInc').append("Sin opciones de Comentario.");
            }
        },
        ipt:{
            armar:function(val){
                var html='<div class="ilb vam" style="margin:5px 0px;">';
                switch (val.complemento.codTipo.codigo){
                    case comComp.complemento.codTipo.numero :
                        html+=comComp.complemento.ipt.numero(val);
                        break;
                    case comComp.complemento.codTipo.seleccion:
                        html+=comComp.complemento.ipt.seleccion(val);
                        break;
                    case comComp.complemento.codTipo.parrafo:
                        html+=comComp.complemento.ipt.parrafo(val);
                        break;
                    case comComp.complemento.codTipo.check:
                        html+=comComp.complemento.ipt.check(val);
                        break;
                    default : html+="";
                }  
                html+='</div>';
                return html;
            },
            numero:function(val){
                var vComp=val.complemento,html='';
                try{
                    html+=' <input type="text" validate="'+vComp.validacion+'" idcomentariocomplemento="'+val.idComentarioComplemento+'" class="ipt-medium"';
                    html+=' maxlength="'+vComp.longitud+'" id="idComp'+vComp.idComplemento+'"';
                    html+=' codtipo="'+vComp.codTipo.codigo+'"';
                    var formato=vComp.formato;
                    var nroEnt=(formato.split('.')[0]!=undefined)?formato.split('.')[0].length:0;
                    var nroDec=(formato.split('.')[1]!=undefined)?formato.split('.')[1].length:0;
                    html+=' formato="'+vComp.formato+'" ent="'+nroEnt+'" dec="'+nroDec+'" ';
                    html+=' valmax="'+vComp.valorMaximo+'" valmin="'+vComp.valorMinimo+'" ';
                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                        html+=' value="'+val.complementoDetSup.valor[0].valorDesc+'" ';
                        html+=' idcomplementodetsupervision="'+val.complementoDetSup.idComplementoDetSupervision+'" ';
                    }
                    html+=' > ';
                }catch(err){console.log(err);}
                return html;
            },
            seleccion:function(val){
                var vComp=val.complemento,html='';
                try{
                    html+='<select validate="'+vComp.validacion+'" id="idComp'+vComp.idComplemento+'" idcomentariocomplemento="'+val.idComentarioComplemento+'" codtipo="'+vComp.codTipo.codigo+'" class="slct-medium"';
                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                        html+=' idcomplementodetsupervision="'+val.complementoDetSup.idComplementoDetSupervision+'" ';
                    }
                    html+=' >'
                    html+=' <option value="">--Seleccione--</option>';
                    $.each(vComp.opciones,function(k,v){
                        html+=' <option value="'+v.id+'"';
                        if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                            html+= (val.complementoDetSup.valor[0].valorId==v.id)?" selected ":" ";
                        }
                        html+=' >'+v.desc+'</option>';
                    })
                    html+='</select>'
                }catch(err){console.log(err);}
                return html;
            },
            parrafo:function(val){
                var vComp=val.complemento,html='';
                try{
                    html+=' <textarea validate="'+vComp.validacion+'" idcomentariocomplemento="'+val.idComentarioComplemento+'" class="ipt-medium-small-medium"';
                    html+=' maxlength="'+vComp.longitud+'" id="idComp'+vComp.idComplemento+'"';
                    html+=' codtipo="'+vComp.codTipo.codigo+'" style="width:375px;"';
                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                        html+=' idcomplementodetsupervision="'+val.complementoDetSup.idComplementoDetSupervision+'" ';
                    }
                    html+=' > ';
                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                        html+= val.complementoDetSup.valor[0].valorDesc;
                    }
                    html+=' </textarea>';
                }catch(err){console.log(err);}
                return html;
            },
            check:function(val){
                var vComp=val.complemento,html='';
                try{
                    html+='<div>';
                    html+='<div class="ilb tblChk head"><table><tr>';
                    html+=' <td class="chk tac">&nbsp;</td><td class="desc">'+vComp.etiqueta+'</td>';
                    html+='</tr></table></div>';
                    html+='</div>';
                    
                    html+='<div codtipo="'+vComp.codTipo.codigo+'" validate="'+vComp.validacion+'" id="idComp'+vComp.idComplemento+'" idcomentariocomplemento="'+val.idComentarioComplemento+'" ';
                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                        html+=' idcomplementodetsupervision="'+val.complementoDetSup.idComplementoDetSupervision+'" ';
                    }
                    html+=' >'
                    html+='<div class="ilb tblChk body"><table>';
                    $.each(vComp.opciones,function(k,v){
                        html+='<tr>'
                        html+=' <td class="chk tac"><div>';
                         html+='<input id="idCompOpc'+vComp.idComplemento+'-'+v.id+'" type="checkbox" value="'+v.id+'" desc="'+v.desc+'"';
                         if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
                            for(var i=0;i<val.complementoDetSup.valor.length;i++){
                                if(v.id==val.complementoDetSup.valor[i].valorId){
                                    html+=' checked ';
                                    break;
                                }                                
                            }
                         }
                         html+='><label for="idCompOpc'+vComp.idComplemento+'-'+v.id+'" class="checkbox"></label></div></td>';
                        html+=' <td class="desc">'+v.desc+'</td>';
                        html+='</tr>';
                    });                
                    html+='</table></div>';
                    html+='</div>';
//                    
//                    
//                    html+='<select validate="'+vComp.validacion+'" id="idComp'+vComp.idComplemento+'" idcomentariocomplemento="'+val.idComentarioComplemento+'" codtipo="'+vComp.codTipo.codigo+'" class="slct-medium"';
//                    if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
//                        html+=' idcomplementodetsupervision="'+val.complementoDetSup.idComplementoDetSupervision+'" ';
//                    }
//                    html+=' >'
//                    html+=' <option value="">--Seleccione--</option>';
//                    $.each(vComp.opciones,function(k,v){
//                        html+=' <option value="'+v.id+'"';
//                        if(val.flagComCompEnCompDetSup!=null && val.flagComCompEnCompDetSup==constant.estado.activo){
//                            html+= (val.complementoDetSup.valor[0].valorId==v.id)?" selected ":" ";
//                        }
//                        html+=' >'+v.desc+'</option>';
//                    })
//                    html+='</select>'
                }catch(err){console.log(err);}
                return html;
            },
            numeroValida:function(){
                $('#frmComInc').find('input[codtipo]').map(function(){
                    if($(this).attr('codtipo')==comComp.complemento.codTipo.numero){
                        $(this).numeric({
                            allowMinus          : false,
                            allowThouSep        : false,
                            allowDecSep         : ($(this).attr('dec')!='' && $(this).attr('dec')>0)?true:false,
                            maxDecimalPlaces    : ($(this).attr('dec')!='' && $(this).attr('dec')>0)?$(this).attr('dec'):NaN,
                            maxPreDecimalPlaces : ($(this).attr('ent')!='' && $(this).attr('ent')>0)?$(this).attr('ent'):NaN,
                            max                 : ($(this).attr('valmax')!='' && $(this).attr('valmax')>0)?$(this).attr('valmax'):NaN,
                            min                 : ($(this).attr('valmin')!='' && $(this).attr('valmin')>0)?$(this).attr('valmin'):NaN,
                        })
                    }
                })
            },
            checkValida:function(){
                var retorno=true;
                $('div[codtipo="'+comComp.complemento.codTipo.check+'"]').map(function(){
                    if($(this).attr('validate').indexOf('[O]')!=-1){
                        if($(this).find(':checked').length==0){
                            retorno=false;
                            mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir.");
                            return retorno;
                        }
                    }
                })
                return retorno;
            },
            numberValida:function(){
                var retorno=true;
                $('input[codtipo="'+comComp.complemento.codTipo.numero+'"]').map(function(){
                    if($(this).val().indexOf('.')==0){
                        $(this).val("0"+$(this).val());
                    }else if($(this).val().indexOf('.')==($(this).val().length-1)){
                        $(this).val($(this).val().substring(0,$(this).val().length-1));
                    }
                })
                return retorno;
            }
        },
        lbl:{
            armar:function(val){
                var vComp=val.complemento,html='';
                try{
                    var aster=(vComp.validacion!=null && vComp.validacion.indexOf("[O]")!=-1)?"(*)":"";
                    html+='<div class="ilb lbl-small vam"><label for="idComp'+vComp.idComplemento+'">'+vComp.etiqueta+aster+':</label></div>';
                }catch(err){console.log(err);}
                return html;
            }
        },
        init:function(){
            comComp.complemento.ipt.numeroValida();            
        }
    },
    guardar:{
        validaComplDetSupervision:function(){
            if ($('#frmComInc').validateAllForm("#divMensajeValidaFrmComInc")) {
                if(comComp.complemento.ipt.checkValida()){
                    if(comComp.complemento.ipt.numberValida()){
                        confirmer.open("¿Confirma que desea Guardar?","comComp.guardar.procesarComplDetSupervision()");
                    }
                }
            }
        },
        procesarComplDetSupervision:function(){
            var parameters = "_=p";
            parameters += "&comentarioIncumplimiento.idComentarioIncumplimiento="+$('#idComentarioIncumplimientoCoCo').val(); 
            parameters += "&detalleSupervision.idDetalleSupervision="+$('#idDetalleSupervisionCoCo').val(); 
            parameters += "&idEsceIncumplimiento="+$('#idEsceIncumplimientoCoCo').val(); 
            parameters += "&idComentarioDetSupervision="+$('#idComentarioDetSupervisionCoCo').val(); 
            
            var cont=0;
            $('#frmComInc').find('input[type=text],textarea').map(function(){
                if($.trim($(this).val())!=''){
                    parameters += ($(this).attr('idcomplementodetsupervision')!=undefined)?"&lstCompDetSup["+cont+"].idComplementoDetSupervision="+$(this).attr('idcomplementodetsupervision'):"";
                    parameters += "&lstCompDetSup["+cont+"].idComentarioComplemento="+$(this).attr('idcomentariocomplemento');
                    parameters += "&lstCompDetSup["+cont+"].valor[0].valorDesc="+$.trim($(this).val());
                    cont++;
                }
            });
            $('#frmComInc').find('select').map(function(){
                if($(this).val()!=''){
                    parameters += ($(this).attr('idcomplementodetsupervision')!=undefined)?"&lstCompDetSup["+cont+"].idComplementoDetSupervision="+$(this).attr('idcomplementodetsupervision'):"";
                    parameters += "&lstCompDetSup["+cont+"].idComentarioComplemento="+$(this).attr('idcomentariocomplemento');
                    parameters += "&lstCompDetSup["+cont+"].valor[0].valorId="+$(this).val();
                    parameters += "&lstCompDetSup["+cont+"].valor[0].valorDesc="+$(this).find(':selected').html();
                    cont++;
                }
            });
            $('div[codtipo="'+comComp.complemento.codTipo.check+'"]').map(function(){                
                if($(this).find(':checked').length>0){
                    parameters += ($(this).attr('idcomplementodetsupervision')!=undefined)?"&lstCompDetSup["+cont+"].idComplementoDetSupervision="+$(this).attr('idcomplementodetsupervision'):"";
                    parameters += "&lstCompDetSup["+cont+"].idComentarioComplemento="+$(this).attr('idcomentariocomplemento');
                    var contOpc=0;
                    $(this).find(':checked').map(function(k,v){
                        parameters += "&lstCompDetSup["+cont+"].valor["+contOpc+"].valorId="+$(v).val();
                        parameters += "&lstCompDetSup["+cont+"].valor["+contOpc+"].valorDesc="+$(v).attr('desc');
                        contOpc++;
                    })
                    cont++;
                }                
            });
            
            $.ajax({
                url: baseURL + "pages/comentarioIncumplimiento/guardarComplDetSupervision",
                type: 'post',
                async: false,
                data:parameters,
                beforeSend:loading.open,
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        mensajeGrowl("success", constant.confirm.save); 
                        $('#dialogComentarioComplemento').dialog('close');                        
                    } else {
                        mensajeGrowl("error", data.mensaje);
                    }
                },
                error: errorAjax
            });
        }
    },
    initEventos : function(){
    	$('#btnGuardarComentarioComplemento').click(comComp.guardar.validaComplDetSupervision);
    }
}

$(function() {
    comComp.initEventos();
    comComp.getDataComentarioComplemento();
    boton.closeDialog();
});

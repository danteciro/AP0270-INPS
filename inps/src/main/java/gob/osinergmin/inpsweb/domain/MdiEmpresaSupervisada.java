/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_EMPRESA_SUPERVISADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiEmpresaSupervisada.findAll", query = "SELECT m FROM MdiEmpresaSupervisada m"),
})
public class MdiEmpresaSupervisada extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EMPRESA_SUPERVISADA")
    private Long idEmpresaSupervisada;
    @Size(max = 200)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Size(max = 30)
    @Column(name = "NUMERO_IDENTIFICACION")
    private String numeroIdentificacion;
    @Size(max = 100)
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @Size(max = 50)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 50)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Size(max = 30)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "CIIU_PRINCIPAL")
    @ManyToOne
    private MdiMaestroColumna ciiuPrincipal;
    @Size(max = 2)
    @Column(name = "FLAG_BANDERA")
    private String flagBandera;
//    @Column(name = "TIPO_DOCUMENTO_IDENTIDAD")
//    private Long tipoDocumentoIdentidad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_DOCUMENTO_IDENTIDAD")
    private MdiMaestroColumna tipoDocumentoIdentidad;
    @Column(name = "NATURALEZA")
    private String naturaleza;
    @Size(max = 2)
    @Column(name = "ORIGEN_INFORMACION")
    private String origenInformacion;
    @Column(name = "CODIGO_EMPRESA")
    private Long codigoEmpresa;
    @Size(max = 11)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 100)
    @Column(name = "UTM")
    private String utm;
    @Size(max = 50)
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 18)
    @Column(name = "CELULAR")
    private String celular;
    @Size(max = 50)
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @OneToMany(mappedBy = "idEmpresaSupervisada", fetch = FetchType.LAZY)
    private List<MdiUnidadSupervisada> mdiUnidadSupervisadaList;
    @OneToMany(mappedBy = "idEmpresaSupervisada", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;

    public MdiEmpresaSupervisada() {
    }

    public MdiEmpresaSupervisada(Long idEmpresaSupervisada) {
        this.idEmpresaSupervisada = idEmpresaSupervisada;
    }
    
    public MdiEmpresaSupervisada( Long idEmpresaSupervisada , String razonSocial , String numeroIdentificacion,String nombreComercial, String telefono, String ruc ,String correoElectronico,Long idMaestroColumna,String codigo,String descripcion){
        this.idEmpresaSupervisada = idEmpresaSupervisada;
        this.razonSocial = razonSocial;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreComercial = nombreComercial;
        this.telefono = telefono;
        this.ruc = ruc;
        this.correoElectronico = correoElectronico;
        this.tipoDocumentoIdentidad = new  MdiMaestroColumna(idMaestroColumna, descripcion, codigo);
    }
    
    public MdiEmpresaSupervisada(Long idEmpresaSupervisada,String razonSocial,String ruc,String numeroIdentificacion,Long tipoDocumentoIdentidad,String descripcion) {
        this.idEmpresaSupervisada = idEmpresaSupervisada;
        this.razonSocial=razonSocial;
        this.ruc=ruc;
        this.numeroIdentificacion=numeroIdentificacion;
        MdiMaestroColumna tipoDocIdentidad= new MdiMaestroColumna();
        tipoDocIdentidad.setIdMaestroColumna(tipoDocumentoIdentidad);
        tipoDocIdentidad.setDescripcion(descripcion);
        this.tipoDocumentoIdentidad=tipoDocIdentidad;
    }

    public Long getIdEmpresaSupervisada() {
        return idEmpresaSupervisada;
    }

    public void setIdEmpresaSupervisada(Long idEmpresaSupervisada) {
        this.idEmpresaSupervisada = idEmpresaSupervisada;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTerminalActualizacion() {
        return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
        this.terminalActualizacion = terminalActualizacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getFlagBandera() {
        return flagBandera;
    }

    public void setFlagBandera(String flagBandera) {
        this.flagBandera = flagBandera;
    }
    
    

//    public Long getTipoDocumentoIdentidad() {
//        return tipoDocumentoIdentidad;
//    }
//
//    public void setTipoDocumentoIdentidad(Long tipoDocumentoIdentidad) {
//        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
//    }

    public MdiMaestroColumna getCiiuPrincipal() {
		return ciiuPrincipal;
	}

	public void setCiiuPrincipal(MdiMaestroColumna ciiuPrincipal) {
		this.ciiuPrincipal = ciiuPrincipal;
	}

	public MdiMaestroColumna getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(MdiMaestroColumna tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }
    

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getOrigenInformacion() {
        return origenInformacion;
    }

    public void setOrigenInformacion(String origenInformacion) {
        this.origenInformacion = origenInformacion;
    }

    public Long getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Long codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getUtm() {
        return utm;
    }

    public void setUtm(String utm) {
        this.utm = utm;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @XmlTransient
    @JsonIgnore
    public List<MdiUnidadSupervisada> getMdiUnidadSupervisadaList() {
        return mdiUnidadSupervisadaList;
    }

    public void setMdiUnidadSupervisadaList(List<MdiUnidadSupervisada> mdiUnidadSupervisadaList) {
        this.mdiUnidadSupervisadaList = mdiUnidadSupervisadaList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresaSupervisada != null ? idEmpresaSupervisada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiEmpresaSupervisada)) {
            return false;
        }
        MdiEmpresaSupervisada other = (MdiEmpresaSupervisada) object;
        if ((this.idEmpresaSupervisada == null && other.idEmpresaSupervisada != null) || (this.idEmpresaSupervisada != null && !this.idEmpresaSupervisada.equals(other.idEmpresaSupervisada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiEmpresaSupervisada[ idEmpresaSupervisada=" + idEmpresaSupervisada + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;
import javax.persistence.SequenceGenerator;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vubaldo
 */
@Entity
@Table(name = "NPS_SUPERVISION_RECH_CARGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsSupervisionRechCarga.findAll", query = "SELECT n FROM NpsSupervisionRechCarga n"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByIdSupervisionRechCarga", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.idSupervisionRechCarga = :idSupervisionRechCarga"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByAnio", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.anio = :anio"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByNombreOficioDoc", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.nombreOficioDoc = :nombreOficioDoc"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByNumeroExpediente", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.numeroExpediente = :numeroExpediente"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByUsuarioCreacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByFechaCreacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByTerminalCreacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByUsuarioActualizacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByFechaActualizacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByTerminalActualizacion", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByEstado", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.estado = :estado"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByEmpCodemp", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.empCodemp = :empCodemp"),
    @NamedQuery(name = "NpsSupervisionRechCarga.findByIdOficioDoc", query = "SELECT n FROM NpsSupervisionRechCarga n WHERE n.idOficioDoc = :idOficioDoc")})
public class NpsSupervisionRechCarga extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUPERVISION_RECH_CARGA")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_SUPERVISION_RECH_CARGA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idSupervisionRechCarga;
    @Column(name = "ANIO")
    @Temporal(TemporalType.DATE)
    private Date anio;
    @Column(name = "NOMBRE_OFICIO_DOC")
    private String nombreOficioDoc;
    @Column(name = "NUMERO_EXPEDIENTE")
    private String numeroExpediente;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "EMP_CODEMP")
    private String empCodemp;
    @Column(name = "ID_OFICIO_DOC")
    private Long idOficioDoc;

    public NpsSupervisionRechCarga() {
    }

    public NpsSupervisionRechCarga(Long idSupervisionRechCarga) {
        this.idSupervisionRechCarga = idSupervisionRechCarga;
    }

    public NpsSupervisionRechCarga(Long idSupervisionRechCarga, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idSupervisionRechCarga = idSupervisionRechCarga;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }

    public Long getIdSupervisionRechCarga() {
        return idSupervisionRechCarga;
    }

    public void setIdSupervisionRechCarga(Long idSupervisionRechCarga) {
        this.idSupervisionRechCarga = idSupervisionRechCarga;
    }

    public Date getAnio() {
        return anio;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

    public String getNombreOficioDoc() {
        return nombreOficioDoc;
    }

    public void setNombreOficioDoc(String nombreOficioDoc) {
        this.nombreOficioDoc = nombreOficioDoc;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

   
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmpCodemp() {
        return empCodemp;
    }

    public void setEmpCodemp(String empCodemp) {
        this.empCodemp = empCodemp;
    }

    public Long getIdOficioDoc() {
        return idOficioDoc;
    }

    public void setIdOficioDoc(Long idOficioDoc) {
        this.idOficioDoc = idOficioDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSupervisionRechCarga != null ? idSupervisionRechCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsSupervisionRechCarga)) {
            return false;
        }
        NpsSupervisionRechCarga other = (NpsSupervisionRechCarga) object;
        if ((this.idSupervisionRechCarga == null && other.idSupervisionRechCarga != null) || (this.idSupervisionRechCarga != null && !this.idSupervisionRechCarga.equals(other.idSupervisionRechCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.siged.com.NpsSupervisionRechCarga[ idSupervisionRechCarga=" + idSupervisionRechCarga + " ]";
    }
    
}

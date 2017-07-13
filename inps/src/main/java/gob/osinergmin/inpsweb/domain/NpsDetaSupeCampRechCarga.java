/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vubaldo
 */
@Entity
@Table(name = "NPS_DETA_SUPE_CAMP_RECH_CARGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findAll", query = "SELECT n FROM NpsDetaSupeCampRechCarga n"),
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findByIdDetaSupeCampRechCarga", query = "SELECT n FROM NpsDetaSupeCampRechCarga n WHERE n.idDetaSupeCampRechCarga = :idDetaSupeCampRechCarga and n.estado = '1' "),
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findByIdSupeCampRechCarga", query = "SELECT n FROM NpsDetaSupeCampRechCarga n WHERE n.idSupeCampRechCarga.idSupeCampRechCarga = :idSupeCampRechCarga and n.estado = :estado "),
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findByIdSupeCampRechCargaAll", query = "SELECT n FROM NpsDetaSupeCampRechCarga n WHERE n.idSupeCampRechCarga.idSupeCampRechCarga = :idSupeCampRechCarga "),
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findByIdRele", query = "SELECT n FROM NpsDetaSupeCampRechCarga n WHERE n.idRele IN (:idRele) and n.idSupeCampRechCarga.idSupeCampRechCarga = :idSupeCampRechCarga and n.estado = '1' "),
    @NamedQuery(name = "NpsDetaSupeCampRechCarga.findByEstado", query = "SELECT n FROM NpsDetaSupeCampRechCarga n WHERE n.estado = :estado")})
public class NpsDetaSupeCampRechCarga extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETA_SUPE_CAMP_RECH_CARGA")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_DET_SUP_CAM_RECH_CARGA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idDetaSupeCampRechCarga;
    @Column(name = "ID_RELE")
    private BigInteger idRele;
    @Column(name = "RELE_UMBRAL_HZ")
    private BigDecimal releUmbralHz;
    @Column(name = "RELE_UMBRAL_S")
    private BigDecimal releUmbralS;
    @Column(name = "RELE_DERIVADA_HZ")
    private BigDecimal releDerivadaHz;
    @Column(name = "RELE_DERIVADA_HZS")
    private BigDecimal releDerivadaHzs;
    @Column(name = "RELE_DERIVADA_S")
    private BigDecimal releDerivadaS;
    @Column(name = "DEMANDA_MAXIMA")
    private BigDecimal demandaMaxima;
    @Column(name = "DEMANDA_MEDIA")
    private BigDecimal demandaMedia;
    @Column(name = "DEMANDA_MINIMA")
    private BigDecimal demandaMinima;
    @Column(name = "DEMANDA_MW")
    private BigDecimal demandaMw;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @Column(name = "FLG_FISCALIZADO")
    private String flgFiscalizado;
    @Column(name = "FLG_EXISTE_RELE_UMBRAL")
    private String flgExisteReleUmbral;
    @Column(name = "FLG_EXISTE_RELE_DERIVADA")
    private String flgExisteReleDerivada;
    @Column(name = "FLG_AJUSTE_RELE_UMBRAL")
    private String flgAjusteReleUmbral;
    @Column(name = "FLG_AJUSTE_RELE_DERIVADA")
    private String flgAjusteReleDerivada;
    @Column(name = "FLG_OTRO_AJUSTE_UMBRAL")
    private String flgOtroAjusteUmbral;
    @Column(name = "FLG_OTRO_AJUSTE_DERIVADA")
    private String flgOtroAjusteDerivada;
    @Column(name = "FLG_PROTOCOLO_UMBRAL")
    private String flgProtocoloUmbral;
    @Column(name = "FLG_PROTOCOLO_DERIVADA")
    private String flgProtocoloDerivada;
    @Column(name = "POTR")
    private BigDecimal potr;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_SUPE_CAMP_RECH_CARGA", referencedColumnName = "ID_SUPE_CAMP_RECH_CARGA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NpsSupeCampRechCarga idSupeCampRechCarga;

    public NpsDetaSupeCampRechCarga() {
    }

    public NpsDetaSupeCampRechCarga(Long idDetaSupeCampRechCarga) {
        this.idDetaSupeCampRechCarga = idDetaSupeCampRechCarga;
    }

    public NpsDetaSupeCampRechCarga(Long idDetaSupeCampRechCarga, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idDetaSupeCampRechCarga = idDetaSupeCampRechCarga;
        this.estado = estado;
    }

    public Long getIdDetaSupeCampRechCarga() {
        return idDetaSupeCampRechCarga;
    }

    public void setIdDetaSupeCampRechCarga(Long idDetaSupeCampRechCarga) {
        this.idDetaSupeCampRechCarga = idDetaSupeCampRechCarga;
    }

    public BigInteger getIdRele() {
        return idRele;
    }

    public void setIdRele(BigInteger idRele) {
        this.idRele = idRele;
    }

    public BigDecimal getReleUmbralHz() {
        return releUmbralHz;
    }

    public void setReleUmbralHz(BigDecimal releUmbralHz) {
        this.releUmbralHz = releUmbralHz;
    }

    public BigDecimal getReleUmbralS() {
        return releUmbralS;
    }

    public void setReleUmbralS(BigDecimal releUmbralS) {
        this.releUmbralS = releUmbralS;
    }

    public BigDecimal getReleDerivadaHz() {
        return releDerivadaHz;
    }

    public void setReleDerivadaHz(BigDecimal releDerivadaHz) {
        this.releDerivadaHz = releDerivadaHz;
    }

    public BigDecimal getReleDerivadaHzs() {
        return releDerivadaHzs;
    }

    public void setReleDerivadaHzs(BigDecimal releDerivadaHzs) {
        this.releDerivadaHzs = releDerivadaHzs;
    }

    public BigDecimal getReleDerivadaS() {
        return releDerivadaS;
    }

    public void setReleDerivadaS(BigDecimal releDerivadaS) {
        this.releDerivadaS = releDerivadaS;
    }

    public BigDecimal getDemandaMaxima() {
        return demandaMaxima;
    }

    public void setDemandaMaxima(BigDecimal demandaMaxima) {
        this.demandaMaxima = demandaMaxima;
    }

    public BigDecimal getDemandaMedia() {
        return demandaMedia;
    }

    public void setDemandaMedia(BigDecimal demandaMedia) {
        this.demandaMedia = demandaMedia;
    }

    public BigDecimal getDemandaMinima() {
        return demandaMinima;
    }

    public void setDemandaMinima(BigDecimal demandaMinima) {
        this.demandaMinima = demandaMinima;
    }

    public BigDecimal getDemandaMw() {
        return demandaMw;
    }

    public void setDemandaMw(BigDecimal demandaMw) {
        this.demandaMw = demandaMw;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getFlgFiscalizado() {
        return flgFiscalizado;
    }

    public void setFlgFiscalizado(String flgFiscalizado) {
        this.flgFiscalizado = flgFiscalizado;
    }

    public String getFlgExisteReleUmbral() {
        return flgExisteReleUmbral;
    }

    public void setFlgExisteReleUmbral(String flgExisteReleUmbral) {
        this.flgExisteReleUmbral = flgExisteReleUmbral;
    }

    public String getFlgExisteReleDerivada() {
        return flgExisteReleDerivada;
    }

    public void setFlgExisteReleDerivada(String flgExisteReleDerivada) {
        this.flgExisteReleDerivada = flgExisteReleDerivada;
    }

    public String getFlgAjusteReleUmbral() {
        return flgAjusteReleUmbral;
    }

    public void setFlgAjusteReleUmbral(String flgAjusteReleUmbral) {
        this.flgAjusteReleUmbral = flgAjusteReleUmbral;
    }

    public String getFlgAjusteReleDerivada() {
        return flgAjusteReleDerivada;
    }

    public void setFlgAjusteReleDerivada(String flgAjusteReleDerivada) {
        this.flgAjusteReleDerivada = flgAjusteReleDerivada;
    }

    public String getFlgOtroAjusteUmbral() {
        return flgOtroAjusteUmbral;
    }

    public void setFlgOtroAjusteUmbral(String flgOtroAjusteUmbral) {
        this.flgOtroAjusteUmbral = flgOtroAjusteUmbral;
    }

    public String getFlgOtroAjusteDerivada() {
        return flgOtroAjusteDerivada;
    }

    public void setFlgOtroAjusteDerivada(String flgOtroAjusteDerivada) {
        this.flgOtroAjusteDerivada = flgOtroAjusteDerivada;
    }

    public String getFlgProtocoloUmbral() {
        return flgProtocoloUmbral;
    }

    public void setFlgProtocoloUmbral(String flgProtocoloUmbral) {
        this.flgProtocoloUmbral = flgProtocoloUmbral;
    }

    public String getFlgProtocoloDerivada() {
        return flgProtocoloDerivada;
    }

    public void setFlgProtocoloDerivada(String flgProtocoloDerivada) {
        this.flgProtocoloDerivada = flgProtocoloDerivada;
    }

    public BigDecimal getPotr() {
        return potr;
    }

    public void setPotr(BigDecimal potr) {
        this.potr = potr;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public NpsSupeCampRechCarga getIdSupeCampRechCarga() {
        return idSupeCampRechCarga;
    }

    public void setIdSupeCampRechCarga(NpsSupeCampRechCarga idSupeCampRechCarga) {
        this.idSupeCampRechCarga = idSupeCampRechCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetaSupeCampRechCarga != null ? idDetaSupeCampRechCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsDetaSupeCampRechCarga)) {
            return false;
        }
        NpsDetaSupeCampRechCarga other = (NpsDetaSupeCampRechCarga) object;
        if ((this.idDetaSupeCampRechCarga == null && other.idDetaSupeCampRechCarga != null) || (this.idDetaSupeCampRechCarga != null && !this.idDetaSupeCampRechCarga.equals(other.idDetaSupeCampRechCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.siged.com.NpsDetaSupeCampRechCarga[ idDetaSupeCampRechCarga=" + idDetaSupeCampRechCarga + " ]";
    }
    
}

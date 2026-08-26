package com.floresdelvalle.floristeria.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    public enum Estado { EN_CURSO, COMPLETADO, ENTREGADO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "Selecciona un cliente")
    private Cliente cliente;

    @NotBlank(message = "La dirección de entrega es obligatoria")
    @Size(max = 250, message = "La dirección no puede superar 250 caracteres")
    private String direccionEntrega;

    @NotBlank(message = "La información de contacto es obligatoria")
    @Size(max = 150, message = "El contacto no puede superar 150 caracteres")
    private String contacto;

    @NotBlank(message = "El tipo de arreglo es obligatorio")
    @Size(max = 120, message = "El tipo de arreglo no puede superar 120 caracteres")
    private String tipoArreglo;

    @NotBlank(message = "La ocasión es obligatoria")
    @Size(max = 100, message = "La ocasión no puede superar 100 caracteres")
    private String ocasion;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    private LocalDate fechaEntrega;

    @NotNull(message = "El presupuesto es obligatorio")
    @DecimalMin(value = "0.01", message = "El presupuesto debe ser mayor que cero")
    private BigDecimal presupuesto;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Estado estado = Estado.EN_CURSO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "pedido")
    private Entrega entrega;

    @OneToOne(mappedBy = "pedido")
    private Factura factura;

    @Transient
    private Long florId;

    @Transient
    private Integer cantidadFlor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTipoArreglo() { return tipoArreglo; }
    public void setTipoArreglo(String tipoArreglo) { this.tipoArreglo = tipoArreglo; }
    public String getOcasion() { return ocasion; }
    public void setOcasion(String ocasion) { this.ocasion = ocasion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public BigDecimal getPresupuesto() { return presupuesto; }
    public void setPresupuesto(BigDecimal presupuesto) { this.presupuesto = presupuesto; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
    public Entrega getEntrega() { return entrega; }
    public void setEntrega(Entrega entrega) { this.entrega = entrega; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public Long getFlorId() { return florId; }
    public void setFlorId(Long florId) { this.florId = florId; }
    public Integer getCantidadFlor() { return cantidadFlor; }
    public void setCantidadFlor(Integer cantidadFlor) { this.cantidadFlor = cantidadFlor; }
}

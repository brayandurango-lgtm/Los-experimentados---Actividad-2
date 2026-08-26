package com.floresdelvalle.floristeria.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {

    public enum Estado { PENDIENTE, PAGADA, ANULADA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "El número de factura es obligatorio")
    @Size(max = 40, message = "El número no puede superar 40 caracteres")
    private String numeroFactura;

    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", unique = true)
    @NotNull(message = "Selecciona un pedido")
    private Pedido pedido;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @NotNull(message = "Los costos adicionales son obligatorios")
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal costosAdicionales = BigDecimal.ZERO;

    @DecimalMin(value = "0.01")
    private BigDecimal total;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede estar en el futuro")
    private LocalDate fecha = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @NotNull
    private Estado estado = Estado.PENDIENTE;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getCostosAdicionales() { return costosAdicionales; }
    public void setCostosAdicionales(BigDecimal costosAdicionales) { this.costosAdicionales = costosAdicionales; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }
}

package com.inventario.backend.controller;

import com.inventario.backend.model.Producto;
import com.inventario.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository repository;

    // =========================
    // GUARDAR PRODUCTO
    // =========================
    @PostMapping("/guardar")
    public Producto guardar(@RequestBody Producto p) {
        return repository.save(p);
    }

    // =========================
    // LISTAR PRODUCTOS
    // =========================
    @GetMapping("/listar")
    public List<Producto> listar() {
        return repository.findAll();
    }

    // =========================
    // BUSCAR POR CODIGO
    // =========================
    @GetMapping("/buscar")
    public Producto buscar(@RequestParam String codigoProducto) {
        return repository.findByCodigoProducto(codigoProducto)
                .orElse(null);
    }

    // =========================
    // EDITAR PRODUCTO
    // =========================
    @PutMapping("/editar/{codigo}")
    public Producto editar(
            @PathVariable String codigo,
            @RequestBody Producto datos
    ) {
        Producto p = repository.findByCodigoProducto(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        p.setNombreProducto(datos.getNombreProducto());
        p.setStock(datos.getStock());
        p.setCodigoCaja(datos.getCodigoCaja());
        p.setUbicacion(datos.getUbicacion());

        return repository.save(p);
    }

    // =========================
    // ELIMINAR PRODUCTO (✔ FIX)
    // =========================
    @DeleteMapping("/eliminar/{codigo}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable String codigo) {

        System.out.println("🧨 INTENTANDO ELIMINAR: " + codigo);

        Producto producto = repository.findByCodigoProducto(codigo)
                .orElse(null);

        if (producto == null) {
            System.out.println("❌ PRODUCTO NO EXISTE");
            return ResponseEntity.notFound().build();
        }

        repository.delete(producto);

        System.out.println("✅ PRODUCTO ELIMINADO");
        return ResponseEntity.ok().build();
    }
}

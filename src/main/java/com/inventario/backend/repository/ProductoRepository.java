package com.inventario.backend.repository;

import com.inventario.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoProducto(String codigoProducto);

    void deleteByCodigoProducto(String codigoProducto);
}

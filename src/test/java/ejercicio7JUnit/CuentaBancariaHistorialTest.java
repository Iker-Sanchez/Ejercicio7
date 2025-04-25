package ejercicio7JUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CuentaBancariaHistorialTest {

	CuentaBancaria cuenta;

	@BeforeEach
	public void init() {
		cuenta = new CuentaBancaria(100);
	}

	/**
	 * Comprobar que el movimiento del ingreso inicial se guarda bien
	 */
	@Test
	void historial_IngresoInicial() {
		List<Movimiento> historial = cuenta.getHistorial();
		assertEquals(1, historial.size());
		Movimiento m = historial.get(0);
		assertEquals(Movimiento.Tipo.DEPOSITO, m.getTipo());
		assertEquals(100, m.getCantidad());
	}

	/**
	 * Comprobar que se añade correctamente un deposito a una cuenta que
	 * ya existe con un depósito inicial
	 */
	@Test
	void historial_AddDepositoCorrecto() {
		cuenta.depositar(50);
		List<Movimiento> historial = cuenta.getHistorial();
		assertEquals(2, historial.size());
		Movimiento m = historial.get(1);
		assertEquals(Movimiento.Tipo.DEPOSITO, m.getTipo());
		assertEquals(150, m.getCantidad()); // Se guarda el saldo actual según tu implementación
		assertEquals(150, cuenta.getSaldo());
	}

	/**
	 * Comprobar que no se añade un movimiento si el deposito es negativo
	 */
	@Test
	void historial_AddDepositoIncorrecto() {
		boolean resultado = cuenta.depositar(-30);
		assertFalse(resultado);
		assertEquals(1, cuenta.getHistorial().size());
		assertEquals(100, cuenta.getSaldo());
	}

	/**
	 * Comprobar que se puede hacer un retiro de una cantidad correcta y
	 * se añade a los movimientos
	 */
	@Test
	void historial_AddRetiroCorrecto() {
		cuenta.retirar(40);
		List<Movimiento> historial = cuenta.getHistorial();
		assertEquals(2, historial.size());
		Movimiento m = historial.get(1);
		assertEquals(Movimiento.Tipo.RETIRO, m.getTipo());
		assertEquals(60, m.getCantidad()); // Se guarda el saldo actual según tu implementación
		assertEquals(60, cuenta.getSaldo());
	}

	/**
	 * Comprobar que no se puede hacer un retiro de una cantidad negativa/incorrecta y
	 * no se añade a los movimientos
	 */
	@Test
	void historial_AddRetiroIncorrecto() {
		boolean resultado = cuenta.retirar(-50);
		assertFalse(resultado);
		assertEquals(1, cuenta.getHistorial().size());
		assertEquals(100, cuenta.getSaldo());
	}
}

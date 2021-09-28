package com.metrics;

import javax.security.auth.login.AccountException;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class MetricApplication {

	public static void main(String[] args) {

		/*Declaracion global de meter. Esto evita declarar metricas en otros lugares
		 * 
		 */
		food();
		
		/*
		 * Micrometer provee un CompositeMeterRegistry donde puedes agregar múltiples
		 * registros, permitiendo publicar métricas a más de un sistema de monitoreo de
		 * forma simultanea.
		 * 
		 */
		CompositeMeterRegistry compositeMeterRegistry = new CompositeMeterRegistry();
		
		Counter counterComposite = compositeMeterRegistry.counter("numero.empleados", "oficina", "Christian Ferndz");

		
		
		MeterRegistry registryComposite = new SimpleMeterRegistry();

		compositeMeterRegistry.add(registryComposite);
		counterComposite.increment();

		counterComposite.increment(200);
		System.out.printf("counterComposite -> Numero de empleados %f", counterComposite.count());


		/*
		 * Una Medida es la interfaz para recolectar un conjunto de datos sobre tu
		 * aplicación. En Micrometer una medida se crea y mantiene en un MeterRegistry.
		 * Cada sistema de monitoreo tiene su propia implementación del MeterRegistry.
		 */
		MeterRegistry registry = new SimpleMeterRegistry();
		Counter counter = registry.counter("numero.empleados", "oficina", "Christian Ferndz");

		counter.increment();

		counter.increment(200);

		System.out.printf("Numero de empleados %f", counter.count());
	}

	/**
	 * Ejemplo correspondiente a la metrica global 
	 */
	private static void food() {
		CompositeMeterRegistry meterRegistry = Metrics.globalRegistry;
		Counter counter = meterRegistry.counter("numero.empleados", "oficina", "Christian Ferndz");
		counter.increment();

		counter.increment(400);

		System.out.printf("Numero de empleados %f", counter.count());
		
	}
}

# CONTEXT - Flores del Valle

## Proyecto
Flores del Valle es un PMV académico para la gestión de una floristería. El proyecto centraliza inventario, clientes, pedidos, entregas, facturación y reportes en una aplicación web basada en Spring Boot + Thymeleaf.

## Estado actual
- Backend en Java con Spring Boot y Maven.
- Estructura por capas MVC: controller, service, repository, model.
- Base de datos PostgreSQL con variables de entorno.
- Se contemplan módulos de flores, clientes, pedidos, entregas, facturación y reportes.
- El repositorio ya tiene una base funcional, pero hay que confirmar qué parte del negocio debe priorizarse y qué falta por cerrar para cumplir el objetivo del curso.

## Preguntas clave de la entrevista
1. ¿El objetivo principal es dejar la aplicación funcional y completa para el curso, o priorizar un conjunto mínimo de módulos críticos?
2. ¿Qué módulos consideran imprescindibles para la entrega final: inventario, pedidos, entregas, facturación, reportes o todos?
3. ¿Hay errores o comportamientos que ya se hayan detectado y que deban resolverse primero?
4. ¿La aplicación debe ser una versión local + funcional, o se busca una experiencia más pulida y con validaciones mejoradas?
5. ¿Hay requisitos del profesor o de la actividad que debamos seguir exactamente y no asumir por nuestra cuenta?

## Suposiciones iniciales
- La app debe manejar un flujo completo de una floristería desde inventario hasta facturación.
- Se prioriza claridad de negocio, buen modelado y funcionalidad end-to-end.
- El proyecto se corrige y mejora iterando por módulos, no por cambios improvisados.

## Siguiente paso
Ajustar prioridades y evaluar el estado real de cada módulo para convertirlo en un plan de trabajo con tickets y entregas pequeñas.

## Decisión de alcance asumida
Dado el tipo de proyecto académico y la estructura presente, el enfoque más realista es priorizar un flujo funcional end-to-end de la floristería:

1. Inventario
2. Clientes
3. Pedidos
4. Entregas
5. Facturación
6. Reportes

Esto mantiene la lógica del negocio coherente y permite demostrar la aplicación funcionando como un sistema completo, más que dejar módulos aislados sin conexión.

## Hipótesis de trabajo
- La base del proyecto ya existe y solo requiere completar y corregir flujos.
- El valor más importante para la entrega es la operación real del negocio, no la perfección visual.
- Las validaciones, persistencia y navegación deben funcionar de forma consistente.
- El trabajo debe dividirse en subtareas pequeñas para evitar cambios invasivos.

## Siguientes tickets recomendados
- Revisar estado actual de entidades y relaciones en el backend.
- Validar que la creación y listado de flores y clientes funcionen correctamente.
- Completar flujo de pedidos con datos coherentes y validaciones.
- Implementar seguimiento de entregas y su asociación con conductores y pedidos.
- Generar facturas y pagos desde pedidos terminados.
- Revisar reportes básicos para mostrar información útil del negocio.

## Resultado esperado
Una aplicación funcional que demuestre la gestión completa de una floristería y que pueda ser evaluada por módulos y por flujo de negocio, con una base clara para futuras mejoras.

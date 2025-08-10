import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorDeTareas gestor = new GestorDeTareas();
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("                  FLOW");
            System.out.println("========================================");
            System.out.println(" 1. Agregar tarea");
            System.out.println(" 2. Ver tareas priorizadas");
            System.out.println(" 3. Marcar tarea como completada");
            System.out.println(" 4. Postergar tarea");
            System.out.println(" 5. Ver recomendaciones de productividad");
            System.out.println(" 6. Pausa y salud emocional");
            System.out.println(" 7. Escribir mini-journal");
            System.out.println(" 8. Ver mini-journal");
            System.out.println(" 9. Salir");
            System.out.println("----------------------------------------");
            System.out.print("Elige una opción: ");

            while (!sc.hasNextInt()) {
                System.out.print("Entrada inválida. Ingresa un número: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Agregar tarea ---");
                    System.out.print("Nombre de la tarea: ");
                    String nombre = sc.nextLine();
                    System.out.print("¿Es urgente? (s/n): ");
                    boolean urgente = leerSiNo(sc);
                    System.out.print("¿Es importante? (s/n): ");
                    boolean importante = leerSiNo(sc);
                    gestor.agregarTarea(nombre, urgente, importante);
                    System.out.println("Tarea agregada correctamente.");
                    break;

                case 2:
                    System.out.println("\n--- Tareas priorizadas ---");
                    List<Tarea> lista = gestor.obtenerTareasPrioritizadas();
                    if (lista.isEmpty()) {
                        System.out.println("No hay tareas registradas.");
                    } else {
                        for (int i = 0; i < lista.size(); i++) {
                            System.out.println("[" + i + "] " + lista.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- Marcar tarea como completada ---");
                    System.out.print("Índice de la tarea: ");
                    int iComp = leerIndice(sc);
                    gestor.marcarCompletada(iComp);
                    System.out.println("Operación realizada.");
                    break;

                case 4:
                    System.out.println("\n--- Postergar tarea ---");
                    System.out.print("Índice de la tarea: ");
                    int iPost = leerIndice(sc);
                    gestor.postergarTarea(iPost);
                    System.out.println("Tarea postergada.");
                    break;

                case 5:
                    System.out.println("\n--- Recomendaciones de productividad ---");
                    List<String> recs = gestor.generarRecomendaciones();
                    if (recs.isEmpty()) {
                        System.out.println("No hay recomendaciones por ahora.");
                    } else {
                        for (String r : recs) {
                            System.out.println(" - " + r);
                        }
                    }
                    break;

                case 6:
                    manejarSaludEmocional(sc, gestor);
                    break;

                case 7:
                    System.out.println("\n--- Mini-journal ---");
                    System.out.print("Escribe tu entrada (máx 2 líneas): ");
                    String entry = sc.nextLine();
                    gestor.agregarJournalEntry(entry);
                    String analisis = gestor.analizarJournalEntry(entry);
                    if (analisis.equals("PARECE_ESTRESADO")) {
                        System.out.println("Se detecta posible estrés. Sugerencias:");
                        System.out.println(" - Respiración profunda (4-7-8)");
                        System.out.println(" - Descanso breve de 5 minutos");
                        System.out.println(" - Dividir tareas grandes en pasos pequeños");
                    } else if (analisis.equals("ESTADO_POSITIVO")) {
                        System.out.println("¡Buen ánimo! Me alegra saber que estás bien. Mantén tus hábitos.");
                    } else {
                        System.out.println("Entrada guardada en mini-journal.");
                    }
                    break;

                case 8:
                    System.out.println("\n--- Mini-journal registrado ---");
                    List<String> entradas = gestor.getJournalEntries();
                    if (entradas.isEmpty()) {
                        System.out.println("No hay entradas registradas.");
                    } else {
                        for (int i = 0; i < entradas.size(); i++) {
                            System.out.println((i + 1) + ". " + entradas.get(i));
                        }
                    }
                    break;

                case 9:
                    System.out.println("\nGracias por usar FLOW");
                    System.out.println("Recuerda descansar y cuidar tu salud.");
                    System.out.println("    (\\//)");
                    System.out.println("   (o_o )  cuídate");
                    System.out.println("    /   \\");
                    System.out.println("    |   \\ ( ");
                    System.out.println("    ||||  ||");
                    System.out.println("    =='=='=='");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 9);

        sc.close();
    }

    private static boolean leerSiNo(Scanner sc) {
        String resp = sc.nextLine().trim().toLowerCase();
        while (!(resp.equals("s") || resp.equals("n") || resp.equals("si") || resp.equals("no"))) {
            System.out.print("Ingresa 's' o 'n': ");
            resp = sc.nextLine().trim().toLowerCase();
        }
        return resp.startsWith("s");
    }

    private static int leerIndice(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ingresa un número válido: ");
            sc.next();
        }
        int idx = sc.nextInt();
        sc.nextLine();
        return idx;
    }

    private static void manejarSaludEmocional(Scanner sc, GestorDeTareas gestor) {
        String nivel = gestor.detectarSobrecarga();
        System.out.println("\n--- Pausa y Salud Emocional ---");
        switch (nivel) {
            case "ALTA_CARGA":
                System.out.println("Se detecta alta carga de trabajo. Recomendaciones:");
                System.out.println(" - Pausa de 5-10 minutos");
                System.out.println(" - Priorizar una tarea inmediata");
                gestor.ejerciciosRelajacion().forEach(r -> System.out.println(" - " + r));
                break;
            case "CARGA_MEDIA":
                System.out.println("Carga media. Sugerencias:");
                gestor.ejerciciosRelajacion().stream().limit(2).forEach(r -> System.out.println(" - " + r));
                break;
            default:
                System.out.println("Carga baja. Mantén pausas cortas y frecuentes.");
                gestor.ejerciciosRelajacion().stream().limit(1).forEach(r -> System.out.println(" - " + r));
        }

        System.out.print("\n¿Realizar ejercicio de respiración guiado? (s/n): ");
        boolean hacer = leerSiNo(sc);
        if (hacer) {
            System.out.println("\nIniciando respiración 4-7-8 (3 repeticiones):");
            for (int i = 3; i >= 1; i--) {
                System.out.println("Inhala 4 segundos...");
                pauseSeconds(4);
                System.out.println("Mantén 7 segundos...");
                pauseSeconds(7);
                System.out.println("Exhala 8 segundos...");
                pauseSeconds(8);
                System.out.println("Repeticiones restantes: " + (i - 1));
            }
            System.out.print("¿Cómo te sientes ahora?: ");
            String respuesta = sc.nextLine();
            gestor.agregarJournalEntry("Después de respiración: " + respuesta);
            System.out.println("Entrada guardada en mini-journal.");
        }
    }

    private static void pauseSeconds(int secs) {
        try {
            Thread.sleep(secs * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorDeTareas {
    private List<Tarea> tareas;
    private List<String> journalEntries;

    public GestorDeTareas() {
        tareas = new ArrayList<>();
        journalEntries = new ArrayList<>();
    }

    public void agregarTarea(String nombre, boolean urgente, boolean importante) {
        tareas.add(new Tarea(nombre, urgente, importante));
    }

    public List<Tarea> obtenerTareas() {
        return tareas;
    }

    public List<Tarea> obtenerTareasPrioritizadas() {
        return tareas.stream()
                .sorted(Comparator.comparing(Tarea::isCompletada))
                .sorted(Comparator.comparingInt(this::rankPrioridad))
                .collect(Collectors.toList());
    }

    private int rankPrioridad(Tarea t) {
        String p = t.getPrioridad();
        switch (p) {
            case "Hacer ahora": return 1;
            case "Planificar": return 2;
            case "Delegar": return 3;
            default: return 4;
        }
    }

    public void marcarCompletada(int index) {
        if (index >= 0 && index < tareas.size()) {
            tareas.get(index).marcarCompletada();
        }
    }

    public void postergarTarea(int index) {
        if (index >= 0 && index < tareas.size()) {
            tareas.get(index).postergar();
        }
    }

    public List<String> generarRecomendaciones() {
        List<String> recomendaciones = new ArrayList<>();
        for (Tarea t : tareas) {
            if (!t.isCompletada() && t.getVecesPostergada() >= 2 && t.getPrioridad().equals("Hacer ahora")) {
                recomendaciones.add("Estás procrastinando una tarea crítica: \"" + t.getNombre()
                        + "\". Sugerencia: divide la tarea en subtareas y usa Pomodoro (25/5).");
            }
        }
        long urgentesNoCompletadas = tareas.stream()
                .filter(x -> x.getPrioridad().equals("Hacer ahora") && !x.isCompletada())
                .count();
        if (urgentesNoCompletadas >= 5) {
            recomendaciones.add("Parece que tienes muchas tareas urgentes. Prioriza y delega lo que puedas.");
        }
        return recomendaciones;
    }

    public String detectarSobrecarga() {
        long urgentesNoCompletadas = tareas.stream()
                .filter(x -> x.getPrioridad().equals("Hacer ahora") && !x.isCompletada())
                .count();

        long tareasPostergadas = tareas.stream()
                .filter(x -> x.getVecesPostergada() >= 2)
                .count();

        if (urgentesNoCompletadas >= 3 || tareasPostergadas >= 3) {
            return "ALTA_CARGA";
        } else if (urgentesNoCompletadas >= 1 || tareasPostergadas >= 1) {
            return "CARGA_MEDIA";
        } else {
            return "CARGA_BAJA";
        }
    }

    public List<String> ejerciciosRelajacion() {
        List<String> ejercicios = new ArrayList<>();
        ejercicios.add("Respiración 4-7-8: Inhala 4s, mantén 7s, exhala 8s. Repetir 3 veces.");
        ejercicios.add("Micro-descanso: levantarse, estirar brazos y cuello durante 1-2 minutos.");
        ejercicios.add("Ejercicio de enfoque: mirar por la ventana 30 segundos y respirar conscientemente.");
        ejercicios.add("Regla 2 minutos: si una tarea toma <=2 minutos, hazla ahora.");
        return ejercicios;
    }

    public void agregarJournalEntry(String texto) {
        journalEntries.add(texto);
    }

    public String analizarJournalEntry(String texto) {
        String lower = texto.toLowerCase();
        String[] palabrasEstres = {"estres", "agot", "ansiedad", "agobi", "frustr", "cansad", "sobreca"};
        for (String p : palabrasEstres) {
            if (lower.contains(p)) {
                return "PARECE_ESTRESADO";
            }
        }
        String[] palabrasPositivas = {"bien", "feliz", "content", "satisfech", "relajad"};
        for (String p : palabrasPositivas) {
            if (lower.contains(p)) {
                return "ESTADO_POSITIVO";
            }
        }
        return "NEUTRO";
    }

    public List<String> getJournalEntries() {
        return journalEntries;
    }
}

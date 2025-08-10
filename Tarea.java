import java.time.LocalDate;

public class Tarea {
    private String nombre;
    private boolean urgente;
    private boolean importante;
    private boolean completada;
    private int vecesPostergada;
    private LocalDate fechaCreacion;

    public Tarea(String nombre, boolean urgente, boolean importante) {
        this.nombre = nombre;
        this.urgente = urgente;
        this.importante = importante;
        this.completada = false;
        this.vecesPostergada = 0;
        this.fechaCreacion = LocalDate.now();
    }

    public String getPrioridad() {
        if (urgente && importante) return "Hacer ahora";
        if (!urgente && importante) return "Planificar";
        if (urgente && !importante) return "Delegar";
        return "Eliminar o hacer después";
    }

    public void marcarCompletada() {
        this.completada = true;
    }

    public void postergar() {
        this.vecesPostergada++;
    }

    public String getNombre() { return nombre; }
    public boolean isUrgente() { return urgente; }
    public boolean isImportante() { return importante; }
    public boolean isCompletada() { return completada; }
    public int getVecesPostergada() { return vecesPostergada; }
    public String getFechaCreacion() { return fechaCreacion.toString(); }

    @Override
    public String toString() {
        return String.format("%s | %s | Completada: %s | Postergada: %d",
                nombre, getPrioridad(), completada ? "Sí" : "No", vecesPostergada);
    }
}
